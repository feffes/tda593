package mdsd.view;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AreaView implements IAreaView {
    Map<String, Set<String>> robotAreaMap;

    public AreaView(Set<String> robots) {
        robotAreaMap = new HashMap<>();
        for (String r : robots) {
            robotAreaMap.put(r, new HashSet<>());
        }
    }

    private void printRobots() {
        System.out.println("---------------------------------------");
        for (Map.Entry entry : robotAreaMap.entrySet()) {
            Set<String> areas = (Set<String>) entry.getValue();

            if (areas.size() < 1) {
                System.out.println("Robot " + entry.getKey().toString() + " is not inside any area");
            } else {
                System.out.print("Robot " + entry.getKey() + " is inside: ");
                StringBuilder sb = new StringBuilder();
                for (String s : areas) {
                    sb.append(", ").append(s);
                }

                System.out.print(sb.substring(2));
                System.out.print("\n");
            }
        }
        System.out.println("---------------------------------------");
    }

    @Override
    public void robotEnteredArea(String robot, String area) {
        robotAreaMap.get(robot).add(area);
        printRobots();
    }

    @Override
    public void robotLeftArea(String robot, String area) {
        robotAreaMap.get(robot).remove(area);
        printRobots();
    }
}
