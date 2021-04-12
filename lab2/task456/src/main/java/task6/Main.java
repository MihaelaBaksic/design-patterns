package task6;

public class Main {

    public static void main(String[] args){

        Sheet s = new Sheet();

        s.set("A1", "2");
        s.set("A2", "5");
        s.set("A3", "A1+A2");

        s.set("A1", "4");
        s.set("A4", "A1+A3");

        s.set("A1", "A2");

        try{
            s.set("A2", "A4");
        }
        catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }


        System.out.println(s.getCells());
    }
}
