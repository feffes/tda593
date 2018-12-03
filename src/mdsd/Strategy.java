package mdsd;

import project.Point;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class Strategy implements IStrategy {

    @Override
    public List<Point> ComputeNext(IGoal goal, List<Point> otherRobots, Point robotPosition) {
        return null;
    }

    @Override
    public void addRestrictedArea(Area area, int numberOfRobots) {

    }
}
