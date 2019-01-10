package demos.utils;

import mdsd.model.*;

import java.util.HashMap;
import java.util.Map;

public class InitializeUtils {
    public static Map<String, Class<? extends IGoal>> initGoalMap() {
        Map<String, Class<? extends IGoal>> goalMap = new HashMap<>();
        goalMap.put("enter", EnterAreaGoal.class);
        goalMap.put("exit", ExitAreaGoal.class);
        goalMap.put("point", PointGoal.class);
        goalMap.put("middle", MiddleAreaGoal.class);

        return goalMap;
    }
}
