package mdsd.controller;

import mdsd.model.Area;
import mdsd.model.IRobot;
import mdsd.model.RobotObserver;
import mdsd.view.IAreaView;
import project.Point;

import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;

public class AreaController implements RobotObserver, IAreaController {
    private Set<Area> areas;
    private Map<IRobot, Point> waitingDestinationMap;
    private Map<Area, Queue<IRobot>> waitingQueueMap;
    private Map<IRobot, Set<Area>> robotsInsideMap;
    private Map<Area, Integer> robotsInAreaMap;
    private Set<IAreaView> views;

    public AreaController(Set<Area> areas) {
        this.views = new HashSet<>();
        waitingDestinationMap = new ConcurrentHashMap<>();
        waitingQueueMap = initWaitingQueueMap(areas);
        robotsInsideMap = new ConcurrentHashMap<>();
        this.areas = areas.stream().filter(a -> a.isLimited()).collect(Collectors.toSet());
        this.robotsInAreaMap = initRobotsInAreaMap(areas);
    }

    private Map<Area, Queue<IRobot>> initWaitingQueueMap(Set<Area> areas) {
        Map<Area, Queue<IRobot>> map = new ConcurrentHashMap<>();
        for (Area a : areas) {
            map.put(a, new ConcurrentLinkedDeque<>());
        }
        return map;
    }

    private Map<Area, Integer> initRobotsInAreaMap(Set<Area> areas) {
        Map<Area, Integer> map = new ConcurrentHashMap<>();
        for (Area a : areas) {
            map.put(a, 0);
        }
        return map;
    }

    private void activateWaitingRobots(Area a) {
        Queue<IRobot> queue = waitingQueueMap.get(a);
        if (queue.size() > 0) {
            IRobot robotInQueue = queue.poll();

            Point previousDestination = waitingDestinationMap.remove(robotInQueue);
            robotInQueue.setDestination(previousDestination);
        }
    }

    private void stopRobot(IRobot robot, Area area) {
        Point destination = new Point(robot.getDestination().getX(), robot.getDestination().getZ());

        waitingDestinationMap.put(robot, destination);

        Queue<IRobot> queue = waitingQueueMap.get(area);
        queue.add(robot);

        robot.setWaiting();
    }

    private void handleLeavingAreas(IRobot robot) {
        if (robotsInsideMap.containsKey(robot)) {
            Set<Area> areasToRemove = new HashSet<>();
            Set<Area> robotInsideAreas = robotsInsideMap.get(robot);

            for (Area a : robotInsideAreas) {
                if (!a.isInside(robot)) {
                    areasToRemove.add(a);

                }
            }

            for (Area a : areasToRemove) {
                for (IAreaView view : views) {
                    view.robotLeftArea(robot.toString(), a.getName());
                }

                robotInsideAreas.remove(a);
                robotsInsideMap.put(robot, robotInsideAreas);
                robotsInAreaMap.put(a, robotsInAreaMap.get(a) - 1);
                activateWaitingRobots(a);
            }

            if (robotInsideAreas.size() == 0) {
                robotsInsideMap.remove(robot);
            }
        }
    }

    private void handleEnteringAreas(IRobot robot) {
        Set<Area> robotInsideAreas = new HashSet<>();


        if (robotsInsideMap.containsKey(robot))
            robotInsideAreas = robotsInsideMap.get(robot);

        for (Area area : areas) {
            if (!robotInsideAreas.contains(area) && area.isInside(robot) && robotsInAreaMap.get(area) > 0) {
                stopRobot(robot, area);
            } else if (!robotInsideAreas.contains(area) && area.isInside(robot)) {

                robot.setTempWaiting(2, robot.getDestination());
                robotInsideAreas.add(area);

                for (IAreaView view : views) {
                    view.robotEnteredArea(robot.toString(), area.getName());
                }

                robotsInsideMap.put(robot, robotInsideAreas);
                robotsInAreaMap.put(area, robotsInAreaMap.get(area) + 1);

            }
        }

    }


    @Override
    public synchronized void update(IRobot robot) {
        if (robot.isWaiting()) {
            return;
        }
        handleLeavingAreas(robot);
        handleEnteringAreas(robot);
    }

    @Override
    public void addAreaView(IAreaView view) {
        views.add(view);
    }

    @Override
    public void removeAreaView(IAreaView view) {
        views.remove(view);
    }
}
