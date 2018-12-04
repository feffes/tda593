package mdsd;

import project.Point;

import java.util.Set;

public class BoundedArea implements Area {

    private Set<Point> boundary;

    public BoundedArea(Set<Point> boundary) {

        this.boundary = boundary;
    }

    @Override
    public boolean isInside(IRobot robot) {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }
}
