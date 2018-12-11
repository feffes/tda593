package mdsd;
import java.util.*;

import Tests.TestUtils;
import mdsd.betterproject.BetterAbstractSimulatorMonitor;
import mdsd.controller.RobotController;
import mdsd.model.*;
import mdsd.view.SimulatorMonitor;
import project.Point;
import simbad.sim.EnvironmentDescription;

import java.awt.Color;
@SuppressWarnings("unused")
public class Main {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws InterruptedException {

		EnvironmentDescription e = new EnvironmentDescription();
		
		Color color = Color.GRAY;

        GridManager gm = new GridManager();
        gm.generateGrid(-10,-10,10,10,0.5);
        IEnvironmentManager env = new EnvironmentManager(e, gm);


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

        Robot robot1 = new Robot(new Point(2, 2), "Robot 1",10);
        Set<Robot> robots = new HashSet<>();
        robots.add(robot1);
        BetterAbstractSimulatorMonitor controller = new SimulatorMonitor(robots, e);

        IStrategy strt = new DijkstraStrategy(gm, 1);
        strt.setName("dijkstra");
        Set<IStrategy> strts = new HashSet<>();
        strts.add(strt);

        Set<Area> areas = new HashSet<>();

        Area room1 = TestUtils.initRoom1();
        areas.add(room1);
        Point exitRoom1 = room1.getExits().iterator().next();

        Area room2 = TestUtils.initRoom2();
        areas.add(room2);
        Point exitRoom2 = room2.getExits().iterator().next();

        Area room3 = TestUtils.initRoom3();
        areas.add(room3);
        Point exitRoom3 = room3.getExits().iterator().next();

        Area room4 = TestUtils.initRoom4();
        areas.add(room4);
        Point exitRoom4 = room4.getExits().iterator().next();


        IMission mission = new Mission();
        mission.addGoal(new PointGoal(new Point(-7,-7)));
        mission.addGoal(new PointGoal(new Point(7,7)));

        List<IRobot> robotsList = new ArrayList<>();
        robotsList.add(robot1);

        RobotController rbtctl = new RobotController(robotsList,areas,strts);
        robot1.addObserver(rbtctl);

        rbtctl.setMission(0, Arrays.asList("Room 3", "Room 4", "exit"), "dijkstra");

        //rbtctl.addRobot(robot1);
        //rbtctl.attachAll(robot1,strt,mission);

		//robot1.setDestination(new Point(2.5,2.5));
		//Robot robot2 = new Robot(new Point(1, 3), "Robot 2");

		//robots.add(robot2);
				


	}

}
