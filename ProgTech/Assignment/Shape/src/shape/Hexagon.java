package shape;

public class Hexagon extends ShapeAbs {
    protected double side;

    // Constructor
    public Hexagon(double dotX, double dotY, double side) {
        super(dotX, dotY);
        this.side = side;
    }

    @Override
    public double getArea() {
        return (((3 * Math.sqrt(3)) / 2) * side * side);
    }

    @Override
    public double getC() {
        return (6 * side);
    }
    @Override
    public String collectCat(){
        return "H";
    }
    @Override
    public String toString(){
        return "Hexagon";
    }
}
