package task4;

import task4.generator.FibonacciGenerator;
import task4.generator.Generator;
import task4.generator.RandomGenerator;
import task4.generator.SequentialGenerator;
import task4.percentile.InterpolatedPercentileCalculator;
import task4.percentile.NearestRankPercentileCalculator;
import task4.percentile.PercentileCalculator;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args){

        // Generators
        List<Generator> generators = new ArrayList<>();
        generators.add(new SequentialGenerator(10L, 40L, 4));
        generators.add(new RandomGenerator(10., 10., 30));
        generators.add(new FibonacciGenerator(15));

        List<PercentileCalculator> calculators = new ArrayList<>();
        calculators.add(new NearestRankPercentileCalculator());
        calculators.add(new InterpolatedPercentileCalculator());

        DistributionTester dt = new DistributionTester(generators.get(0), calculators.get(0));

        for(var generator : generators){
            for(var calculator : calculators){
                dt.setGenerator(generator);
                dt.setCalculator(calculator);
                dt.generate();
                dt.calculatePercentile();
            }
        }

    }
}
