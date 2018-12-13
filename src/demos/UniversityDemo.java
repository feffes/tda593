package demos;

import mdsd.betterproject.BetterAbstractSimulatorMonitor;
import mdsd.controller.AreaController;
import mdsd.controller.RobotController;
import mdsd.model.*;
import mdsd.view.SimulatorMonitor;
import project.Point;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UniversityDemo extends AbstractDemo{
    private GridManager gm;

    public Robot robot1;
    public Robot robot2;
    public Robot robot3;
    public Robot robot4;
    public Set<Area> areas;

    public UniversityDemo(){
        super();
        this.gm = new GridManager();
        IEnvironmentManager env = new EnvironmentManager(this, gm);
        gm.generateGrid(-10,-10,10,10,0.5);

        env.addHorizontalBoundary(-10.0f, -10.0f, 10.0f);
        env.addHorizontalBoundary(10.0f, -10.0f, 10.0f);
        env.addVerticalBoundary(10.0f, -10.0f, 10.0f);
        env.addVerticalBoundary(-10.0f, -10.0f, 10.0f);

        //thikness always 0.3f
        //The outer square
        env.addHorizontalWall(-5f, -1.5f, 1.5f);
        env.addHorizontalWall(-5f, -5f, -3.5f);
        env.addHorizontalWall(-5f, 3.5f, 5f);

        env.addHorizontalWall(5f, -1.5f, 1.5f);
        env.addHorizontalWall(5f, -5f, -3.5f);
        env.addHorizontalWall( 5f, 3.5f, 5f);

        env.addVerticalWall(5f, -5f, 5f);
        env.addVerticalWall(-5f, -5f, 5f);

        //inner vertical walls
        env.addVerticalWall(0f, -1.5f, 1.5f);
        env.addVerticalWall(0f, -5f, -3.5f);
        env.addVerticalWall(0f, 3.5f, 5f);
        //inner horizontal walls
        env.addHorizontalWall(0f, -1.5f, 1.5f);
        env.addHorizontalWall(0f, -5f, -3.5f);
        env.addHorizontalWall(0f, 3.5f, 5f);

        areas = new HashSet<>();

        Area room1 = initRoom1();
        areas.add(room1);
        Point exitRoom1 = room1.getExits().iterator().next();

        Area room2 = initRoom2();
        areas.add(room2);
        Point exitRoom2 = room2.getExits().iterator().next();

        Area room3 = initRoom3();
        areas.add(room3);
        Point exitRoom3 = room3.getExits().iterator().next();

        Area room4 = initRoom4();
        areas.add(room4);
        Point exitRoom4 = room4.getExits().iterator().next();

        IStrategy simpleStrategy =  new DijkstraStrategy(gm,1);//new SimpleStrategy(new HashSet<>(Arrays.asList(exitRoom2, exitRoom3)), "simple");
        simpleStrategy.setName("dijkstra");
        robot1 = new Robot(new Point(-7, 2), "Robot1", 10);
        robot2 = new Robot(new Point(-7, 1), "Robot2", 10);
        robot3 = new Robot(new Point(-7, -1), "Robot3", 10);
        robot4 = new Robot(new Point(-7, -2), "Robot4", 10);

        Set<Robot> robots = new HashSet<>(Arrays.asList(robot1, robot2, robot3, robot4));

        BetterAbstractSimulatorMonitor controller = new SimulatorMonitor(robots, this);

        List<IRobot> controlledRobots = Arrays.asList(robot1, robot2, robot3, robot4);

        Set<IStrategy> strategies = new HashSet<>(Arrays.asList(simpleStrategy));

        RobotController robotController = new RobotController(controlledRobots, areas, strategies);

        AreaController areaController = new AreaController(areas);

        for (IRobot r : controlledRobots) {
            r.addObserver(areaController);
            r.addObserver(robotController);
        }

        robotController.setMission(0, Arrays.asList("Room 1", "Room 2", "exit"), "dijkstra");
        robotController.setMission(1, Arrays.asList("Room 2", "Room 3", "exit"), "dijkstra");
        robotController.setMission(2, Arrays.asList("Room 3", "Room 4", "exit"), "dijkstra");
        robotController.setMission(3, Arrays.asList("Room 4", "Room 1", "exit"), "dijkstra");



    }

    private Area initRoom1() {
        Set<Point> exits = new HashSet<>();
        exits.add(new Point(5, 2.5));

        return new RectangleArea("Room 1", 0, 5, 0, 5, exits);

    }

    private Area initRoom2() {
        Set<Point> exits = new HashSet<>();
        exits.add(new Point(-5, 2.5));

        return new RectangleArea("Room 2", -5, 0, 0, 5, exits);

    }

    private Area initRoom3() {
        Set<Point> exits = new HashSet<>();
        exits.add(new Point(-5, -2.5));

        return new RectangleArea("Room 3", -5, 0, -5, 0, exits);

    }

    private Area initRoom4() {
        Set<Point> exits = new HashSet<>();
        exits.add(new Point(5, -2.5));

        return new RectangleArea("Room 4", 0, 5, -5, 0, exits);

    }

}
