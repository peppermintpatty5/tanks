import java.util.ArrayList;
import java.util.List;

/*
 * Name: Shane Arcaro
 * File: GenAlg.java
 * Date: Nov 9, 2019
 * Description: 
*/

public class GenAlg {
	
	private int populationSize;
	private int generation;
	
	private int maxGeneration;
	private boolean keep;
	
	private double crossoverRate;
	private double mutationRate;
	private double geneMultiplier;
	
	
	public GenAlg(int populationSize, int maxGeneration, double crossoverRate, double mutationRate, double geneMultiplier, boolean keep) {
		this.populationSize = populationSize;
		this.maxGeneration = maxGeneration;
		this.crossoverRate = crossoverRate;
		this.geneMultiplier = geneMultiplier;
		this.mutationRate = mutationRate;
		this.keep = keep;
	}
	
	public List<Tank> process(List<Tank> currentGeneration) { 
		
		// Find the highest and lowest fitness present within a current generation
		double lowestFitness = currentGeneration.get(0).getBrain().getFitness();
		double highestFitness = currentGeneration.get(0).getBrain().getFitness();
		
		for(int i = 1; i < currentGeneration.size(); i++) {
			double currentFitness = currentGeneration.get(0).getBrain().getFitness();
			if(lowestFitness > currentFitness)
				lowestFitness = currentFitness;
			if(currentFitness > highestFitness)
				highestFitness = currentFitness;
		}
		
		// Normalize the fitness of every Tank
		for(int i = 0; i < currentGeneration.size(); i++)
			currentGeneration.get(0).getBrain().setFitness((int)((currentGeneration.get(0).getBrain().getFitness() - lowestFitness) / (highestFitness - lowestFitness) * geneMultiplier) + 1);
		
		// Create a proportional gene pool of the current generation
		List<Tank> genePool = new ArrayList<Tank>();
		
		for(Tank e : currentGeneration) {
			for(int i = 0; i < e.getBrain().getFitness(); i++) {
				genePool.add(e);
			}
		}
		
		return null;
	}
	
	public Tank crossover(Tank father, Tank mother) {
		double[] fWeight = father.getBrain().getWeights();
		double[] mWeight = mother.getBrain().getWeights();
		
		int length = fWeight.length;
		int split = (int)(Math.random() * length);
		
		double[] fatherWeights = new double[split];
		double[] motherWeights = new double[length - split];
		
		for(int i = 0; i < fatherWeights.length; i++) 
			fatherWeights[i] = fWeight[i];
		for(int i = fatherWeights.length - 1; i < mWeight.length; i++)
			motherWeights[i] = mWeight[i];
		
		Tank child = new Tank(0, 0, 0);
		Arrays.
		child.getBrain().sendWeights(weights);
		
	}
	
	public Tank mutate(Tank tank) {
		
	}
}


