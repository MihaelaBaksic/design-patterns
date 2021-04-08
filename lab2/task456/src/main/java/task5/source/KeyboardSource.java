package task5.source;

import java.util.Scanner;

public class KeyboardSource implements DataSource{

    Scanner scanner = new Scanner(System.in);

    @Override
    public Long getNextNumber() {
        System.out.println("Enter a new positive integer or -1 to terminate");
        long i = scanner.nextLong();
        if( i < 0)
            return -1L;
        return i;
    }
}
