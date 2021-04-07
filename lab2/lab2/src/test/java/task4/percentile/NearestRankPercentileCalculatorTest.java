package percentile;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class NearestRankPercentileCalculatorTest {

    private NearestRankPercentileCalculator nearestRankPercentileCalculator;

    @BeforeEach
    public void setUp(){
        this.nearestRankPercentileCalculator = new NearestRankPercentileCalculator();
    }

    @Test
    public void testPercentile(){
        List<Long> elements = Arrays.asList(15L, 20L, 35L, 40L, 50L);

        Long percentile = nearestRankPercentileCalculator.calculate(5., elements);
        Assertions.assertEquals(15L, percentile);

        percentile = nearestRankPercentileCalculator.calculate(0., elements);
        Assertions.assertEquals(15L, percentile);

        percentile = nearestRankPercentileCalculator.calculate(100., elements);
        Assertions.assertEquals(50L, percentile);

    }
}
