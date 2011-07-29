package siren;


import static jsi3.lib.maths.Statics.*;
import static jsi3.lib.system.Statics.*;
import static jsi3.lib.console.Statics.*;

import jsi3.lib.maths.*;


public class PolarTranslator
{
	public final Vec3d target;

	public final Vec3d position;

	public double theta;

	public double phi;

	public double rad;


	public PolarTranslator()
	{
		this( new Vec3d(), new Vec3d() );
	}

	public PolarTranslator( Vec3d target )
	{
		this( target, new Vec3d() );
	}


	public PolarTranslator( Vec3d position, Vec3d target )
	{
		this.position = position;

		this.target = target;
	}


	public void update()
	{
		// the object deg referrs to Trig jsi3.lib.maths.Statics.deg

		cverbose.println( mtrace() );
		
		double cos_theta = deg.cos( theta );
		double cos_phi = deg.cos( phi );

		double sin_theta = deg.sin( theta );
		double sin_phi = deg.sin( phi );

		position.x = target.x + rad * sin_theta * cos_phi;
		position.y = target.y + rad * sin_phi;
		position.z = target.z + rad * cos_theta * cos_phi;
	}
}
