package siren;

public interface GLRenderer
{
	void initGL() throws Exception;
	
	void updateGL( double dt );
	
	void displayGL();
}
