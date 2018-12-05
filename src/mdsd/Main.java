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
        gm.generateGrid(-10,-10,10,10,0.5);
        IEnvironmentManager env = new EnvironmentManager(e, gm);


        Boundary w1 = new HorizontalBoundary(-10.0f, -10.0f, 10.0f;
		Boundary w2 = new HorizontalBoundary(10.0f, -10.0f, 10.0f;
		Boundary w3 = new VerticalBoundary(10.0f, -10.0f, 10.0f;
		Boundary w4 = new VerticalBoundary(-10.0f, -10.0f, 10.0f;

        //thikness always 0.3f
        //The outer square
		env.addHorizontalWall(-5f, -1.5f, 1.5f);
        env.addHorizontalWall(-5f, -5f, -3.5f);
        env.addHorizontalWall(-5f, 3.5f, 5f);

        env.addHorizontalWall(5f, -1.5f, 1.5f);
        env.addHorizontalWall(5f, -5f, -3.5f);
        env.addHorizontalWall( 5f, 3.5f, 5f);

		env.addVerticalWall(5f, -5f, 5f);
		env.addVerticalWall(-5f, -5f, 5f);

		//inner vertical walls
        env.addVerticalWall(0f, -1.5f, 1.5f);
        env.addVerticalWall(0f, -5f, -3.5f);
        env.addVerticalWall(0f, 3.5f, 5f);
        //inner horizontal walls
        env.addHorizontalWall(0f, -1.5f, 1.5f);
        env.addHorizontalWall(0f, -5f, -3.5f);
        env.addHorizontalWall(0f, 3.5f, 5f);

        Robot robot1 = new Robot(new Point(2, 2), "Robot 1",100);
        IStrategy strt = new DijkstraStrategy(gm);
        IMission mission = new Mission();
        mission.addGoal(new PointGoal(new Point(-7,-7)));
        RobotController rbtctl = new RobotController();
        rbtctl.addRobot(robot1);
        rbtctl.attachAll(robot1,strt,mission);
        Set<Robot> robots = new HashSet<>();
		robot1.setDestination(new Point(2.5,2.5));
		//Robot robot2 = new Robot(new Point(1, 3), "Robot 2");
		robots.add(robot1);
		//robots.add(robot2);
				
		AbstractSimulatorMonitor controller = new SimulatorMonitor(robots, e);
		rbtctl.startUpdater();

	}

}
