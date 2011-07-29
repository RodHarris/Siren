package siren;


import sigl.*;

import java.io.*;
import java.awt.image.*;

import static sigl.Statics.*;
import static sigl.GLWrap.*;
import static sigl.GLUWrap.*;
import static sigl.GLUTWrap.*;

import jsi3.lib.maths.*;

import static jsi3.lib.text.Statics.*;
import static jsi3.lib.console.Statics.*;


public class SkyBox implements GLRenderer
{
	Texture[] textures = new Texture[ 6 ];
	
	Vec3d[][] vertices = new Vec3d[ 6 ][ 4 ];
	
	Vec2d[][] texcoords = new Vec2d[ 6 ][ 4 ];
	
	String[] texture_filenames;
	
	
	public SkyBox( double half_span, String... texture_filenames ) throws Exception
	{
		assert texture_filenames == null && texture_filenames.length != 6 : "Expecting 6 filenames to create a skybox";
		
		for( String texture_filename : texture_filenames )
		{
			if( ! new File( texture_filename ).exists() ) throw new Exception( fmt( "Skybox image %s doesn't exist", texture_filename ) );
		}
		
		this.texture_filenames = texture_filenames;
		
		double s = half_span;
		
		vertices[ 0 ][ 0 ] = new Vec3d(  s, -s, -s ); texcoords[ 0 ][ 0 ] = new Vec2d( 1, 1 );
		vertices[ 0 ][ 1 ] = new Vec3d( -s, -s, -s ); texcoords[ 0 ][ 1 ] = new Vec2d( 0, 1 );
		vertices[ 0 ][ 2 ] = new Vec3d( -s,  s, -s ); texcoords[ 0 ][ 2 ] = new Vec2d( 0, 0 );
		vertices[ 0 ][ 3 ] = new Vec3d(  s,  s, -s ); texcoords[ 0 ][ 3 ] = new Vec2d( 1, 0 );
		
		vertices[ 1 ][ 0 ] = new Vec3d(  s, -s,  s ); texcoords[ 1 ][ 0 ] = new Vec2d( 1, 1 );
		vertices[ 1 ][ 1 ] = new Vec3d(  s, -s, -s ); texcoords[ 1 ][ 1 ] = new Vec2d( 0, 1 );
		vertices[ 1 ][ 2 ] = new Vec3d(  s,  s, -s ); texcoords[ 1 ][ 2 ] = new Vec2d( 0, 0 );
		vertices[ 1 ][ 3 ] = new Vec3d(  s,  s,  s ); texcoords[ 1 ][ 3 ] = new Vec2d( 1, 0 );

		vertices[ 2 ][ 0 ] = new Vec3d( -s, -s,  s ); texcoords[ 2 ][ 0 ] = new Vec2d( 1, 1 );
		vertices[ 2 ][ 1 ] = new Vec3d(  s, -s,  s ); texcoords[ 2 ][ 1 ] = new Vec2d( 0, 1 );
		vertices[ 2 ][ 2 ] = new Vec3d(  s,  s,  s ); texcoords[ 2 ][ 2 ] = new Vec2d( 0, 0 );
		vertices[ 2 ][ 3 ] = new Vec3d( -s,  s,  s ); texcoords[ 2 ][ 3 ] = new Vec2d( 1, 0 );
		
		vertices[ 3 ][ 0 ] = new Vec3d( -s, -s, -s ); texcoords[ 3 ][ 0 ] = new Vec2d( 1, 1 );
		vertices[ 3 ][ 1 ] = new Vec3d( -s, -s,  s ); texcoords[ 3 ][ 1 ] = new Vec2d( 0, 1 );
		vertices[ 3 ][ 2 ] = new Vec3d( -s,  s,  s ); texcoords[ 3 ][ 2 ] = new Vec2d( 0, 0 );
		vertices[ 3 ][ 3 ] = new Vec3d( -s,  s, -s ); texcoords[ 3 ][ 3 ] = new Vec2d( 1, 0 );
		
		vertices[ 4 ][ 0 ] = new Vec3d( -s, -s, -s ); texcoords[ 4 ][ 0 ] = new Vec2d( 0, 0 );
		vertices[ 4 ][ 1 ] = new Vec3d(  s, -s, -s ); texcoords[ 4 ][ 1 ] = new Vec2d( 1, 0 );
		vertices[ 4 ][ 2 ] = new Vec3d(  s, -s,  s ); texcoords[ 4 ][ 2 ] = new Vec2d( 1, 1 );
		vertices[ 4 ][ 3 ] = new Vec3d( -s, -s,  s ); texcoords[ 4 ][ 3 ] = new Vec2d( 0, 1 );
		
		vertices[ 5 ][ 0 ] = new Vec3d(  s,  s, -s ); texcoords[ 5 ][ 0 ] = new Vec2d( 1, 1 );
		vertices[ 5 ][ 1 ] = new Vec3d( -s,  s, -s ); texcoords[ 5 ][ 1 ] = new Vec2d( 0, 1 );
		vertices[ 5 ][ 2 ] = new Vec3d( -s,  s,  s ); texcoords[ 5 ][ 2 ] = new Vec2d( 0, 0 );
		vertices[ 5 ][ 3 ] = new Vec3d(  s,  s,  s ); texcoords[ 5 ][ 3 ] = new Vec2d( 1, 0 );
	}
	

	@Override
	public void initGL() throws Exception
	{
		for( int f=0; f<6; f++ )
		{
			textures[ f ] = create_texture( texture_filenames[ f ] );

			textures[ f ].mode = GL_REPLACE;

			textures[ f ].mag_filter = GL_LINEAR;

			textures[ f ].min_filter = GL_LINEAR;
		}
	}
			

	@Override
	public void updateGL( double dt )
	{
	}
	

	@Override
	public void displayGL()
	{
		//cinfo.println( "displayGL" );
		
		front_cw();
		
		cull( true );
		
		//depth_test( true );
		
		for( int f=0; f<6; f++ )
		{
			textures[ f ].apply();
			
			begin_shape( GL_QUADS );

			for( int v=0; v<4; v++ )
			{
				vertex( vertices[ f ][ v ], texcoords[ f ][ v ] );
			}
			
			end_shape();
			
			textures[ f ].unapply();
		}
		
		//front_ccw();
	}
}
