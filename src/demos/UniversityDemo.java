package demos;

import mdsd.betterproject.BetterAbstractSimulatorMonitor;
import mdsd.controller.AreaController;
import mdsd.controller.RobotController;
import mdsd.model.*;
import mdsd.utils.InitializeUtils;
import mdsd.view.AreaView;
import mdsd.view.PanicButton;
import mdsd.view.SimulatorMonitor;
import project.Point;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

        Area universityArea = initUniversityArea();
        areas.add(universityArea);

        Area room1 = initRoom1();
        areas.add(room1);

        Area room2 = initRoom2();
        areas.add(room2);

        Area room3 = initRoom3();
        areas.add(room3);

        Area room4 = initRoom4();
        areas.add(room4);

        IStrategy simpleStrategy =  new DijkstraStrategy(gm,1);
        simpleStrategy.setName("dijkstra");

        robot1 = new Robot(new Point(-7, 2), "Robot1", 10);
        robot2 = new Robot(new Point(-7, 1), "Robot2", 10);
        robot3 = new Robot(new Point(-7, -1), "Robot3", 10);
        robot4 = new Robot(new Point(-7, -2), "Robot4", 10);

        Set<Robot> robots = new HashSet<>(Arrays.asList(robot1, robot2, robot3, robot4));

        BetterAbstractSimulatorMonitor controller = new SimulatorMonitor(robots, this);

        List<IRobot> controlledRobots = Arrays.asList(robot1, robot2, robot3, robot4);

        Set<IStrategy> strategies = new HashSet<>(Arrays.asList(simpleStrategy));

        RobotController robotController = new RobotController(controlledRobots, areas, strategies, InitializeUtils.initGoalMap());

        PanicButton panicButton = new PanicButton(controller.getSimbadFrame().getDesktopPane(), robotController);
        panicButton.createButton();

        Set<String> robotNames = controlledRobots.stream().map(r -> r.toString()).collect(Collectors.toSet());
        AreaView areaView = new AreaView(robotNames);

        AreaController areaController = new AreaController(areas);
        areaController.addAreaView(areaView);

        for (IRobot r : controlledRobots) {
            r.addObserver(areaController);
            r.addObserver(robotController);
        }

        robotController.setMission(0, Arrays.asList("enter 1", "enter 2", "exit university"), "dijkstra");
        robotController.setMission(1, Arrays.asList("enter 2", "enter 3", "exit university"), "dijkstra");
        robotController.setMission(2, Arrays.asList("enter 3", "enter 4", "exit university"), "dijkstra");
        robotController.setMission(3, Arrays.asList("enter 4", "enter 1", "exit university"), "dijkstra");


    }

    public static Area initUniversityArea() {
        Set<Point> exits = new HashSet<>(Arrays.asList(new Point(5, 2.5), new Point(-5, 2.5),
                new Point(-5, -2.5), new Point(5, -2.5)));

        return new RectangleArea("university", -5, 5, -5, 5, exits, false);
    }

    public static Area initRoom1() {
        Set<Point> exits = new HashSet<>();
        exits.add(new Point(5, 2.5));
        exits.add(new Point(0, 2.5));
        exits.add(new Point(2.5, 0));

        return new RectangleArea("1", 0, 5, 0, 5, exits, true);

    }

    public static Area initRoom2() {
        Set<Point> exits = new HashSet<>();
        exits.add(new Point(0, 2.5));
        exits.add(new Point(-2.5, 0));
        exits.add(new Point(-5, 2.5));

        return new RectangleArea("2", -5, 0, 0, 5, exits, true);

    }

    public static Area initRoom3() {
        Set<Point> exits = new HashSet<>();
        exits.add(new Point(-5, -2.5));
        exits.add(new Point(-2.5, 0));
        exits.add(new Point(0, -2.5));

        return new RectangleArea("3", -5, 0, -5, 0, exits, true);

    }

    public static Area initRoom4() {
        Set<Point> exits = new HashSet<>();
        exits.add(new Point(2.5, 0));
        exits.add(new Point(5, -2.5));
        exits.add(new Point(0, -2.5));

        return new RectangleArea("4", 0, 5, -5, 0, exits, true);

    }

}
