package mdsd;


import project.Point;

public interface IGoal {
    public Point getGoalPosition();

    public void setGoalPosition(Point position);

    public boolean isReached(IRobot robot);

}
