package siren.tests;

import siren.*;


public class SkyBoxTest extends SirenTest
{
	public SkyBoxTest( String maps_dir ) throws Exception
	{
		engine.add_component( new SkyBox( 50, maps_dir + "/tex1.png", maps_dir + "/tex2.png", maps_dir + "/tex3.png", maps_dir + "/tex4.png", maps_dir + "/tex5.png", maps_dir + "/tex6.png" ) );
		
		camera.xfov = 90;
		
		begin();
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
}
