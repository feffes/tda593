package mdsd.model;

import mdsd.betterproject.BetterAbstractRobotSimulator;
import project.Point;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Robot extends BetterAbstractRobotSimulator implements IRobot {

    private Point dest;
    private Point savedDestination;
    private Set<RobotObserver> observers;
    private RobotNotifier positionChecker;
    private boolean isWaiting;
    private ScheduledExecutorService scheduler;
    private Runnable task;


    public Robot(Point position, String name, int updateMillis) {
        super(position, name);
        observers = new HashSet<>();
        isWaiting = false;

        positionChecker = new RobotNotifier(position, updateMillis);
        positionChecker.start();
        setUpTimerTask();

    }


    @Override
    public String toString() {
        return this.getName();
    }


    @Override
    public Point getDestination() {
        return dest;
    }

    @Override
    public void setDestination(Point dest) {
        this.dest = dest;
        super.setDestination(dest);
        isWaiting = false;
    }

    public double getRadius() {
        return this.getAgent().getRadius();
    }

    @Override
    public void addObserver(RobotObserver observer) {
        observers.add(observer);
    }

    @Override
    public void setWaiting() {
        setDestination(this.getPosition());
        isWaiting = true;
    }


    //Make robot wait for x (int) seconds then continue to destination. Used when robot enters new room
    public void setTempWaiting(int seconds, Point destination) {
        savedDestination = destination;
        setWaiting();
        scheduler.schedule(task, seconds, TimeUnit.SECONDS);
    }

    private void setUpTimerTask() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        task = () -> {
            setDestination(savedDestination);
        };
    }

    @Override
    public boolean isWaiting() {
        return isWaiting;
    }

    private void notifyObservers() {
        for (RobotObserver r : observers) {
            r.update(this);
        }
    }

    public void stop() {
        setWaiting();
    }

    public boolean hasfault() {
        //double random = Math.random() * 10000; //between 0 and 100
        //if (random < 1) { //has X percent to get fault. Just to simulate fault on rover
        //    System.out.println("Fault on rover! Stoping the rover");
         //   return true;
        //}
        return false;
    }

    public boolean isAtPosition(Point p) {
        return super.isAtPosition(p);
    }

    public boolean isAtDestination() {
        return dest == null || super.isAtPosition(dest);
    }

    class RobotNotifier extends Thread {
        private int updateInterval;
        private Point lastPosition;

        RobotNotifier(Point startPosition, int updateInterval) {
            this.lastPosition = startPosition;
            this.updateInterval = updateInterval;
        }

        @Override
        public void run() {
            while (true) {
                if (hasfault()) {
                    //stop();
                }

                Point newPosition = getPosition();
                if (dest != null && newPosition != null && !newPosition.equals(lastPosition)) {
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