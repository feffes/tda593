package Rovu;

import javafx.geometry.Point3D;

import java.util.Set;

public interface IStrategy {
    public Set<Point3D> ComputeStrategy(Set<Point3D> mission);
}
