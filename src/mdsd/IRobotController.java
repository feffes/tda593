package mdsd;

import java.util.List;

public interface IRobotController {
    public void setMission(int robotId, List<String> missionStr, String strategy);

}
