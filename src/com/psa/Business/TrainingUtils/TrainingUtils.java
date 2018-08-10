/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.psa.Business.TrainingUtils;

import com.psa.Business.NeuralNetwork.Utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author rahulzore
 */
public class TrainingUtils implements Serializable{
    public final int INPUT_SIZE;
    public final int OUTPUT_SIZE;
    

    //double[][] <- index1: 0 = input, 1 = output || index2: index of element
    private ArrayList<double[][]> data = new ArrayList<>();

    public TrainingUtils(int INPUT_SIZE, int OUTPUT_SIZE) {
        this.INPUT_SIZE = INPUT_SIZE;
        this.OUTPUT_SIZE = OUTPUT_SIZE;
    }

    public void addData(double[] in, double[] expected) {
        if(in.length != INPUT_SIZE || expected.length != OUTPUT_SIZE) return;
        data.add(new double[][]{in, expected});
    }

    public TrainingUtils extractBatch(int size) {
        if(size > 0 && size <= this.size()) {
            TrainingUtils set = new TrainingUtils(INPUT_SIZE, OUTPUT_SIZE);
            Integer[] ids = Utils.randomValues(0,this.size() - 1, size);
            for(Integer i:ids) {
                set.addData(this.getInput(i),this.getOutput(i));
            }
            return set;
        }else return this;
    }

//    public static void main(String[] args) {
//        TrainingUtils set = new TrainingUtils(3,2);
//
//        for(int i = 0; i < 8; i++) {
//            double[] a = new double[3];
//            double[] b = new double[2];
//            for(int k = 0; k < 3; k++) {
//                a[k] = (double)((int)(Math.random() * 10)) / (double)10;
//                if(k < 2) {
//                    b[k] = (double)((int)(Math.random() * 10)) / (double)10;
//                }
//            }
//            set.addData(a,b);
//        }
//
//        System.out.println(set);
//        System.out.println(set.extractBatch(3));
//    }

    public String toString() {
        String s = "TrainSet ["+INPUT_SIZE+ " ; "+OUTPUT_SIZE+"]\n";
        int index = 0;
        for(double[][] r:data) {
            s += index +":   "+Arrays.toString(r[0]) +"  >-||-<  "+Arrays.toString(r[1]) +"\n";
            index++;
        }
        return s;
    }

    public int size() {
        return data.size();
    }

    public double[] getInput(int index) {
        if(index >= 0 && index < size())
            return data.get(index)[0];
        else return null;
    }

    public double[] getOutput(int index) {
        if(index >= 0 && index < size())
            return data.get(index)[1];
        else return null;
    }

    public int getINPUT_SIZE() {
        return INPUT_SIZE;
    }

    public int getOUTPUT_SIZE() {
        return OUTPUT_SIZE;
    }
    
    public void saveSet(String file) throws Exception{
        File f = new File(file);
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
        out.writeObject(this);
        out.flush();
        out.close();
    }
    
    public TrainingUtils loadSet(String file) throws Exception{
        File f = new File(file);
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(f));
        TrainingUtils set = (TrainingUtils)in.readObject();
        in.close();
        return set;
    }
}
