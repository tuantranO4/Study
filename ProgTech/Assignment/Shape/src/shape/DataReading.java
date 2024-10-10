package shape;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class DataReading {
    public final ArrayList<ShapeAbs> shapes;

    public DataReading() {
        shapes = new ArrayList<>();
    }

    public void read(String filename) throws FileNotFoundException, InvalidInputException {
        Scanner sc = new Scanner(new BufferedReader(new FileReader(filename)));
        if (!sc.hasNextInt()) {
            throw new InvalidInputException();
        }
        int shapesqt = sc.nextInt(); 
        while (sc.hasNext()) {
            ShapeAbs shapePoly;
            switch (sc.next()) {
                case "C":  
                    shapePoly = new Circle(sc.nextDouble(), sc.nextDouble(), sc.nextDouble());
                    break;
                case "H":  
                    shapePoly = new Hexagon(sc.nextDouble(), sc.nextDouble(), sc.nextDouble());
                    break;
                case "S":  
                    shapePoly = new Square(sc.nextDouble(), sc.nextDouble(), sc.nextDouble());
                    break;
                case "T": 
                    shapePoly = new Triangle(sc.nextDouble(), sc.nextDouble(), sc.nextDouble());
                    break;
                default:
                    throw new InvalidInputException("Invalid shape type in input file.");
            }
            shapes.add(shapePoly);
        }
        if (shapes.size() != shapesqt) {
            throw new InvalidInputException("Mismatch between the expected and actual number of shapes");
        }
        sc.close();
    }

    public void printres() {
        for (ShapeAbs s : shapes) {
            System.out.println(s + " -> Diff: " + s.getDiff());
        }
        
        ShapeAbs minDiffShape = shapes.stream()
            .min((sh1, sh2) -> Double.compare(sh1.getDiff(), sh2.getDiff()))
            .orElse(null);
        
        if (minDiffShape != null) {
            System.out.println("Shape with the smallest area-perimeter difference across all categories: " + minDiffShape);
        } else {
            System.out.println("No shapes");
        }
    }
}
