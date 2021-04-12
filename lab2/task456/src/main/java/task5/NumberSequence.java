package task5;

import task5.action.Action;
import task5.source.DataSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class NumberSequence {

    private List<Long> numbers;

    private DataSource source;

    private List<Action> actions;

    public NumberSequence(DataSource source){
        this.source = source;
        this.numbers = new ArrayList<>();
        this.actions = new ArrayList<>();
    }

    public void setDataSource(DataSource source){
        this.source = source;
    }

    public void start() throws InterruptedException, IOException {
        Long nextNumber = null;

        while (true){
            nextNumber = source.getNextNumber();

            if(nextNumber == -1)
                return;

            numbers.add(nextNumber);

            for(var action : actions){
                action.update(numbers);
            }

            sleep(1000);
        }
    }


    List<Long> getNumbers(){
        return numbers;
    }

    public void clearNumbers(){
        numbers.clear();
    }

    public void addAction(Action action){
        actions.add(action);
    }

    public void removeAction(Action action){
        actions.remove(action);
    }
}
