package mdsd;

import project.Point;

import java.util.LinkedList;
import java.util.List;

public class Mission implements IMission {
    private LinkedList<IGoal> s;

    public Mission(LinkedList<IGoal> s) {
        this.s = s;
    }
    public Mission(){
        this.s = new LinkedList<>();
    }

//    public boolean reachedGoal(Point point){
//        if (s.peekFirst().isReached(point))
//        {
//            s.removeFirst();
//            return true;
//        }
//        return false;
//    }

    public void addGoal(IGoal goal){
        s.addLast(goal);
    }

    @Override
    public void addGoal(IGoal goal, int i) {

    }

    @Override
    public IGoal getNext() {
        return null;
    }

    public void addGoal(int i,IGoal goal){
        s.add(i, goal);
    }
}
