package mdsd.model;

import project.Point;

public class EnterAreaGoal extends AreaGoal {

    @Override
    public boolean isReached(IRobot robot) {
        return area.isInside(robot);
    }

    @Override
    public int hashCode() {
        return area.hashCode();
    }

    @Override
    public void setGoalPosition(IRobot robot) {
        setGoalPosition(robot, area.getExits());

        Point reprPoint = area.getRepresentativePoint();
        double newX = goalPosition.getX() + .2 * (reprPoint.getX() - goalPosition.getX());
        double newZ = goalPosition.getZ() + .2 * (reprPoint.getZ() - goalPosition.getZ());
        goalPosition.setX(newX);
        goalPosition.setZ(newZ);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof EnterAreaGoal))
            return false;

        EnterAreaGoal other = (EnterAreaGoal) o;
        return area.equals(other.area);
    }
}
