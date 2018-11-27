package Rovu;

import javafx.geometry.Point3D;

import java.util.Set;

public interface IEnvironmentManager {
    public void addWall(Set<Point3D> boundary);

    public Set<Point3D> getRoute(Point3D start, Point3D end);
}
