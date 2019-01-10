
package Tests;

import demos.UniversityDemo;
import mdsd.betterproject.BetterAbstractSimulatorMonitor;
import mdsd.controller.AreaController;
import mdsd.controller.RobotController;
import mdsd.model.*;
import demos.utils.InitializeUtils;
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

        Area university = UniversityDemo.initUniversityArea();
        Area room2 = UniversityDemo.initRoom2();
        Area room3 = UniversityDemo.initRoom3();
        Area room4 = UniversityDemo.initRoom4();
        Set<Area> areas = new HashSet<>(Arrays.asList(university, room2, room3, room4));

        Map<IGoal, List<Point>> dummyStrategyMap = new HashMap<>();

        Point pointGoalPoint = new Point(2.13, 3);
        dummyStrategyMap.put(new PointGoal(pointGoalPoint), Arrays.asList(pointGoalPoint));

        Point enterGoalPoint1 = new Point(2, 0);
        EnterAreaGoal enterAreaGoal = new EnterAreaGoal();
        enterAreaGoal.setArea(room3);
        robot.setPosition(enterGoalPoint1);
        enterAreaGoal.setGoalPosition(robot);
        Point enterGoalPoint2 = enterAreaGoal.getGoalPosition();

        dummyStrategyMap.put(enterAreaGoal, Arrays.asList(enterGoalPoint1, enterGoalPoint2));

        MiddleAreaGoal middleAreaGoal = new MiddleAreaGoal();
        middleAreaGoal.setArea(room2);

        dummyStrategyMap.put(middleAreaGoal, Arrays.asList(room2.getRepresentativePoint()));

        ExitAreaGoal exitAreaGoal = new ExitAreaGoal();
        exitAreaGoal.setArea(university);

        dummyStrategyMap.put(exitAreaGoal, Arrays.asList(new Point(5, -2.5)));

        IStrategy dummyStrategy = new TestUtils.DummyStrategy(dummyStrategyMap);

        Set<IStrategy> strategies = new HashSet<>(Arrays.asList(dummyStrategy));

        robot.setPosition(new Point(0, 0));
        List<IRobot> robots = Arrays.asList(robot);


        RobotController robotController = new RobotController(robots, areas, strategies, InitializeUtils.initGoalMap());

        robot.addObserver(robotController);

        robotController.setMission(0, Arrays.asList("point 2.13 3", "enter 3", "middle 2", "exit university"), "dummy");

        assertEquals(0, robot.getDestination().dist(pointGoalPoint), .0001);

        robot.setPosition(pointGoalPoint);

        assertEquals(0, robot.getDestination().dist(enterGoalPoint1), .0001);

        robot.setPosition(enterGoalPoint1);

        assertEquals(0, robot.getDestination().dist(enterGoalPoint2), .0001);

        robot.setPosition(enterGoalPoint2);

        assertTrue(room3.isInside(robot));

        assertEquals(0, robot.getDestination().dist(room2.getRepresentativePoint()), .0001);

        robot.setPosition(room2.getRepresentativePoint());

        assertEquals(0, robot.getDestination().dist(new Point(5, -2.5)), .0001);

        // Restart mission
        robotController.setMission(0, Arrays.asList("point 2.13 3", "enter 3", "middle 2", "exit university"), "dummy");

        assertEquals(0, robot.getDestination().dist(pointGoalPoint), .0001);

    }

    @Test
    public void TestOneRobotInEachArea() throws InterruptedException {
        EnvironmentDescription ed = new EnvironmentDescription();
        GridManager gm = new GridManager();
        gm.generateGrid(-10, -10, 10, 10, 0.1);

        IEnvironmentManager environmentManager = TestUtils.initEnvironment(ed, gm);

        Area room1 = UniversityDemo.initRoom1();
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
    public void TestUniversityDemo() {
        UniversityDemo ud = new UniversityDemo();
        Robot robot1 = ud.robot1;
        Robot robot2 = ud.robot2;
        Robot robot3 = ud.robot3;
        Robot robot4 = ud.robot4;

        List<Robot> robots = Arrays.asList(robot1, robot2, robot3, robot4);
        Set<Area> areas = ud.areas;
        Set<Point> universityExits = areas.stream().filter(a -> a.getName().equals("university"))
                .findFirst().get().getExits();

        Area room1 = areas.stream().filter(a -> a.getName().equals("1")).findFirst().get();

        Area room2 = areas.stream().filter(a -> a.getName().equals("2")).findFirst().get();

        Area room3 = areas.stream().filter(a -> a.getName().equals("3")).findFirst().get();

        Area room4 = areas.stream().filter(a -> a.getName().equals("4")).findFirst().get();

        List<Area> rooms = Arrays.asList(room1, room2, room3, room4);

        Map<Robot, LinkedList<Area>> visitedRooms = new HashMap<>();
        visitedRooms.put(robot1, new LinkedList<>());
        visitedRooms.put(robot2, new LinkedList<>());
        visitedRooms.put(robot3, new LinkedList<>());
        visitedRooms.put(robot4, new LinkedList<>());

        boolean allOnExit = false;

        while (!allOnExit) {
            allOnExit = true;

            for (Robot r : robots) {
                LinkedList<Area> visited = visitedRooms.get(r);
                for (Area room : rooms) {
                    if ((visited.size() < 1 || !visited.getLast().equals(room)) && room.isInside(r)) {
                        visited.add(room);
                    }
                }

                boolean reachedExit = false;
                for (Point e : universityExits) {
                    reachedExit = reachedExit || r.isAtPosition(e);
                }

                allOnExit = allOnExit && reachedExit;
            }
        }

        List<Area> robot1Visited = visitedRooms.get(robot1);
        assertEquals(room2, robot1Visited.get(0));
        assertEquals(room1, robot1Visited.get(1));
        assertEquals(room2, robot1Visited.get(2));

        List<Area> robot2Visited = visitedRooms.get(robot2);
        assertEquals(room2, robot2Visited.get(0));
        assertEquals(room3, robot2Visited.get(1));

        List<Area> robot3Visited = visitedRooms.get(robot3);
        assertEquals(room3, robot3Visited.get(0));
        assertEquals(room4, robot3Visited.get(1));

        List<Area> robot4Visited = visitedRooms.get(robot4);
        assertEquals(room3, robot4Visited.get(0));
        assertEquals(room4, robot4Visited.get(1));
        assertEquals(room1, robot4Visited.get(2));
    }


}

