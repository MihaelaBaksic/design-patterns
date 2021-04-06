package generator;

import generator.Generator;

import java.util.ArrayList;
import java.util.List;

public class SequentialGenerator implements Generator {

    private Long lowerBound;
    private Long upperBound;
    private Integer step;

    public SequentialGenerator(Long lowerBound, Long upperBound, Integer step){
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.step = step;
    }

    @Override
    public List<Long> generate() {
        List<Long> generatedNumbers = new ArrayList<>();

        for(long i=lowerBound; i<=upperBound; i+=step){
            generatedNumbers.add(i);
        }

        return generatedNumbers;
    }
}
