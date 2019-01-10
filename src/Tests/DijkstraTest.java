package Tests;

import mdsd.model.DijkstraSolver;
import mdsd.model.GridElement;
import mdsd.model.GridManager;
import org.junit.Test;
import simbad.sim.EnvironmentDescription;
import simbad.sim.HorizontalWall;

import java.awt.*;
import java.util.List;


public class DijkstraTest {

    @Test
    public void dijkstraCentralWallTest() {
        EnvironmentDescription ed = new EnvironmentDescription();
        GridManager gm = new GridManager();
        gm.generateGrid(-5, -5, 5, 5, 0.2);
        gm.addHorizantalWall(new HorizontalWall(0.0f, 5.0f, 4.4f, ed, Color.GRAY));
        DijkstraSolver ds = new DijkstraSolver(gm);
        List<GridElement> route = ds.solve(gm.translateToGrid(1.0d, 0.0d), gm.translateToGrid(-1.0d, 0.0d), 0);
        for (GridElement grd : route) {
            System.out.println(String.valueOf(grd.getX()) + ", " + String.valueOf(grd.getZ()));
        }
        System.out.println(route.toString());
        System.out.println(gm.gridToString(route));
    }

}
