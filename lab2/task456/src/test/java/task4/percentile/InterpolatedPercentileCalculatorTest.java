package task4.percentile;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class InterpolatedPercentileCalculatorTest {

    private InterpolatedPercentileCalculator calculator;
    private static List<Long> elements = Arrays.asList(2L, 4L, 7L, 9L, 12L);

    @BeforeEach
    public void setUp(){
        this.calculator = new InterpolatedPercentileCalculator();
    }

    @Test
    public void testSmallerPercentile(){
        Assertions.assertEquals(2L, calculator.calculate(1., elements));
    }

    @Test
    public void testEqualPercentile(){
        List<Long> elements = Arrays.asList(2L, 4L, 7L, 9L, 12L);
        Assertions.assertEquals(2L, calculator.calculate(10., elements));
        Assertions.assertEquals(7L, calculator.calculate(50., elements));
        Assertions.assertEquals(12L, calculator.calculate(90., elements));
    }

    @Test
    public void testGreaterPercentile(){
        List<Long> elements = Arrays.asList(2L, 4L, 7L, 9L, 12L);
        Assertions.assertEquals(12L, calculator.calculate(91., elements));
    }

    @Test
    public void testRandom(){
        List<Long> elements = Arrays.asList(1l, 10l, 50l);
        for(int i=10; i<=100; i+=10){
            System.out.println(calculator.calculate((double)i, elements));
        }
    }

}
