package mdsd;

import project.Point;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Set;

public class EnvironmentManager implements IEnvironmentManager {

    @Override
    public void addHorizontalWall(float p1x, float p1z, float p2x) {

    }

    @Override
    public void addVerticalWall(float p1z, float p1x, float p2x) {

    }

    @Override
    public Set<Point> getRoute(Point start, Point end) {
        throw new NotImplementedException();
    }
}
