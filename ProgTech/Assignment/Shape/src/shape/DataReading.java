package shape;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;

public class DataReading {
    public final ArrayList<ShapeAbs> shapes;
    public DataReading() {
        shapes = new ArrayList<>();
    }
    public void read(String filename) throws FileNotFoundException, InvalidInputException {
        Scanner sc = null;
        try {
            sc = new Scanner(new BufferedReader(new FileReader(filename)));
            int numShape = sc.nextInt();

            for (int i = 0; i < numShape; i++) {
                ShapeAbs shape;
                String shapeType = sc.next();  
                double dotX = sc.nextDouble();  
                double dotY = sc.nextDouble(); 
                double side = sc.nextDouble();    
                switch (shapeType) {
                    case "C" -> shape = new Circle(dotX, dotY, side);
                    case "H" -> shape = new Hexagon(dotX, dotY, side);
                    case "S" -> shape = new Square(dotX, dotY, side);
                    case "T" -> shape = new Triangle(dotX, dotY, side);
                    default -> throw new InvalidInputException("input");
                }
                shapes.add(shape);
            }
            
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
    }

    public void print(){
        
    }
}
