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
        // the change to all cells holding a reference to it

        // check for circular referencing, if it exists, throw exception

        try{
            double value = Double.parseDouble(content);
            Cell cell = new Cell(content, value);
            cells.put(cellName, cell);
        }
        catch (NumberFormatException e){

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

    private boolean checkCircularReferences(Cell cell){
        return true;
    }

    private boolean isCellName(String name){
        return name.matches("^[A-Z]+[1-9]+$");
    }

    private List<Cell> getReferencedBy(Cell cell){
        return null;
    }

}
