//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mdsd.betterproject;

import java.awt.Color;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

import project.Point;
import simbad.sim.Agent;
import simbad.sim.RobotFactory;

public class BetterRobotAgent<R extends BetterAbstractRobotSimulator> extends Agent {
    private static final Color3f greenColor;
    private static final Color3f cyanColor;
    private Color3f currentColor;
    private double MIN_DIST = 0.1D;
    private Point destination;
    private R agentSimulator;
    private BetterAbstractSimulatorMonitor<R> controller;
    public boolean changeColor;

    static {
        greenColor = new Color3f(Color.GREEN);
        cyanColor = new Color3f(Color.CYAN);
    }

    public BetterRobotAgent(Point position, String name) {
        super(new Vector3d(position.getX(), 0.0D, position.getZ()), name);
        this.destination = position;
        RobotFactory.addBumperBeltSensor(this, 12);
        RobotFactory.addSonarBeltSensor(this, 4);
        this.currentColor = greenColor;
        this.changeCol();
        this.changeColor = false;
    }

    public void changeCol() {
        if (this.currentColor.equals(greenColor)) {
            this.setColor(cyanColor);
            this.currentColor = cyanColor;
        } else if (this.currentColor.equals(cyanColor)) {
            this.setColor(greenColor);
            this.currentColor = greenColor;
        }

    }

    public void setDestination(Point destination) {
        this.destination = destination;
    }

    public void setController(BetterAbstractSimulatorMonitor<R> controller) {
        this.controller = controller;
    }

    public void initBehavior() {
        System.out.println("I exist and my name is " + this.name);
    }

    public void performBehavior() {
        Vector3d position = this.getPosition();
        if (!this.isAtPosition(this.destination)) {
            double angle = -Math.atan2((this.destination.getZ() - position.z), (this.destination.getX() - position.x)) ;//* 180.0D / 3.141592653589793D;
            this.rotateY(angle);
            this.setTranslationalVelocity(1.0D);
        } else {
            this.setTranslationalVelocity(0.0D);
//            this.setRotationalVelocity(0.0D);
        }

        if (this.changeColor) {
            this.changeCol();
            this.changeColor = false;
        }

        this.controller.update(this.agentSimulator);
    }

    public boolean isAtPosition(Point dest) {
        Vector3d position = this.getPosition();
        return Math.abs(dest.getZ() - position.z) <= this.MIN_DIST && Math.abs(dest.getX() - position.x) <= this.MIN_DIST;
    }

    protected BetterAbstractRobotSimulator getAgentSimulator() {
        return this.agentSimulator;
    }

    protected void setAgentSimulator(R agentSimulator) {
        this.agentSimulator = agentSimulator;
    }
}
