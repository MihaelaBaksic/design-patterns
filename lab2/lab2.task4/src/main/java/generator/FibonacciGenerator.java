package generator;

import generator.Generator;

import java.util.ArrayList;
import java.util.List;

public class FibonacciGenerator implements Generator {

    private Integer elementsNumber;

    public FibonacciGenerator(Integer elementsNumber){
        this.elementsNumber = elementsNumber>=0 ? elementsNumber : 0;
    }

    @Override
    public List<Long> generate() {
        List<Long> generatedNumbers = new ArrayList<>();

        if(elementsNumber == 0)
            return generatedNumbers;

        generatedNumbers.add(1L);
        if(elementsNumber == 1)
            return generatedNumbers;

        generatedNumbers.add(1L);
        if(elementsNumber == 2)
            return generatedNumbers;

        for(int i=2; i<elementsNumber; i++){
            generatedNumbers.add(generatedNumbers.get(i-1) + generatedNumbers.get(i-2));
        }

        return generatedNumbers;
    }
}
