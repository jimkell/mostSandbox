package edu.rutgers.MOST.Analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import edu.rutgers.MOST.data.*;
import edu.rutgers.MOST.optimization.solvers.*;
import edu.rutgers.MOST.presentation.GraphicalInterfaceConstants;

public class Analysis
{
	protected Model model = new Model();
	protected Solver solver;
	protected Vector< String > varNames = new Vector< String >();
	protected double maxObj;

	public Analysis( Algorithm algorithm )
	{
		this.solver = SolverFactory.createSolver( algorithm );
		this.varNames = new Vector< String >();
	}

	private void setVars()
	{
		Vector< SBMLReaction > reactions = this.model.getReactions();
		for( int i = 0; i < reactions.size(); i++)
		{
			SBMLReaction reac = (SBMLReaction)( reactions.elementAt( i ) );
			String varName = Integer.toString( (Integer)this.model
					.getReactionsIdPositionMap().get( reac.getId() ) );
			// String varName = Integer.toString(reac.getId());
			double lb = reac.getLowerBound();
			double ub = reac.getUpperBound();

			if( reac.getKnockout().equals(
					GraphicalInterfaceConstants.BOOLEAN_VALUES[1] ) )
			{
				lb = 0;
				ub = 0;
			}

			this.getSolver().setVar( varName, VarType.CONTINUOUS, lb, ub );

			this.varNames.add( varName );
		}
	}

	private void setConstraints()
	{
		Vector< SBMLReaction > reactions = this.model.getReactions();
		setConstraints( reactions, ConType.EQUAL, 0.0 );
	}

	private void setConstraints( Vector< SBMLReaction > reactions,
			ConType conType, double bValue )
	{
		ArrayList< Map< Integer, Double >> sMatrix = this.model.getSMatrix();
		for( int i = 0; i < sMatrix.size(); i++)
		{
			this.getSolver().addConstraint( sMatrix.get( i ), conType, bValue );
		}
	}

	private void setObjective()
	{
		this.getSolver().setObjType( ObjType.Maximize );
		Vector< Double > objective = this.model.getObjective();
		Map< Integer, Double > map = new HashMap< Integer, Double >();
		for( int i = 0; i < objective.size(); i++)
		{
			if( objective.elementAt( i ) != 0.0 )
			{
				map.put( i, objective.elementAt( i ) );
			}
		}
		this.getSolver().setObj( map );
	}

	public void setModel( Model m )
	{
		this.model = m;
	}
	
	public ArrayList< Double > run()
	{
		this.setVars();
		this.setConstraints();
		this.setObjective();
		this.maxObj = this.getSolver().optimize();

		return this.getSolver().getSoln();
	}
	
	public Solver getSolver()
	{
		return solver;
	}

	public double getMaxObj()
	{
		return this.maxObj;
	}
}
