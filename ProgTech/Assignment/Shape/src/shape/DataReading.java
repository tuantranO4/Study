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
        Scanner sc = new Scanner(new BufferedReader(new FileReader(filename)));
        int shapesqt=sc.nextInt();
        while (sc.hasNext()) {
            ShapeAbs shapePoly;
            switch(sc.next()) { 
                    case "C":
                    shapePoly = new Circle(sc.nextDouble(),sc.nextDouble(),sc.nextDouble()); 
                        break;
                    case "H":
                    shapePoly = new Hexagon(sc.nextDouble(),sc.nextDouble(),sc.nextDouble()); 
                        break;
                    case "S":
                    shapePoly = new Square(sc.nextDouble(),sc.nextDouble(),sc.nextDouble()); 
                        break;
                    case "T":
                    shapePoly = new Triangle(sc.nextDouble(),sc.nextDouble(),sc.nextDouble()); 
                        break;
                    default:
                        throw new InvalidInputException("shape input");
            }
        }
        }


    public void printres(){
        System.out.println("Vehicles in the database:");
        for (ShapeAbs s : shapes) {
            System.out.println(s);
        }
        String[] ShapeCat = {"C", "H", "S", "T"};

        for (String cat : ShapeCat) { 
            ArrayList<ShapeAbs> catVehicles = collectCat(cat); 
                System.out.println("Least fuel refueled: " + catVehicles.stream()
                .min((vh1, vh2) -> Double.compare(vh1.getDiff(), vh2.getDiff()))
                .get());
            }
    }

    protected ArrayList<ShapeAbs> collectCat(String cat){
        ArrayList<ShapeAbs> catShapes = new ArrayList<>();
        for (ShapeAbs v : catShapes) {
            if (v.collectCat().equals(cat)) {
                catShapes.add(v);
            }
        }
        return catShapes;
    }
}
