package siren;

import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;

import static sigl.Statics.*;

public class FPSCamera extends GLScene
{
/*
	public double sx = 1.0f;

	public double sy = 1.0f;

	public double sz = 1.0f;

	private int last_mouseX, last_mouseY;

	public final Vec3d target;

	public final Vec3d position;

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
		translator = new PolarTranslator( position, target );

		this.position = position;

		this.target = target;
	}


	public void update()
	{
		translator.update();
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

		int mouseX = e.getX();
		int mouseY = e.getY();

		int delta_x = mouseX - last_mouseX;
		int delta_y = mouseY - last_mouseY;

		double dx = sx * delta_x;
		double dy = sy * delta_y;

		translator.theta = ( translator.theta + dx ) % 360.0f;

		translator.phi = ( translator.phi + dy ) % 360.0f;

		if( translator.phi > 89 ) translator.phi = 89;

		if( translator.phi < -89 ) translator.phi = -89;

		last_mouseX = mouseX;
		last_mouseY = mouseY;
	}


	public void mouse_moved( MouseEvent e )
	{
		//cout.println( "MouseOrbitNavigator.mouse_moved" );
		
		last_mouseX = e.getX();
		last_mouseY = e.getY();
	}


	public void mouse_wheel_moved( MouseWheelEvent e )
	{
		int mouse_wheel = e.getWheelRotation();

		double dz = sz * mouse_wheel;

		translator.rad += dz;
	}
	*/
}
