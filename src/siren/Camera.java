package siren;


import jsi3.lib.maths.*;

import static jsi3.lib.maths.Statics.*;
import static jsi3.lib.gui.Statics.*;
import static jsi3.lib.console.Statics.*;
import static jsi3.lib.system.Statics.*;

import static sigl.Statics.*;
import static sigl.GLWrap.*;
import static sigl.GLUWrap.*;


public class Camera extends SirenObject implements GLRenderer
{
	public double xfov = 45;

	public double near = 0.01;

	public double far = 10000;

	public final Vec3d position = new Vec3d( 0, 0, 5 );

	public final Vec3d target = new Vec3d( 0, 0, 0 );

	public final Vec3d up = new Vec3d( 0, 1, 0 );

	public final Vec3d X = new Vec3d();

	public final Vec3d Y = new Vec3d();
	
	public final Vec3d Z = new Vec3d();

	public final Vec3d nc = new Vec3d();

	public final Vec3d fc = new Vec3d();

	public boolean active = true;
	
	public boolean render = false;

	public Mat4d mv_tmp = new Mat4d();

	final Plane[] planes = new Plane[ 6 ];

	final Vec3d aux = new Vec3d();

	final Vec3d normal = new Vec3d();	

	final Vec3d nearZ = new Vec3d();

	final Vec3d farZ = new Vec3d();


	int near_cp = 0;
	int far_cp = 1;
	int top_cp = 2;
	int bottom_cp = 3;
	int left_cp = 4;
	int right_cp = 5;

	
	@Override
	public void initGL()
	{
		cverbose.println( mtrace() );
		
		planes[ near_cp ] = new Plane();
		planes[ far_cp ] = new Plane();
		planes[ top_cp ] = new Plane();
		planes[ bottom_cp ] = new Plane();
		planes[ left_cp ] = new Plane();
		planes[ right_cp ] = new Plane();
	}

	
	@Override
	public void updateGL( double dt )
	{
		cverbose.println( mtrace() );
	}


	@Override
	public void displayGL()
	{
		cverbose.println( mtrace() );
		
		double aspect = 0;

		double yfov = 0;
		
		if( active )
		{
			aspect = clip( 0, 0, get_engine().canvas.getWidth(), get_engine().canvas.getHeight() );

			yfov = xfov / aspect;
			
			glMatrixMode( GL_PROJECTION );

			glLoadIdentity();

			gluPerspective( yfov, aspect, near, far );
			
			//glDepthRange( near, far );

			//cverbose.println( "camera: %s, %s, %s", position, target, up );

			camera( position, target, up );

			get_modelview_matrix( mv_tmp );

			X.set( mv_tmp.m00, mv_tmp.m01, mv_tmp.m02 );
			Y.set( mv_tmp.m10, mv_tmp.m11, mv_tmp.m12 );
			Z.set( -mv_tmp.m20, -mv_tmp.m21, -mv_tmp.m22 );
		}
		else
		{
			push_matrix();

			camera( position, target, up );
			
			get_modelview_matrix( mv_tmp );

			X.set( mv_tmp.m00, mv_tmp.m01, mv_tmp.m02 );
			Y.set( mv_tmp.m10, mv_tmp.m11, mv_tmp.m12 );
			Z.set( -mv_tmp.m20, -mv_tmp.m21, -mv_tmp.m22 );

			pop_matrix();

			double width = get_engine().canvas.getWidth();

			double height = get_engine().canvas.getHeight();

			aspect = width / height;

			yfov = xfov / aspect;
		}

		double tang = deg.tan( yfov * 0.5 );
		
		double nh = near * tang;
		
		double nw = nh * aspect;
		
		double fh = far * tang;
		
		double fw = fh * aspect;
		
		if( render )
		{
			begin_shape( GL_LINES );

			colour( Red );
			draw_vector( position, X );

			colour( Green );
			draw_vector( position, Y );

			colour( Blue );
			draw_vector( position, Z );

			end_shape();
		}

		nearZ.set( Z );

		nearZ.scale( near );

		nc.sum( position, nearZ );

		farZ.set( Z );

		farZ.scale( far );

		fc.sum( position, farZ );

		planes[ near_cp ].n.set( 1, Z );
		planes[ near_cp ].p.set( nc );

		planes[ far_cp ].n.set( -1, Z );
		planes[ far_cp ].p.set( fc );

		aux.set( nc );
		aux.add( nh, Y );
		aux.sub( position );
		aux.normalise();
		normal.cross( aux, X );
		planes[ top_cp ].n.set( normal );
		planes[ top_cp ].p.set( nc );
		planes[ top_cp ].p.add( nh, Y );

		aux.set( nc );
		aux.add( -nh, Y );
		aux.sub( position );
		aux.normalise();
		normal.cross( X, aux );
		planes[ bottom_cp ].n.set( normal );
		planes[ bottom_cp ].p.set( nc );
		planes[ bottom_cp ].p.add( -nh, Y );

		aux.set( nc );
		aux.add( -nw, X );
		aux.sub( position );
		aux.normalise();
		normal.cross( aux, Y );
		planes[ left_cp ].n.set( normal );
		planes[ left_cp ].p.set( nc );
		planes[ left_cp ].p.add( -nw, X );

		aux.set( nc );
		aux.add( nw, X );
		aux.sub( position );
		aux.normalise();
		normal.cross( Y, aux );
		planes[ right_cp ].n.set( normal );
		planes[ right_cp ].p.set( nc );
		planes[ right_cp ].p.add( nw, X );
	}

	/**
	 * Check to see if the given point in within this camera's view frustum
	 * the point may still be occluded - this check doesn't take that into account
	 */
	public boolean can_potentially_see( Vec3d p )
	{
		for( Plane plane : planes )
		{
			if( distance_between_point_and_plane( p, plane.n, plane.p ) < 0 ) return false;
		}

		return true;
	}

	/**
	 * Check to see if any part of the given sphere in within this camera's view frustum
	 * the sphere may still be occluded - this check doesn't take that into account
	 */
	public boolean can_potentially_see( Vec3d p, double r )
	{
		for( Plane plane : planes )
		{
			if( distance_between_point_and_plane( p, plane.n, plane.p ) < -r ) return false;
		}

		return true;
	}
}
