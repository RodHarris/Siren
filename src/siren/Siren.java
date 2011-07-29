package siren;


import javax.swing.*;
import javax.media.opengl.*;
import javax.media.opengl.glu.*;

import com.sun.opengl.util.*;
import com.sun.opengl.util.j2d.*;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.nio.*;
import java.io.*;

import jsi3.lib.gui.*;
import jsi3.lib.maths.*;

import static jsi3.lib.console.Statics.*;
import static jsi3.lib.gui.Statics.*;
import static jsi3.lib.maths.Statics.*;
import static jsi3.lib.system.Statics.*;
import static jsi3.lib.text.Statics.*;

import static sigl.Statics.*;
import static sigl.GLWrap.*;
import static sigl.GLUWrap.*;
import static sigl.GLUTWrap.*;


public class Siren
{
	public final GLCapabilities gl_config = new GLCapabilities();

	private final GLFunc glfunc = new GLFunc();

	public ArrayList<GLRenderer> init_renderers = new ArrayList<GLRenderer>();

	public ArrayList<GLRenderer> renderers = new ArrayList<GLRenderer>();

	public JFrame glwin;

	public GLCanvas canvas;

	public final InputAggregator input_handler = new InputAggregator();

	public final TextRenderer text_renderer = new TextRenderer( new Font( "SansSerif", Font.BOLD, 36 ) );

	private boolean init_render;

	public final Mat4d camera_matrix = new Mat4d();
	
	public Siren()
	{
		cdebug.println( "new Siren engine: %s", this );

		//Thread.currentThread().dumpStack();

		gl_config.setRedBits( 8 );
		gl_config.setGreenBits( 8 );
		gl_config.setBlueBits( 8 );
		gl_config.setAlphaBits( 8 );

		gl_config.setDoubleBuffered( true );

		gl_config.setDepthBits( 24 );
	}


	public static void start() throws Exception
	{
		//lit_cylinder_test()

		Tests.start();
	}


	public JFrame initialise_gl_window( int width, int height )
	{
		return initialise_window_for_gl( new JFrame(), width, height );
	}


	public JFrame initialise_window_for_gl( JFrame _window, int width, int height )
	{
		_window.setSize( width, height );

		return initialise_window_for_gl( _window );
	}


	public JFrame initialise_window_for_gl( JFrame _window )
	{
		if( glwin != null ) throw new IllegalStateException( "This Siren instance is already managing a gl window" );

		glwin = _window;

		canvas = new GLCanvas( gl_config );

		cdebug.println( "canvas: %s", canvas );

		canvas.addGLEventListener( glfunc );

		position_window( glwin, 0.5, 0.5 );

		glwin.add( canvas );

		input_handler.listen_to( canvas );

		return glwin;
	}


	public void add_component( Object... components )
	{
		for( Object component : components )
		{
			boolean added = false;

			if( component instanceof GLRenderer )
			{
				add_renderer_component( (GLRenderer) component );

				added = true;
			}

			if( component instanceof InputListener )
			{
				add_listener_component( (InputListener) component );

				added = true;
			}

			if( ! added ) throw new IllegalArgumentException( component + " not a GLRenderer or InputListener" );

			if( component instanceof SirenObject )
			{
				( (SirenObject) component ).set_engine( this );
			}
		}
	}


	private void add_renderer_component( GLRenderer renderer )
	{
		init_renderers.add( renderer );

		init_render = true;
	}


	private void add_listener_component( InputListener listener )
	{
		input_handler.add_listener( listener );
	}



	public void start_rendering()
	{
		start_rendering( -1 );
	}


	/**
	*	start rendering at the specified framerate
	*/
	public void start_rendering( int framerate )
	{
		glwin.setVisible( true );

		canvas.requestFocus();

		if( framerate > 50 )
		{
			cwarn.println( "requested framerate > 50fps, setting to unlimited" );

			framerate = -1;
		}

		Runnable display_caller = null;
		
		if( framerate > 0 )
		{
			double period = 1000.0f / framerate;

			final long millisecond_period = (long) period;

			display_caller = new Runnable()
			{
				public void run()
				{
					try
					{
						long t0, elapsed, wait_millis;

						while( true )
						{
							t0 = System.currentTimeMillis();

							display();

							elapsed = System.currentTimeMillis() - t0;

							wait_millis = millisecond_period - elapsed;

							if( wait_millis > 0 )
							{
								Thread.currentThread().sleep( wait_millis );
							}
						}
					}
					catch( Throwable ex )
					{
						cerr.println( ex_to_string( ex ) );
						
						return;
					}
				}
			};
		}
		else // if( framerate < 0 || framerate == 0 )
		{
			cdebug.println( "unlimited framerate rendering" );
			
			display_caller = new Runnable()
			{
				public void run()
				{
					while( true )
					{
						display();
					}
				}
			};
		}
		
		assert display_caller != null;
		
		Thread display_thread = new Thread( display_caller );
		
/*
		display_thread.setDefaultUncaughtExceptionHandler
		(
			new Thread.UncaughtExceptionHandler()
			{
				public void uncaughtException( Thread t, Throwable e )
				{
					//cerr.println( ex_to_string( e ) );
					
					System.exit( 0 );
				}
			} 
		);
*/
		
		display_thread.start();
	}


	public void exit_on_close()
	{
		glwin.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	}


	public void exit_on_esc()
	{
		//exit_on_esc = true;
	}


	/*-------------------------------------------------------------------------------------------------*/


	private void display()
	{
		canvas.display();
	}


	private class GLFunc implements GLEventListener
	{
		long last_frame;
		
		public void init( GLAutoDrawable drawable )
		{
			gl = drawable.getGL();

			gl = new DebugGL( gl );

			glu = new GLU();

			glut = new GLUT();

			last_frame = systime();
		}

		public void display( GLAutoDrawable drawable )
		{
			if( init_render )
			{
				for( GLRenderer renderer : init_renderers )
				{
					try
					{
						renderer.initGL();
					}
					catch( Exception ex )
					{
						if( ex instanceof RuntimeException ) throw (RuntimeException) ex;
						
						throw new RuntimeException( ex );
					}

					renderers.add( renderer );
				}

				init_renderers.clear();

				init_render = false;
			}

			long this_frame = systime();
		
			double dt = 0.001 * (this_frame - last_frame );

			last_frame = this_frame;

			for( GLRenderer renderer : renderers )
			{
				renderer.updateGL( dt );
			}

			for( GLRenderer renderer : renderers )
			{
				renderer.displayGL();
			}

 			check_error();
		}

		public void displayChanged( GLAutoDrawable drawable, boolean mode_changed, boolean device_changed )
		{
		}

		public void reshape( GLAutoDrawable drawable, int x, int y, int w, int h )
		{
		}
	}
}
