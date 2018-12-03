package mdsd;

import project.Point;

import java.util.List;
import java.util.Set;

public interface IStrategy {
    public List<Point> ComputeNext(IGoal goal, List<Point> otherRobots, Point robotPosition);

    public void addRestrictedArea(Area area, int numberOfRobots);
}
