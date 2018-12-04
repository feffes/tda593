
package Tests;

import mdsd.*;
import mdsd.controller.AreaController;
import org.junit.*;

import static org.junit.Assert.*;

import project.AbstractSimulatorMonitor;
import project.Point;
import simbad.sim.EnvironmentDescription;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MissionTest {

    @Test
    public void TestOneRobotInEachArea() throws InterruptedException {
        EnvironmentDescription ed = new EnvironmentDescription();
        IEnvironmentManager environmentManager = initEnvironment(ed);

        Set<Point> room1Boundary = new HashSet<>();
        room1Boundary.add(new Point(0, 0));
        room1Boundary.add(new Point(0, 5));
        room1Boundary.add(new Point(5, 0));
        room1Boundary.add(new Point(5, 5));

        Area room1 = new RectangleArea(0, 5, 0, 5);
        Set<Area> areas = new HashSet<>();
        areas.add(room1);

        IRobot robot1 = new Robot(new Point(5, 2.5), "Robot1");
        IRobot robot2 = new Robot(new Point(6, 2.5), "Robot2");

        Set<Robot> robots = new HashSet<>();
        robots.add((Robot) robot1);
        robots.add((Robot) robot2);

        AbstractSimulatorMonitor controller = new SimulatorMonitor(robots, ed);

        AreaController areaController = new AreaController(areas);
        robot1.addObserver(areaController);
        robot2.addObserver(areaController);

        // Both robots goes to exit in room 2
        robot1.setDestination(new Point(-6, 2.5));
        robot2.setDestination(new Point(-6, 2.5));


        while (robot2.getPosition().getX() > 5) {
            // wait till Robot 2 reaches boundary
        }

        while (room1.isInside(robot1)) {
            System.out.println(robot2.getPosition().getX());
            assertTrue(Math.abs(robot2.getPosition().getX() - 5) < .001);
        }

        assertFalse(room1.isInside(robot1));
        assertTrue(room1.isInside(robot2));
    }

    @Test
    public void DoMission() {
        EnvironmentDescription ed = new EnvironmentDescription();
        IEnvironmentManager environmentManager = initEnvironment(ed);
        Map<IRobot, IMission> robotMissionMap = initRobotMissionMap(environmentManager);
        Set<Area> areas = initAreas();

        AreaController areaController = new AreaController(areas);

        for (IRobot r : robotMissionMap.keySet()) {
            r.addObserver(areaController);
        }


    }

    private static Set<Area> initAreas() {
        return null;
    }

    private static Map<IRobot, IMission> initRobotMissionMap(IEnvironmentManager environmentManager) {
        IGoal goalRoom1 = new PointGoal(new Point(2.5, 2.5));
        IGoal goalRoom2 = new PointGoal(new Point(-2.5, 2.5));
        IGoal goalRoom3 = new PointGoal(new Point(-2.5, -2.5));
        IGoal goalRoom4 = new PointGoal(new Point(2.5, -2.5));

        IGoal exitGoal = new ExitGoal(environmentManager);

        IRobot robot1 = new Robot(new Point(5, 2.5), "Robot1");
        IRobot robot2 = new Robot(new Point(6, 2.5), "Robot2");
        IRobot robot3 = new Robot(new Point(7, 2.5), "Robot3");
        IRobot robot4 = new Robot(new Point(8, 2.5), "Robot4");

        IMission mission1 = new Mission();
        mission1.addGoal(goalRoom1);
        mission1.addGoal(goalRoom2);
        mission1.addGoal(exitGoal);

        IMission mission2 = new Mission();
        mission2.addGoal(goalRoom2);
        mission2.addGoal(goalRoom3);
        mission2.addGoal(exitGoal);

        IMission mission3 = new Mission();
        mission3.addGoal(goalRoom3);
        mission3.addGoal(goalRoom4);
        mission3.addGoal(exitGoal);

        IMission mission4 = new Mission();
        mission4.addGoal(goalRoom4);
        mission4.addGoal(goalRoom1);
        mission4.addGoal(exitGoal);

        Map<IRobot, IMission> robotMissionMap = new HashMap<>();
        robotMissionMap.put(robot1, mission1);
        robotMissionMap.put(robot2, mission2);
        robotMissionMap.put(robot3, mission3);
        robotMissionMap.put(robot4, mission4);

        return robotMissionMap;
    }

    private static IEnvironmentManager initEnvironment(EnvironmentDescription ed) {

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
}

