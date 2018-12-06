package mdsd.view;

import java.util.Set;

import mdsd.betterproject.BetterAbstractSimulatorMonitor;
import mdsd.model.Robot;
import simbad.sim.EnvironmentDescription;

public class SimulatorMonitor extends BetterAbstractSimulatorMonitor<Robot> {

	public SimulatorMonitor(Set<Robot> robots, EnvironmentDescription e) {
		super(robots, e);
	}

	

	@Override
	public void update(Robot arg0) {
		System.out.println(arg0.getName());
		System.out.println(arg0.getPosition());
	}

}