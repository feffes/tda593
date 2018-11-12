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

		Boundary w1 = new HorizontalBoundary(-5.0f, -5.0f, 5.0f, e, color);
		Boundary w2 = new HorizontalBoundary(5.0f, -5.0f, 5.0f, e, color);
		Boundary w3 = new VerticalBoundary(5.0f, -5.0f, 5.0f, e, color);
		Boundary w4 = new VerticalBoundary(-5.0f, -5.0f, 5.0f, e, color);

		AbstractWall roomWall1 = new HorizontalWall(-1f, 4.5f, 3.5f, e, color);
		AbstractWall roomWall2 = new HorizontalWall(-4.5f, 4.5f, 1f, e, color);
		AbstractWall roomWall3 = new VerticalWall(4.5f, -4.5f, -1f, e, color);
		AbstractWall roomWall4 = new VerticalWall(1f, -4.5f, -1f, e, color);

		Set<Robot> robots = new HashSet<>();
		Robot robot1 = new Robot(new Point(0, 0), "Robot 1");
		Robot robot2 = new Robot(new Point(1, 3), "Robot 2");

		robots.add(robot1);
		robots.add(robot2);
				
		AbstractSimulatorMonitor controller = new SimulatorMonitor(robots, e);

	}

}
