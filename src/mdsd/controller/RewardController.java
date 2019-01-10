package mdsd.controller;

import mdsd.model.IProcedure;
import mdsd.model.IRobot;
import mdsd.model.RobotObserver;
import mdsd.view.IRewardView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RewardController implements IRewardControlller, RobotObserver {

    private IProcedure activeProcedure;
    private List<IProcedure> procedures;
    private Set<IRewardView> views;
    private List<IRobot> robots;
    private RewardTimer timer;


    public RewardController(List<IRobot> robots,long sleepTime) {
        views = new HashSet<>();
        this.robots = robots;
        procedures = new ArrayList<>();
        timer = new RewardTimer(sleepTime); //2 seconds for testing

    }

    public void startTimer() {
        updateProcedure(procedures.get(0));
        timer.start();
    }

    public void addProcedure(IProcedure procedure) {
        procedures.add(procedure);
    }

    public void removeProcedure(IProcedure procedure) {
        procedures.remove(procedure);
    }


    @Override
    public void updateProcedure(IProcedure procedure) {
        if (procedures.contains(procedure)) {
            activeProcedure = procedure;
        }
    }

    @Override
    public void addRewardView(IRewardView view) {
        views.add(view);
    }


    private void updateView() {
        for (IRobot robot : robots) {
            for (IRewardView view : views) {
                view.updateRewardPoints(robot.toString(), activeProcedure.getPoints(robot), activeProcedure.getName());
            }
        }
    }

    @Override
    public void update(IRobot robot) { //Robot update needs to send different updates
        for (IProcedure procedure : procedures) {
            if (!activeProcedure.equals(procedure)) {
                if (procedure.isValid(robot)) {
                    activeProcedure = procedure;
                    return;
                }
            }
        }

    }


    private class RewardTimer extends Thread {
        long sleepTime;

        RewardTimer(long sleepTime) {
            this.sleepTime = sleepTime;
        }

        @Override
        public void run() {
            while (true) {
                updateView();
                try {
                    sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
