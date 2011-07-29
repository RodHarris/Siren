package siren.tests;

import siren.*;

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


public class SirenTest extends GLScene
{
	Siren engine = new Siren();
	
	MonCamera camera = new MonCamera();
	
	public SirenTest()
	{
		engine.add_component( this );
		
		engine.add_component( new CLW() );

		engine.add_component( camera );
	}
	
	public void begin()
	{
		engine.initialise_gl_window( 800, 600 );

		engine.exit_on_close();

		engine.start_rendering( 30 );
	}
}
