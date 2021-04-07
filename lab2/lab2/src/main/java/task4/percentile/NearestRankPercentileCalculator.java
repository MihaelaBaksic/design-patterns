package task4.percentile;

import java.util.List;

public class NearestRankPercentileCalculator implements PercentileCalculator{
    @Override
    public Long calculate(Double p, List<Long> elements) {

        assert p>=0 && p<=100 : "Percentile must be a value between 0 and 100";

        if(p.equals(0.)) return elements.get(0);

        int n_p = (int) Math.ceil(p*elements.size()/100.);
        return elements.get(n_p - 1);
    }
}
