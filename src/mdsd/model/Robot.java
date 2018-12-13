package mdsd.model;

import mdsd.betterproject.BetterAbstractRobotSimulator;
import project.Point;

import java.util.HashSet;
import java.util.Set;

public class Robot extends BetterAbstractRobotSimulator implements IRobot {

	private Point dest;
	private Set<RobotObserver> observers;
	private RobotNotifier positionChecker;
	private boolean isWaiting;
	
	public Robot(Point position, String name, int updateMillis) {
		super(position, name);
		observers = new HashSet<>();
		isWaiting = false;

		positionChecker = new RobotNotifier(position, updateMillis);
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

	//Make robot wait for x (int) seconds. Used when robot enters new room
	public void setWaiting(int seconds, Point destination) throws InterruptedException{
	    setDestination(this.getPosition());
	    isWaiting = true;

	    Thread.sleep(seconds*1000);
	    setDestination(destination);
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
    }

    class RobotNotifier extends Thread
	{
		private int updateInterval;
		private Point lastPosition;

		RobotNotifier(Point startPosition, int updateInterval){
			this.lastPosition = startPosition;
			this.updateInterval = updateInterval;
		}

		@Override
		public void run() {
			while (true)
			{
				Point newPosition = getPosition();
				if(dest != null && newPosition != null && !newPosition.equals(lastPosition)){
					notifyObservers();
					lastPosition = newPosition;
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