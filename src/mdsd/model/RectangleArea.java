package mdsd.model;

import mdsd.model.Area;
import mdsd.model.IRobot;
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

    @Override
    public int hashCode(){
        return (int)(lower_x + 31*upper_x + 31*31*lower_z + 31*31*31*upper_z);
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof  RectangleArea))
            return false;

        RectangleArea a = (RectangleArea)o;

        float sumDiff = 0;
        sumDiff += Math.abs(lower_x - a.lower_x);
        sumDiff += Math.abs(lower_z - a.lower_z);
        sumDiff += Math.abs(upper_x - a.upper_x);
        sumDiff += Math.abs(upper_z - a.upper_z);

        return sumDiff < .001;
    }
}
