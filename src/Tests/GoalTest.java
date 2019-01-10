package Tests;

import demos.UniversityDemo;
import mdsd.model.*;
import org.junit.Test;
import project.Point;

import static org.junit.Assert.*;

public class GoalTest {

    @Test
    public void ExitAreaGoalTest() {
        Area room1 = UniversityDemo.initRoom1();

        TestUtils.DummyRobot robot = new TestUtils.DummyRobot();
        robot.setPosition(new Point(4.5, 2.5));

        ExitAreaGoal exitGoal = new ExitAreaGoal();
        exitGoal.setArea(room1);
        assertNull(exitGoal.getGoalPosition());
        exitGoal.setGoalPosition(robot);

        assertEquals(0, new Point(5, 2.5).dist(exitGoal.getGoalPosition()), .001);
    }

    @Test
    public void EnterAreaGoalTest() {
        Area room1 = UniversityDemo.initRoom1();

        TestUtils.DummyRobot robot = new TestUtils.DummyRobot();
        robot.setPosition(new Point(-2, 2.5));

        assertFalse(room1.isInside(robot));

        EnterAreaGoal enterAreaGoal = new EnterAreaGoal();
        enterAreaGoal.setArea(room1);
        assertNull(enterAreaGoal.getGoalPosition());
        enterAreaGoal.setGoalPosition(robot);

        robot.setPosition(enterAreaGoal.getGoalPosition());

        assertTrue(room1.isInside(robot));
    }

    @Test
    public void MiddleAreaGoalTest() {
        Area room1 = UniversityDemo.initRoom1();

        TestUtils.DummyRobot robot = new TestUtils.DummyRobot();
        robot.setPosition(new Point(0, 0));

        MiddleAreaGoal middleAreaGoal = new MiddleAreaGoal();
        assertNull(middleAreaGoal.getGoalPosition());
        middleAreaGoal.setArea(room1);
        middleAreaGoal.setGoalPosition(robot);

        assertEquals(0, room1.getRepresentativePoint().dist(middleAreaGoal.getGoalPosition()), .001);
    }

    @Test
    public void PointGoalTest() {

        TestUtils.DummyRobot robot = new TestUtils.DummyRobot();
        robot.setPosition(new Point(0, 0));

        PointGoal pointGoal = new PointGoal(new Point(2, 2));
        assertEquals(0, new Point(2, 2).dist(pointGoal.getGoalPosition()), .001);
    }
}
