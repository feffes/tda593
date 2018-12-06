package mdsd.model;

import project.Point;

public interface IRobot {

    Point getPosition();

    public Point getDestination();

    public void setDestination(Point point);

    void addObserver(RobotObserver observer);

    double getRadius();

    boolean isAtDestination();

    boolean isAtPosition(Point p);

    public boolean isWaiting();

    public void setWaiting();
}
