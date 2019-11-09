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
	
	public Tank[] process(Tank[] currentGeneration) { 
		
		// Find the highest and lowest fitness present within a current generation
		double lowestFitness = currentGeneration[0].getBrain().getFitness();
		double highestFitness = currentGeneration[0].getBrain().getFitness();
		
		for(int i = 1; i < currentGeneration.length; i++) {
			double currentFitness = currentGeneration[i].getBrain().getFitness();
			if(lowestFitness > currentFitness)
				lowestFitness = currentFitness;
			if(currentFitness > highestFitness)
				highestFitness = currentFitness;
		}
		
		// Normalize the fitness of every Tank
		for(int i = 0; i < currentGeneration.length; i++)
			currentGeneration[i].getBrain().setFitness((int)((currentGeneration[i].getBrain().getFitness() - lowestFitness) / (highestFitness - lowestFitness) * geneMultiplier) + 1);
		
		// Create a proportional gene pool of the current generation
		List<Tank> genePool = new ArrayList<Tank>();
		
		for(Tank e : currentGeneration) {
			for(int i = 0; i < e.getBrain().getFitness(); i++) {
				genePool.add(e);
			}
		}
		
		return null;
	}
}


