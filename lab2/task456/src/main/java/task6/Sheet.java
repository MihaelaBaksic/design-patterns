package task6;

import java.util.*;
import java.util.regex.*;
import java.util.stream.Collectors;

public class Sheet {

    private Map<String, Cell> cells;

    public Sheet(){
        this.cells = new HashMap<>();
    }

    public void set(String cellName, String content){

        cellName = cellName.toUpperCase(Locale.ROOT);
        content = content.toUpperCase(Locale.ROOT);

        // if cellName already exists in map cells, then update the cell and propagate
        //      the change to all cells holding a reference to it

        // else
        //      check for circular referencing, if it exists, throw exception
        //      create new cell and calculate its value


        if( cells.containsKey(cellName) ){

            Double value = evaluate(content);
            cells.get(cellName).setExp(content, value);

            //update all holding a reference to it
        }
        else{
            //check for circular referencing

            Double value = evaluate(content);
            Cell cell = new Cell(content, value);
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
                .map(r -> cell(r))
                .collect(Collectors.toList());
    }

    private Double evaluate(String exp){
        List<Cell> arguments = getRefs(exp);

        if (arguments.size()==0)
            return Double.parseDouble(exp);

        Double value = 0.;

        for(var argument : arguments){
            value += argument.getValue();
        }
        return value;
    }

    private List<String> getOperators(String exp){

        return Pattern.compile("\\+|-|\\*|/")
                .matcher(exp)
                .results()
                .map(MatchResult::group)
                .collect(Collectors.toList());
    }

    private void checkCircularReferences(Cell cell){

        List<Cell> checkedCells = new ArrayList<>();
        List<Cell> cellsToCheck = new ArrayList<>();

        cellsToCheck.addAll(getRefs(cell.getExp()));

        while(cellsToCheck.size() > 0){

            // bfs

        }

    }

    private boolean isCellName(String name){
        return name.matches("^[A-Z]+[1-9]+$");
    }

    private List<Cell> getReferencedBy(Cell cell){
        return null;
    }

}
