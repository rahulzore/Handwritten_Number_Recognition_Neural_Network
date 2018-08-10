package com.psa.Test.NeuralNetwork;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import com.psa.Business.NeuralNetwork.NeuralNetwork;
import com.psa.Business.NeuralNetwork.Perceptron;
import com.psa.Business.NeuralNetwork.Utils;
import com.psa.Business.TrainingUtils.TrainingUtils;

@SuppressWarnings("ALL")
public class NeuralNetworkTest {
	
	private static final double DELTA = 1e-15;

	@Test
	public void activationTest() throws Exception{
		NeuralNetwork nn = new NeuralNetwork(784, 70, 35, 10);
		double sigmoid = nn.activationFunction(0);
		assertEquals(0.5, sigmoid, DELTA);
	}
	
	@Test
	public void activationTest1() throws Exception{
		NeuralNetwork nn = new NeuralNetwork(784, 70, 35, 10);
		double sigmoid = nn.activationFunction(1);
		assertNotEquals(0.5, sigmoid);
	}
	
	@Test
	public void learningRate() throws Exception{
		Perceptron p = new Perceptron();
		assertEquals(p.getLEARNING_RATE(), 0.3, DELTA);
	}
	
	@Test
	public void MSETest() throws Exception{
		NeuralNetwork nn = new NeuralNetwork(2,3,3,2);
		double mseResult = nn.MSE(new double[] {1,2,3}, new double[] {1,2,3});
		assertEquals(0.0, mseResult, DELTA);
	}
	
	@Test
	public void trainingsetInputTest() throws Exception{
		TrainingUtils set = new TrainingUtils(4, 2);
		set.addData(new double[]{0.1,0.2,0.3,0.4}, new double[]{0.9,0.1});
        
		assertArrayEquals(new double[]{0.1,0.2,0.3,0.4}, set.getInput(0), DELTA);
	}
	
	@Test
	public void trainingsetOutputTest() throws Exception{
		TrainingUtils set = new TrainingUtils(4, 2);
		set.addData(new double[]{0.1,0.2,0.3,0.4}, new double[]{0.9,0.1});
        
		assertArrayEquals(new double[]{0.9,0.1}, set.getOutput(0), DELTA);
	}
	
	@Test
	public void neuralNetworkInputSize() throws Exception{
		NeuralNetwork net = new NeuralNetwork(4,3,3,2);
		
		int inputSize = net.getLayer().getINPUT_SIZE();
		
		assertEquals(4, inputSize);
	}
	
	@Test
	public void neuralNetworkOutputSize() throws Exception{
		NeuralNetwork net = new NeuralNetwork(4,3,3,2);
		
		int outputSize = net.getLayer().getOUTPUT_SIZE();
		
		assertEquals(2, outputSize);
	}
	
	@Test
	public void neuralNetworkTest() throws Exception{
		NeuralNetwork net = new NeuralNetwork(4,3,3,2);
		
		TrainingUtils set = new TrainingUtils(4, 2);
		set.addData(new double[]{0.1,0.2,0.3,0.4}, new double[]{0.9,0.1});
        set.addData(new double[]{0.9,0.8,0.7,0.6}, new double[]{0.1,0.9});
        set.addData(new double[]{0.3,0.8,0.1,0.4}, new double[]{0.3,0.7});
        set.addData(new double[]{0.9,0.8,0.1,0.2}, new double[]{0.7,0.3});
        
        net.train(set, 10000, 4);
        
        for(int i=0; i<4; i++) {
        	double highest = Utils.indexOfHighestValue(net.calculateResult(set.getInput(i)));
            double actualHighest = Utils.indexOfHighestValue(set.getOutput(i));
            assertEquals(highest, actualHighest, DELTA);
        }
		
	}
}
