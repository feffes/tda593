//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mdsd;

import project.Point;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

public abstract class RoboSim {
    private BetterRobotAgent agent;

    public RoboSim(Point position, String name) {
        if (name == null) {
            throw new NullPointerException("The name of the robot cannot be null");
        } else if (position == null) {
            throw new NullPointerException("The position of the robot cannot be null");
        } else {
            this.agent = new BetterRobotAgent(position, name);
        }
    }

    public void resetPosition(Point position) {
        this.agent.resetPositionAt(new Vector3d(position.getX(), 0.0D, position.getZ()));
    }

    public void changeColor(Color3f color) {
        this.agent.setColor(color);
    }

    public void changeColor() {
        this.agent.changeColor = true;
    }

    public void setDestination(Point destination) {
        if (destination == null) {
            throw new NullPointerException("The destination cannot be null");
        } else {
            this.agent.setDestination(destination);
        }
    }

    public Point getPosition() {
        return new Point(this.agent.getPosition().x, this.agent.getPosition().z);
    }

    protected void setController(BetterAbstractSimulatorMonitor<? extends RoboSim> controller) {
        if (controller == null) {
            throw new NullPointerException("The controller cannot be null");
        } else {
            this.agent.setController(controller);
        }
    }

    protected BetterRobotAgent getAgent() {
        return this.agent;
    }

    public String getName() {
        return this.agent.getName();
    }

    public boolean isAtPosition(Point dest) {
        if (dest == null) {
            throw new NullPointerException("The destination cannot be null");
        } else {
            return this.agent.isAtPosition(dest);
        }
    }

    public boolean checkObstacle() {
        return this.agent.anOtherAgentIsVeryNear() || this.agent.collisionDetected();
    }

    public boolean checkCameraDetection() {
        return this.agent.anOtherAgentIsVeryNear();
    }

    public boolean checkAgent() {
        return this.agent.anOtherAgentIsVeryNear();
    }
}
