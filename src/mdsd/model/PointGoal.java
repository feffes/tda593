package mdsd.model;

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
    public boolean equals(Object o) {
        if (!(o instanceof PointGoal))
            return false;
        PointGoal p = (PointGoal) o;

        if (p.point.dist(this.point) < .00001)
            return true;

        return false;

    }

    @Override
    public int hashCode() {
        return 31 * (int) point.getX() + (int) point.getZ();
    }


    @Override
    public boolean isReached(IRobot robot) {
        return robot.isAtPosition(point);
    }
}
