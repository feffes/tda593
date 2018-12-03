package mdsd;

import project.Point;

import java.util.List;
import java.util.Set;

public interface IStrategy {
    public List<Point> ComputeStrategy(List<Point> mission);
}
