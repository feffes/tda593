package mdsd.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Mission implements IMission {
    private LinkedList<IGoal> gls;

    public Mission(LinkedList<IGoal> gls) {
        this.gls = gls;
    }

    public Mission() {
        this.gls = new LinkedList<>();
    }

    public boolean reachedGoal(IRobot robot) {
        IGoal peeked = gls.peekFirst();
        if (peeked == null) {
        }
        if (peeked != null && peeked.isReached(robot)) {
            gls.removeFirst();
            return true;
        }
        return false;
    }

    @Override
    public List<String> getStringList() {
        List<String> stringList = new ArrayList<>();
        gls.forEach(g -> stringList.add(g.toString()));
        return stringList;
    }

    public void addGoal(IGoal goal) {
        gls.addLast(goal);
    }

    @Override
    public void addGoal(IGoal goal, int i) {

    }

    @Override
    public IGoal getNext() {
        return gls.getFirst();
    }

    @Override
    public Boolean hasNextGoal() {
        try {
            if (gls.getFirst() != null) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public void addGoal(int i, IGoal goal) {
        gls.add(i, goal);
    }
}
