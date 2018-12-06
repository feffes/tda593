package Tests;

import mdsd.model.GridElement;
import mdsd.model.GridManager;
import org.junit.*;
import project.Point;

public class GridTest {

    @Test
    public void translateTestGridOrigo(){
        GridManager gm = new GridManager();
        gm.generateGrid(-10,-20,10,20,1.0);
        GridElement[][] grdel = gm.getGrid();
        Assert.assertEquals(-10.0,gm.translateToPoint(grdel[0][0]).getX(),0.0001);
        Assert.assertEquals(-20.0,gm.translateToPoint(grdel[0][0]).getZ(),0.0001);
        Assert.assertEquals(gm.translateToGrid(new Point(-10,-20)), grdel[0][0]);
    }
    @Test
    public void translateTestRealOrigo(){
        GridManager gm = new GridManager();
        Point rorigo = new Point(0,0);
        gm.generateGrid(-10,-10,10,10,1.0);
        GridElement[][] grdel = gm.getGrid();
        Point trorigo = gm.translateToPoint(gm.translateToGrid(rorigo));
        Assert.assertEquals(rorigo.getX(),trorigo.getX(),0.001);
        Assert.assertEquals(rorigo.getZ(),trorigo.getZ(),0.001);

    }

}
