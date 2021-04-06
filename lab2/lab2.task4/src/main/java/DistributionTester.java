import generator.Generator;
import percentile.PercentileCalculator;

import java.util.List;

public class DistributionTester {

    private Generator generator;

    private PercentileCalculator calculator;

    public DistributionTester(Generator generator, PercentileCalculator calculator){
        this.generator = generator;
        this.calculator = calculator;
    }

    public void setGenerator(Generator generator) {
        this.generator = generator;
    }

    public void setCalculator(PercentileCalculator calculator) { this.calculator = calculator; }

    public void generateAndPrint(){
        List<Long> generatedNumbers = generator.generate();

        System.out.println("Generated: " + generatedNumbers);

        for(int i=10; i<=90; i+=10){
            System.out.println(i + "th percentile: " + calculator.calculate((double)i, generatedNumbers));
        }
    }
}
