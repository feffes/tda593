package Tests;

import mdsd.betterproject.BetterAbstractSimulatorMonitor;
import mdsd.model.*;
import mdsd.view.SimulatorMonitor;
import org.junit.*;
import static org.junit.Assert.*;

import project.Point;
import simbad.sim.EnvironmentDescription;

import java.util.HashSet;
import java.util.Set;

public class GoalTest {

    @Test
    public void ExitGoalTest(){
        EnvironmentDescription ed = new EnvironmentDescription();
        IEnvironmentManager environmentManager = TestUtils.initEnvironment(ed);

        Robot robot = new Robot(new Point(4, 3), "Robot1", 10);
        Set<Robot> robots = new HashSet<>();
        robots.add(robot);

        BetterAbstractSimulatorMonitor controller = new SimulatorMonitor(robots, ed);

        Area room1 = TestUtils.initRoom1();
        Set<Area> areas = new HashSet<>();
        areas.add(room1);


        IGoal exitGoal = new ExitGoal(areas);
        exitGoal.setGoalPosition(robot);
        assertEquals(5, exitGoal.getGoalPosition().getX(), .0001);
        assertEquals(2.5, exitGoal.getGoalPosition().getZ(), .0001);
    }
}
