/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.psa.Business.NeuralNetwork;

import com.psa.Business.FileParser.Attribute;
import com.psa.Business.FileParser.Node;
import com.psa.Business.FileParser.Parser;
import com.psa.Business.FileParser.ParserTools;
import com.psa.Business.TrainingUtils.TrainingUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Arrays;

/**
 *
 * @author rahulzore
 */
public class NeuralNetwork {

    private Layer layer;

    public NeuralNetwork(int... NETWORK_LAYER_SIZES) {

        this.layer = new Layer(NETWORK_LAYER_SIZES);
        
    }

    public double[] calculateResult(double... input) {
        if(input.length != this.layer.getINPUT_SIZE()) return null;
        this.layer.getPerceptron().getOutput()[0] = input;
        for(int layer = 1; layer < this.layer.getNETWORK_SIZE(); layer ++) {
            for(int neuron = 0; neuron < this.layer.getLayer()[layer]; neuron ++) {

                double sum = this.layer.getPerceptron().getBias()[layer][neuron];
                for(int prevNeuron = 0; prevNeuron < this.layer.getLayer()[layer-1]; prevNeuron ++) {
                    sum += this.layer.getPerceptron().getOutput()[layer-1][prevNeuron] * this.layer.getPerceptron().getWeights()[layer][neuron][prevNeuron];
                }
                this.layer.getPerceptron().getOutput()[layer][neuron] = activationFunction(sum);
                this.layer.getPerceptron().getOutput_derivative()[layer][neuron] = this.layer.getPerceptron().getOutput()[layer][neuron] * (1 - this.layer.getPerceptron().getOutput()[layer][neuron]);
            }
        }
        return this.layer.getPerceptron().getOutput()[this.layer.getNETWORK_SIZE()-1];
    }

    public void train(TrainingUtils set, int loops, int batch_size) {
        if(set.INPUT_SIZE != this.layer.getINPUT_SIZE() || set.OUTPUT_SIZE != this.layer.getOUTPUT_SIZE()) return;
        for(int i = 0; i < loops; i++) {
            TrainingUtils batch = set.extractBatch(batch_size);
            for(int b = 0; b < batch_size; b++) {
                this.trainNetwork(batch.getInput(b), batch.getOutput(b), 0.3);
            }
            System.out.println(MSE(batch));
        }
    }

    public double MSE(double[] input, double[] target) {
        if(input.length != this.layer.getINPUT_SIZE() || target.length != this.layer.getOUTPUT_SIZE()) return 0;
        calculateResult(input);
        double v = 0;
        for(int i = 0; i < target.length; i++) {
            v += (target[i] - this.layer.getPerceptron().getOutput()[this.layer.getNETWORK_SIZE()-1][i]) * (target[i] - this.layer.getPerceptron().getOutput()[this.layer.getNETWORK_SIZE()-1][i]);
        }
        return v / (2d * target.length);
    }

    public double MSE(TrainingUtils set) {
        double v = 0;
        for(int i = 0; i< set.size(); i++) {
            v += MSE(set.getInput(i), set.getOutput(i));
        }
        return v / set.size();
    }

    public void trainNetwork(double[] input, double[] target, double eta) {
        if(input.length != this.layer.getINPUT_SIZE() || target.length != this.layer.getOUTPUT_SIZE()) return;
        calculateResult(input);
        backpropError(target);
        adjustWeights(eta);
    }

    public void backpropError(double[] target) {
        for(int neuron = 0; neuron < this.layer.getLayer()[this.layer.getNETWORK_SIZE()-1]; neuron ++) {
            this.layer.getPerceptron().getError_signal()[this.layer.getNETWORK_SIZE()-1][neuron] = (this.layer.getPerceptron().getOutput()[this.layer.getNETWORK_SIZE()-1][neuron] - target[neuron])
                    * this.layer.getPerceptron().getOutput_derivative()[this.layer.getNETWORK_SIZE()-1][neuron];
        }
        for(int layer = this.layer.getNETWORK_SIZE()-2; layer > 0; layer --) {
            for(int neuron = 0; neuron < this.layer.getLayer()[layer]; neuron ++){
                double sum = 0;
                for(int nextNeuron = 0; nextNeuron < this.layer.getLayer()[layer+1]; nextNeuron ++) {
                    sum += this.layer.getPerceptron().getWeights()[layer + 1][nextNeuron][neuron] * this.layer.getPerceptron().getError_signal()[layer + 1][nextNeuron];
                }
                this.layer.getPerceptron().getError_signal()[layer][neuron] = sum * this.layer.getPerceptron().getOutput_derivative()[layer][neuron];
            }
        }
    }

    public void adjustWeights(double eta) {
        for(int layer = 1; layer < this.layer.getNETWORK_SIZE(); layer++) {
            for(int neuron = 0; neuron < this.layer.getLayer()[layer]; neuron++) {

                double delta = - eta * this.layer.getPerceptron().getError_signal()[layer][neuron];
                this.layer.getPerceptron().getBias()[layer][neuron] += delta;

                for(int prevNeuron = 0; prevNeuron < this.layer.getLayer()[layer-1]; prevNeuron ++) {
                    this.layer.getPerceptron().getWeights()[layer][neuron][prevNeuron] += delta * this.layer.getPerceptron().getOutput()[layer-1][prevNeuron];
                }
            }
        }
    }

    private double activationFunction( double x) {
        return 1d / ( 1 + Math.exp(-x));
    }
    
    public void saveNetwork(String fileName) throws Exception {
        Parser p = new Parser();
        p.create(fileName);
        Node root = p.getContent();
        Node netw = new Node("Perceptron");
        Node ly = new Node("Layers");
        netw.addAttribute(new Attribute("sizes", Arrays.toString(this.layer.getLayer())));
        netw.addChild(ly);
        root.addChild(netw);
        for (int layer = 1; layer < this.layer.getNETWORK_SIZE(); layer++) {

            Node c = new Node("" + layer);
            ly.addChild(c);
            Node w = new Node("weights");
            Node b = new Node("biases");
            c.addChild(w);
            c.addChild(b);

            b.addAttribute("values", Arrays.toString(this.layer.getPerceptron().getBias()[layer]));

            for (int we = 0; we < this.layer.getPerceptron().getWeights()[layer].length; we++) {

                w.addAttribute("" + we, Arrays.toString(this.layer.getPerceptron().getWeights()[layer][we]));
            }
        }
        p.close();
    }

    public NeuralNetwork loadNetwork(String fileName) throws Exception {

        Parser p = new Parser();

            p.load(fileName);
            String sizes = p.getValue(new String[] { "Perceptron" }, "sizes");
            int[] si = ParserTools.parseIntArray(sizes);
            NeuralNetwork ne = new NeuralNetwork(si);

            for (int i = 1; i < ne.layer.getNETWORK_SIZE(); i++) {
                String biases = p.getValue(new String[] { "Perceptron", "Layers", new String(i + ""), "biases" }, "values");
                double[] bias = ParserTools.parseDoubleArray(biases);
                ne.layer.getPerceptron().getBias()[i] = bias;

                for(int n = 0; n < ne.layer.getLayer()[i]; n++){

                    String current = p.getValue(new String[] { "Perceptron", "Layers", new String(i + ""), "weights" }, ""+n);
                    double[] val = ParserTools.parseDoubleArray(current);

                    ne.layer.getPerceptron().getWeights()[i][n] = val;
                }
            }
            p.close();
            return ne;

    }
    
   
}
