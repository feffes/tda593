package mdsd;

import project.Point;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class DijkstraStrategy implements IStrategy {
    DijkstraSolver solver;
    GridManager gm;
    public DijkstraStrategy(GridManager gm){
        solver = new DijkstraSolver(gm);
        this.gm = gm;
    }

    @Override
    public Iterator<Point> ComputeNext(IGoal goal, Point robotPosition) {
        return gm.translateToPoints(solver.solve(robotPosition,goal.getGoalPosition())).iterator();
    }

    @Override
    public void addRestrictedArea(Area area, int numberOfRobots) {

    }
}
