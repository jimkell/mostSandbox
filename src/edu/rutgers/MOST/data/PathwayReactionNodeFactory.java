package edu.rutgers.MOST.data;

import java.util.ArrayList;

import edu.rutgers.MOST.config.LocalConfig;

public class PathwayReactionNodeFactory {

	public PathwayReactionNode createPathwayReactionNode(PathwayReactionData data, String compartment) {
		PathwayReactionNode pn = new PathwayReactionNode();
		ArrayList<String> sideReactants = new ArrayList<String>();
		ArrayList<String> sideProducts = new ArrayList<String>();
		ArrayList<String> enzymeDataEquations = new ArrayList<String>();
		ArrayList<String> modelReactionNames = new ArrayList<String>();
		ArrayList<String> modelEquations = new ArrayList<String>();
		ArrayList<Double> fluxes = new ArrayList<Double>();
		ArrayList<String> ecNumbers = new ArrayList<String>();
		
		for (int m = 0; m < data.getEcNumbers().size(); m++) {
			if (LocalConfig.getInstance().getEcNumberReactionMap().containsKey(data.getEcNumbers().get(m))) {
				ecNumbers.add(data.getEcNumbers().get(m));
				ArrayList<SBMLReaction> reac = LocalConfig.getInstance().getEcNumberReactionMap().get(data.getEcNumbers().get(m));
				for (int r = 0; r < reac.size(); r++) {
					// if compartment not defined, just draw everything for now
					if (compartment != null && compartment.length() > 0) {
						SBMLReactionEquation equn = (SBMLReactionEquation) LocalConfig.getInstance().getReactionEquationMap().get(reac.get(r).getId());
						if (equn.getCompartmentList().size() == 1 && equn.getCompartmentList().contains(compartment)) {
							modelReactionNames.add(reac.get(r).getReactionName());
							modelEquations.add(reac.get(r).getReactionEqunAbbr());
							fluxes.add(reac.get(r).getFluxValue());
//							System.out.println("c " + equn.getCompartmentList());
						} else {
							// uncomment to show that reactions are eliminated if not correct compartment
//							System.out.println("n c " + equn.getCompartmentList());
//							System.out.println(data.getEcNumbers());
						}
					} else {
						modelReactionNames.add(reac.get(r).getReactionName());
						modelEquations.add(reac.get(r).getReactionEqunAbbr());
						fluxes.add(reac.get(r).getFluxValue());
					}
				}
				if (LocalConfig.getInstance().getEnzymeDataMap().get(data.getEcNumbers().get(m)).getCatalyticActivity() == null) {
					// description can have alternate numbers. need to get these
					//System.out.println(keys.get(j) + " " + LocalConfig.getInstance().getEnzymeDataMap().get(keys.get(j)).getDescription());
				} else {
					enzymeDataEquations.add(LocalConfig.getInstance().getEnzymeDataMap().get(data.getEcNumbers().get(m)).getCatalyticActivity());
					String[] halfReactions = LocalConfig.getInstance().getEnzymeDataMap().get(data.getEcNumbers().get(m)).getCatalyticActivity().split(" = ");
					for (int n = 0; n < LocalConfig.getInstance().getSideSpeciesList().size(); n++) {
						if (halfReactions[0].contains(LocalConfig.getInstance().getSideSpeciesList().get(n))) {
							if (!sideReactants.contains(LocalConfig.getInstance().getSideSpeciesList().get(n))) {
								sideReactants.add(LocalConfig.getInstance().getSideSpeciesList().get(n));
							}
						}
						if (halfReactions[1].contains(LocalConfig.getInstance().getSideSpeciesList().get(n))) {
							if (!sideProducts.contains(LocalConfig.getInstance().getSideSpeciesList().get(n))) {
								sideProducts.add(LocalConfig.getInstance().getSideSpeciesList().get(n));
							}
						}
					}
				}
			}
		}
		//pn.setPathwayId(pathway.getId());
		pn.setSideReactants(sideReactants);
		pn.setSideProducts(sideProducts);
		pn.setEnzymeDataEquations(enzymeDataEquations);
		pn.setModelEquations(modelEquations);
		pn.setModelReactionNames(modelReactionNames);
		pn.setFluxes(fluxes);
		pn.setEcNumbers(ecNumbers);
		
		return pn;
	}
	
	public String createDisplayName(PathwayReactionData data, PathwayReactionNode pn) {
		String displayName = data.getDisplayName();
		if (pn.getModelReactionNames().size() > 0) {
			String reacName = pn.getModelReactionNames().get(0);
			if (pn.getModelReactionNames().size() > 1) {
				reacName = pn.getModelReactionNames().toString();
			}
			displayName = "<html>" + reacName
					+ displayECNumber(pn)
					+ "<p> Equation: " + data.getName()
					+ displayModelEquation(pn);
		}
		return displayName;
	}
	
	public String displayECNumber(PathwayReactionNode pn) {
		String ec = "";
		if (pn.getEcNumbers().size() > 0) {
			ec = "<p>EC Number: " + pn.getEcNumbers().get(0);
		}
		if (pn.getEcNumbers().size() > 1) {
			ec = "<p>EC Number(s): " + pn.getEcNumbers().toString();
		}
		return ec;
	}
	
	public String displayModelEquation(PathwayReactionNode pn) {
		// since equations can be quite long and a list of reactions may not fit on screen,
		// each reaction is put on a separate line
		String modelEquationString = "";
		if (pn.getModelEquations().size() > 0) {
			modelEquationString = "<p>Equation from Model: " + pn.getModelEquations().get(0);
		}
		if (pn.getModelEquations().size() > 1) {
			modelEquationString = "<p>Equation(s) from Model: " + pn.getModelEquations().get(0);
			for (int m = 1; m < pn.getModelEquations().size(); m++) {
				modelEquationString += ", <p>" + pn.getModelEquations().get(m);
			}
		}
		return modelEquationString;
	}
	
	public String reversibleString(PathwayReactionData data) {
		String reversible = "";
		if (data.getReversible().equals("0")) {
			reversible = "false";
		} else if (data.getReversible().equals("1")) {
			reversible = "true";
		}
		return reversible;
	}

}
