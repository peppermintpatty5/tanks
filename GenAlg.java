import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/*
 * Name: Shane Arcaro
 * File: GenAlg.java
 * Date: Nov 9, 2019
 * Description:
*/

public class GenAlg {

	private int generation;
	private int maxGeneration;
	private boolean keep;

	private double crossoverRate;
	private double mutationRate;
	private double maxFitness;

	public GenAlg(int maxGeneration, double crossoverRate, double mutationRate, double geneMultiplier, boolean keep) {
		this.maxGeneration = maxGeneration;
		this.crossoverRate = crossoverRate;
		this.mutationRate = mutationRate;
		this.keep = keep;
	}

	public List<Tank> process(List<Tank> currentGeneration) {
		List<Tank> genePool = new ArrayList<Tank>();

		// Find the highest and lowest fitness present within a current generation
		double lowestFitness = currentGeneration.get(0).getBrain().getFitness();
		double highestFitness = currentGeneration.get(0).getBrain().getFitness();
		
		List<Double> fitnessScore = new ArrayList<Double>();
		
		for (Tank e : currentGeneration) {
			fitnessScore.add(e.getBrain().getFitness());
		}
		Collections.sort(fitnessScore);
		
		System.out.print("Fitness: [");
		for(Double e : fitnessScore) {
			System.out.print(e + ", ");
		}
		System.out.println("]");
		
		for (int i = 1; i < currentGeneration.size(); i++) {
			double currentFitness = currentGeneration.get(0).getBrain().getFitness();
			if (lowestFitness > currentFitness)
				lowestFitness = currentFitness;
			if (currentFitness > highestFitness)
				highestFitness = currentFitness;
		}
		maxFitness = highestFitness;
		
		// Normalize the fitness of every Tank
		double mean = currentGeneration.stream().mapToDouble(t -> t.getBrain().getFitness()).sum() / currentGeneration.size();
		double stddev = Math.sqrt(currentGeneration.stream().mapToDouble(t -> t.getBrain().getFitness()).map(d -> Math.pow(d - mean, 2)).sum() / (currentGeneration.size() - 1));
		
		for (var t : currentGeneration)
			t.getBrain().setFitness(((((t.getBrain().getFitness() - mean) / stddev) + 4) * 100));
		currentGeneration.get(0).getBrain().setFitness(0);
		
		System.out.print("Adjusted Fitness: [");
		for(Tank e : currentGeneration) {
			System.out.print(e.getBrain().getFitness() + ", ");
		}
		System.out.println("]");
		
		System.out.println();
		
		var topTwo = currentGeneration.stream().sorted((a, b) -> Double.valueOf((b.getBrain().getFitness())).compareTo(Double.valueOf(a.getBrain().getFitness()))).limit(2).collect(Collectors.toList());
		System.out.println("Top Two: " + topTwo.get(0).getBrain().getFitness() + ", " + topTwo.get(1).getBrain().getFitness());
		
		// Create a proportional gene pool of the current generation
		
		currentGeneration.set(0, topTwo.get(0));
		currentGeneration.set(1, topTwo.get(1));
		for (Tank e : currentGeneration) {
			for (int i = 0; i < e.getBrain().getFitness(); i++) {
				genePool.add(e);
			}
		}

		// Start to create the new generation
		for (int i = 2; i < currentGeneration.size(); i++) {
			if (Math.random() < crossoverRate)
				currentGeneration.set(i, crossover(currentGeneration.get(i),
						currentGeneration.get((int) (Math.random() * currentGeneration.size() - 1))));
			if (Math.random() < mutationRate)
				currentGeneration.set(i, mutate(currentGeneration.get(i)));
		}
		generation++;
		return currentGeneration;
	}

	/**
	 * Crossover to create a child from two parent Tanks
	 *
	 * @param father father to be used
	 * @param mother mother to be used
	 * @return child based on parents
	 */
	public Tank crossover(Tank father, Tank mother) {
		double[] fWeight = father.getBrain().getWeights();
		double[] mWeight = mother.getBrain().getWeights();

		int length = fWeight.length;
		int split = (int) (Math.random() * (length - 1) + 1);

		Tank child = new Tank(father.team);
		double[] fatherWeights = new double[split];
		double[] motherWeights = new double[length - split];
		double[] childWeights = new double[fatherWeights.length + motherWeights.length];

		for (int i = 0; i < fatherWeights.length; i++)
			fatherWeights[i] = fWeight[i];
		for (int i = fatherWeights.length; i < motherWeights.length - 1; i++) {
			motherWeights[i] = mWeight[i];
		}

		for (int i = 0; i < fatherWeights.length; i++)
			childWeights[i] = fatherWeights[i];

		int counter = 0;
		for (int i = fatherWeights.length + 1; i < childWeights.length; i++)
			childWeights[i] = motherWeights[counter++];

		child.getBrain().sendWeights(childWeights);

		return child;
	}

	public Tank mutate(Tank tank) {
		tank.getBrain().targetWeight((int) (Math.random() * tank.getBrain().getWeights().length),
				Math.random() * 4 - 2);
		return tank;
	}

	public int getGeneration() {
		return generation;
	}

	public int getMaxGeneration() {
		return maxGeneration;
	}
	
	public double getMaxFitness() {
		return maxFitness;
	}
}
