package task5.action;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OutputToFile implements Action{

    private String PATH;

    public OutputToFile(String PATH){
        this.PATH = PATH;
    }

    @Override
    public void update(List<Long> numbers) throws IOException {

        FileWriter fileWriter = new FileWriter(PATH);
        BufferedWriter out = new BufferedWriter(fileWriter);

        Locale loc = new Locale("hr", "HR");
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, loc);
        DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.DEFAULT, loc);

        out.write("Date: " + dateFormat.format(new Date()));
        out.newLine();
        out.write("Time: " + timeFormat.format(new Date()));
        out.newLine();
        out.write("Numbers: " + numbers.toString().replaceAll("\\[|\\]", ""));
        out.newLine();

        out.close();
        fileWriter.close();
    }
}
