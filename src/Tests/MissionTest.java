
package Tests;

import demos.UniversityDemo;
import mdsd.betterproject.BetterAbstractSimulatorMonitor;
import mdsd.controller.AreaController;
import mdsd.controller.RobotController;
import mdsd.model.*;
import mdsd.view.SimulatorMonitor;
import org.junit.*;

import static org.junit.Assert.*;

import project.Point;
import simbad.sim.EnvironmentDescription;

import java.util.*;

public class MissionTest {

    @Test
    public void TestSetMission() {
        TestUtils.DummyRobot robot = new TestUtils.DummyRobot();
        robot.setPosition(new Point(0, 0));
        List<IRobot> robots = Arrays.asList(robot);

        Area room2 = TestUtils.initRoom2();
        Area room3 = TestUtils.initRoom3();
        Area room4 = TestUtils.initRoom4();
        Set<Area> areas = new HashSet<>(Arrays.asList(room2, room3, room4));

        Map<IGoal, List<Point>> dummyStrategyMap = new HashMap<>();
        dummyStrategyMap.put(new PointGoal(room2.getRepresentativePoint()),
                Arrays.asList(room2.getRepresentativePoint()));
        dummyStrategyMap.put(new PointGoal(room4.getRepresentativePoint()),
                Arrays.asList(room3.getRepresentativePoint(), room4.getRepresentativePoint()));
        dummyStrategyMap.put(new ExitGoal(areas), Arrays.asList(new Point(5, -2.5)));

        IStrategy dummyStrategy = new TestUtils.DummyStrategy(dummyStrategyMap);

        Set<IStrategy> strategies = new HashSet<>(Arrays.asList(dummyStrategy));

        RobotController robotController = new RobotController(robots, areas, strategies);
        robot.addObserver(robotController);

        robotController.setMission(0, Arrays.asList("Room 2", "Room 4", "exit"), "dummy");

        assertEquals(0, robot.getDestination().dist(room2.getRepresentativePoint()), .0001);

        robot.setPosition(room2.getRepresentativePoint());

        assertEquals(0, robot.getDestination().dist(room3.getRepresentativePoint()), .0001);

        robot.setPosition(room3.getRepresentativePoint());

        assertEquals(0, robot.getDestination().dist(room4.getRepresentativePoint()), .0001);

        robot.setPosition(room4.getRepresentativePoint());

        Point exitRoom4 = room4.getExits().iterator().next();
        assertEquals(0, robot.getDestination().dist(exitRoom4), .0001);

        robot.setPosition(exitRoom4);

        assertEquals(0, robot.getDestination().dist(exitRoom4), .0001);

        // Restart mission
        robotController.setMission(0, Arrays.asList("Room 2", "Room 4", "exit"), "dummy");

        assertEquals(0, robot.getDestination().dist(room2.getRepresentativePoint()), .0001);

    }

    @Test
    public void TestOneRobotInEachArea() throws InterruptedException {
        EnvironmentDescription ed = new EnvironmentDescription();
        GridManager gm = new GridManager();
        gm.generateGrid(-10, -10, 10, 10, 0.1);

        IEnvironmentManager environmentManager = TestUtils.initEnvironment(ed, gm);

        Area room1 = TestUtils.initRoom1();
        Set<Area> areas = new HashSet<>();
        areas.add(room1);

        IRobot robot1 = new Robot(new Point(5, 2.5), "Robot1", 10);
        IRobot robot2 = new Robot(new Point(6, 2.5), "Robot2", 10);

        Set<Robot> robots = new HashSet<>();
        robots.add((Robot) robot1);
        robots.add((Robot) robot2);

        BetterAbstractSimulatorMonitor controller = new SimulatorMonitor(robots, ed);

        AreaController areaController = new AreaController(areas);
        robot1.addObserver(areaController);
        robot2.addObserver(areaController);

        // Both robots goes to exit in room 2
        robot1.setDestination(new Point(-6, 2.5));
        robot2.setDestination(new Point(-6, 2.5));

        // wait till Robot 2 reaches boundary
        while (robot2.getPosition().getX() > 5) {
            Thread.sleep(100);
        }

        Point lastPos = robot2.getPosition();

        while (room1.isInside(robot1)) {
            assertTrue(robot2.isWaiting());
            assertEquals(lastPos.getX(), robot2.getPosition().getX(), .01);
            lastPos = robot2.getPosition();
            Thread.sleep(100);
        }

        assertFalse(room1.isInside(robot1));
        assertTrue(room1.isInside(robot2));

        Thread.sleep(200);

        assertFalse(robot2.isWaiting());
        assertEquals(-6, robot2.getDestination().getX(), .01);
    }

    @Test
    public void DoMission(){
        UniversityDemo ud = new UniversityDemo();
        Robot robot1 = ud.robot1;
        Robot robot2 = ud.robot2;
        Robot robot3 = ud.robot3;
        Robot robot4 = ud.robot4;
        Set<Area> areas = ud.areas;
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
        try {
            while (!room2.isInside(robot1)) {
                Thread.sleep(200);
            }

            while (!robot2.isWaiting()) {
                Thread.sleep(200);
            }

            while (room2.isInside(robot1)) {
                assertTrue(room3.isInside(robot4));
                assertTrue(robot2.isWaiting());
                assertTrue(robot3.isWaiting());

                Thread.sleep(200);
            }

            Thread.sleep(2000);

            while (room1.isInside(robot1)) {
                assertTrue(room4.isInside(robot4));
                assertTrue(room2.isInside(robot2));
                assertTrue(room3.isInside(robot3));

                Thread.sleep(450);
            }
            Thread.sleep(1000);

            while (room2.isInside(robot1) && robot1.getPosition().dist(exitRoom2) > 1) {
                assertTrue(room3.isInside(robot2));
                assertTrue(room2.isInside(robot1));
                assertTrue(room4.isInside(robot3));

                Thread.sleep(200);
            }

            Thread.sleep(2000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        assertTrue(robot1.isAtPosition(exitRoom2));
        assertTrue(robot2.isAtPosition(exitRoom3));
        assertTrue(robot3.isAtPosition(exitRoom4));
        assertTrue(robot4.isAtPosition(exitRoom1));
    }


}

