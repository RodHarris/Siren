package siren;


import jsi3.lib.gui.*;
import jsi3.lib.maths.*;

import static jsi3.lib.gui.Statics.*;
import static jsi3.lib.system.Statics.*;
import static jsi3.lib.console.Statics.*;

import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;

import static sigl.Statics.*;


public class MouseOrbitNavigator extends PolarTranslator implements InputListener
{
	public double sx = 1.0f;

	public double sy = 1.0f;

	public double sz = 1.0f;

	private int last_mouseX, last_mouseY;

	public int drag_button = any_mouse;

	private boolean dragging;


	public MouseOrbitNavigator()
	{
		this( new Vec3d(), new Vec3d() );
	}


	public MouseOrbitNavigator( Vec3d target )
	{
		this( target, new Vec3d() );
	}


	public MouseOrbitNavigator( Vec3d position, Vec3d target )
	{
		super( position, target );
	}


	public void mouse_pressed( MouseEvent e )
	{
		dragging = mouse_button( drag_button, e );
	}

	public void mouse_released( MouseEvent e )
	{
		dragging = false;
	}

	public void mouse_dragged( MouseEvent e )
	{
		if( ! dragging ) return;

		mouse_rotating( e );
	}


	public void mouse_moved( MouseEvent e )
	{
		//cout.println( "MouseOrbitNavigator.mouse_moved" );

		last_mouseX = e.getX();
		last_mouseY = e.getY();
	}

	private void mouse_rotating( MouseEvent e )
	{
		cverbose.println( mtrace() );

		int mouseX = e.getX();
		int mouseY = e.getY();

		int delta_x = mouseX - last_mouseX;
		int delta_y = mouseY - last_mouseY;

		double dx = sx * delta_x;
		double dy = sy * delta_y;

		theta = ( theta - dx ) % 360.0f;

		if( theta < 0 ) theta += 360;

		phi += dy;

		if( phi > 90 ) phi = 90;

		if( phi < -90 ) phi = -90;

		last_mouseX = mouseX;

		last_mouseY = mouseY;
	}

	public void mouse_wheel_moved( MouseWheelEvent e )
	{
		int mouse_wheel = e.getWheelRotation();

		double dz = sz * mouse_wheel;

		rad += dz;
	}

	public void key_typed( char key, int code, KeyEvent e ){};

	public void key_pressed( char key, int code, KeyEvent e ){};

	public void key_released( char key, int code, KeyEvent e ){};

	public void mouse_clicked( MouseEvent e ){};

	public void mouse_entered( MouseEvent e ){};

	public void mouse_exited( MouseEvent e ){};
}
