package task4;

import task4.generator.Generator;
import task4.percentile.PercentileCalculator;

import java.util.ArrayList;
import java.util.List;

public class DistributionTester {

    private Generator generator;

    private PercentileCalculator calculator;

    private List<Long> numbers;

    public DistributionTester(Generator generator, PercentileCalculator calculator){
        this.numbers = new ArrayList<>();
        this.generator = generator;
        this.calculator = calculator;
    }

    public void setGenerator(Generator generator) {
        this.generator = generator;
    }

    public void setCalculator(PercentileCalculator calculator) { this.calculator = calculator; }

    public void generate(){
        numbers = generator.generate();
        System.out.println("Generated: " + numbers);
    }

    public void calculatePercentile(){
        for(int i=10; i<=90; i+=10){
            System.out.println(i + "th percentile: " + calculator.calculate((double)i, numbers));
        }
    }


}
