package edu.rutgers.MOST.data;

import java.util.ArrayList;

import edu.rutgers.MOST.config.LocalConfig;
import edu.rutgers.MOST.presentation.PathwaysFrameConstants;

public class VisualizationFluxesProcessor {
	// need fluxes to get max and secondary max if there are infinite fluxes
	// usually in column as something like 999999.0
	private ArrayList<Double> fluxes = new ArrayList<Double>();

	private double maxUpperBound;
	private double secondaryMaxFlux;
	public ArrayList<Double> getFluxes() {
		return fluxes;
	}
	public void setFluxes(ArrayList<Double> fluxes) {
		this.fluxes = fluxes;
	}
	public double getMaxUpperBound() {
		return maxUpperBound;
	}
	public void setMaxUpperBound(double maxUpperBound) {
		this.maxUpperBound = maxUpperBound;
	}
	public double getSecondaryMaxFlux() {
		return secondaryMaxFlux;
	}
	public void setSecondaryMaxFlux(double secondaryMaxFlux) {
		this.secondaryMaxFlux = secondaryMaxFlux;
	}
	
	public void processFluxes() {
		for (int j = 0; j< fluxes.size(); j++) {
			if (Math.abs(fluxes.get(j)) <= PathwaysFrameConstants.INFINITE_FLUX_RATIO*maxUpperBound) {
				if (Math.abs(fluxes.get(j)) > secondaryMaxFlux) {
					secondaryMaxFlux = Math.abs(fluxes.get(j));
				}
			}
		}
		LocalConfig.getInstance().setFluxes(fluxes);
		LocalConfig.getInstance().setMaxUpperBound(maxUpperBound);
		LocalConfig.getInstance().setSecondaryMaxFlux(secondaryMaxFlux);
	}

}
