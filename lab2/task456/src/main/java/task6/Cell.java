package task6;

public class Cell{

    private String exp;
    private Double value;
    private String cellName;

    public Cell(String cellName, String exp, Double value){
        this.cellName = cellName;
        this.exp = exp;
        this.value = value;
    }

    public Double getValue(){
        return value;
    }

    public void updateValue(Double value) {
        this.value = value;
    }

    public void setExp(String exp, Double value){
        this.exp = exp;
        this.value = value;
    }

    public String getCellName(){
        return cellName;
    }

    public String getExp(){
        return this.exp;
    }

    @Override
    public boolean equals(Object o){
        if( o == this)
            return true;

        if( ! (o instanceof Cell))
            return false;

        return this.cellName.equals(((Cell) o).cellName);
    }


    @Override
    public String toString(){
        return " [Name:" + cellName + " exp:" + exp + " value:" + value + "]";
    }
}
