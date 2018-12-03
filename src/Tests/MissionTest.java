
package Tests;

import mdsd.*;
import org.junit.*;
import project.Point;

public class MissionTest {


    @Test
    public void DoMission() {
        IEnvironmentManager environmentManager = initTestEnvironment();

        Point startPoint = new Point(5, 2.5);
        Point exitRoom2 = new Point(-5, 2.5);
        Point exitRoom3 = new Point(-2.5, -5);
        Point exitRoom4 = new Point(5, -2.5);

        IGoal goalRoom1 = new PointGoal(new Point(2.5, 2.5));
        IGoal goalRoom2 = new PointGoal(new Point(-2.5, 2.5));
        IGoal goalRoom3 = new PointGoal(new Point(-2.5, -2.5));
        IGoal goalRoom4 = new PointGoal(new Point(2.5, -2.5));

        IGoal exitGoalRoom2 = new PointGoal(exitRoom2);
        IGoal exitGoalRoom3 = new PointGoal(exitRoom3);
        IGoal exitGoalRoom4 = new PointGoal(exitRoom4);

        IRobot robot1 = new Robot(new Point(5, 2.5), "Robot1");
        IRobot robot2 = new Robot(new Point(6, 2.5), "Robot2");
        IRobot robot3 = new Robot(new Point(7, 2.5), "Robot3");
        IRobot robot4 = new Robot(new Point(8, 2.5), "Robot4");

        IMission mission1 = new Mission();
        mission1.addGoal(goalRoom1);
        mission1.addGoal(goalRoom2);
        mission1.addGoal(exitGoalRoom2);

        IMission mission2 = new Mission();
        mission2.addGoal(goalRoom2);
        mission2.addGoal(goalRoom3);
        mission2.addGoal(exitGoalRoom3);

        IMission mission3 = new Mission();
        mission3.addGoal(goalRoom3);
        mission3.addGoal(goalRoom4);
        mission3.addGoal(exitGoalRoom4);


    }

    private IEnvironmentManager initTestEnvironment() {
        IEnvironmentManager environmentManager = new EnvironmentManager();

        environmentManager.addHorizontalWall(-10.0f, -10.0f, 10.0f);
        environmentManager.addHorizontalWall(10.0f, -10.0f, 10.0f);
        environmentManager.addVerticalWall(10.0f, -10.0f, 10.0f);
        environmentManager.addVerticalWall(-10.0f, -10.0f, 10.0f);

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

