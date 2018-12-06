package mdsd.controller;

import mdsd.Area;
import mdsd.IRobot;
import mdsd.RobotObserver;
import project.Point;

import java.util.*;

public class AreaController implements RobotObserver {
    private Set<Area> areas;
    private Map<IRobot, Point> waitingDestinationMap;
    private Map<Area, Queue<IRobot>> waitingQueueMap;
    private Map<IRobot, Set<Area>> robotsInsideMap;
    private Map<Area, Integer> robotsInAreaMap;

    public AreaController(Set<Area> areas) {
        waitingDestinationMap = new HashMap<>();
        waitingQueueMap = new HashMap<>();
        robotsInsideMap = new HashMap<>();
        this.areas = areas;
        this.robotsInAreaMap = initRobotsInAreaMap(areas);
    }

    private Map<Area, Integer> initRobotsInAreaMap(Set<Area> areas) {
        Map<Area, Integer> map = new HashMap<>();
        for (Area a : areas) {
            map.put(a, 0);
        }
        return map;
    }

    private void activateWaitingRobots(Area a) {
        if (waitingQueueMap.containsKey(a)) {
            Queue<IRobot> queue = waitingQueueMap.get(a);
            IRobot robotInQueue = queue.poll();

            if (queue.size() == 0) {
                waitingQueueMap.remove(a);
            }

            Point previousDestination = waitingDestinationMap.remove(robotInQueue);
            robotInQueue.setDestination(previousDestination);
        }
    }

    private void stopRobot(IRobot robot, Area area) {
        Point destination = robot.getDestination();

        if (!waitingDestinationMap.containsKey(robot)) {
            waitingDestinationMap.put(robot, destination);
        }

        if (!waitingQueueMap.containsKey(area)) {
            waitingQueueMap.put(area, new ArrayDeque<>());
        }

        Queue<IRobot> queue = waitingQueueMap.get(area);
        queue.add(robot);
        waitingQueueMap.put(area, queue);

        robot.setWaiting();
    }

    public void handleLeavingAreas(IRobot robot) {
        if (robotsInsideMap.containsKey(robot)) {
            Set<Area> areasToRemove = new HashSet<>();
            Set<Area> robotInsideAreas = robotsInsideMap.get(robot);

            for (Area a : robotInsideAreas) {
                if (!a.isInside(robot)) {
                    areasToRemove.add(a);
                }
            }

            for (Area a : areasToRemove) {
                robotInsideAreas.remove(a);
                robotsInsideMap.put(robot, robotInsideAreas);

                Integer previousPresent = robotsInAreaMap.get(a);
                robotsInAreaMap.put(a, previousPresent - 1);

                activateWaitingRobots(a);
            }

            if (robotInsideAreas.size() == 0) {
                robotsInsideMap.remove(robot);
            }
        }
    }

    public void handleEnteringAreas(IRobot robot) {
        Set<Area> robotInsideAreas = new HashSet<>();

        if (robotsInsideMap.containsKey(robot))
            robotInsideAreas = robotsInsideMap.get(robot);

        for (Area area : areas) {
            if (!robotInsideAreas.contains(area) && area.isInside(robot) && robotsInAreaMap.get(area) > 0) {
                stopRobot(robot, area);
            } else if (!robotInsideAreas.contains(area) && area.isInside(robot)) {

                robotInsideAreas.add(area);

                robotsInsideMap.put(robot, robotInsideAreas);

                Integer previousPresent = robotsInAreaMap.get(area);
                robotsInAreaMap.put(area, previousPresent + 1);
            }
        }
    }

    @Override
    public void update(IRobot robot) {
        handleLeavingAreas(robot);
        handleEnteringAreas(robot);
    }
}
