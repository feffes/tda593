package mdsd;

import project.AbstractRobotSimulator;
import project.Point;

import java.util.Set;

public interface IRobot {

    Point getPosition();

    void setDestination(Point point);

    void addObserver(RobotObserver observer);

    double getRadius();

    boolean isAtDestination();
}
