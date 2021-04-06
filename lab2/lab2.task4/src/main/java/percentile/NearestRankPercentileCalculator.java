package percentile;

import java.util.List;

public class NearestRankPercentileCalculator implements PercentileCalculator{
    @Override
    public Long calculate(Integer p, List<Long> elements) {
        if(p.equals(0)) return elements.get(0);

        int n_p = (int) Math.ceil(p*elements.size()/100.);
        return elements.get(n_p - 1);
    }
}
