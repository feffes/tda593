package demos;

import mdsd.betterproject.BetterAbstractSimulatorMonitor;
import mdsd.controller.AreaController;
import mdsd.controller.RobotController;
import mdsd.model.*;
import mdsd.utils.InitializeUtils;
import mdsd.view.PanicButton;
import mdsd.view.SimulatorMonitor;
import project.Point;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HospitalDemo extends AbstractDemo {
    private GridManager gm;

    public Robot robot1;
    public Robot robot2;
    public Robot robot3;
    public Robot robot4;
    public Set<Area> areas;


    public HospitalDemo(){
        super();
        this.gm = new GridManager();
        IEnvironmentManager env = new EnvironmentManager(this, gm);
        gm.generateGrid(-10,-10,10,10,0.5);

        env.addHorizontalBoundary(-10.0f, -10.0f, 10.0f);
        env.addHorizontalBoundary(10.0f, -10.0f, 10.0f);
        env.addVerticalBoundary(10.0f, -10.0f, 10.0f);
        env.addVerticalBoundary(-10.0f, -10.0f, 10.0f);

        //thickness always 0.3f
        //The outer square
        env.addHorizontalWall(-5f, -1.5f, 1.5f);
        env.addHorizontalWall(-5f, -5f, -3.5f);
        env.addHorizontalWall(-5f, 3.5f, 5f);

        env.addHorizontalWall(5f, -1.5f, 1.5f);
        env.addHorizontalWall(5f, -5f, -3.5f);
        env.addHorizontalWall( 5f, 3.5f, 5f);

        env.addVerticalWall(5f, -5f, 3f); //added door to outer wall
        env.addVerticalWall(-5f, -5f, 5f);

        //inner vertical walls
        env.addVerticalWall(0f, -1.5f, 1.5f);
        env.addVerticalWall(0f, -5f, -3.5f);
        env.addVerticalWall(0f, 3.5f, 5f);

        //inner horizontal walls
        env.addHorizontalWall(0f, -1.5f, 5f); //removed door
        env.addHorizontalWall(0f, -5f, -3.5f);
        env.addHorizontalWall(0f, 3.5f, 5f);

        //adding last surgery room
        //env.addVerticalWall(-9f,-3.5f,-5f); //left wall
        env.addHorizontalWall(-5f, 3.5f, 10f); //top wall
        env.addHorizontalWall(5f, 5f, 10f); //bottom wall

        robot1 = new Robot(new Point(-2, 4), "Robot 1",100); //starts in surgery room 001
        robot2 = new Robot(new Point(-2, -2), "Robot 2",100); //starts in surgery room 002
        robot3 = new Robot(new Point(2, -2), "Robot 3",100); //starts in surgery room 003
        robot4 = new Robot(new Point(2, 2), "Robot 4",100); //starts in surgery room 004


        areas = new HashSet<>();

        Area universityArea = initHospitalArea();
        areas.add(universityArea);

        Area room1 = initRoom1();
        areas.add(room1);

        Area room2 = initRoom2();
        areas.add(room2);

        Area room3 = initRoom3();
        areas.add(room3);

        Area room4 = initRoom4();
        areas.add(room4);

        IStrategy dijkstraStrategy =  new DijkstraStrategy(gm,1);
        dijkstraStrategy.setName("dijkstra");

        robot1 = new Robot(new Point(-7, 2), "Robot1", 10);
        robot2 = new Robot(new Point(-7, 1), "Robot2", 10);
        robot3 = new Robot(new Point(-7, -1), "Robot3", 10);
        robot4 = new Robot(new Point(-7, -2), "Robot4", 10);

        Set<Robot> robots = new HashSet<>(Arrays.asList(robot1, robot2, robot3, robot4));

        BetterAbstractSimulatorMonitor controller = new SimulatorMonitor(robots, this);

        List<IRobot> controlledRobots = Arrays.asList(robot1, robot2, robot3, robot4);

        Set<IStrategy> strategies = new HashSet<>(Arrays.asList(dijkstraStrategy));

        RobotController robotController = new RobotController(controlledRobots, areas, strategies, InitializeUtils.initGoalMap());

        PanicButton panicButton = new PanicButton(controller.getSimbadFrame().getDesktopPane(), robotController);
        panicButton.createButton();

        AreaController areaController = new AreaController(areas);
        for (IRobot r : controlledRobots) {
            r.addObserver(areaController);
            r.addObserver(robotController);
        }

        robotController.setMission(0, Arrays.asList("enter sur1", "enter sur2", "exit hospital"), "dijkstra");
        robotController.setMission(1, Arrays.asList("enter sur2", "enter sur3", "exit hospital"), "dijkstra");
        robotController.setMission(2, Arrays.asList("enter sur3", "enter sur4", "exit hospital"), "dijkstra");
        robotController.setMission(3, Arrays.asList("enter sur4", "enter sur1", "exit hospital"), "dijkstra");
    }

    public static Area initHospitalArea() {
        Set<Point> exits = new HashSet<>(Arrays.asList(new Point(5, 2.5), new Point(-5, 2.5),
                new Point(-5, -2.5), new Point(5, -2.5)));

        return new RectangleArea("hospital", -5, 5, -5, 5, exits, false);
    }

    public static Area initRoom1() {
        Set<Point> exits = new HashSet<>();
        exits.add(new Point(5, 2.5));
        exits.add(new Point(0, 2.5));
        exits.add(new Point(2.5, 0));

        return new RectangleArea("sur1", 0, 5, 0, 5, exits, true);

    }

    public static Area initRoom2() {
        Set<Point> exits = new HashSet<>();
        exits.add(new Point(0, 2.5));
        exits.add(new Point(-2.5, 0));
        exits.add(new Point(-5, 2.5));

        return new RectangleArea("sur2", -5, 0, 0, 5, exits, true);

    }

    static Area initRoom3() {
        Set<Point> exits = new HashSet<>();
        exits.add(new Point(-5, -2.5));
        exits.add(new Point(-2.5, 0));
        exits.add(new Point(0, -2.5));

        return new RectangleArea("sur3", -5, 0, -5, 0, exits, true);

    }

    static Area initRoom4() {
        Set<Point> exits = new HashSet<>();
        exits.add(new Point(2.5, 0));
        exits.add(new Point(5, -2.5));
        exits.add(new Point(0, -2.5));

        return new RectangleArea("sur4", 0, 5, -5, 0, exits, true);

    }

    static Area initRoom5(){
        Set<Point> exits = new HashSet<>();
        exits.add(new Point(2.5, 0));
        exits.add(new Point(5, -2.5));
        exits.add(new Point(0, -2.5));
        return new RectangleArea("con1",0,5,-5,0,exits,true);
    }
}
