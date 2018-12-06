package Tests;

import mdsd.model.*;
import project.Point;
import simbad.sim.EnvironmentDescription;

import java.util.*;

public class TestUtils {

    public static IEnvironmentManager initEnvironment(EnvironmentDescription ed) {

        GridManager gm = new GridManager();
        gm.generateGrid(-10, -10, 10, 10, 0.1);
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


    public static Area initRoom1() {
        Set<Point> exits = new HashSet<>();
        exits.add(new Point(5, 2.5));

        return new RectangleArea("Room 1", 0, 5, 0, 5, exits);

    }

    public static Area initRoom2() {
        Set<Point> exits = new HashSet<>();
        exits.add(new Point(-5, 2.5));

        return new RectangleArea("Room 2", -5, 0, 0, 5, exits);

    }

    public static Area initRoom3() {
        Set<Point> exits = new HashSet<>();
        exits.add(new Point(-5, -2.5));

        return new RectangleArea("Room 3", -5, 0, -5, 0, exits);

    }

    public static Area initRoom4() {
        Set<Point> exits = new HashSet<>();
        exits.add(new Point(5, -2.5));

        return new RectangleArea("Room 4", 0, 5, -5, 0, exits);

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
            return false;
        }

        @Override
        public boolean isWaiting() {
            return false;
        }

        @Override
        public void setWaiting() {

        }
    }

    public static class DummyStrategy implements IStrategy {

        private Map<IGoal, List<Point>> iteratorMap;

        public DummyStrategy(Map<IGoal, List<Point>> iteratorMap){
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

}
