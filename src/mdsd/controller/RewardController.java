package mdsd.controller;

import mdsd.model.IProcedure;
import mdsd.model.IRobot;
import mdsd.model.RobotObserver;
import mdsd.view.IRewardView;
import mdsd.view.RewardView;

import java.util.*;

public class RewardController implements IRewardControlller{

    private IProcedure activeProcedure;
    private Set<IProcedure> procedures;
    private Set<IRewardView> views;
    private List<IRobot> robots;
    private RewardTimer timer;

    public RewardController(List<IRobot> robots){
        views = new HashSet<>();
        this.robots = robots;
        procedures = new HashSet<>();
        timer = new RewardTimer();
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


    class RewardTimer extends Thread{

        long sleepTime = 2000; //20 seconds

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
