package mdsd;

import project.Point;

public class ExitGoal implements IGoal {

    private IEnvironmentManager environmentManager;

    public ExitGoal(IEnvironmentManager environmentManager) {

        this.environmentManager = environmentManager;
    }

    @Override
    public Point getGoalPosition() {
        return null;
    }

    @Override
    public void setGoalPosition(IRobot robot) {

    }

    @Override
    public boolean isReached(IRobot robot) {
        return false;
    }
}
