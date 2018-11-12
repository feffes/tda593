package mdsd;

import project.AbstractRobotSimulator;
import project.Point;

public class Robot extends AbstractRobotSimulator {

	
	
	public Robot(Point position, String name) {
		super(position, name);
		
	}

	@Override
	public String toString() {
		return "Robot " + this.getName();
	}

}