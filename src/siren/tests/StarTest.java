package siren.tests;

import siren.*;

import static jsi3.lib.gui.Statics.*;

import jsi3.lib.gui.*;

import javax.swing.*;

import static sigl.GLWrap.*;

public class StarTest extends SirenTest
{
	Star star;
	
	public StarTest( String img_filename ) throws Exception
	{
		engine.add_component( star = new Star( 2, argb( 200, 200, 175, 50 ), load_image( img_filename ) ) );
		
		begin();
	}
	
	
	@Override
	public void begin()
	{
		engine.initialise_window_for_gl( new StarMenuFrame(), 800, 800 );
		
		engine.exit_on_close();

		engine.start_rendering( 30 );
	}
	
	
	@Override
	public void initGL() throws Exception
	{
	}

	@Override
	public void updateGL( double dt )
	{
	}

	@Override
	public void displayGL()
	{
	}
	
	class StarMenuFrame extends MenuFrame
	{
		public StarMenuFrame()
		{
			addMenuItem( "src/GL_ZERO" );
			addMenuItem( "src/GL_ONE" );
			addMenuItem( "src/GL_DST_COLOR" );
			addMenuItem( "src/GL_ONE_MINUS_DST_COLOR" );
			addMenuItem( "src/GL_SRC_ALPHA" );
			addMenuItem( "src/GL_ONE_MINUS_SRC_ALPHA" );
			addMenuItem( "src/GL_DST_ALPHA" );
			addMenuItem( "src/GL_ONE_MINUS_DST_ALPHA" );
			addMenuItem( "src/GL_SRC_ALPHA_SATURATE" );
			
			addMenuItem( "src/GL_CONSTANT_COLOR" );
			addMenuItem( "src/GL_ONE_MINUS_CONSTANT_COLOR" );
			addMenuItem( "src/GL_CONSTANT_ALPHA" );
			addMenuItem( "src/GL_ONE_MINUS_CONSTANT_ALPHA" );
			
			addMenuItem( "dst/GL_ZERO" );
			addMenuItem( "dst/GL_ONE" );
			addMenuItem( "dst/GL_SRC_COLOR" );
			addMenuItem( "dst/GL_ONE_MINUS_SRC_COLOR" );
			addMenuItem( "dst/GL_SRC_ALPHA" );
			addMenuItem( "dst/GL_ONE_MINUS_SRC_ALPHA" );
			addMenuItem( "dst/GL_DST_ALPHA" );
			addMenuItem( "dst/GL_ONE_MINUS_DST_ALPHA" );
			addMenuItem( "dst/GL_CONSTANT_COLOR" );
			addMenuItem( "dst/GL_ONE_MINUS_CONSTANT_COLOR" );
			addMenuItem( "dst/GL_CONSTANT_ALPHA" );
			addMenuItem( "dst/GL_ONE_MINUS_CONSTANT_ALPHA" );
			
			

					
		}
		
		public void menuItemSelected( String item ) 
		{
			if( "src/GL_ZERO".equals( item ) ) star.src_blend = GL_ZERO;
			
			if( "src/GL_ONE".equals( item ) ) star.src_blend = GL_ONE;
			
			if( "src/GL_DST_COLOR".equals( item ) ) star.src_blend = GL_DST_COLOR;
			
			if( "src/GL_ONE_MINUS_DST_COLOR".equals( item ) ) star.src_blend = GL_ONE_MINUS_DST_COLOR;
			
			if( "src/GL_SRC_ALPHA".equals( item ) ) star.src_blend = GL_SRC_ALPHA;
			
			if( "src/GL_ONE_MINUS_SRC_ALPHA".equals( item ) ) star.src_blend = GL_ONE_MINUS_SRC_ALPHA;
			
			if( "src/GL_DST_ALPHA".equals( item ) ) star.src_blend = GL_DST_ALPHA;
			
			if( "src/GL_ONE_MINUS_DST_ALPHA".equals( item ) ) star.src_blend = GL_ONE_MINUS_DST_ALPHA;
			
			if( "src/GL_SRC_ALPHA_SATURATE".equals( item ) ) star.src_blend = GL_SRC_ALPHA_SATURATE;
			
			
			if( "src/GL_CONSTANT_COLOR".equals( item ) ) star.src_blend = GL_CONSTANT_COLOR;
			
			if( "src/GL_ONE_MINUS_CONSTANT_COLOR".equals( item ) ) star.src_blend = GL_ONE_MINUS_CONSTANT_COLOR;
			
			if( "src/GL_CONSTANT_ALPHA".equals( item ) ) star.src_blend = GL_CONSTANT_ALPHA;
			
			if( "src/GL_ONE_MINUS_CONSTANT_ALPHA".equals( item ) ) star.src_blend = GL_ONE_MINUS_CONSTANT_ALPHA;
			
			
			if( "dst/GL_ZERO".equals( item ) ) star.dst_blend = GL_ZERO;
			
			if( "dst/GL_ONE".equals( item ) ) star.dst_blend = GL_ONE;
			
			if( "dst/GL_SRC_COLOR".equals( item ) ) star.dst_blend = GL_SRC_COLOR;
			
			if( "dst/GL_ONE_MINUS_SRC_COLOR".equals( item ) ) star.dst_blend = GL_ONE_MINUS_SRC_COLOR;
			
			if( "dst/GL_SRC_ALPHA".equals( item ) ) star.dst_blend = GL_SRC_ALPHA;
			
			if( "dst/GL_ONE_MINUS_SRC_ALPHA".equals( item ) ) star.dst_blend = GL_ONE_MINUS_SRC_ALPHA;
			
			if( "dst/GL_DST_ALPHA".equals( item ) ) star.dst_blend = GL_DST_ALPHA;
			
			if( "dst/GL_ONE_MINUS_DST_ALPHA".equals( item ) ) star.dst_blend = GL_ONE_MINUS_DST_ALPHA;
			
			if( "dst/GL_CONSTANT_COLOR".equals( item ) ) star.dst_blend = GL_CONSTANT_COLOR;
			
			if( "dst/GL_ONE_MINUS_CONSTANT_COLOR".equals( item ) ) star.dst_blend = GL_ONE_MINUS_CONSTANT_COLOR;
			
			if( "dst/GL_CONSTANT_ALPHA".equals( item ) ) star.dst_blend = GL_CONSTANT_ALPHA;
			
			if( "dst/GL_ONE_MINUS_CONSTANT_ALPHA".equals( item ) ) star.dst_blend = GL_ONE_MINUS_CONSTANT_ALPHA;
		}
	}
}
