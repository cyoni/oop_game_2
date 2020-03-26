package algorithms;

import javax.swing.JFrame;

import utils.Point3D;

public  class converter{
	
	double xTop = 32.107640;
	double yTop = 35.212816;
	
	double xBottom =  32.101067;
	double yBottom =  35.184181;
 //
	private Point3D f;
		
	
	public converter(Point3D f2) {
		this.f = f2;
	}

	/**
	* this function converts coordinate to pixel
	* @param 
	* @return coordinate
	**/
	public Point3D coordsToPixel(double x, double y) {
		int x1 = (int) ((-f.y()*x+f.y()*xBottom+f.y()*xTop-f.y()*xBottom)/(xTop-xBottom));
		int y1 = (int) ((f.x()*y - f.x()*yBottom)/(yTop-yBottom));
		return new Point3D(y1, f.y()-x1, 0);
	}

	
	/**
	* this function converts pixel to coordinates
	* @param 
	* @return coordinate
	**/
	public Point3D pixelToCoords(double x, double y) {
		double x1 = (((f.y()*xBottom) + (f.y()-y)*(xTop-xBottom)) / f.y());
		double y1 = (((f.x()*yBottom) + x*(yTop-yBottom)) / f.x());
		return new Point3D(x1, y1, 0);
	}


	
}