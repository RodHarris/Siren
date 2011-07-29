package siren;

import static siren.Siren.*;

import jsi3.lib.maths.*;

import static jsi3.lib.system.Statics.*;
import static jsi3.lib.gui.Statics.*;
import static jsi3.lib.console.Statics.*;
import static jsi3.lib.maths.Statics.*;

import java.io.*;
import java.awt.image.*;

import sigl.*;

import static sigl.Statics.*;
import static sigl.GLWrap.*;
import static sigl.GLUWrap.*;
import static sigl.GLUTWrap.*;


public class Terrain
{
	private BufferedImage height_map_image, normal_map_image, colour_map_image;

	private Texture height_map_texture, normal_map_texture, colour_map_texture;

	private ShaderProgram height_map_shader;

	private int tw, th;
	
	private float ox, oz;
	
	private String vertex_shader_filename;
	
	private String fragment_shader_filename;
	
	
	public Terrain( BufferedImage dem_img, BufferedImage norm_img, BufferedImage colour_img, int new_width, int new_height, String vertex_shader_filename, String fragment_shader_filename )
	{
		height_map_image = resize_image( dem_img, new_width, new_height );
		
		normal_map_image = resize_image( norm_img, new_width, new_height );
		
		colour_map_image = resize_image( colour_img, new_width, new_height );
		
		tw = new_width;
		
		th = new_height;
		
		this.vertex_shader_filename = vertex_shader_filename;
		
		this.fragment_shader_filename = fragment_shader_filename;
	}

	
	public void init() throws FileNotFoundException, IOException
	{
		height_map_texture = create_texture( height_map_image );
		
		height_map_texture.mag_filter = GL_LINEAR;
		
		height_map_texture.min_filter = GL_LINEAR;
		
		height_map_texture.wrap_s = GL_REPEAT;
		
		height_map_texture.wrap_t = GL_REPEAT;
		
		height_map_texture.mode = GL_REPLACE;
		
		height_map_image = null;
		
		normal_map_texture = create_texture( normal_map_image );
		
		normal_map_texture.mag_filter = GL_LINEAR;
		
		normal_map_texture.min_filter = GL_LINEAR;
		
		normal_map_texture.wrap_s = GL_REPEAT;
		
		normal_map_texture.wrap_t = GL_REPEAT;

		normal_map_texture.mode = GL_REPLACE;
		
		normal_map_image = null;
		
		colour_map_texture = create_texture( colour_map_image );
		
		colour_map_texture.mag_filter = GL_LINEAR;
		
		colour_map_texture.min_filter = GL_LINEAR;
		
		colour_map_texture.wrap_s = GL_REPEAT;
		
		colour_map_texture.wrap_t = GL_REPEAT;

		colour_map_texture.mode = GL_REPLACE;
		
		colour_map_image = null;
		
		height_map_shader = create_shader( vertex_shader_filename, fragment_shader_filename );

		height_map_shader.init();
	}

	
	public void set_offset( float ox, float oz )
	{
		this.ox = ox;
		
		this.oz = oz;
	}
	
	
	public void apply()
	{
		height_map_texture.apply( GL_TEXTURE0 );
		
		normal_map_texture.apply( GL_TEXTURE1 );
		
		colour_map_texture.apply( GL_TEXTURE2 );

		height_map_shader.apply();
		
		height_map_shader.set( "tex0", 0 );
		
		height_map_shader.set( "tex1", 1 );
		
		height_map_shader.set( "tex2", 2 );
		
		height_map_shader.set( "tw", tw );

		height_map_shader.set( "th", th );

		height_map_shader.set( "ox", ox );

		height_map_shader.set( "oz", oz );
	}
	

	public void unapply()
	{
		height_map_texture.unapply();
		
		normal_map_texture.unapply();
		
		colour_map_texture.unapply();
		
		height_map_shader.unapply();
	}
}
