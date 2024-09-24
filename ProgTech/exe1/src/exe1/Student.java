/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exe1;

/**
 *
 * @author ducan
 */
public class Student {
    private String name;
    private int ID;
    private double GPA;
    
    public Student(String name, int age) {
        this.name = name;
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int id) {
        this.ID = id;
    }
    
    public double getgpa(){
        return GPA;
    }
    public void setgpa(double val){
        this.GPA=val;
    }
}
