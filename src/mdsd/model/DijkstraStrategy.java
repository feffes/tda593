package mdsd.model;

import project.Point;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DijkstraStrategy implements IStrategy {
    DijkstraSolver solver;
    GridManager gm;
    int wallBuffer;
    private String name;

    public DijkstraStrategy(GridManager gm, int wallBuffer) {
        solver = new DijkstraSolver(gm);
        this.gm = gm;
        this.wallBuffer = wallBuffer;
    }

    @Override
    public Iterator<Point> ComputeRoute(IGoal goal, Point robotPosition) {

        try {
            List<Point> route = gm.translateToPoints(solver.solve(robotPosition, goal.getGoalPosition(), wallBuffer));
            route.add(goal.getGoalPosition());
            return route.iterator();

        } catch (DijkstraException e) {
            return null;
        }
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
