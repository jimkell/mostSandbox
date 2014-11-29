package edu.rutgers.MOST.presentation;

import java.io.File;

public class GurobiParametersDialog extends AbstractParametersDialog
{
	private static final long serialVersionUID = 1L;

	public GurobiParametersDialog( File saveFile )
	{
		super( "Gurobi", saveFile );
		GurobiParameters params = new GurobiParameters();
		add( params.getDialogPanel(), params.getSavableParameters() );
		finishSetup();
		setModal( true );
	}
}







