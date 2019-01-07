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

        System.out.println("Inside of computeRoute");

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
            double k = (yDeltaVal/xDeltaVal);
            double m = robotPosition.getZ() - (k*robotPosition.getX());

        System.out.println("The function k: " + k + " The function m: "+ m);
            int count = 0;

            Point nextPoint = robotPosition;
            double newDistance = distance;

            while((newDistance > 0)) {

                System.out.println("Goal: " + goal.getGoalPosition().toString());
                System.out.println("Robot: " + robotPosition.toString());


                    if (robotPosition.getX() > goal.getGoalPosition().getX()) {
                        xVal = xVal - 0.5;
                    }
                    else {
                        xVal = xVal + 0.5;
                    }
                    count++;
                    yVal = calculateFunction(k, m ,xVal);

                    nextPoint = new Point(xVal, yVal);
                    route.add(nextPoint);

                    newDistance = calculateNewDistance(nextPoint,goal);

                    System.out.println("New distance:" + calculateNewDistance(nextPoint, goal)); //avståndet minskar ej, varför inte?
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
        double a = (x1-x2)*(x1-x2);
        double b = (y1-y2)*(y1-y2);
        double distance = Math.sqrt(a+b);

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
