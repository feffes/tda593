//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mdsd.betterproject;

import java.util.Set;
import java.util.function.Consumer;
import simbad.gui.Simbad;
import simbad.sim.EnvironmentDescription;
import simbad.sim.Simulator;

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
        Simulator sim = this.frame.getSimulator();
        sim.startSimulation();
    }

    public abstract void update(R var1);
}
