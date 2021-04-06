package generator;

import generator.FibonacciGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FibonacciGeneratorTest {

    private FibonacciGenerator fibonacciGenerator;

    @Test
    public void testZeroElements(){
        this.fibonacciGenerator = new FibonacciGenerator(0);
        Assertions.assertEquals(new ArrayList<Long>(), fibonacciGenerator.generate());
    }

    @Test
    public void testOneElement(){
        this.fibonacciGenerator = new FibonacciGenerator(1);

        List<Long> expectedElements = new ArrayList<>();
        expectedElements.add(1L);

        Assertions.assertEquals(expectedElements, fibonacciGenerator.generate());
    }

    @Test
    public void testTwoElements(){
        this.fibonacciGenerator = new FibonacciGenerator(2);

        List<Long> expectedElements = new ArrayList<>();
        expectedElements.add(1L);
        expectedElements.add(1L);

        Assertions.assertEquals(expectedElements, fibonacciGenerator.generate());
    }

    @Test
    public void testMoreElements(){
        this.fibonacciGenerator = new FibonacciGenerator(10);

        Long[] expectedElements = {1L, 1L, 2L, 3L, 5L, 8L, 13L, 21L, 34L, 55L};

        Assertions.assertEquals(Arrays.asList(expectedElements), fibonacciGenerator.generate());
    }




}
