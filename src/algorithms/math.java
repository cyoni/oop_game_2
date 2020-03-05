package algorithms;



public class math {
 
	/**
	 * This function returns a point somewhere between a line
	 * @return vector
	 **/
	public double[] getPointOnLine(double x1, double y1, double x2, double y2, double percent) {
		
		double v[] = {x2-x1, y2-y1};
		double length = Math.sqrt(v[0]*v[0]+v[1]*v[1]);
		double u[] = {1/length*v[0], 1/length*v[1]}; // normalized vector
		double distance = Math.sqrt(Math.pow((x2-x1), 2) + Math.pow(y2-y1, 2));
		double x = x1 + u[0] * distance*(percent/100); 
		double y = y1 + u[1] * distance*(percent/100);
		
		return new double[]{x,y};
	}

	
	
}
