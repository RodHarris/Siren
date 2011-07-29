package siren;

import jsi3.lib.maths.*;

public class BSplineInterpolator
{
	private  BSplineInterpolator()
	{
	}

	public static void bspline_interpolate( Vec3d r, double u, Vec3d c0, Vec3d c1, Vec3d c2, Vec3d c3 )
	{
		catmullrom( r, u, c0, c1, c2, c3 );
	}

	public static void catmullrom( Vec3d r, double u, Vec3d c0, Vec3d c1, Vec3d c2, Vec3d c3 )
	{
		double u2 = u * u;
		double u3 = u2 * u;

		double P0, P1, P2, P3;

		P0 = c0.x;
		P1 = c1.x;
		P2 = c2.x;
		P3 = c3.x;
		r.x = 0.5 * ( (2 * P1) + (-P0 + P2) * u + (2*P0 - 5*P1 + 4*P2 - P3) * u2 + (-P0 + 3*P1- 3*P2 + P3) * u3);

		P0 = c0.y;
		P1 = c1.y;
		P2 = c2.y;
		P3 = c3.y;
		r.y = 0.5 * ( (2 * P1) + (-P0 + P2) * u + (2*P0 - 5*P1 + 4*P2 - P3) * u2 + (-P0 + 3*P1- 3*P2 + P3) * u3);

		P0 = c0.z;
		P1 = c1.z;
		P2 = c2.z;
		P3 = c3.z;
		r.z = 0.5 * ( (2 * P1) + (-P0 + P2) * u + (2*P0 - 5*P1 + 4*P2 - P3) * u2 + (-P0 + 3*P1- 3*P2 + P3) * u3);
	}
}
