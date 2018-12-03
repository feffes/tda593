package mdsd;

import project.AbstractRobotSimulator;
import project.Point;

import java.util.Set;

public interface IRobot {

    public Point getPosition();

    public void setDestination(Point point);
}
