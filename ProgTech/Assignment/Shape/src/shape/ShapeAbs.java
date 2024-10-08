/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package shape;
public abstract class ShapeAbs {
    protected double dotX;
    protected double dotY;

    public ShapeAbs(double dotX, double dotY) {
        this.dotX = dotX;
        this.dotY = dotY;
    }
    public abstract double getArea();
    public abstract double getC();
    public abstract String collectCat();

    public double getDiff() {
        return Math.abs(getArea()-getC());
    }
}
