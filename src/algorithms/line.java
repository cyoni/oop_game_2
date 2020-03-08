package algorithms;

import utils.Point3D;

public class line {
 
	/**
	 * This function returns a point somewhere between a line
	 * @return vector
	 **/
	public static double[] getPointOnLine(Point3D p1, Point3D p2, int percent) {
		double x1 = p1.x(), y1 = p1.y();
		double x2 = p2.x(), y2 = p2.y();
		
		double v[] = {x2-x1, y2-y1};
		double length = Math.sqrt(v[0]*v[0]+v[1]*v[1]);
		double u[] = {1/length*v[0], 1/length*v[1]}; // normalized vector
		double distance = Math.sqrt(Math.pow((x2-x1), 2) + Math.pow(y2-y1, 2));
		double x = x1 + u[0] * distance*(percent/100); 
		double y = y1 + u[1] * distance*(percent/100);
		return new double[]{x,y};
	}
	
	/**
	 * This method checks if a point lies between two points.
	 * @param p1
	 * @param p2
	 * @param point_to_check
	 * @return boolean
	 */
	
	public static boolean isIn(Point3D p1, Point3D p2,  Point3D point_to_check) {
		double degree = (p2.y()-p1.y())/(p1.x()-p2.x());
		return (degree*p1.x()+p2.y()) == point_to_check.y();
	}	
}
