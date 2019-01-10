package mdsd.controller;

import mdsd.view.IMissionView;

import java.util.List;

public interface IRobotController {
    public void setMission(int robotId, List<String> missionStr, String strategy);

    public int getAmountRobots();

    public void addView(IMissionView view);
    public void removeView(IMissionView view);

    String getRobotInfo(int id);

}
