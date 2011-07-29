package siren;

import jsi3.lib.maths.*;

import static sigl.Statics.*;
import static sigl.GLWrap.*;
import static sigl.GLUWrap.*;
import static sigl.GLUTWrap.*;


public class ColourSphere implements GLRenderer
{
	public final Vec3d pos = new Vec3d();
	
	public double pitch;
	
	public int colour;	

	public void initGL()
	{
	}
	
	public void updateGL( double dt )
	{
	}

	public void displayGL()
	{
		push_matrix();

		translate( pos );
		
		rotateX( pitch );
		
		colour( colour );
		
		glutSolidSphere( 1, 16, 16 );
		
		pop_matrix();
	}
}
