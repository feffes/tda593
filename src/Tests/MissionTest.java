
package Tests;

import mdsd.*;
import mdsd.controller.AreaController;
import org.junit.*;

import static org.junit.Assert.*;

import project.AbstractSimulatorMonitor;
import project.Point;
import simbad.sim.EnvironmentDescription;

import java.util.*;

public class MissionTest {

    @Test
    public void TestSetMission() {
        TestUtils.DummyRobot robot = new TestUtils.DummyRobot();
        robot.setPosition(new Point(0,0));
        List<IRobot> robots = Arrays.asList(robot);

        Area room2 = TestUtils.initRoom2();
        Area room3 = TestUtils.initRoom3();
        Area room4 = TestUtils.initRoom4();
        Set<Area> areas = new HashSet<>(Arrays.asList(room2, room3, room4));

        Map<IGoal, List<Point>> dummyStrategyMap = new HashMap<>();
        dummyStrategyMap.put(new PointGoal(room2.getRepresentativePoint()),
                Arrays.asList(room2.getRepresentativePoint()));
        dummyStrategyMap.put(new PointGoal(room4.getRepresentativePoint()),
                Arrays.asList(room3.getRepresentativePoint(),room4.getRepresentativePoint()));
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
        IEnvironmentManager environmentManager = TestUtils.initEnvironment(ed);

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
            Thread.sleep(10);
        }

        assertFalse(room1.isInside(robot1));
        assertTrue(room1.isInside(robot2));

        Thread.sleep(200);

        assertFalse(robot2.isWaiting());
        assertEquals(-6, robot2.getDestination().getX(), .01);
    }

    @Test
    public void DoMission() throws InterruptedException {
        EnvironmentDescription ed = new EnvironmentDescription();
        IEnvironmentManager environmentManager = TestUtils.initEnvironment(ed);
        Set<Area> areas = new HashSet<>();

        Map<IGoal, List<Point>> dummyStrategyMap = new HashMap<>();
        IStrategy dummyStrategy = new TestUtils.DummyStrategy(dummyStrategyMap);

        Area room1 = TestUtils.initRoom1();
        areas.add(room1);

        Area room2 = TestUtils.initRoom2();
        areas.add(room2);

        Area room3 = TestUtils.initRoom3();
        areas.add(room3);

        Area room4 = TestUtils.initRoom4();
        areas.add(room4);

        Robot robot1 = new Robot(new Point(-7, 2), "Robot1", 10);
        Robot robot2 = new Robot(new Point(-7, 1), "Robot2", 10);
        Robot robot3 = new Robot(new Point(-7, -1), "Robot3", 10);
        Robot robot4 = new Robot(new Point(-7, -2), "Robot4", 10);

        Set<Robot> robots = new HashSet<>();
        robots.add(robot1);
        robots.add(robot2);
        robots.add(robot3);
        robots.add(robot4);

        Map<IRobot, IMission> robotMissionMap = initRobotMissionMap(areas, robot1, robot2, robot3, robot4);

        BetterAbstractSimulatorMonitor controller = new SimulatorMonitor(robots, ed);

        List<IRobot> controlledRobots = Arrays.asList(robot1, robot2, robot3, robot4);

        Set<IStrategy> strategies = new HashSet<>(Arrays.asList(dummyStrategy));

        RobotController robotController = new RobotController(controlledRobots, areas, strategies);

        AreaController areaController = new AreaController(areas);

        for (IRobot r : robotMissionMap.keySet()) {
            r.addObserver(areaController);
            r.addObserver(robotController);
        }

        robotController.setMission(1, Arrays.asList("Room 1", "Room 2", "exit"), "Dummy");
        robotController.setMission(2, Arrays.asList("Room 2", "Room 3", "exit"), "Dummy");
        robotController.setMission(3, Arrays.asList("Room 3", "Room 4", "exit"),"Dummy");
        robotController.setMission(4, Arrays.asList("Room 4", "Room 1", "exit"), "Dummy");

        while (!room2.isInside(robot1)) {
            Thread.sleep(100);
        }

        while (room2.isInside(robot1)) {
            // Robot 1 in room 2
            // Robot 2 waits to enter room 2
            // Robot 3 waits to enter room 3
            // Robot 4 in room 3

            assertTrue(room3.isInside(robot4));

            assertEquals(2.5, robot2.getPosition().getZ(), .01);
            assertEquals(-5, robot2.getPosition().getX(), .01);

            assertEquals(-2.5, robot3.getPosition().getZ(), .01);
            assertEquals(-5, robot3.getPosition().getX(), .01);

            Thread.sleep(100);
        }

        while (room1.isInside(robot1)) {
            // Robot 1 in room 1
            // Robot 2 in room 2

            // Robot 3 in room 3
            // Robot 4 in room 4
            assertTrue(room4.isInside(robot4));
            assertTrue(room2.isInside(robot2));
            assertTrue(room3.isInside(robot3));

            Thread.sleep(100);
        }

        while (room2.isInside(robot1)) {
            // Robot 1 in room 2
            // Robot 2 in room 3
            // Robot 3 in room 4
            // Robot 4 in room 1
            assertTrue(room3.isInside(robot2));
            assertTrue(room1.isInside(robot4));
            assertTrue(room4.isInside(robot3));
        }

        // Robot 1 at exit in room 2
        assertEquals(-5, robot1.getPosition().getX(), .01);
        assertEquals(2.5, robot1.getPosition().getZ(), .01);

        // Robot 2 at exit in room 3
        assertEquals(-5, robot2.getPosition().getX(), .01);
        assertEquals(-2.5, robot2.getPosition().getZ(), .01);

        // Robot 3 at exit in room 4
        assertEquals(5, robot1.getPosition().getX(), .01);
        assertEquals(-2.5, robot1.getPosition().getZ(), .01);

        // Robot 4 at exit in room 1
        assertEquals(5, robot1.getPosition().getX(), .01);
        assertEquals(2.5, robot1.getPosition().getZ(), .01);

    }

    private static Map<IRobot, IMission> initRobotMissionMap(Set<Area> areas, IRobot robot1, IRobot robot2,
                                                             IRobot robot3, IRobot robot4) {
        IGoal goalRoom1 = new PointGoal(new Point(2.5, 2.5));
        IGoal goalRoom2 = new PointGoal(new Point(-2.5, 2.5));
        IGoal goalRoom3 = new PointGoal(new Point(-2.5, -2.5));
        IGoal goalRoom4 = new PointGoal(new Point(2.5, -2.5));
        IGoal exitGoal1 = new ExitGoal(areas);
        IGoal exitGoal2 = new ExitGoal(areas);
        IGoal exitGoal3 = new ExitGoal(areas);
        IGoal exitGoal4 = new ExitGoal(areas);


        IMission mission1 = new Mission();
        mission1.addGoal(goalRoom1);
        mission1.addGoal(goalRoom2);
        mission1.addGoal(exitGoal1);

        IMission mission2 = new Mission();
        mission2.addGoal(goalRoom2);
        mission2.addGoal(goalRoom3);
        mission2.addGoal(exitGoal2);

        IMission mission3 = new Mission();
        mission3.addGoal(goalRoom3);
        mission3.addGoal(goalRoom4);
        mission3.addGoal(exitGoal3);

        IMission mission4 = new Mission();
        mission4.addGoal(goalRoom4);
        mission4.addGoal(goalRoom1);
        mission4.addGoal(exitGoal4);

        Map<IRobot, IMission> robotMissionMap = new HashMap<>();
        robotMissionMap.put(robot1, mission1);
        robotMissionMap.put(robot2, mission2);
        robotMissionMap.put(robot3, mission3);
        robotMissionMap.put(robot4, mission4);

        return robotMissionMap;
    }


}

