package mdsd;

import project.Point;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Set;

public class Strategy implements IStrategy {
    @Override
    public Set<Point> ComputeStrategy(Set<Point> mission) {
        return mission;//dumb strats yo
    }
}
