package mdsd;

import project.Point;

import java.util.Iterator;


public interface IStrategy {
    public Iterator<Point> ComputeRoute(IGoal goal, Point robotPosition);

    public void setName(String name);

    public String getName();
}
