package mdsd;

public interface IMission {
    public void addGoal(IGoal goal);

    public void addGoal(IGoal goal, int i);

    public IGoal getNext();
}
