/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package shape;

/**
 *
 * @author ducan
 */
public class Circle extends ShapeAbs{
    protected double radius;
    public Circle(double dotX, double dotY, double radius) {
        super(dotX, dotY);
        if (radius <=0){
            throw new IllegalArgumentException("need positive value for sides/radius");
        }
        this.radius = radius;
    }
     @Override
    public double getArea() {
        return (Math.PI * radius * radius);
    }

    @Override
    public double getC() {
        return (2 * Math.PI * radius);
    }

    @Override
    public String collectCat(){
        return "C";
    }

    @Override
    public String toString(){
        return "Circle";
    }
}

