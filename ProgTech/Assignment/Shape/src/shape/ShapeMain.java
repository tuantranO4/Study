package shape;
import java.io.FileNotFoundException;

public class ShapeMain {

    public static void main(String[] args) {
        DataReading database = new DataReading();
        try {
            database.read("input.txt");
        } catch (FileNotFoundException ex) {
            System.out.println("File not found!");
            System.exit(-1);
        } catch (InvalidInputException ex) {
            System.out.println("Invalid input!");
            System.exit(-1);
        }
        database.printres();
    }

}
