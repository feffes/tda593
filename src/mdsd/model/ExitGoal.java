package mdsd.model;

import project.Point;

import java.util.Set;

public class ExitGoal implements IGoal {

    private Point goalPosition;
    private Set<Area> areas;

    public ExitGoal(Set<Area> areas) {
        this.areas = areas;
    }

    @Override
    public Point getGoalPosition() {
        return goalPosition;
    }

    @Override
    public void setGoalPosition(IRobot robot) {
        Point robotPosition = robot.getPosition();
        double closestDistance = Double.POSITIVE_INFINITY;
        Point closestExit = null;

        for (Area area : areas) {
            if (area.isInside(robot)) {
                for (Point exit : area.getExits()) {
                    double distance = robotPosition.dist(exit);
                    if (distance < closestDistance) {
                        closestDistance = distance;
                        closestExit = exit;
                    }
                }
            }
        }

        if (closestExit == null) {
            this.goalPosition = robotPosition;
        } else {
            this.goalPosition = closestExit;
        }
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof ExitGoal))
            return false;
        ExitGoal e = (ExitGoal) o;

        for(Area a:areas){
            if(!e.areas.contains(a)){
                return false;
            }
        }

        return true;

    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        for(Area a:areas){
            hashCode += a.hashCode();
        }

        return hashCode;
    }

    @Override
    public boolean isReached(IRobot robot) {
        return false;
    }
}
