package mdsd;

import project.Point;

import java.util.Set;

public interface IStrategy {
    public Set<Point> ComputeStrategy(Set<Point> mission);
}
