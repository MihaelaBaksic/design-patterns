import generator.FibonacciGenerator;
import generator.RandomGenerator;
import generator.SequentialGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import percentile.InterpolatedPercentileCalculator;
import percentile.NearestRankPercentileCalculator;

import java.util.Random;

public class DistributionTesterTest {

    private DistributionTester distributionTester;

    private FibonacciGenerator fibonacciGenerator;
    private RandomGenerator randomGenerator;
    private SequentialGenerator sequentialGenerator;

    private NearestRankPercentileCalculator rankPercentileCalculator;
    private InterpolatedPercentileCalculator interpolatedPercentileCalculator;

    @BeforeEach
    public void setUp(){
        this.fibonacciGenerator = new FibonacciGenerator(13);
        this.randomGenerator = new RandomGenerator(0., 10., 13);
        this.sequentialGenerator = new SequentialGenerator(10L, 30L, 2);

        this.rankPercentileCalculator = new NearestRankPercentileCalculator();
        this.interpolatedPercentileCalculator = new InterpolatedPercentileCalculator();
    }


    @Test
    public void testRandomNearestRank(){

        DistributionTester tester = new DistributionTester(randomGenerator, rankPercentileCalculator);
        tester.generateAndPrint();
    }

    @Test
    public void testFibNearestRank(){

        DistributionTester tester = new DistributionTester(fibonacciGenerator, rankPercentileCalculator);
        tester.generateAndPrint();
    }


    @Test
    public void testSeqInterpolated(){

        DistributionTester tester = new DistributionTester(sequentialGenerator, interpolatedPercentileCalculator);
        tester.generateAndPrint();
    }
}
