package mdsd;

import project.AbstractRobotSimulator;
import project.Point;

import java.util.HashSet;
import java.util.Set;

public class Robot extends RoboSim implements IRobot {

	private Point dest;
	private Set<RobotObserver> observers;
	private RobotPositionChecker positionChecker;
	private boolean isWaiting;
	
	public Robot(Point position, String name, int updateMillis) {
		super(position, name);
		observers = new HashSet<>();
		isWaiting = false;

		positionChecker = new RobotPositionChecker(position, updateMillis);
		positionChecker.start();
	}

	@Override
	public String toString() {
		return "Robot " + this.getName();
	}


	@Override
	public Point getDestination() {
		return dest;
	}

	@Override
    public void setDestination(Point dest){
	    this.dest = dest;
	    super.setDestination(dest);
	    isWaiting = false;
    }
    public double getRadius(){
		return this.getAgent().getRadius();
    }

	@Override
	public void addObserver(RobotObserver observer) {
		observers.add(observer);
	}

	@Override
	public void setWaiting(){
		setDestination(this.getPosition());
		isWaiting = true;
	}

	@Override
	public boolean isWaiting(){
		return isWaiting;
	}

	private void notifyObservers(){
		for(RobotObserver r:observers){
			r.update(this);
		}
	}

    public boolean isAtPosition(Point p){
	    return super.isAtPosition(p);
    }

	public boolean isAtDestination(){
        return dest==null || super.isAtPosition(dest);
	   // return dest==null || this.getPosition().dist(dest) <= this.getAgent().getRadius()+0.5;
    }

    class RobotPositionChecker extends Thread
	{
		private int updateInterval;
		private Point lastPosition;

		RobotPositionChecker(Point startPosition, int updateInterval){
			this.lastPosition = startPosition;
			this.updateInterval = updateInterval;
		}

		@Override
		public void run() {
			while (true)
			{
				Point newPosition = getPosition();
				if(!newPosition.equals(lastPosition)){
					notifyObservers();
				}

				try {
					sleep(updateInterval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}