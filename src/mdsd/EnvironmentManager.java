package mdsd;

import project.Point;
import simbad.sim.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.Color;

import project.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EnvironmentManager implements IEnvironmentManager {
    EnvironmentDescription ed;
    GridManager gm;
    DijkstraSolver ds;


    public EnvironmentManager(EnvironmentDescription ed, GridManager gm) {
        this.ed = ed;
        this.gm = gm;
        ds = new DijkstraSolver(gm);
    }

    public void addVerticalWall(float p1z, float p1x, float p2x) {
        gm.addVerticalWall(new VerticalWall(p1z, p1x, p2x, ed, Color.GRAY));
    }

    @Override
    public void addHorizontalBoundary(float p1x, float p1z, float p2z) {
        new HorizontalBoundary(p1x, p1z, p2z, ed, Color.GRAY);
    }

    @Override
    public void addVerticalBoundary(float p1z, float p1x, float p2x) {
        new VerticalBoundary(p1z, p1x, p2x, ed, Color.GRAY);
    }

    public void addHorizontalWall(float p1x, float p1z, float p2z) {
        gm.addHorizantalWall(new HorizontalWall(p1x, p1z, p2z, ed, Color.GRAY));
    }

    public void dijkstraSolve(Point start, Point dest) {
        List<GridElement> gridList = ds.solve(
                gm.translateToGrid(start.getX(), start.getZ()),
                gm.translateToGrid(dest.getX(), dest.getZ()));
        List<Point> points = new ArrayList<>();
        for (GridElement gel : gridList) {
            points.add(new Point(gm.translateToPoint(gel.getX(), gel.getZ()).getX(), gm.translateToPoint(gel.getX(), gel.getZ()).getZ()));
        }

    }

    @Override
    public Set<Point> getRoute(Point start, Point end) {
        throw new NotImplementedException();
    }
}
