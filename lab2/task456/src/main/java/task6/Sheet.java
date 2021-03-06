package task6;

import task6.operators.AdditionOperator;
import task6.operators.Operator;
import task6.operators.SubtractionOperator;

import java.util.*;
import java.util.regex.*;
import java.util.stream.Collectors;

public class Sheet {

    private Map<String, Cell> cells;

    public Sheet(){
        this.cells = new HashMap<>();
    }

    public Map<String, Cell> getCells(){ return cells;}

    public void set(String cellName, String content){

        cellName = cellName.toUpperCase(Locale.ROOT);
        content = content.toUpperCase(Locale.ROOT);

        assert isCellName(cellName);

        checkCircularReferences(cellName, content);

        Double value = evaluate(content);
        if( cells.containsKey(cellName) ){
            cells.get(cellName).setExp(content, value);
            propagateUpdate(cells.get(cellName));
        }
        else{
            Cell cell = new Cell(cellName, content, value);
            cells.put(cellName, cell);
        }
    }

    public Cell cell(String ref){
        ref = ref.toUpperCase(Locale.ROOT);
        if(cells.containsKey(ref))
            return cells.get(ref);
        return null;
    }

    // Returns list of cell references
    private List<Cell> getRefs(String exp){
        exp = exp.toUpperCase(Locale.ROOT);

        return Arrays.stream(exp.split("\\+|-|\\*|/"))
                .filter(r -> isCellName(r))
                .map(r -> {
                    if(! cells.containsKey(r))
                        throw new IllegalArgumentException("Expression cannot reference uninitialized cells. " + r + " is not initialized");
                    return cell(r);
                })
                .collect(Collectors.toList());
    }

    private Double evaluate(String exp){
        List<Cell> arguments = getRefs(exp);
        List<Operator> operators = getOperators(exp);

        if (arguments.size()==0)
            return Double.parseDouble(exp);

        Double value = arguments.get(0).getValue();

        for(int i=1; i<arguments.size(); i++){
            value = operators.get(i-1).calculate(value, arguments.get(i).getValue());
        }
        return value;
    }


    private void checkCircularReferences(String cellName, String exp){

        //search for cellName in parents of referenced cells
        List<String> checkedCells = new ArrayList<>();
        List<Cell> cellsToCheck = getRefs(exp);

        while(!cellsToCheck.isEmpty()){
            //Pop front
            Cell cell = cellsToCheck.get(0);
            cellsToCheck.remove(0);

            if(checkedCells.contains(cell.getCellName()))
                continue;

            checkedCells.add(cell.getCellName());

            if(cell.getCellName().equals(cellName))
                throw new IllegalArgumentException("Circular references found.");

            cellsToCheck.addAll(getRefs(cell.getExp()));
        }
    }

    private List<Operator> getOperators(String exp){

        return Pattern.compile("\\+|-|\\*|/")
                .matcher(exp)
                .results()
                .map(MatchResult::group)
                .map(o -> mapToOperator(o))
                .collect(Collectors.toList());
    }


    private boolean isCellName(String name){
        return name.matches("^[A-Z]+[1-9]+$");
    }

    private List<Cell> getReferencedBy(Cell cell){

        List<Cell> referencingCells = new ArrayList<>();
        for(var c : cells.values()){
            if (getRefs(c.getExp()).contains(cell)){
                referencingCells.add(c);
            }
        }
        return referencingCells;
    }


    private void propagateUpdate(Cell cell) {

        List<Cell> cellsToCheck = new ArrayList<>();
        cellsToCheck.addAll(getReferencedBy(cell));

        while (!cellsToCheck.isEmpty()) {
            // Pop the cell from check list
            Cell current = cellsToCheck.get(0);
            cellsToCheck.remove(0);

            //Update cell
            Double value = evaluate(current.getExp());
            current.updateValue(value);

            cellsToCheck.addAll(getReferencedBy(current));
        }
    }


    private Operator mapToOperator(String op){
        switch (op){
            case "+": return new AdditionOperator();
            case "-": return new SubtractionOperator();
            default: throw new IllegalArgumentException("Unsupported operator");
        }
    }

}
