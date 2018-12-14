package mdsd.model;

public class ExitAreaGoal extends AreaGoal {

    @Override
    public boolean isReached(IRobot robot) {
        return robot.isAtPosition(goalPosition);
    }

    @Override
    public int hashCode() {
        return area.hashCode();
    }

    @Override
    public void setGoalPosition(IRobot robot) {
        setGoalPosition(robot, area.getExits());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ExitAreaGoal))
            return false;

        ExitAreaGoal other = (ExitAreaGoal) o;
        return area.equals(other.area);
    }

}
