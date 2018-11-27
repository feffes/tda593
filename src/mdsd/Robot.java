package mdsd;

import project.AbstractRobotSimulator;
import project.Point;
import simbad.sim.*;
import java.util.Set;

public class Robot extends AbstractRobotSimulator implements IRobot {

	
	
	public Robot(Point position, String name) {
		super(position, name);
		
	}

	@Override
	public String toString() {
		return "Robot " + this.getName();
	}

	@Override
	public void setMission(Set<Point> positionSet) {

	}

	@Override
	public void setPosition(Point position) {

	}
}