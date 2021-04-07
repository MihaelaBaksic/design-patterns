package task5.action;

import java.util.List;

public class PrintSum implements Action{
    @Override
    public void update(List<Long> numbers) {
        Long sum = numbers.stream().reduce(0L, Long::sum);
        System.out.println("Sum : " + sum);
    }
}
