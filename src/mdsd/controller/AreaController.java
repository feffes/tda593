package mdsd.controller;

import mdsd.Area;
import mdsd.IRobot;
import mdsd.RobotObserver;

import java.util.Set;

public class AreaController implements RobotObserver {
    private Set<Area> areas;

    public AreaController(Set<Area> areas) {

        this.areas = areas;
    }

    @Override
    public void update(IRobot robot) {
        for(Area a: areas){
        }
    }
}
