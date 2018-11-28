package mdsd;

import project.AbstractRobotSimulator;
import project.Point;
import java.util.Set;

public class Robot extends AbstractRobotSimulator implements IRobot {

	private Point dest;
	
	public Robot(Point position, String name) {
		super(position, name);
		
	}

	@Override
	public String toString() {
		return "Robot " + this.getName();
	}

    @Override
    public void setDestination(Point dest){
	    this.dest = dest;
	    super.setDestination(dest);
    }

	public boolean isAtDestination(){
	    return this.getPosition().dist(dest) <= this.getAgent().getRadius();
    }
}