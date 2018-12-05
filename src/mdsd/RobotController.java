package mdsd;

import project.Point;

import java.util.*;

public class RobotController implements RobotObserver, IRobotController{
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

    public RobotController(List<IRobot> robots, Map<String,Point> roomPointMap, Set<Area> areas){

    }

    public IRobot getRobot(String name) {

        return new Robot(new Point(1,2),"hej", 10);
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

            IGoal gl = missionMap.get(robot).getNext();
            IStrategy strt = strategyMap.get(robot);
            Iterator<Point> pnts = strt.ComputeNext(gl, robot.getPosition());
            travelMap.put(robot, pnts);
        //    robot.setDestination(travelMap.get(robot).next());
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }
    public void attachAll(IRobot r, IStrategy s, IMission m){
        addRobot(r);
        attachMission(r, m);
        attachStrategy(r, s);
       // updateTravelMap(r);

    }
    public void startUpdater(){
        RobotUpdater updtr = new RobotUpdater();
        updtr.start();
    }

    @Override
    public void update(IRobot robot) {

    }

    @Override
    public void setMission(int robotId, List<String> missionStr) {

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
                            if (!travelMap.containsKey(robot))
                                updateTravelMap(robot);
                            Point p = travelMap.get(robot).next();
                            System.out.println("####################");
                            System.out.println(p);
                            System.out.println("#####################");
                            robot.setDestination(p);
                        }catch (NullPointerException e){
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

