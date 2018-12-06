package mdsd;

import project.Point;

import java.util.Set;

public class RectangleArea implements Area {

    private String name;
    private final float lower_x;
    private final float upper_x;
    private final float lower_z;
    private final float upper_z;
    private Set<Point> exits;

    private Point representativePoint;

    public RectangleArea(String name, float lower_x, float upper_x, float lower_z, float upper_z, Set<Point> exits) {
        this.name = name;
        this.lower_x = lower_x;
        this.upper_x = upper_x;
        this.lower_z = lower_z;
        this.upper_z = upper_z;
        this.exits = exits;

        representativePoint = new Point((upper_x - lower_x)/2 + lower_x, (upper_z - lower_z)/2 + lower_z);
    }

    @Override
    public boolean isInside(IRobot robot) {
        Point robotPosition = robot.getPosition();

        return robotPosition.getX() > lower_x && robotPosition.getX() < upper_x
                && robotPosition.getZ() > lower_z && robotPosition.getZ() < upper_z;
    }

    @Override
    public void setExits(Set<Point> exits) {
        this.exits = exits;
    }

    @Override
    public Point getRepresentativePoint() {
        return representativePoint;
    }

    @Override
    public void setRepresentativePoint(Point point) {
        representativePoint = point;
    }

    @Override
    public Set<Point> getExits() {
        return exits;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
