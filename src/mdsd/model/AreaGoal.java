package mdsd.model;

import project.Point;

import java.util.Set;

public abstract class AreaGoal implements IGoal {

    protected Point goalPosition;
    protected Area area;

    public AreaGoal() {}

    public void setArea(Area area){
        this.area = area;
    }

    @Override
    public Point getGoalPosition() {
        return goalPosition;
    }

    public abstract void setGoalPosition(IRobot robot);

    protected void setGoalPosition(IRobot robot, Set<Point> potentialGoals) {
        Point robotPosition = robot.getPosition();
        double closestDistance = Double.POSITIVE_INFINITY;
        Point closestExit = null;

        for (Point potentialGoal : potentialGoals) {
            double distance = robotPosition.dist(potentialGoal);
            if (distance < closestDistance) {
                closestDistance = distance;
                closestExit = potentialGoal;
            }
        }

        if (closestExit == null) {
            this.goalPosition = robotPosition;
        } else {
            this.goalPosition = closestExit;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AreaGoal))
            return false;
        AreaGoal e = (AreaGoal) o;

        return area.equals(e.area);

    }

    @Override
    public int hashCode() {
        return area.hashCode();
    }

    @Override
    public abstract boolean isReached(IRobot robot);
}
