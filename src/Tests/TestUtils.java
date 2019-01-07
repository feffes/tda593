package Tests;

import mdsd.model.*;
import mdsd.view.IMissionView;
import project.Point;
import simbad.sim.EnvironmentDescription;

import java.util.*;

public class TestUtils {

    public static IEnvironmentManager initEnvironment(EnvironmentDescription ed, GridManager gm) {

        IEnvironmentManager environmentManager = new EnvironmentManager(ed, gm);

        environmentManager.addHorizontalWall(-5f, -1.5f, 1.5f);
        environmentManager.addHorizontalWall(-5f, -5f, -3.5f);
        environmentManager.addHorizontalWall(-5f, 3.5f, 5f);
        //gm.addVerticalWall( new HorizontalWall(-5.3f, 3.5f, 5f, e, Color.CYAN);

        environmentManager.addHorizontalWall(5f, -1.5f, 1.5f);
        environmentManager.addHorizontalWall(5f, -5f, -3.5f);
        environmentManager.addHorizontalWall(5f, 3.5f, 5f);

        environmentManager.addVerticalWall(5f, -5f, 5f);
        environmentManager.addVerticalWall(-5f, -5f, 5f);

        //inner vertical walls
        environmentManager.addVerticalWall(0f, -1.5f, 1.5f);
        environmentManager.addVerticalWall(0f, -5f, -3.5f);
        environmentManager.addVerticalWall(0f, 3.5f, 5f);
        //inner horizontal walls
        environmentManager.addHorizontalWall(0f, -1.5f, 1.5f);
        environmentManager.addHorizontalWall(0f, -5f, -3.5f);
        environmentManager.addHorizontalWall(0f, 3.5f, 5f);

        return environmentManager;
    }

    public static class DummyRobot implements IRobot {
        Point destination;
        Point position;
        List<RobotObserver> observers;

        public DummyRobot() {
            observers = new ArrayList<>();
        }

        @Override
        public Point getPosition() {
            return position;
        }

        public void setPosition(Point position) {
            this.position = position;

            for (RobotObserver observer : observers) {
                observer.update(this);
            }
        }

        @Override
        public Point getDestination() {
            return destination;
        }

        @Override
        public void setDestination(Point destination) {
            this.destination = destination;
        }

        @Override
        public void addObserver(RobotObserver observer) {
            observers.add(observer);
        }

        @Override
        public double getRadius() {
            return 0;
        }

        @Override
        public boolean isAtDestination() {
            return position.dist(destination) < .0001;
        }

        @Override
        public boolean isAtPosition(Point p) {
            return position.dist(p) < .0001;
        }

        @Override
        public boolean isWaiting() {
            return false;
        }

        @Override
        public void setWaiting() {

        }

        @Override
        public void stop() {
            System.out.println("Stop");
        }
    }

    public static class DummyStrategy implements IStrategy {

        private Map<IGoal, List<Point>> iteratorMap;

        public DummyStrategy(Map<IGoal, List<Point>> iteratorMap) {
            this.iteratorMap = iteratorMap;
        }

        @Override
        public Iterator<Point> ComputeRoute(IGoal goal, Point robotPosition) {
            return iteratorMap.get(goal).iterator();
        }

        @Override
        public void setName(String name) {

        }

        @Override
        public String getName() {
            return "dummy";
        }
    }

    public static class SimpleStrategy implements IStrategy {

        private Set<Point> notDestinationPoints;
        private String name;

        public SimpleStrategy(Set<Point> notDestinationPoints, String name) {

            this.notDestinationPoints = notDestinationPoints;
            this.name = name;
        }

        @Override
        public Iterator<Point> ComputeRoute(IGoal goal, Point robotPosition) {
            double zDiff = Math.abs(goal.getGoalPosition().getZ() - robotPosition.getZ());
            double xDiff = Math.abs(goal.getGoalPosition().getX() - robotPosition.getX());

            if (zDiff < .1 || xDiff < .1) {
                return Arrays.asList(goal.getGoalPosition()).iterator();
            }

            Point closestPoint = null;
            double minDistance = Double.POSITIVE_INFINITY;
            for (Point p : notDestinationPoints) {
                double distance = goal.getGoalPosition().dist(p);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestPoint = p;
                }
            }

            return Arrays.asList(closestPoint, goal.getGoalPosition()).iterator();


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

    public static class DummyMissionView implements IMissionView {

        @Override
        public void updateMission(int robotIndex, List<String> mission) {
            System.out.println("Robot " + robotIndex);
            mission.stream().forEach(g -> System.out.println(g));
        }
    }

}
