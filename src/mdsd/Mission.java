package mdsd;

import project.Point;

import java.util.LinkedList;

public class Mission implements IMission {
    private LinkedList<IGoal> gls;

    public Mission(LinkedList<IGoal> gls) {
        this.gls = gls;
    }
    public Mission(){
        this.gls = new LinkedList<>();
    }

    public boolean reachedGoal(IRobot robot){
        if (gls.peekFirst().isReached(robot))
        {
            gls.removeFirst();
            return true;
        }
        return false;
    }



    public void addGoal(IGoal goal){
        gls.addLast(goal);
    }

    @Override
    public void addGoal(IGoal goal, int i) {

    }

    @Override
    public IGoal getNext() {
        return gls.getFirst();
    }

    public void addGoal(int i,IGoal goal){
        gls.add(i, goal);
    }
}
