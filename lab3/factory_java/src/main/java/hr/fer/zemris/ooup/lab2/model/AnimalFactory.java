package hr.fer.zemris.ooup.lab2.model;

public class AnimalFactory {

    public static Animal newInstance(String animalKind, String name) throws Exception {

        Class<Animal> descriptor = (Class<Animal>) Class
                .forName("hr.fer.zemris.ooup.lab2.model.plugins." + animalKind);

        Animal animal = (Animal) descriptor.getConstructor(String.class).newInstance(name);

        return animal;
    }
}
