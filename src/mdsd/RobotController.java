package mdsd;

import project.Point;

import java.util.*;

public class RobotController {
    private List<IRobot> robots = new ArrayList<IRobot>();
    private Map<IRobot, IMission> missionMap;
    private Map<IRobot, IStrategy> strategyMap;
    private Map<IRobot, Iterator<Point>> travelMap;
    private IStrategy strategy;

    public RobotController(){
        missionMap = new HashMap<>();
        strategyMap = new HashMap<>();
        travelMap = new HashMap<>();
    }

    public IRobot getRobot(String name) {

        return new Robot(new Point(1,2),"hej");
    }
    public void attachStrategy(IRobot robot, IStrategy strategy){
        strategyMap.put(robot, strategy);
    }
    public void addRobot(IRobot robot) {
        robots.add(robot);
    }
    public void attachMission(IRobot robot, IMission mission){
        missionMap.put(robot, mission);
    }

    private void updateTravelMap(IRobot robot){
        try {
            travelMap.put(robot,

                    strategyMap.get(robot).
                    ComputeNext(missionMap.get(robot).
                            getNext(), robot.getPosition()));
        } catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
    }
    public void attachAll(IRobot r, IStrategy s, IMission m){
        attachMission(r, m);
        attachStrategy(r, s);
    }



    class RobotUpdater extends Thread{
        @Override
        public void run() {
            boolean bool = true;
            while (bool){
                for (IRobot robot: robots){
                    if (robot.isAtDestination()){
                        try{
                            if(missionMap.get(robot).reachedGoal(robot)){
                                updateTravelMap(robot);
                            }
                        } catch (NullPointerException e){
                            e.printStackTrace();
                            break;
                        }
                        try{
                            robot.setDestination(travelMap.get(robot).next());
                        }catch (NullPointerException e){
                            System.out.println(e.getMessage());
                        }
                   }
                }
            }
        }
    }

}

