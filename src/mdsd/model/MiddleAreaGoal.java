package mdsd.model;
import java.util.Arrays;
import java.util.HashSet;

public class MiddleAreaGoal extends AreaGoal {

    @Override
    public void setGoalPosition(IRobot robot) {
        setGoalPosition(robot, new HashSet<>(Arrays.asList(area.getRepresentativePoint())));
    }

    @Override
    public boolean isReached(IRobot robot) {
        return area.isInside(robot) && robot.isAtPosition(goalPosition);
    }
}
