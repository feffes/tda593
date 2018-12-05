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

    public Point getGoalPosition() {
        return point;
    }

    @Override
    public void setGoalPosition(IRobot robot) {

    }

    @Override
    public boolean isReached(IRobot robot) {
        return robot.isAtPosition(point);
    }
}
