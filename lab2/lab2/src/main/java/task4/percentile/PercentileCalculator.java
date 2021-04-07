package percentile;

import java.util.List;

public interface PercentileCalculator {

    Long calculate(Double p, List<Long> elements);

}
