
package siren;

import sigl.*;

import java.awt.image.*;

import static sigl.GLWrap.*;
import static sigl.GLUWrap.*;
import static sigl.GLUTWrap.*;

import static sigl.Statics.*;

public class SkySphere implements GLRenderer
{
	BufferedImage image;

	Texture texture;

	int list_no;

	double radius;

	int xtile, ytile;


	public SkySphere( double radius, int xtile, int ytile, BufferedImage image )
	{
		this.radius = radius;

		this.image = image;

		this.xtile = xtile;

		this.ytile = ytile;
	}


	public void initGL()
	{
		texture = create_texture( image );

		texture.mode = GL_REPLACE;

		texture.mag_filter = GL_LINEAR;

		texture.min_filter = GL_LINEAR;

		image = null;

		list_no = start_list();

		sphere( 16, 8, radius, xtile, ytile, false, true );

		end_list();
	}


	public void updateGL( double dt )
	{
	}
	

	public void displayGL()
	{
		front_cw();

		texture.apply();

		call_list( list_no );

		front_ccw();

		texture.unapply();
	}
}
