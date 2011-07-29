package siren;

import static jsi3.lib.maths.Statics.*;

import jsi3.lib.maths.*;


public class PolarTranslator2
{
	public final Vec3d target;

	public final Vec3d position;

	public double theta;

	public double phi;

	public double current_theta;

	public double current_phi;

	public double rad;


	public PolarTranslator2()
	{
		this( new Vec3d(), new Vec3d() );
	}

	public PolarTranslator2( Vec3d target )
	{
		this( target, new Vec3d() );
	}


	public PolarTranslator2( Vec3d position, Vec3d target )
	{
		this.position = position;

		this.target = target;
	}


	public void update()
	{
		// the object deg referrs to Trig jsi3.lib.maths.Statics.deg

		current_theta += 0.1 * ( theta - current_theta );
		current_phi += 0.1 * ( phi - current_phi );

		
		double cos_theta = deg.cos( current_theta );
		double cos_phi = deg.cos( current_phi );

		double sin_theta = deg.sin( current_theta );
		double sin_phi = deg.sin( current_phi );

		position.x = target.x + rad * sin_theta * cos_phi;
		position.y = target.y + rad * sin_phi;
		position.z = target.z + rad * cos_theta * cos_phi;
	}
}
