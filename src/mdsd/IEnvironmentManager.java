package mdsd;

import project.Point;

import java.util.Set;

public interface IEnvironmentManager {
    public void addWall(Set<Point> boundary);

    public Set<Point> getRoute(Point start, Point end);
}
