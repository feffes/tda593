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


    public RobotController(List<IRobot> robots, Set<Area> areas, Set<IStrategy> strategies) {
        this.strategies = strategies;
        this.robots = robots;
        this.areas = areas;
        missionMap = new HashMap<>();
        strategyMap = new HashMap<>();
        travelMap = new HashMap<>();
    }

    public void addRobot(IRobot robot) {
        robots.add(robot);
    }

    public void addStrategy(IStrategy strategy){
        strategies.add(strategy);
    }

    private void updateTravelMap(IRobot robot) {
        try {

            IGoal goal = missionMap.get(robot).getNext();
            goal.setGoalPosition(robot);
            IStrategy strt = strategyMap.get(robot);
            Iterator<Point> pnts = strt.ComputeRoute(goal, robot.getPosition());
            travelMap.put(robot, pnts);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void update(IRobot robot) {
        if (robot.isWaiting()) {
            return;
        }

        IMission mission = missionMap.get(robot);
        if (!robot.isAtDestination()) {
            return;
        }
        if (mission.reachedGoal(robot)) {
            updateTravelMap(robot);
        }
        updateDestination(robot);
    }

    private void updateDestination(IRobot robot) {
        Iterator<Point> travelIterator = travelMap.get(robot);

        if (travelIterator != null && travelIterator.hasNext()) {
            Point destination = travelIterator.next();
            robot.setDestination(destination);
        }
    }

    @Override
    public void setMission(int robotIndex, List<String> missionStr, String strategyStr) {
        IMission mission = createMission(missionStr);
        IRobot robot = robots.get(robotIndex);

        Optional<IStrategy> strategyOpt = strategies.stream().filter(s -> s.getName().equals(strategyStr)).findFirst();
        if (!strategyOpt.isPresent()) {
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

}
