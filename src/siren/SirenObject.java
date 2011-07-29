package siren;

import static jsi3.lib.text.Statics.*;

public abstract class SirenObject
{
	protected Siren engine;

	public void set_engine( Siren engine )
	{
		if( this.engine != null ) throw new IllegalArgumentException( fmt( "This object (%s) has already been added to a Siren engine (%s) (and can't be removed)", this, this.engine ) );
		
		this.engine = engine;
	}

	public Siren get_engine()
	{
		return engine;
	}
}
