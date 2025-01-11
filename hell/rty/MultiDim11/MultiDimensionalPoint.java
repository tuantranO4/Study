package action.user;

import java.util.Arrays;
import java.util.Objects;

import action.Scalable;
import action.Undoable;

public class MultiDimensionalPoint implements Scalable, Undoable, Comparable<MultiDimensionalPoint> {
    private int[] coordinates;
    private int[] lastCoordinates;

    public MultiDimensionalPoint(int... coordinates) {
        this.coordinates = new int[coordinates.length];
        System.arraycopy(coordinates, 0, this.coordinates, 0, coordinates.length);

        lastCoordinates = new int[coordinates.length];
        System.arraycopy(coordinates, 0, lastCoordinates, 0, coordinates.length);
    }

    public int get(int idx) {
        return coordinates[idx];
    }

    public void set(int idx, int value) {
        saveToLast();

        coordinates[idx] = value;
    }

    @Override
    public void scale(int factor) {
        saveToLast();

        for (int i = 0; i < coordinates.length; ++i) {
            coordinates[i] *= factor;
        }
    }

    @Override
    public void undoLast() {
        int[] tmp = lastCoordinates;
        lastCoordinates = coordinates;
        coordinates = tmp;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
    
        if (other instanceof MultiDimensionalPoint mdp) {
            Arrays.equals(coordinates, mdp.coordinates);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(coordinates);
    }

    @Override
    public int compareTo(MultiDimensionalPoint other) {
        int dimDiff = coordinates.length - other.coordinates.length;
        if (dimDiff != 0) {
            return dimDiff;
        }

        for (int i = 0; i < coordinates.length; ++i) {
            int coordDiff = coordinates[i] - other.coordinates[i];
            if (coordDiff != 0) {
                return coordDiff;
            }
        }

        return 0;
    }

    private void saveToLast() {
        lastCoordinates = new int[coordinates.length];
        System.arraycopy(coordinates, 0, lastCoordinates, 0, coordinates.length);
    }
}
