package mdsd;
import java.util.HashSet;
import java.util.Set;
import project.Point;
import project.AbstractSimulatorMonitor;
import project.AbstractRobotSimulator;
import simbad.sim.AbstractWall;
import simbad.sim.Boundary;
import simbad.sim.EnvironmentDescription;
import simbad.sim.HorizontalBoundary;
import simbad.sim.HorizontalWall;
import simbad.sim.VerticalBoundary;
import simbad.sim.VerticalWall;
import java.awt.Color;
@SuppressWarnings("unused")
public class Main {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws InterruptedException {

		EnvironmentDescription e = new EnvironmentDescription();
		
		Color color = Color.GRAY;

        GridManager gm = new GridManager();
        gm.generateGrid(-10,-10,10,10,0.1);


		Boundary w1 = new HorizontalBoundary(-10.0f, -10.0f, 10.0f, e, color);
		Boundary w2 = new HorizontalBoundary(10.0f, -10.0f, 10.0f, e, color);
		Boundary w3 = new VerticalBoundary(10.0f, -10.0f, 10.0f, e, color);
		Boundary w4 = new VerticalBoundary(-10.0f, -10.0f, 10.0f, e, color);

        //gm.addVerticalWall(new VerticalWall(1f, -4.5f, -1f, e, color);
        //thikness always 0.3f
        //The outer square
		gm.addHorizantalWall(new HorizontalWall(-5f, -1.5f, 1.5f, e, Color.BLUE));
        gm.addHorizantalWall(new HorizontalWall(-5f, -5f, -3.5f, e, color));
        gm.addHorizantalWall(new HorizontalWall(-5f, 3.5f, 5f, e, color));
        //gm.addVerticalWall( new HorizontalWall(-5.3f, 3.5f, 5f, e, Color.CYAN);

        gm.addHorizantalWall(new HorizontalWall(5f, -1.5f, 1.5f, e, color));
        gm.addHorizantalWall(new HorizontalWall(5f, -5f, -3.5f, e, color));
        gm.addHorizantalWall( new HorizontalWall(5f, 3.5f, 5f, e, color));

		gm.addVerticalWall(new VerticalWall(5f, -5f, 5f, e, color));
		gm.addVerticalWall(new VerticalWall(-5f, -5f, 5f, e, color));

		//inner vertical walls
        gm.addVerticalWall(new VerticalWall(0f, -1.5f, 1.5f, e, color));
        gm.addVerticalWall(new VerticalWall(0f, -5f, -3.5f, e, color));
        gm.addVerticalWall(new VerticalWall(0f, 3.5f, 5f, e, color));
        //inner horizontal walls
        gm.addHorizantalWall(new HorizontalWall(0f, -1.5f, 1.5f, e, color));
        gm.addHorizantalWall(new HorizontalWall(0f, -5f, -3.5f, e, color));
        gm.addHorizantalWall(new HorizontalWall(0f, 3.5f, 5f, e, color));



        GridElement[][] grid = gm.getGrid();
        System.out.println("HElloHELLOHEllo");
        System.out.println( gm.gridToString());

        Set<Robot> robots = new HashSet<>();
		Robot robot1 = new Robot(new Point(2, 2), "Robot 1");
		//Robot robot2 = new Robot(new Point(1, 3), "Robot 2");
        robot1.setDestination(new Point(-2,-2));
		robots.add(robot1);
		//robots.add(robot2);
				
		AbstractSimulatorMonitor controller = new SimulatorMonitor(robots, e);

	}

}
