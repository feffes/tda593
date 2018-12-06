package mdsd.model;

import project.Point;

import java.util.ArrayList;
import java.util.Iterator;

public class DijkstraStrategy implements IStrategy {
    DijkstraSolver solver;
    GridManager gm;
    int wallBuffer;
    public DijkstraStrategy(GridManager gm, int wallBuffer){
        solver = new DijkstraSolver(gm);
        this.gm = gm;
        this.wallBuffer = wallBuffer;
    }

    @Override
    public Iterator<Point> ComputeRoute(IGoal goal, Point robotPosition) {
        try {
            return gm.translateToPoints(solver.solve(robotPosition, goal.getGoalPosition(), wallBuffer)).iterator();
        } catch (DijkstraException e){
            return null;
        }
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public String getName() {
        return null;
    }
}
