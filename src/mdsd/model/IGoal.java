package mdsd.model;


import project.Point;

public interface IGoal {
    public Point getGoalPosition();

    public void setGoalPosition(IRobot robot);

    public boolean isReached(IRobot robot);

}
