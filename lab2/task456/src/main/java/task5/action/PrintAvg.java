package task5.action;

import java.util.List;

public class PrintAvg implements Action{
    @Override
    public void update(List<Long> numbers) {
        Double avg = numbers.stream().reduce(0L, Long::sum) / (double) numbers.size();
        System.out.printf("Avg : %.2f\n", avg);
    }
}
