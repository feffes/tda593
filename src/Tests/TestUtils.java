package Tests;

import mdsd.*;
import project.Point;
import simbad.sim.EnvironmentDescription;

import java.util.HashSet;
import java.util.Set;

public class TestUtils {

    public static IEnvironmentManager initEnvironment(EnvironmentDescription ed) {

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

    public static Area initRoom1() {
        Set<Point> exits = new HashSet<>();
        exits.add(new Point(5, 2.5));
        exits.add(new Point(0, 2.5));
        exits.add(new Point(2.5, 0));



        return new RectangleArea("Room 1", 0, 5, 0, 5, exits);

    }

}
