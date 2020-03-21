package algorithms;
import utils.Point3D;

public class line {
 
	/**
	 * This function returns a point somewhere between a line
	 * @return vector
	 **/
	public static Point3D getPointOnLine(Point3D p1, Point3D p2, int percent) {
		double x1 = p1.x(), y1 = p1.y();
		double x2 = p2.x(), y2 = p2.y();
			
		double v[] = {x2-x1, y2-y1};
		double length = Math.sqrt(v[0]*v[0]+v[1]*v[1]);
		double u[] = {1/length*v[0], 1/length*v[1]}; // normalized vector
		double distance = Math.sqrt(Math.pow((x2-x1), 2) + Math.pow(y2-y1, 2));
		double x = x1 + u[0] * distance*((double)percent/100); 
		double y = y1 + u[1] * distance*((double)percent/100);
		return new Point3D(x,y);
	}
	
	/**
	 * This method checks if a point lies between two points.
	 * @param p1
	 * @param p2
	 * @param point_to_check
	 * @return boolean
	 */
	
	public static boolean isIn(Point3D p1, Point3D p2,  Point3D point_to_check) {
		// range check!
		double maxX = 0;
		double minX = 0;
		if (p1.x() > p2.x()) { maxX = p1.x(); minX = p2.x();}
			else {maxX = p2.x(); minX = p1.x();}
		
		double maxY = 0;
		double minY = 0;
		if (p1.y() > p2.y()) { maxY = p1.y(); minY = p2.y();}
			else {maxY = p2.y(); minY = p1.y();}

		double m = (p2.y()-p1.y())/(p2.x()-p1.x());
		int expected = (int) point_to_check.y();
		int result = (int) (m*point_to_check.x()+(-1)*m*p2.x()+ p2.y());	

		//System.out.println(p1.x() +"," + p1.y() +" " + p2.x() +"," + p2.y() +" " + point_to_check.x() +"," + point_to_check.y() +" rslt " + result);
		
		if (!(maxX >= point_to_check.x() && minX <= point_to_check.x() && maxY >= point_to_check.y() && minY <= point_to_check.y())) return false;
		

		
		return expected-result < 9 && expected-result >= -25;	
	}	
	
	/**
	 * This method returns the closest distance and point to a line
	 * @param p1
	 * @param p2
	 * @param p
	 * @return array 
	 */
	public static double[] distanceBetweenLineAnd2Points(Point3D p1, Point3D p2, Point3D p) {
		double x0 = p.x(), y0 = p.y();
		double x1 = p1.x(), y1 = p1.y();
		double x2 = p2.x();
			if (x1 == 0 && x2 == 0) {throw new IllegalArgumentException("Argument 'divisor' is 0");}
		double m = (p2.y()-p1.y())/(p2.x()-p1.x());
		double a = m;
		double b = -1.0;
		double c = -m*x1+y1;	
		double x_p = (b*(b*x0-a*y0)-a*c)/(a*a+b*b);
		double y_p = (a*(-b*x0+a*y0)-b*c)/(a*a+b*b);
		double arr[] = {Math.abs(a*x0+b*y0+c)/Math.sqrt(a*a+b*b), y_p, x_p};
	    return arr;
	}
}
