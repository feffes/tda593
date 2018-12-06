package mdsd.model;


import project.Point;

import java.util.Set;

public interface Area {
    public boolean isInside(IRobot robot);

    public void setExits(Set<Point> exits);

    public Point getRepresentativePoint();

    public void setRepresentativePoint(Point point);

    public Set<Point> getExits();

    public String getName();

    public void setName(String name);
}
