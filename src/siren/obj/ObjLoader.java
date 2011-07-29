package siren.obj;

import siren.*;

import sigl.*;

import jsi3.lib.maths.*;

import java.util.*;
import java.util.zip.*;
import java.io.*;
import java.awt.image.*;

import static sigl.Statics.*;
import static sigl.GLWrap.*;

import static jsi3.lib.maths.Statics.*;
import static jsi3.lib.filesystem.Statics.*;
import static jsi3.lib.system.Statics.*;
import static jsi3.lib.console.Statics.*;
import static jsi3.lib.gui.Statics.*;
import static jsi3.lib.text.Statics.*;


public class ObjLoader
{
	public static ArrayList<Exception> problems = new ArrayList<Exception>();

	
	public static ObjModelRenderer parse( File f ) throws FileNotFoundException, IOException
	{
		ObjModelRenderer model = new ObjModelRenderer();

		Group group = null;
		
		String data = load_text_file( f );

		int line_no = 0;
		
		for( String line : data.split( "\n" ) )
		{
			line_no ++;
			
			String[] tokens = line.split( "\\s+" );

			if( tokens.length < 1 ) continue;

			String key = tokens[ 0 ];
			
			if( key.startsWith( "#" ) )
			{
				continue;
			}
			else if( "g".equals( key ) )
			{
				group = new Group();

				group.name = tokens[ 1 ];

				model.groups.add( group );
			}
			else if( "usemtl".equals( key ) )
			{
				if( tokens.length != 2 )
				{
					problems.add( new DataFormatException( fmt( "%s:%d couldn't parse '%s' as a usemtl: tokens length != 2", f.getPath(), line_no, line ) ) );

					continue;
				}

				group.material_name = tokens[ 1 ];
			}
			else if( "v".equals( key ) )
			{
				if( tokens.length != 4 )
				{
					problems.add( new DataFormatException( fmt( "%s:%d couldn't parse '%s' as a Vec3d: tokens length != 4", f.getPath(), line_no, line ) ) );

					continue;
				}

				double x = _double( tokens[ 1 ] );

				double y = _double( tokens[ 2 ] );

				double z = _double( tokens[ 3 ] );
				
				model.verticies.add( new Vec3d( x, y, z ) );
			}
			else if( "vt".equals( key ) )
			{
				if( tokens.length != 3 )
				{
					problems.add( new DataFormatException( fmt( "%s:%d couldn't parse '%s' as a Vec2d: tokens length != 3", f.getPath(), line_no, line ) ) );

					continue;
				}

				double u = _double( tokens[ 1 ] );

				double v = _double( tokens[ 2 ] );
				
				model.tex_coords.add( new Vec2d( u, v ) );
			}
			else if( "vn".equals( key ) )
			{
				if( tokens.length != 4 )
				{
					problems.add( new DataFormatException( fmt( "%s:%d couldn't parse '%s' as a Vec3d: tokens length != 4", f.getPath(), line_no, line ) ) );

					continue;
				}

				double x = _double( tokens[ 1 ] );

				double y = _double( tokens[ 2 ] );

				double z = _double( tokens[ 3 ] );

				model.normals.add( new Vec3d( x, y, z ) );
			}
			else if( "f".equals( key ) )
			{
				Face face = new Face();

				if( tokens.length != 4 )
				{
					problems.add( new DataFormatException( fmt( "%s:%d couldn't parse '%s' as a Face: tokens length != 4", f.getPath(), line_no, line ) ) );

					continue;
				}
				
				for( int i=1; i<tokens.length; i++ )
				{
					String[] tokens2 = tokens[ i ].split( "/" );

					//cout.println( "face token length = %d", tokens2.length );

					VTNIndex vtn_index = new VTNIndex();

					vtn_index.vertex = _int( tokens2[ 0 ] ) -1;

					if( tokens2.length > 1 )
					{
						if( tokens2[ 1 ].length() != 0 )
						{
							vtn_index.tex_coord = _int( tokens2[ 1 ] ) -1;
						}
						
						if( tokens2[ 2 ].length() != 0 )
						{
							vtn_index.normal = _int( tokens2[ 2 ] ) -1;
						}
					}

					face.indicies.add( vtn_index );
				}

				group.faces.add( face );

				model.total_faces ++;
			}
			else if( "mtllib".equals( key ) )
			{
				if( tokens.length != 2 )
				{
					problems.add( new DataFormatException( fmt( "%s:%d couldn't parse '%s' as a mtllib: tokens length != 2", f.getPath(), line_no, line ) ) );

					continue;
				}
				
				model.materials = parse_mtl_file( new File( f.getParent() + "/" + tokens[ 1 ] ) );
			}
			else
			{
				problems.add( new DataFormatException( fmt( "%s:%d couldn't parse '%s' unknown line definition", f.getPath(), line_no, line ) ) );
				
				continue;
			}
		}

		cverbose.println( "Obj Model Parser:" );

		cverbose.println( "   Groups: %d", model.groups.size() );

		for( Group _group : model.groups )
		{
			cverbose.println( "   Group: %s", _group.name );

			cverbose.println( "      Faces: %d", _group.faces.size() );

			cverbose.println( "      Material: %s", _group.material_name );

			Material mat = model.materials.get( _group.material_name );
			
			cverbose.println( "         Diffuse: %d-%d-%d-%d", argbA( mat.diffuse ), argbR( mat.diffuse ), argbG( mat.diffuse ), argbB( mat.diffuse ) );

			cverbose.println( "         Specular: %d-%d-%d-%d", argbA( mat.specular ), argbR( mat.specular ), argbG( mat.specular ), argbB( mat.specular ) );

			cverbose.println( "         Ambient: %d-%d-%d-%d", argbA( mat.ambient ), argbR( mat.ambient ), argbG( mat.ambient ), argbB( mat.ambient ) );
		}
		
		return model;
	}

	private static Map<String,Material> parse_mtl_file( File file ) throws FileNotFoundException, IOException
	{
		Map<String,Material> map = new HashMap<String,Material>();

		String data = load_text_file( file );

		Material mtl = null;

		int line_no = 0;

		for( String line : data.split( "\n" ) )
		{
			line_no ++;
			
			String[] tokens = line.split( "\\s+" );

			if( tokens.length < 1 ) continue;

			String key = tokens[ 0 ];
			
			if( key.startsWith( "#" ) )
			{
				continue;
			}
			else if( "newmtl".equals( key ) )
			{
				if( tokens.length != 2 )
				{
					problems.add( new DataFormatException( fmt( "%s:%d couldn't parse '%s' as a newmtl: tokens length != 2", file.getPath(), line_no, line ) ) );

					continue;
				}
				
				mtl = new Material();

				map.put( tokens[ 1 ], mtl );
				
				continue;
			}
			else if( "Kd".equals( key ) )
			{
				if( tokens.length != 4 )
				{
					problems.add( new DataFormatException( fmt( "%s:%d couldn't parse '%s' as a Colour3d: tokens length != 4", file.getPath(), line_no, line ) ) );

					continue;
				}

				double r = _double( tokens[ 1 ] );

				double g = _double( tokens[ 2 ] );

				double b = _double( tokens[ 3 ] );
				
				mtl.diffuse = rgb( r, g, b );
			}
			else if( "Ks".equals( key ) )
			{
				if( tokens.length != 4 )
				{
					problems.add( new DataFormatException( fmt( "%s:%d couldn't parse '%s' as a Colour3d: tokens length != 4", file.getPath(), line_no, line ) ) );

					continue;
				}


				double r = _double( tokens[ 1 ] );

				double g = _double( tokens[ 2 ] );

				double b = _double( tokens[ 3 ] );
				
				mtl.specular = rgb( r, g, b );
			}
			else if( "Ka".equals( key ) )
			{
				if( tokens.length != 4 )
				{
					problems.add( new DataFormatException( fmt( "%s:%d couldn't parse '%s' as a Colour3d: tokens length != 4", file.getPath(), line_no, line ) ) );

					continue;
				}

				double r = _double( tokens[ 1 ] );

				double g = _double( tokens[ 2 ] );

				double b = _double( tokens[ 3 ] );
				
				mtl.ambient = rgb( r, g, b );
			}
			else if( "d".equals( key ) || "Tr".equals( key ) )
			{
				if( tokens.length != 2 )
				{
					problems.add( new DataFormatException( fmt( "%s:%d couldn't parse '%s' as a transparency: tokens length != 2", file.getPath(), line_no, line ) ) );

					continue;
				}

				mtl.alpha = _double( tokens[ 1 ] );
			}
			else if( "map_Kd".equals( key ) )
			{
				if( tokens.length != 2 )
				{
					problems.add( new DataFormatException( fmt( "%s:%d couldn't parse '%s' as a transparency: tokens length != 2", file.getPath(), line_no, line ) ) );

					continue;
				}


				mtl.diffuse_img = load_image( file.getParent() + "/" + tokens[ 1 ] );
			}
			else
			{
				problems.add( new DataFormatException( fmt( "%s:%d couldn't parse '%s' unknown line definition", file.getPath(), line_no, line ) ) );
				
				continue;
			}
		}

		for( Material mat : map.values() )
		{
			mat.init();
		}

		map.put( "default", Material.default_material );
		
		return map;
	}
}
