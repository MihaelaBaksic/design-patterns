package percentile;

import java.util.List;

public class InterpolatedPercentileCalculator implements PercentileCalculator{

    @Override
    public Long calculate(Double p, List<Long> elements) {

        assert p>=0 && p<=100 : "Percentile must be a value between 0 and 100";

        Double p_0 = calcPercentile(1, elements.size());
        if( p < p_0 ) return elements.get(0);

        for(int i=1; i<elements.size(); i++){
            double p_i = calcPercentile(i, elements.size());

            if( Math.abs(p-p_i) < 1E-8) // p == p_i
                return elements.get(i-1);
            else if(p < p_i)
                return (long) interpolate(elements.get(i-2), elements.get(i-1), p, p_i, p_i, elements.size());
        }
        return elements.get(elements.size()-1);
    }

    private double calcPercentile(Integer i, Integer N){
        return 100*(i-0.5)/N;
    }

    private double interpolate(double v_0, double v_1, double p, double p_0, double p_1, double N){
        return v_0 + N * (p-p_0)*(v_1-v_0) / 100;
    }
}
