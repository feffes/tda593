package mdsd.model;

import project.Point;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class RoamingStrategy implements IStrategy {

    private String name;


    public RoamingStrategy(){

    }

    @Override
    public Iterator<Point> ComputeRoute(IGoal goal, Point robotPosition) {

        System.out.println("KOmmer vi hit??");

            List<Point> route = new ArrayList<>();

            double distance = calculateNewDistance(robotPosition, goal);

            double xVal;
            double yVal;

            if(robotPosition.getX() > goal.getGoalPosition().getX()){
                xVal = robotPosition.getX();
            }
            else{
                xVal = goal.getGoalPosition().getX();
            }

            double xDeltaVal = (robotPosition.getX() - goal.getGoalPosition().getX());
            double yDeltaVal = (robotPosition.getZ() - goal.getGoalPosition().getZ());
            double k = (xDeltaVal/yDeltaVal);
            double m = robotPosition.getZ() - (k*robotPosition.getX());
            int count = 0;

            Point nextPoint = robotPosition;
            double newDistance = distance;

            //while(!(nextPoint == goal.getGoalPosition())) {

                while(count != 100){

                    if (robotPosition.getX() > goal.getGoalPosition().getX()) {
                        xVal--;
                    }
                    else{
                        xVal++;
                    }
                    System.out.println("OCH HIT????");
                    count++;
                    yVal = calculateFunction(k, m ,xVal);

                    nextPoint = new Point(xVal, yVal);
                    route.add(nextPoint);

                    System.out.println("New distance:" + calculateNewDistance(nextPoint, goal));
                    System.out.println("This coordinate" + nextPoint.toString());
            }
            route.add(goal.getGoalPosition());
            return route.iterator();

    }
    private double calculateFunction(double k, double m, double xVal){

        double yVal;

        yVal = k*xVal + m;

        return yVal;

    }

    private double calculateNewDistance(Point robotpos, IGoal goal){

        double x1 = robotpos.getX();
        double y1 = robotpos.getZ();
        double x2 = goal.getGoalPosition().getX();
        double y2 = goal.getGoalPosition().getZ();
        double distance = Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));

        return distance;

    }

    @Override
    public void setName(String name) {
        this.name = name;

    }

    @Override
    public String getName() {
        return name;
    }
}
