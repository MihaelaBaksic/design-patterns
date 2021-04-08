package task4.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RandomGenerator implements Generator {

    private Double mean;
    private Double variance;
    private Integer elementsNumber;
    private Random random;

    public RandomGenerator(Double mean, Double variance, Integer elementsNumber){
        assert variance >= 0 : "Variance must be positive or zero";
        assert elementsNumber >= 0: "Number of elements must pe positive or zero";
        this.mean = mean;
        this.variance = variance;
        this.elementsNumber = elementsNumber;
        this.random = new Random();
    }

    @Override
    public List<Long> generate() {
        List<Long> generatedNumbers = new ArrayList<>();
        for(int i=0; i<elementsNumber; i++){
            generatedNumbers.add(Math.round(random.nextGaussian()*variance + mean));
        }
        return generatedNumbers.stream().sorted().collect(Collectors.toList());
    }
}
