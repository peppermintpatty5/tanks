import java.util.Arrays;

/*
 * Name: Shane Arcaro
 * File: Brain.java
 * Date: Nov 9, 2019
 * Description: 
*/

public class Brain {
	
	private double[] input;
	private double[] hidden;
	private double[] output;
	private double[] weights;
	
	private double fitness;
	
	public Brain(int input, int hidden, int output) {
		this.input = new double[input];
		this.hidden = new double[hidden];
		this.output = new double[output];
		this.weights = new double[(input * hidden) + (hidden * output)];
	}
	
	public void sendInputs(double [] input) {
		this.input = input;
	}
	
	public void sendWeights(double[] weights) {
		this.weights = weights;
	}
	
	public double[] getInput() {
		return input;
	}
	
	public double[] getHidden() {
		return hidden;
	}
	
	public double[] getOutput() {
		return output;
	}
	
	public double[] getWeights() {
		return weights;
	}
	
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	public double getFitness() {
		return fitness;
	}
	
	public void randomizeWeights() {
		for(int i = 0; i < weights.length; i++) {
			weights[i] = Math.random() * 4 - 2;
		}
	}
	
	public void targetWeight(int index, double weight) {
		weights[index] = weight;
	}
	
	public void generateOutput() {
		int counter = 0;
		for(int i = 0; i < hidden.length; i++) {
			for(int j = 0; j < input.length; j++) {
				hidden[i] += input[j] * weights[counter];
				counter++;
				
			}
		}
		
		for(int i = 0; i < output.length; i++) {
			for(int j = 0; j < hidden.length; j++) {
				output[i] += hidden[j] * weights[counter];
				counter++;
			}
		}
	}
	
	
	public String toString() {
		return "Input: " + Arrays.toString(input) +
				"\nHidden: " + Arrays.toString(hidden) + 
				"\nOutput: " + Arrays.toString(output) + 
				"\nWeights: " + Arrays.toString(weights);
	}

}


