
package siren;

import sigl.*;

import java.awt.image.*;

import jsi3.lib.maths.*;
import jsi3.lib.gui.*;

import static sigl.GLWrap.*;
import static sigl.GLUWrap.*;
import static sigl.GLUTWrap.*;

import static sigl.Statics.*;

import static jsi3.lib.maths.Statics.*;
import static jsi3.lib.gui.Statics.*;

public class Star implements GLRenderer
{
	int layers;
	
	int color;

	Texture texture, noise_texture;

	int list_no;
	
	double rot;

	BufferedImage image;
	
	public int src_blend, dst_blend;
	
	ShaderProgram haze_shader;
	
	
	static int isize = 128;
	
	static int fsize = 512;
	
	static double rot_delta = -0.00025;
	
	static double noise_offset_delta = 0.00025;
	
	

	public Star( int layers, int color, BufferedImage image ) // throws Exception
	{
		this.color = color;

		this.image = filter_image( image, new SeamlessImageFilter( image ) );
		
		this.layers = layers;
		
		//rots = new Vec2d[ layers ];
		
		//drots = new Vec2d[ layers ];
		
		src_blend = GL_ONE;
		
		dst_blend = GL_ONE;
/*
		
		for( int i=0; i<layers; i++ )
		{
			rots[ i ] = new Vec2d( rndd( -1, 1 ), rndd( -1, 1 ) );
			
			drots[ i ] = new Vec2d( rndd( -.01, .01 ), rndd( -.01, .01 ) );
		}
*/
	}


	@Override
	public void initGL() throws Exception
	{
		texture = create_texture( image );
		
		//textures[ i ].mode = GL_REPLACE;

		texture.mode = GL_REPLACE;

		texture.mag_filter = GL_LINEAR;

		texture.min_filter = GL_LINEAR;

		texture.wrap_s = GL_REPEAT;

		texture.wrap_t = GL_REPEAT;

		noise_texture = create_texture( generate_noise_map() );

		noise_texture.mode = GL_REPLACE;

		noise_texture.mag_filter = GL_LINEAR;

		noise_texture.min_filter = GL_LINEAR;

		noise_texture.wrap_s = GL_REPEAT;

		noise_texture.wrap_t = GL_REPEAT;

		image = null;

		list_no = start_list();

		//degrees();
		
		sphere( 70, 30, 1.0f, false, true );

		end_list();
		
		haze_shader = create_shader( "star.vert", "star.frag" );
		
		haze_shader.init();

	}


	static BufferedImage generate_noise_map()
	{
		//int isize = 128;
		//int fsize = 512;
		
		BufferedImage img = create_rgb_image( isize, isize );
		
		for( int x=0; x<isize; x++ )
		{
			for( int y=0; y<isize; y++ )
			{
				set_pixel( img, x, y, rgb( rnd( 0, 255 ), rnd( 0, 255 ), rnd( 0, 255 ) ) );
			}
		}
		
		img = filter_image( img, new SeamlessImageFilter( img ) );
		
		img = filter_image( img, new SeamlessImageFilterY( img ) );
		
		img = resize_image( img, fsize, fsize );
		
		view_image( img );
		
		return img;
	}


	double noise_offset = 0;

	public void updateGL( double dt )
	{
		noise_offset = ( noise_offset + noise_offset_delta ) % 1.0;
		
		rot += rot_delta;
		
/*
		for( int i=0; i<layers; i++ )
		{
			rots[ i ].x += dt * drots[ i ].x;
			rots[ i ].y += dt * drots[ i ].y;
		}
*/
	}
	
	
	public void displayGL()
	{
		texture.apply( GL_TEXTURE0 );
		
		noise_texture.apply( GL_TEXTURE1 );
		
		haze_shader.apply();
		
		haze_shader.set( "tex", 0 );
		
		haze_shader.set( "tex1", 1 );
		
		haze_shader.set( "noise_offset", noise_offset );
		
		haze_shader.set( "layers", layers );
		
		haze_shader.set( "rots", rot );
		
		
		depth_test( true );
		
		depth_write( true );
		
		glDisable( GL_BLEND );
		
		colour( color );
		
		cull( true );
			
		call_list( list_no );			
	}
	
}


/*
 * 	public void displayGL()
	{
		texture.apply();
		
		noise_texture.apply();
		
		haze_shader.apply();
		
		haze_shader.set( "tex", 0 );
		
		haze_shader.set( "tex1", 1 );
		
		depth_test( true );
		
		colour( color );
		
		cull( true );
				
		for( int i=0; i<layers; i++ )
		{
			if( i == 0 )
			{
				glDisable( GL_BLEND );
			}
			else
			{
				glEnable( GL_BLEND );
				
				//glBlendFunc( GL_ONE_MINUS_SRC_COLOR, GL_ONE_MINUS_DST_COLOR );
				
				//glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
				
				//glBlendFunc(GL_SRC_ALPHA, GL_ONE);
				
				glBlendFunc( src_blend, dst_blend );
			}
			
			if( i == layers - 1 )
			{
				depth_write( true );
				
				depth_test( true );
			}
			else
			{
				depth_write( false );
				
				depth_test( true );
			}
			
			push_matrix();
			
			rotateX( rots[ i ].x );
			
			rotateY( rots[ i ].y );
			
			rotateZ( rots[ i ].z );
			
			call_list( list_no );			
			
			pop_matrix();
			
		}

		glDisable( GL_BLEND );
	}
	*/
