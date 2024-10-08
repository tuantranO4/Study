package shape;

public class Triangle extends ShapeAbs {
    private double side;
    public Triangle(double dotX, double dotY, double sideLength) {
        super(dotX, dotY);
        this.side = side;
    }

    @Override
    public double getArea() {
        return ((Math.sqrt(3) / 4) * side * side);
    }

    @Override
    public double getC() {
        return (3 * side);
    }
    @Override
    public String collectCat(){
        return "T";
    }
}
