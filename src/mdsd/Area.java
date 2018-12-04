package mdsd;


import project.Point;

import java.util.Set;

public interface Area {
    public boolean isInside(IRobot robot);

    public void setExists(Set<Point> exits);

    public Set<Point> getExits();

    public String getName();

    public void setName(String name);
}
