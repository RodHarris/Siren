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

public class Tests
{
	static Siren engine = new Siren();

	public static void start() throws Exception
	{
		quad_strip_test();
	}

	static void quad_strip_test()
	{
		engine.initialise_gl_window( 800, 600 );

		engine.exit_on_close();

		engine.add_component( new CLW(  ) );

 		MonCamera camera = new MonCamera(  );

		engine.add_component( camera );

		//camera.rad = 5;

		//degrees();

		engine.add_component( new GLScene()
		{
			public void initGL()
			{
				wireframe( true );
			}

			public void displayGL()
			{
				quad_strip_grid( new Vec3d( -10, 0, 0 ), new Vec3d( 10, 0, 5 ), 2, 10 );
			}
		}
		);

		engine.start_rendering( 30 );
	}


	static void lit_sphere_test()
	{
		engine.initialise_gl_window( 800, 600 );

		engine.exit_on_close();

		engine.add_component( new CLW(  ) );

		MonCamera camera = new MonCamera(  );

		engine.add_component( camera );

		//camera.rad = 5;

		//degrees();

		engine.add_component( new SirenTest() );

// 		{
// 			SimpleSphere s = new SimpleSphere();
//
// 			s.pos.z = -5;
//
// 			s.pitch = 90;
//
// 			//add_scenes( ss );
// 		}
//
// 		{
// 			ColourSphere s = new ColourSphere();
//
// 			s.pos.z = -5;
//
// 			s.pitch = 90;
//
// 			s.colour = Red;
//
// 			//add_scenes( s );
// 		}
//
		{
			LitColourSphere s = new LitColourSphere();

			//s.pos.z = -5;

			s.pitch = 0;

			s.colour = Red;

			engine.add_component( s );
		}

		engine.start_rendering( 30 );

	}

	static void lit_cylinder_test()
	{
		//Terrain.run();
		engine.initialise_gl_window( 800, 600 );

		engine.exit_on_close();

		engine.add_component( new CLW(  ) );

		MonCamera camera = new MonCamera(  );

		engine.add_component( camera );

		//camera.rad = 5;

		//degrees();

		engine.add_component( new SirenTest() );

		engine.start_rendering( 30 );
	}
}

class SirenTest extends GLScene
{
	public void initGL()
	{
		depth_test( true );

		cull( true );
	}

	public void displayGL()
	{
		lights( false );

		begin_shape( GL_LINES );

		colour( Red );

		draw_vector( 0, 0, 0, 1, 0, 0 );

		colour( Green );

		draw_vector( 0, 0, 0, 0, 1, 0 );

		colour( Blue );

		draw_vector( 0, 0, 0, 0, 0, 1 );

		end_shape();

		lights( true );

		point_light( 1, LightGray, 10, 20, 30 );

		point_light( 2, Gray, -10, -20, -30 );

		//cube( 1 );

		//glutSolidCube( 1 );

		//glutSolidCylinder( 1, 0.75, 8, 1 );

		cylinder( 1, 1, 36 );
	}
}
