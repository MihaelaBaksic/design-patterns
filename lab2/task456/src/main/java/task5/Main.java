package task5;

import task5.action.OutputToFile;
import task5.action.PrintAvg;
import task5.action.PrintMedian;
import task5.action.PrintSum;
import task5.source.FileSource;
import task5.source.KeyboardSource;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {

        KeyboardSource keyboardSource = new KeyboardSource();
        FileSource fileSource = new FileSource("src/numbers.in");

        NumberSequence numberSequence = new NumberSequence(fileSource);

        PrintAvg printAvg = new PrintAvg();
        PrintMedian printMedian = new PrintMedian();
        PrintSum printSum = new PrintSum();
        OutputToFile outputToFile = new OutputToFile("src/action.out");

        numberSequence.addAction(printAvg);
        numberSequence.addAction(printMedian);
        numberSequence.addAction(printSum);
        numberSequence.addAction(outputToFile);

        numberSequence.start();

        System.out.println(numberSequence.getNumbers());

    }
}
