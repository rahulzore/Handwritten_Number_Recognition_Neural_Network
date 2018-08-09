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
public class Perceptron {
    
    private double[][] output;
    private double[][][] weights;
    private double[][] bias;
    private double[][] error_signal;
    private double[][] output_derivative;
    private double LEARNING_RATE;
    
    public Perceptron(){
        this.LEARNING_RATE = 0.3;
    }

    public double[][] getOutput() {
        return output;
    }

    public void setOutput(double[][] output) {
        this.output = output;
    }

    public double[][][] getWeights() {
        return weights;
    }

    public void setWeights(double[][][] weights) {
        this.weights = weights;
    }

    public double[][] getBias() {
        return bias;
    }

    public void setBias(double[][] bias) {
        this.bias = bias;
    }

    public double[][] getError_signal() {
        return error_signal;
    }

    public void setError_signal(double[][] error_signal) {
        this.error_signal = error_signal;
    }

    public double[][] getOutput_derivative() {
        return output_derivative;
    }

    public void setOutput_derivative(double[][] output_derivative) {
        this.output_derivative = output_derivative;
    }

    public double getLEARNING_RATE() {
        return LEARNING_RATE;
    }

    public void setLEARNING_RATE(double LEARNING_RATE) {
        this.LEARNING_RATE = LEARNING_RATE;
    }
    
    
}
