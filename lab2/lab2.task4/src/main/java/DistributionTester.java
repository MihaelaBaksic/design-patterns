import generator.Generator;

import java.util.List;

public class DistributionTester {

    private Generator generator;

    public DistributionTester(Generator generator){
        this.generator = generator;
    }

    public void setGenerator(Generator generator) {
        this.generator = generator;
    }

    public void generateAndPrint(){
        List<Long> generatedNumbers = generator.generate();
    }
}
