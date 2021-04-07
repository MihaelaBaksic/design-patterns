package task4.generator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class SequentialGeneratorTest {

    private SequentialGenerator sequentialGenerator;

    @BeforeEach
    public void setUp(){
        this.sequentialGenerator = new SequentialGenerator(10L, 40L, 2 );
    }

    @Test
    public void testGeneration(){
        List<Long> elements = sequentialGenerator.generate();

        List<Long> expectedElements = new ArrayList<>();
        for(long i=10L; i<=40L; i+=2)
            expectedElements.add(i);

        Assertions.assertEquals(elements, expectedElements);
    }
}
