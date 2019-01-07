package mdsd.model;

import java.util.List;

public interface IMission {
    public void addGoal(IGoal goal);

    public void addGoal(IGoal goal, int i);

    public IGoal getNext();

    Boolean hasNextGoal();

    boolean reachedGoal(IRobot robot);

    List<String> getStringList();
}
