/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehicles;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author bli
 */
public class Database {

    private final ArrayList<Vehicle> vehicles;

    public Database() {
        vehicles = new ArrayList<>();
    }

    public void read(String filename) throws FileNotFoundException, InvalidInputException {
        Scanner sc = new Scanner(new BufferedReader(new FileReader(filename)));
        int numVehicles = sc.nextInt();
        while (sc.hasNext()) { //read in a line, not in a row. Check if line has ended or not
            Vehicle vehicle; //var declaration for subclasses polymorphism down below
            switch (sc.next()) { //sc.next(): returns the next com   plete token from the input as a String
            //Vehicle(<-> var) vehicle = switch(sc.next()) {...} 
                case "C":
                    vehicle = new Car(sc.next()); //subclass Car/Bus/Truck inherit vehicle superclass. the "sc.next()" returns Plate datas.
                    break;
                case "B":
                    vehicle = new Bus(sc.next());
                    break;
                case "T":
                    vehicle = new Truck(sc.next());
                    break;
                //case "T" -> vehicle = new Truck(sc.next());
                default:
                    throw new InvalidInputException();
            }
            int numRefuels = sc.nextInt();
            for (int i = 0; i < numRefuels; i++) { //iterate through the list: C PLATE 4 22 31 12 82 -> add refuel
                vehicle.addRefuel(sc.nextInt());
            }
            vehicles.add(vehicle);
        }
    }
    
    //this to print out the result on main 
    public void report() {
        System.out.println("Vehicles in the database:");
        for (Vehicle v : vehicles) {
            System.out.println(v);
        }
        System.out.println("Mean refuels: ");
        for (Vehicle vehicle: vehicles) {
            System.out.println(vehicle.plate + ": " + vehicle.meanRefuels());
        }
        String[] categories = {"C", "B", "T"};
        for (String cat : categories) { //for loop
            System.out.println("Refuels in category " + cat + ":");
            ArrayList<Vehicle> catVehicles = collectCategory(cat); //looping thru cat in {C,B,T}
            System.out.println("Most fuel refueled: " + catVehicles.stream().max(
                (vh1, vh2) -> vh1.sumRefuels().compareTo(vh2.sumRefuels()) //lambda function
                ).get());
            System.out.println("Least fuel refueled: " + catVehicles.stream().min(
                (vh1, vh2) -> vh1.sumRefuels().compareTo(vh2.sumRefuels()) //lambda function  
                ).get());
            System.out.println("Most times refueled: " + catVehicles.stream().max((vh1, vh2) -> vh1.numRefuels().compareTo(vh2.numRefuels())).get());
            System.out.println("Least times refueled: " + catVehicles.stream().min((vh1, vh2) -> vh1.numRefuels().compareTo(vh2.numRefuels())).get());
        }
    }

    public ArrayList<Vehicle> collectCategory(String category) { //use this one on report()
        ArrayList<Vehicle> catVehicles = new ArrayList<>();
        for (Vehicle v : vehicles) {
            if (v.getCategory().equals(category)) {
                catVehicles.add(v);
            }
        }
        return catVehicles;
    }   
    
    public void clear() {
        vehicles.clear();
    }

}
