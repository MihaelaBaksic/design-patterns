package task5.action;

import java.util.List;
import java.util.stream.Collectors;

public class PrintMedian implements Action{

    @Override
    public void update(List<Long> numbers) {

        assert numbers.size() > 0 : "List must contain at least one element";

        numbers = numbers.stream().sorted().collect(Collectors.toList());

        Double median = -1.;

        if(numbers.size() % 2 != 0){ // odd list size
             median = (double) numbers.get(numbers.size()/2);
        }
        else{ // even list size
            median = ( numbers.get(numbers.size()/2 - 1) + numbers.get(numbers.size()/2) ) / 2.;
        }

        System.out.printf("Median %.2f\n", median);
    }
}
