//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mdsd.betterproject;

import java.awt.*;
import java.util.Set;
import java.util.function.Consumer;

import simbad.gui.Simbad;
import simbad.sim.EnvironmentDescription;
import simbad.sim.Simulator;

import javax.swing.*;

public abstract class BetterAbstractSimulatorMonitor<R extends BetterAbstractRobotSimulator> {
    private Simbad frame;

    public Simbad getSimbadFrame() {
        return this.frame;
    }

    public BetterAbstractSimulatorMonitor(Set<R> robots, EnvironmentDescription e) {
        System.out.println("*********************");
        System.setProperty("j3d.implicitAntialiasing", "true");
        robots.forEach((r) -> {
            e.add(r.getAgent());
            r.getAgent().setAgentSimulator(r);
            r.setController(this);
        });
        this.frame = new Simbad(e, false);
        this.frame.update(this.frame.getGraphics());

        JInternalFrame[] frames = this.frame.getDesktopPane().getAllFrames();
        frames[0].setLocation(0,0);
        frames[1].setVisible(false); //this is the robot info panel that didn't show anything good
        frames[2].setLocation(frames[0].getWidth(), 0);
        
        Simulator sim = this.frame.getSimulator();
        sim.startSimulation();
    }

    public abstract void update(R var1);
}
