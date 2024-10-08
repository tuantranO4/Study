package shape;
public class Square extends ShapeAbs{
    protected double side;

    public Square(double dotX, double dotY, double side) {
        super(dotX, dotY);
        if (side <=0){
            throw new IllegalArgumentException("need positive value for sides/radius");
        }
        this.side = side;
    }

    @Override
    public double getArea() {
        return (side * side);
    }

    @Override
    public double getC() {
        return (4 * side);
    }
    @Override
    public String collectCat(){
        return "S";
    }
    @Override
    public String toString(){
        return "Square";
    }
}
