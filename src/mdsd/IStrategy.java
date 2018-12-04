package mdsd;

import project.Point;

import java.util.Iterator;


public interface IStrategy {
    public Iterator<Point> ComputeNext(IGoal goal, Point robotPosition);

    public void addRestrictedArea(Area area, int numberOfRobots);
}
