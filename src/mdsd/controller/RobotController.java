package mdsd.controller;

import mdsd.model.*;
import project.Point;

import java.util.*;

public class RobotController implements RobotObserver, IRobotController {
    private List<IRobot> robots;
    private Map<IRobot, IMission> missionMap;
    private Map<IRobot, IStrategy> strategyMap;
    private Map<IRobot, Iterator<Point>> travelMap;
    private Set<Area> areas;
    private Set<IStrategy> strategies;

    private final String exitGoalKeyword = "exit";

    public RobotController() {
        missionMap = new HashMap<>();
        strategyMap = new HashMap<>();
        travelMap = new HashMap<>();
        this.robots = new ArrayList<>();

    }

    public RobotController(List<IRobot> robots, Set<Area> areas, Set<IStrategy> strategies) {
        this.strategies = strategies;
        this.robots = robots;
        this.areas = areas;
        missionMap = new HashMap<>();
        strategyMap = new HashMap<>();
        travelMap = new HashMap<>();
    }

    public IRobot getRobot(String name) {

        return new Robot(new Point(1, 2), "hej", 10);
    }

    public void attachStrategy(IRobot robot, IStrategy strategy) {
        strategyMap.put(robot, strategy);
    }

    public void addRobot(IRobot robot) {
        robots.add(robot);
    }

    public void attachMission(IRobot robot, IMission mission) {
        missionMap.put(robot, mission);
    }

    private void updateTravelMap(IRobot robot) {
        try {

            IGoal goal = missionMap.get(robot).getNext();
            IStrategy strt = strategyMap.get(robot);
            Iterator<Point> pnts = strt.ComputeRoute(goal, robot.getPosition());
            travelMap.put(robot, pnts);
            //    robot.setDestination(travelMap.get(robot).next());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void attachAll(IRobot r, IStrategy s, IMission m) {
        addRobot(r);
        attachMission(r, m);
        attachStrategy(r, s);
        // updateTravelMap(r);

    }

    public void startUpdater() {
        RobotUpdater updtr = new RobotUpdater();
        updtr.start();
    }

    @Override
    public void update(IRobot robot) {
        IMission mission = missionMap.get(robot);

        if(!robot.isAtDestination()){
            return;
        }

        if(mission.reachedGoal(robot)){
            updateTravelMap(robot);
        }

        updateDestination(robot);
    }

    private void updateDestination(IRobot robot){
        Iterator<Point> travelIterator = travelMap.get(robot);

        if(travelIterator != null && travelIterator.hasNext()){
            Point destination = travelIterator.next();
            robot.setDestination(destination);
        }
    }

    @Override
    public void setMission(int robotIndex, List<String> missionStr, String strategyStr) {
        IMission mission = createMission(missionStr);
        IRobot robot = robots.get(robotIndex);

        Optional<IStrategy> strategyOpt = strategies.stream().filter(s -> s.getName().equals(strategyStr)).findFirst();
        if(!strategyOpt.isPresent()){
            throw new IllegalArgumentException("Strategy does not exist");
        }

        missionMap.put(robot, mission);
        strategyMap.put(robot, strategyOpt.get());
        updateTravelMap(robot);
        updateDestination(robot);
    }

    private IMission createMission(List<String> goalStrings) {
        IMission mission = new Mission();
        for (String gStr : goalStrings) {
            if (gStr.equals(exitGoalKeyword)) {
                mission.addGoal(new ExitGoal(areas));
            } else if (areas.stream().anyMatch(a -> a.getName().equals(gStr))) {
                Area area = areas.stream().filter(a -> a.getName().equals(gStr)).iterator().next();
                mission.addGoal(new PointGoal(area.getRepresentativePoint()));
            } else {
                throw new IllegalArgumentException("Area in mission does not exist.");
            }
        }

        return mission;
    }


    class RobotUpdater extends Thread {
        @Override
        public void run() {
            boolean bool = true;
            while (bool) {
                for (IRobot robot : robots) {
                    if (robot.isAtDestination()) {
                        try {
                            if (missionMap.get(robot).reachedGoal(robot)) {
                                updateTravelMap(robot);
                            }
                            if (!travelMap.containsKey(robot))
                                updateTravelMap(robot);
                            Point p = travelMap.get(robot).next();
                            System.out.println("####################");
                            System.out.println(p);
                            System.out.println("#####################");
                            robot.setDestination(p);
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                    }
                }
                try {
                    this.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("one round");
            }
        }
    }

}

