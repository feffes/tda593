package Rovu;

import javafx.geometry.Point3D;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Set;

public class Robot implements IRobot{

    @Override
    public void setMission(Set<Point3D> positionSet) {
        throw new NotImplementedException();
    }

    @Override
    public Point3D getPosition() {
        throw new NotImplementedException();
    }

    @Override
    public void setPosition(Point3D position) {
        throw new NotImplementedException();
    }
}
