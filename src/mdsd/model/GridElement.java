package mdsd.model;

import project.Point;

public class GridElement {


    private Boolean free;
    private int x;
    private int z;

    GridElement(int x, int z) {
        this.x = x;
        this.z = z;
    }

    GridElement(int x, int z, Boolean free) {
        this.free = free;
        this.x = x;
        this.z = z;

    }

    public Boolean isDiagonalNeighbor(GridElement e) {
        return Math.abs(e.getX() - this.x) == 1
                && Math.abs(e.getZ() - this.z) == 1;
    }

    public Point getPoint() {
        return new Point(x, z);
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public Boolean isFree() {
        return free;
    }

    public void occupy() {
        this.free = false;
    }

    public void free() {
        this.free = true;
    }

    @Override
    public String toString() {
        return (free) ? " " : "#";
    }
}
