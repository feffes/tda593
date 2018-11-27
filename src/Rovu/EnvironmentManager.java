package Rovu;

import javafx.geometry.Point3D;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Set;

public class EnvironmentManager implements IEnvironmentManager {
    @Override
    public void addWall(Set<Point3D> boundary) {
        throw new NotImplementedException();
    }

    @Override
    public Set<Point3D> getRoute(Point3D start, Point3D end) {
        throw new NotImplementedException();
    }
}
