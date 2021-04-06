package percentile;

import java.util.List;

public interface PercentileCalculator {

    Long calculate(Integer p, List<Long> elements);

}
