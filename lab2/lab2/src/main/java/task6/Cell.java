package task6;

import java.util.List;

public class Cell {

    private String exp;
    private Double value;

    private List<Cell> references;

    public Cell(String exp, Double value){
        this.exp = exp;
        this.value = value;
    }

    public Double getValue(){
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

}
