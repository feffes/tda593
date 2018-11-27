package mdsd;

import project.Point;
import java.util.Set;

public interface IRobot {
    public void setMission(Set<Point> positionSet);

    public Point getPosition();

    public void setPosition(Point position);
}
