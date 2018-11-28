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

		Boundary w1 = new HorizontalBoundary(-10.0f, -10.0f, 10.0f, e, color);
		Boundary w2 = new HorizontalBoundary(10.0f, -10.0f, 10.0f, e, color);
		Boundary w3 = new VerticalBoundary(10.0f, -10.0f, 10.0f, e, color);
		Boundary w4 = new VerticalBoundary(-10.0f, -10.0f, 10.0f, e, color);

        //AbstractWall roomWall4 = new VerticalWall(1f, -4.5f, -1f, e, color);

        //The outer square
		AbstractWall roomWall1 = new HorizontalWall(-5f, -1.5f, 1.5f, e, color);
        AbstractWall roomWall11 = new HorizontalWall(-5f, -5f, -3.5f, e, color);
        AbstractWall roomWall12 = new HorizontalWall(-5f, 3.5f, 5f, e, color);

        AbstractWall roomWall13 = new HorizontalWall(5f, -1.5f, 1.5f, e, color);
        AbstractWall roomWall14 = new HorizontalWall(5f, -5f, -3.5f, e, color);
        AbstractWall roomWall15 = new HorizontalWall(5f, 3.5f, 5f, e, color);

		AbstractWall roomWall3 = new VerticalWall(5f, -5f, 5f, e, color);
		AbstractWall roomWall4 = new VerticalWall(-5f, -5f, 5f, e, color);

		//inner vertical walls
        AbstractWall roomWall5 = new VerticalWall(0f, -1.5f, 1.5f, e, color);
        AbstractWall roomWall6 = new VerticalWall(0f, -5f, -3.5f, e, color);
        AbstractWall roomWall7 = new VerticalWall(0f, 3.5f, 5f, e, color);
        //inner horizontal walls
        AbstractWall roomWall8 = new HorizontalWall(0f, -1.5f, 1.5f, e, color);
        AbstractWall roomWall9 = new HorizontalWall(0f, -5f, -3.5f, e, color);
        AbstractWall roomWall10 = new HorizontalWall(0f, 3.5f, 5f, e, color);

        Set<Robot> robots = new HashSet<>();
		Robot robot1 = new Robot(new Point(0, 0), "Robot 1");
		Robot robot2 = new Robot(new Point(1, 3), "Robot 2");

		robots.add(robot1);
		robots.add(robot2);
				
		AbstractSimulatorMonitor controller = new SimulatorMonitor(robots, e);

	}

}
