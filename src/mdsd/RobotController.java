package mdsd;

import project.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RobotController {
    private List<IRobot> robots = new ArrayList<IRobot>();
    private Map<IRobot, Mission> missionMap;
    private Map<IRobot, Strategy> strategyMap;
    private IStrategy strategy;
    public RobotController(){
        // build map
    }

    public IRobot getRobot(String name) {

        return new Robot(new Point(1,2),"hej");
    }

    public void addRobot(IRobot robot) {
        robots.add(robot);
    }
    public void attachMission(IRobot robot, Mission mission){
        missionMap.put(robot, mission);
    }



    class RobotUpdater extends Thread{
        @Override
        public void run() {
            boolean bool = true;
            while (bool){
                for (IRobot robot: robots){
//                    if (robot.isAtDestination()){
//
//                    }
                }
            }
        }
    }

}

