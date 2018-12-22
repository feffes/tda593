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

    private Robot robot1;
    private Robot robot2;
    private Robot robot3;
    private Robot robot4;
    private Set<Area> areas;


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
        env.addHorizontalWall(5f, 3.5f, 5f);

        env.addVerticalWall(5f, -2.5f, 2.5f); //added door to outer wall
        env.addVerticalWall(-5f, -5f, 5f);

        //inner vertical walls
        env.addVerticalWall(0f, -1.5f, 1.5f);
        env.addVerticalWall(0f, -5f, -3.5f);
        env.addVerticalWall(0f, 3.5f, 5f);

        //inner horizontal walls
        env.addHorizontalWall(0f, -1.5f, 1.5f); //removed door
        env.addHorizontalWall(0f, -5f, -3.5f);
        env.addHorizontalWall(0f, 3.5f, 5f);

        //adding last surgery room
        //env.addVerticalWall(-9f,-3.5f,-5f); //left wall
        env.addHorizontalWall(-5f, 5f, 10f); //top wall
        env.addHorizontalWall(5f, 5f, 10f); //bottom wall

        areas = new HashSet<>();

        Area universityArea = initHospitalArea();
        areas.add(universityArea);

        Area surRoom1 = initSurRoom1();
        areas.add(surRoom1);

        Area surRoom2 = initSurRoom2();
        areas.add(surRoom2);

        Area surRoom3 = initSurRoom3();
        areas.add(surRoom3);

        Area surRoom4 = initSurRoom4();
        areas.add(surRoom4);

        Area confRoom1 = initConfRoom1();
        areas.add(confRoom1);

        IStrategy dijkstraStrategy =  new DijkstraStrategy(gm,1);
        dijkstraStrategy.setName("dijkstra");

        robot1 = new Robot(new Point(-2.5, -2.5), "Robot1", 10);
        robot2 = new Robot(new Point(2.5, -2.5), "Robot2", 10);
        robot3 = new Robot(new Point(-2.5, 2.5), "Robot3", 10);
        robot4 = new Robot(new Point(2.5, 2.5), "Robot4", 10);

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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        robotController.setMission(0, Arrays.asList("enter conf1", "enter sur1"), "dijkstra");
        robotController.setMission(1, Arrays.asList("enter conf1", "enter sur2", "enter conf1", "enter sur2"), "dijkstra");
        robotController.setMission(2, Arrays.asList("enter conf1", "enter sur3", "exit hospital"), "dijkstra");
        robotController.setMission(3, Arrays.asList("enter conf1", "enter sur4", "exit hospital"), "dijkstra");

    }

    private static Area initHospitalArea() {
        Set<Point> exits = new HashSet<>(Arrays.asList(
                new Point(5, 2.5),
                new Point(-5, 2.5),
                new Point(-5, -2.5),
                new Point(5, -2.5)
        ));

        return new RectangleArea("hospital", -5, 5, -5, 10, exits, false);
    }

    private static Area initSurRoom4() {
        Set<Point> exits = new HashSet<>();
        exits.add(new Point(5, 2.5));
        exits.add(new Point(0, 2.5));
        exits.add(new Point(2.5, 0));
        exits.add(new Point(3.75, 5));
        return new RectangleArea("sur4", 0, 5, 0, 5, exits, true);

    }

    private static Area initSurRoom3() {
        Set<Point> exits = new HashSet<>();
        exits.add(new Point(0, 2.5));
        exits.add(new Point(-2.5, 0));
        exits.add(new Point(-5, 2.5));
        exits.add(new Point(-3.75, 5));
        return new RectangleArea("sur3", -5, 0, 0, 5, exits, true);

    }

    private static Area initSurRoom1() {
        Set<Point> exits = new HashSet<>();
        exits.add(new Point(-5, -2.5));
        exits.add(new Point(-2.5, 0));
        exits.add(new Point(0, -2.5));
        return new RectangleArea("sur1", -5, 0, -5, 0, exits, true);

    }

    private static Area initSurRoom2() {
        Set<Point> exits = new HashSet<>();
        exits.add(new Point(2.5, 0));
        exits.add(new Point(5, -2.5));
        exits.add(new Point(0, -2.5));
        return new RectangleArea("sur2", 0, 5, -5, 0, exits, true);

    }

    private static Area initConfRoom1(){
        Set<Point> exits = new HashSet<>();
        exits.add(new Point(3.75, 5));
        exits.add(new Point(-3.75, 5));
        return new RectangleArea("conf1",-5,5,5,10,exits,true);
    }
}
