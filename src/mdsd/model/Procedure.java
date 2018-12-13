package mdsd.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Procedure implements IProcedure {

    private Map<Area,Integer> rewardPointMap;
    private Set<Area> areas;
    private String name;

    public Procedure(String name){
        rewardPointMap = new HashMap<>();
        this.name = name;
        areas = new HashSet<>();
    }

    @Override
    public int getPoints(IRobot robot) {
        int points = 0;
        for (Area a : areas){
            if(a.isInside(robot)){
                points += rewardPointMap.get(a);
            }
        }
        return points;
    }

    @Override
    public void addArea(Area area, int rewardPoints) {
        areas.add(area);
        rewardPointMap.put(area,rewardPoints);
    }

    @Override
    public void removeArea(Area area) {
        rewardPointMap.remove(area);
    }

    @Override
    public boolean isValid(IRobot robot) {
        for(Area a : areas){
            if(a.isInside(robot)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
