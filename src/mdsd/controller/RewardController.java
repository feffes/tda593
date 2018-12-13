package mdsd.controller;

import mdsd.model.IProcedure;
import mdsd.model.IRobot;
import mdsd.model.RobotObserver;
import mdsd.view.IRewardView;


import java.util.*;

public class RewardController implements IRewardControlller, RobotObserver{

    private IProcedure activeProcedure;
    private Set<IProcedure> procedures;
    private Set<IRewardView> views;
    private List<IRobot> robots;
    private RewardTimer timer;


    public RewardController(List<IRobot> robots){
        views = new HashSet<>();
        this.robots = robots;
        procedures = new HashSet<>();
        timer = new RewardTimer(2000); //2 seconds for testing

    }

    public void startTimer(){
        timer.start();
    }

    public void addProcedure(IProcedure procedure){
        procedures.add(procedure);
    }

    public void removeProcedure(IProcedure procedure){
        procedures.remove(procedure);
    }



    @Override
    public void updateProcendure(IProcedure procedure) {
        if(procedures.contains(procedure)){
            activeProcedure = procedure;
        }
    }

    @Override
    public void addRewardView(IRewardView view) {
        views.add(view);
    }



    public void updateView(){
        for(IRobot robot : robots){
            for(IRewardView view : views){
                view.updateRewardPoints(robot.toString(), activeProcedure.getPoints(robot),activeProcedure.getName());
            }
        }
    }

    @Override
    public void update(IRobot robot) { //Robot update needs to send different updates
        for(IProcedure procedure : procedures ){
            if(!activeProcedure.equals(procedure)) {
                if (procedure.isValid(robot)) {
                    activeProcedure = procedure;
                    return;
                }
            }
        }

    }


    class RewardTimer extends Thread{
        long sleepTime;

        RewardTimer(long sleepTime){
            this.sleepTime = sleepTime;
        }

        @Override
        public void run() {
            while (true){
                updateView();
                try {
                    sleep(sleepTime);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

}
