package hr.fer.zemris.ooup.lab2.model;

public class Main {

    public static void main(String[] args) throws Exception {

        AnimalFactory factory = new AnimalFactory();

        Animal a = factory.newInstance("Parrot", "Slavko");

        a.animalPrintGreeting();
        a.animalPrintMenu();

    }
}
