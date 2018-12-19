package mdsd.model;

import mdsd.model.IGoal;
import mdsd.model.IMission;
import mdsd.model.IRobot;

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
        IGoal peeked = gls.peekFirst();
        if(peeked == null){
        }
        if (peeked != null && peeked.isReached(robot))
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

    @Override
    public Boolean hasNextGoal(){
        try{
            if(gls.getFirst() != null){
                return true;
            }
            return false;
        }catch (Exception e){
            return false;
        }
    }

    public void addGoal(int i,IGoal goal){
        gls.add(i, goal);
    }
}
