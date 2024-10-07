package shape;
public class Square extends ShapeAbs{
    private double side;

    public Square(double dotX, double dotY, double sideLength) {
        super(dotX, dotY);
        this.side = side;
    }

    @Override
    public double getArea() {
        return side * side;
    }

    @Override
    public double getC() {
        return 4 * side;
    }
}
