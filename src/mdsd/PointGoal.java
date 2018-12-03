package mdsd;

import project.Point;

public class PointGoal implements IGoal {
    private Point point;

    public PointGoal(Point point) {
        this.point = point;
    }

    public Point getPoint() {
        return point;
    }

    @Override
    public Point getGoalPosition() {
        return null;
    }

    @Override
    public void setGoalPosition(Point position) {

    }

    @Override
    public boolean isReached(IRobot robot) {
        return false;
    }
}
