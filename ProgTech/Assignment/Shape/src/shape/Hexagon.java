package shape;

public class Hexagon extends ShapeAbs {
    private double side;

    // Constructor
    public Hexagon(double dotX, double dotY, double sideLength) {
        super(dotX, dotY);
        this.side = side;
    }

    @Override
    public double getArea() {
        return (3 * Math.sqrt(3) / 2) * side * side;
    }

    @Override
    public double getC() {
        return 6 * side;
    }
}
