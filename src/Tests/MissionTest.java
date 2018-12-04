
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
        IEnvironmentManager environmentManager = TestUtils.initEnvironment(ed);

        Area room1 = TestUtils.initRoom1();
        Set<Area> areas = new HashSet<>();
        areas.add(room1);

        IRobot robot1 = new Robot(new Point(5, 2.5), "Robot1", 10);
        IRobot robot2 = new Robot(new Point(6, 2.5), "Robot2", 10);

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

        // wait till Robot 2 reaches boundary
        while (robot2.getPosition().getX() > 5) {
            Thread.sleep(100);
        }

        Point lastPos = robot2.getPosition();

        while (room1.isInside(robot1)) {
            Thread.sleep(10);
            assertEquals(lastPos.getX(), robot2.getPosition().getX(), .01);
            lastPos = robot2.getPosition();
            //assertTrue(Math.abs(robot2.getPosition().getX() - 5) < .1);
        }

        assertFalse(room1.isInside(robot1));
        assertTrue(room1.isInside(robot2));

        Thread.sleep(200);

        assertEquals(-6, robot2.getDestination().getX(), .01);
    }

    @Test
    public void DoMission() {
        EnvironmentDescription ed = new EnvironmentDescription();
        IEnvironmentManager environmentManager = TestUtils.initEnvironment(ed);
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
        IGoal exitGoal = new PointGoal(new Point(2.5, -2.5));
        //IGoal exitGoal = new ExitGoal(environmentManager);

        IRobot robot1 = new Robot(new Point(5, 2.5), "Robot1", 100);
        IRobot robot2 = new Robot(new Point(6, 2.5), "Robot2", 100);
        IRobot robot3 = new Robot(new Point(7, 2.5), "Robot3", 100);
        IRobot robot4 = new Robot(new Point(8, 2.5), "Robot4", 100);

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


}

