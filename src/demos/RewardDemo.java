package demos;

import Tests.TestUtils;
import mdsd.betterproject.BetterAbstractSimulatorMonitor;
import mdsd.controller.IRewardControlller;
import mdsd.controller.RewardController;
import mdsd.model.IProcedure;
import mdsd.model.IRobot;
import mdsd.model.Procedure;
import mdsd.view.IRewardView;
import mdsd.view.RewardView;

import java.util.ArrayList;
import java.util.List;

public class RewardDemo {
    private List<IRobot> robots;
    private BetterAbstractSimulatorMonitor monitor;

    public RewardDemo(List<IRobot> robots, BetterAbstractSimulatorMonitor monitor) {
        this.robots = robots;
        this.monitor = monitor;
    }

    public void initDemo1() {
        IRewardControlller rewardControlller = new RewardController(robots,2000);

        IProcedure procedureA = new Procedure("A");
        procedureA.addArea(UniversityDemo.initRoom3(), 2);
        rewardControlller.addProcedure(procedureA);

        IProcedure procedureB = new Procedure("B");
        procedureB.addArea(UniversityDemo.initRoom4(), 3);
        rewardControlller.addProcedure(procedureB);

        rewardControlller.updateProcedure(procedureA);

        IRewardView rewardView = new RewardView(monitor.getSimbadFrame().getDesktopPane(), getRobotNames());
        rewardControlller.addRewardView(rewardView);

        addObservers((RewardController) rewardControlller);

        rewardControlller.startTimer();
    }

    private List<String> getRobotNames() {
        List<String> strings = new ArrayList<>();
        for (IRobot ro : robots) {
            strings.add(ro.toString());
        }
        return strings;
    }

    private void addObservers(RewardController rewcont) {
        for (IRobot robot : robots) {
            robot.addObserver(rewcont);
        }
    }
}
