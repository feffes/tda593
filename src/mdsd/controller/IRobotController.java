package mdsd.controller;

import java.util.List;

public interface IRobotController {
    public void setMission(int robotId, List<String> missionStr);

    //public void addMissionView(IMissionView missionView)
}
