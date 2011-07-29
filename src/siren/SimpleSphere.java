package siren;

import jsi3.lib.maths.*;

import static sigl.Statics.*;
import static sigl.GLWrap.*;
import static sigl.GLUWrap.*;
import static sigl.GLUTWrap.*;


public class SimpleSphere implements GLRenderer
{
	public final Vec3d pos = new Vec3d();
	
	public double pitch;
	
	
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
		
		glutWireSphere( 1, 16, 16 );
		
		pop_matrix();
	}
}
