package edu.rutgers.MOST.data;

import java.util.ArrayList;

import edu.rutgers.MOST.config.LocalConfig;

public class PathwayReactionNodeFactory {

	public PathwayReactionNode createPathwayReactionNode(ArrayList<String> ec, String compartment) {
		PathwayReactionNode pn = new PathwayReactionNode();
		ArrayList<String> sideReactants = new ArrayList<String>();
		ArrayList<String> sideProducts = new ArrayList<String>();
		ArrayList<String> enzymeDataEquations = new ArrayList<String>();
		ArrayList<String> modelReactionNames = new ArrayList<String>();
		ArrayList<String> modelEquations = new ArrayList<String>();
		ArrayList<Double> fluxes = new ArrayList<Double>();
		ArrayList<String> ecNumbers = new ArrayList<String>();
		
		for (int m = 0; m < ec.size(); m++) {
			if (LocalConfig.getInstance().getEcNumberReactionMap().containsKey(ec.get(m))) {
				ecNumbers.add(ec.get(m));
				// attributes from SBML Reaction
				ArrayList<SBMLReaction> reac = LocalConfig.getInstance().getEcNumberReactionMap().get(ec.get(m));
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
//							System.out.println(ec);
						}
					} else {
						modelReactionNames.add(reac.get(r).getReactionName());
						modelEquations.add(reac.get(r).getReactionEqunAbbr());
						fluxes.add(reac.get(r).getFluxValue());
					}
				}
				// attributes from Enzyme.dat
				if (LocalConfig.getInstance().getEnzymeDataMap().get(ec.get(m)).getCatalyticActivity() == null) {
					// description can have alternate numbers. need to get these
					//System.out.println(keys.get(j) + " " + LocalConfig.getInstance().getEnzymeDataMap().get(keys.get(j)).getDescription());
				} else {
					enzymeDataEquations.add(LocalConfig.getInstance().getEnzymeDataMap().get(ec.get(m)).getCatalyticActivity());
					String[] halfReactions = LocalConfig.getInstance().getEnzymeDataMap().get(ec.get(m)).getCatalyticActivity().split(" = ");
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
	
	public PathwayConnectionNode createPathwayConnectionNode(PathwayReactionNode pn) {
		PathwayConnectionNode pcn = new PathwayConnectionNode();
		pcn.setSideReactants(pn.getSideReactants());
		pcn.setSideProducts(pn.getSideProducts());
		pcn.setEnzymeDataEquations(pn.getEnzymeDataEquations());
		pcn.setModelEquations(pn.getModelEquations());
		pcn.setModelReactionNames(pn.getModelReactionNames());
		pcn.setFluxes(pn.getFluxes());
		pcn.setEcNumbers(pn.getEcNumbers());
		
		return pcn;
	}
	
	public String createDisplayName(String displayName, String name, ArrayList<String> modelReactionNames,
			ArrayList<String> ecnumbers, ArrayList<String> equations) {
		if (modelReactionNames.size() > 0) {
			String reacName = modelReactionNames.get(0);
			if (modelReactionNames.size() > 1) {
				reacName = modelReactionNames.toString();
			}
			displayName = "<html>" + reacName
					+ displayECNumber(ecnumbers)
					+ "<p> Equation: " + name
					+ displayModelEquation(equations);
		}
		return displayName;
	}
	
	public String displayECNumber(ArrayList<String> ecnumbers) {
		String ec = "";
		if (ecnumbers.size() > 0) {
			ec = "<p>EC Number: " + ecnumbers.get(0);
		}
		if (ecnumbers.size() > 1) {
			ec = "<p>EC Number(s): " + ecnumbers.toString();
		}
		return ec;
	}
	
	public String displayModelEquation(ArrayList<String> equations) {
		// since equations can be quite long and a list of reactions may not fit on screen,
		// each reaction is put on a separate line
		String modelEquationString = "";
		if (equations.size() > 0) {
			modelEquationString = "<p>Equation from Model: " + equations.get(0);
		}
		if (equations.size() > 1) {
			modelEquationString = "<p>Equation(s) from Model: " + equations.get(0);
			for (int m = 1; m < equations.size(); m++) {
				modelEquationString += ", <p>" + equations.get(m);
			}
		}
		return modelEquationString;
	}
	
	public String reversibleString(String reversibleValue) {
		String reversible = "";
		if (reversibleValue.equals("0")) {
			reversible = "false";
		} else if (reversibleValue.equals("1")) {
			reversible = "true";
		}
		return reversible;
	}

}
