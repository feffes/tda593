package mdsd.model;

import project.Point;

import java.util.Set;

public interface IEnvironmentManager {
    public void addHorizontalBoundary(float p1x, float p1z, float p2z);

    public void addVerticalBoundary(float p1z, float p1x, float p2x);

    public void addHorizontalWall(float p1x, float p1z, float p2z);

    public void addVerticalWall(float p1z, float p1x, float p2x);

    public Set<Point> getRoute(Point start, Point end);
}
