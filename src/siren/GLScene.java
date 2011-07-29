package siren;

import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;

import jsi3.lib.gui.*;

/**
 * Adapter class for GLRenderer and InputListener
 */
public class GLScene implements GLRenderer, InputListener
{
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
	
	@Override
	public void key_typed( char key, int code, KeyEvent e )
	{
	}
	
	@Override
	public void key_pressed( char key, int code, KeyEvent e )
	{
	}
	
	@Override
	public void key_released( char key, int code, KeyEvent e )
	{
	}
	
	@Override
	public void mouse_clicked( MouseEvent e )
	{
	}
	
	@Override
	public void mouse_pressed( MouseEvent e )
	{
	}
	
	@Override
	public void mouse_released( MouseEvent e )
	{
	}
	
	@Override
	public void mouse_entered( MouseEvent e )
	{
	}
	
	@Override
	public void mouse_exited( MouseEvent e )
	{
	}
	
	@Override
	public void mouse_dragged( MouseEvent e )
	{
	}
	
	@Override
	public void mouse_moved( MouseEvent e )
	{
	}
	
	@Override
	public void mouse_wheel_moved( MouseWheelEvent e )
	{
	}
}
