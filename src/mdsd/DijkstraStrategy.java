package mdsd;

import project.Point;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class DijkstraStrategy implements IStrategy {
    DijkstraSolver solver;
    GridManager gm;
    public DijkstraStrategy(GridManager gm){
        solver = new DijkstraSolver(gm);
        this.gm = gm;
    }

    @Override
    public List<Point> ComputeNext(IGoal goal, List<Point> otherRobots, Point robotPosition) {
        return gm.translateToPoints(solver.solve(robotPosition,goal.getGoalPosition()));
    }

    @Override
    public void addRestrictedArea(Area area, int numberOfRobots) {

    }
}
