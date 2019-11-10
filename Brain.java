import java.util.Arrays;

/*
 * Name: Shane Arcaro
 * File: Brain.java
 * Date: Nov 9, 2019
 * Description: 
*/

public class Brain {
	
	/**
	 * Input values to be inserted into the brain as sensory data
	 */
	private double[] input;
	
	/**
	 * Hidden values to be used for deep calculations
	 */
	private double[] hidden;
	
	/**
	 * Output data given out to the world
	 */
	private double[] output;
	
	/**
	 * Weights used to bias the input-output ration
	 */
	private double[] weights;
	
	/**
	 * Fitness used to determine how well a specific brain performs.
	 */
	private double fitness;
	
	/**
	 * Create a new brain
	 * @param input input values to be set
	 * @param hidden hidden values to be set
	 * @param output output values to be set
	 */
	public Brain(int input, int hidden, int output) {
		this.input = new double[input];
		this.hidden = new double[hidden];
		this.output = new double[output];
		this.weights = new double[(input * hidden) + (hidden * output)];
	}
	
	/**
	 * Insert input data into the network
	 * @param input input data to be set
	 */
	public void sendInputs(double [] input) {
		this.input = input;
	}
	
	/**
	 * Insert new weights into the network
	 * @param weights
	 */
	public void sendWeights(double[] weights) {
		this.weights = weights;
	}
	
	/**
	 * Retrieve the input values
	 * @return input values
	 */
	public double[] getInput() {
		return input;
	}
	
	/**
	 * Retrieve the hidden values
	 * @return hidden values
	 */
	public double[] getHidden() {
		return hidden;
	}
	
	/**
	 * Retrieve the output values
	 * @return output values
	 */
	public double[] getOutput() {
		return output;
	}
	
	/**
	 * Retrieve the weight values
	 * @return weight values
	 */
	public double[] getWeights() {
		return weights;
	}
	
	/**
	 * Alter the current fitness
	 * @param fitness the fitness to be set
	 */
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	/**
	 * Retrieve the fitness value
	 * @return fitness value
	 */
	public double getFitness() {
		return fitness;
	}
	
	/**
	 * Generate random weighted values
	 */
	public void randomizeWeights() {
		for(int i = 0; i < weights.length; i++) {
			weights[i] = Math.random() * 4 - 2;
		}
	}
	
	/**
	 * Target and alter a specific weight
	 * @param index specific weight to be targeted
	 * @param weight weight to be set
	 */
	public void targetWeight(int index, double weight) {
		weights[index] = weight;
	}
	
	/**
	 * Run through the network and generate the desired output
	 */
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
				output[i] = output[i] > 0 ? 1 : 0;
				counter++;
			}
		}
	}
	
	@Override
	public String toString() {
		return "Input: " + Arrays.toString(input) +
				"\nHidden: " + Arrays.toString(hidden) + 
				"\nOutput: " + Arrays.toString(output) + 
				"\nWeights: " + Arrays.toString(weights);
	}

}


