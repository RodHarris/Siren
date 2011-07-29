package siren.obj;

import siren.*;

import sigl.*;

import jsi3.lib.maths.*;

import java.util.*;
import java.io.*;
import java.awt.image.*;

import static sigl.Statics.*;
import static sigl.GLWrap.*;

import static jsi3.lib.maths.Statics.*;
import static jsi3.lib.filesystem.Statics.*;
import static jsi3.lib.system.Statics.*;
import static jsi3.lib.console.Statics.*;
import static jsi3.lib.gui.Statics.*;


public class ObjModelRenderer implements GLRenderer
{
	public final Vec3d min_bound = new Vec3d();

	public final Vec3d max_bound = new Vec3d();

	public final Vec3d middle = new Vec3d();

	public final Vec3d extent = new Vec3d();
	
	public ArrayList<Vec3d> verticies = new ArrayList<Vec3d>();

	public ArrayList<Vec3d> normals = new ArrayList<Vec3d>();

	public ArrayList<Vec2d> tex_coords = new ArrayList<Vec2d>();

	public ArrayList<Group> groups = new ArrayList<Group>();

	public Map<String,Material> materials;

	public int total_faces;

	public void initGL()
	{
		for( Material mat : materials.values() ) mat.initGL();

		min_bound.set( verticies.get( 0 ) );

		max_bound.set( verticies.get( 0 ) );
		
		for( Vec3d v : verticies )
		{
			if( v.x < min_bound.x ) min_bound.x = v.x;

			if( v.y < min_bound.y ) min_bound.y = v.y;

			if( v.z < min_bound.z ) min_bound.z = v.z;

			if( v.x > max_bound.x ) max_bound.x = v.x;

			if( v.y > max_bound.y ) max_bound.y = v.y;

			if( v.z > max_bound.z ) max_bound.z = v.z;
		}

		middle.x = ( min_bound.x + max_bound.x ) / 2;

		middle.y = ( min_bound.y + max_bound.y ) / 2;

		middle.z = ( min_bound.z + max_bound.z ) / 2;

		extent.x = ( max_bound.x - middle.x ) * 2;

		extent.y = ( max_bound.y - middle.y ) * 2;

		extent.z = ( max_bound.z - middle.z ) * 2;
	}
	
	public void updateGL( double dt )
	{
	}

	public void displayGL()
	{
		render_immediate();
	}

	public void render_immediate()
	{
		for( Group group : groups )
		{
			materials.get( group.material_name ).apply();

			begin_shape( GL_TRIANGLES );
			
			for( Face face : group.faces )
			{
				for( VTNIndex vtn_index: face.indicies )
				{
					if( vtn_index.normal != -1 )
					{
						normal( normals.get( vtn_index.normal ) );
					}
					if( vtn_index.tex_coord != -1 )
					{
						tex_coord( tex_coords.get( vtn_index.tex_coord ) );
					}

					vertex( verticies.get( vtn_index.vertex ) );
				}
			}

			end_shape();
		}
	}

	public void render_bounds()
	{
	}
	
	public void render_normals()
	{
		colour( Blue );
		
		begin_shape( GL_LINES );

		for( Group group : groups )
		{
			for( Face face : group.faces )
			{
				for( VTNIndex vtn_index: face.indicies )
				{
					if( vtn_index.normal != -1 )
					{
						draw_vector( verticies.get( vtn_index.vertex ), normals.get( vtn_index.normal ) );

						//cout.println( "draw vector %s + %s", verticies.get( vtn_index.vertex ), normals.get( vtn_index.normal ) );
					}
				}
			}
		}

		end_shape();
	}
}



class Material
{
	public int ambient = rgb( 0.2, 0.2, 0.2 );

	public int diffuse = rgb( 0.8, 0.8, 0.8 );

	public int specular = rgb( 1.0, 1.0, 1.0 );

	public double shininess = 0;

	public double alpha = 1.0;

	public BufferedImage diffuse_img;

	public Texture diffuse_texture;

	static Material default_material = new Material();

	static
	{
		default_material.init();
	}

	public void init()
	{
		ambient = argb( alpha, argbRd( ambient ), argbGd( ambient ), argbBd( ambient ) );

		diffuse = argb( alpha, argbRd( diffuse ), argbGd( diffuse ), argbBd( diffuse ) );

		specular = argb( alpha, argbRd( specular ), argbGd( specular ), argbBd( specular ) );
	}

	
	public void initGL()
	{
		if( diffuse_img != null )
		{
			diffuse_texture = create_texture( diffuse_img );

			diffuse_img = null;

			diffuse_texture.mode = GL_MODULATE;
		}
	}

	public void apply()
	{
		colour( diffuse );
		
		ambient_material( ambient );

		diffuse_material( diffuse );

		specular_material( specular );

		apply_texture( diffuse_texture );
	}
}
