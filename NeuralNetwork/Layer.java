/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.psa.Business.NeuralNetwork;

/**
 *
 * @author rahulzore
 */
public class Layer {
    
    private int[] layer;
    private int INPUT_SIZE;
    private int OUTPUT_SIZE;
    private int NETWORK_SIZE;
    private Perceptron perceptron;
    
    public Layer(int[] layer){
        this.perceptron = new Perceptron();
        this.layer = layer;
        this.NETWORK_SIZE = this.layer.length;
        this.INPUT_SIZE = this.layer[0];
        this.OUTPUT_SIZE = this.layer[this.NETWORK_SIZE - 1];
        
        this.perceptron.setOutput(new double[this.NETWORK_SIZE][]);
        this.perceptron.setWeights(new double[this.NETWORK_SIZE][][]);
        this.perceptron.setBias(new double[NETWORK_SIZE][]);
        
        this.perceptron.setError_signal(new double[NETWORK_SIZE][]);
        this.perceptron.setOutput_derivative(new double[NETWORK_SIZE][]);
        
        for(int i=0; i<this.NETWORK_SIZE; i++){
            this.perceptron.getOutput()[i] = new double[layer[i]];
            this.perceptron.getError_signal()[i] = new double[layer[i]];
            this.perceptron.getOutput_derivative()[i] = new double[layer[i]];
            
            this.perceptron.getBias()[i] = Utils.createRandomArray(layer[i], -0.5,0.7);
            
            if(i>0){
                this.perceptron.getWeights()[i] = Utils.createRandomArray(layer[i],layer[i-1], -1,1);
            }
        }
        
    }

    public int getINPUT_SIZE() {
        return INPUT_SIZE;
    }

    public void setINPUT_SIZE(int INPUT_SIZE) {
        this.INPUT_SIZE = INPUT_SIZE;
    }

    public int getOUTPUT_SIZE() {
        return OUTPUT_SIZE;
    }

    public void setOUTPUT_SIZE(int OUTPUT_SIZE) {
        this.OUTPUT_SIZE = OUTPUT_SIZE;
    }

    public int getNETWORK_SIZE() {
        return NETWORK_SIZE;
    }

    public void setNETWORK_SIZE(int NETWORK_SIZE) {
        this.NETWORK_SIZE = NETWORK_SIZE;
    }

    
    public int[] getLayer() {
        return layer;
    }

    public void setLayer(int[] Layer) {
        this.layer = Layer;
    }

    public Perceptron getPerceptron() {
        return perceptron;
    }

    public void setPerceptron(Perceptron perceptron) {
        this.perceptron = perceptron;
    }
    
    
}
