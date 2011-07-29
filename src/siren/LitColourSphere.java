package siren;


import static jsi3.lib.maths.Statics.*;
import static jsi3.lib.system.Statics.*;
import static jsi3.lib.gui.Statics.*;

import jsi3.lib.maths.*;

import static sigl.Statics.*;
import static sigl.GLWrap.*;
import static sigl.GLUWrap.*;
import static sigl.GLUTWrap.*;


public class LitColourSphere implements GLRenderer
{
	public final Vec3d pos = new Vec3d();
	
	public final Vec3d light_pos = new Vec3d();
	
	public double pitch;
	
	public int colour;	


	public void initGL()
	{
	}
	
	public void updateGL( double dt )
	{
		//degrees();
		
		long t = ( systime() % 3600 ) / 10;
		
		//cout.println( t );
		
		light_pos.x = 10 * deg.cos( t );
		
		light_pos.z = 10 * deg.sin( t );
	}
	
	public void displayGL()
	{
		cull( true );
		
		lights( true );
		
		antialias( true );
		
		depth_test( true );
		
		ambient_light( DarkGray );
		
		point_light( White, light_pos.x, light_pos.y, light_pos.z);
		
		push_matrix();
		
		translate( pos );
		
		rotateX( pitch );
		
		colour( colour );
		
		//glutSolidSphere( 1, 32, 16 );
		
		sphere( 16, 16, 1, 1, 1, true, false );
		
		//sphere( int xsegs, int ysegs, double radius, int xtile, int ytile, boolean normals, boolean texcoords )
		
		pop_matrix();
	}
}
