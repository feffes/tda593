package mdsd;

import project.Point;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Set;

public class EnvironmentManager implements IEnvironmentManager {
    @Override
    public void addWall(Set<Point> boundary) {
        throw new NotImplementedException();
    }

    @Override
    public Set<Point> getRoute(Point start, Point end) {
        throw new NotImplementedException();
    }
}
