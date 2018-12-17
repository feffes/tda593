package mdsd.controller;

//import com.sun.javaws.exceptions.InvalidArgumentException;
import mdsd.model.*;
import project.Point;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.stream.Stream;

public class RobotController implements RobotObserver, IRobotController,ActionListener {
    private List<IRobot> robots;
    private Map<IRobot, IMission> missionMap;
    private Map<IRobot, IStrategy> strategyMap;
    private Map<IRobot, Iterator<Point>> travelMap;
    private Set<Area> areas;
    private Set<IStrategy> strategies;
    private Map<String, Class<? extends IGoal>> goalTypeMap;

    public RobotController(List<IRobot> robots, Set<Area> areas, Set<IStrategy> strategies, Map<String, Class<? extends IGoal>> goalTypeMap) {
        this.strategies = strategies;
        this.robots = robots;
        this.areas = areas;
        this.goalTypeMap = goalTypeMap;

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
            IGoal goal;
            if(missionMap.get(robot).hasNextGoal()){
                goal = missionMap.get(robot).getNext();
                goal.setGoalPosition(robot);
                IStrategy strt = strategyMap.get(robot);
                Iterator<Point> pnts = strt.ComputeRoute(goal, robot.getPosition());
                travelMap.put(robot, pnts);
            }else{
                return;
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (NoSuchElementException e){
            System.out.println("I've got nothin' to do, man");
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

        if(!strategies.stream().anyMatch(s -> s.getName().equals(strategyStr))){
            throw new IllegalArgumentException("Strategy does not exist");
        }

        Optional<IStrategy> strategyOpt = strategies.stream().filter(s -> s.getName().equals(strategyStr)).findFirst();
        IStrategy strategy = strategyOpt.get();

        missionMap.put(robot, mission);
        strategyMap.put(robot, strategy);
        updateTravelMap(robot);
        updateDestination(robot);
    }

    private IMission createMission(List<String> goalStrings) {
        IMission mission = new Mission();
        for (String gStr : goalStrings) {
            String[] args = gStr.split(" ");
            IGoal goal = null;
            try {
                goal = createGoal(args);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
            mission.addGoal(goal);
        }

        return mission;
    }

    private IGoal createGoal(String[] args) throws IllegalAccessException, InstantiationException {
        Class<? extends IGoal> goalType = goalTypeMap.get(args[0]);

        if(args.length > 1 && AreaGoal.class.isAssignableFrom(goalType)){
            if(areas.stream().anyMatch(a -> a.getName().equals(args[1]))){
                Area area = areas.stream().filter(a -> a.getName().equals(args[1])).findFirst().get();

                AreaGoal areaGoal = (AreaGoal)goalType.newInstance();

                areaGoal.setArea(area);
                return areaGoal;

            } else {
                throw new IllegalArgumentException("Area in mission not specified.");
            }
        } else if (args.length > 2 && PointGoal.class.isAssignableFrom(goalType)){
            Point point = new Point(Double.parseDouble(args[1]), Double.parseDouble(args[2]));
            return new PointGoal(point);
        }

        throw new IllegalArgumentException("Goal type is not handled");

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        for(IRobot robot : robots){
            robot.stop();
        }
    }
}

