package task5.source;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileSource implements DataSource{

    private Scanner scanner;

    public FileSource(String PATH) throws FileNotFoundException {
        this.scanner = new Scanner(new File(PATH));
    }

    @Override
    public Long getNextNumber() {
        if (scanner.hasNextLong()){
            long i = scanner.nextLong();
            return i < 0 ? -1L : i;
        }
        return -1L;
    }
}
