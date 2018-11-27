package Rovu;

import javafx.geometry.Point3D;

import java.util.Set;

public interface IRobot {
    public void setMission(Set<Point3D> positionSet);

    public Point3D getPosition();

    public void setPosition(Point3D position);
}
