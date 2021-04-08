package task4.generator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class RandomGeneratorTest {

    private RandomGenerator randomGenerator;

    @BeforeEach
    public void setUp(){
        this.randomGenerator = new RandomGenerator(10., 5., 20);
    }

    @Test
    public void testGeneration(){
        List<Long> elements = randomGenerator.generate();

        Assertions.assertEquals(elements.size(), 20);
    }
}
