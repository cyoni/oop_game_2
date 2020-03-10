package algorithms;

import javax.swing.JFrame;

import utils.Point3D;

public  class converter{
	
	double xTop = 32.107640;
	double yTop = 35.212816;
	
	double xBottom =  32.101067;
	double yBottom =  35.184181;
 //
	private JFrame f;
		
	
	public converter(JFrame f) {
		this.f = f;
	}

	/**
	* this function converts coordinate to pixel
	* @param 
	* @return coordinate
	**/
	public Point3D coordsToPixel(double x, double y) {
		int x1 = (int) ((-f.getHeight()*x+f.getHeight()*xBottom+f.getHeight()*xTop-f.getHeight()*xBottom)/(xTop-xBottom));
		int y1 = (int) ((f.getWidth()*y - f.getWidth()*yBottom)/(yTop-yBottom));
		return new Point3D(y1, f.getHeight()-x1, 0);
	}

	
	/**
	* this function converts pixel to coordinates
	* @param 
	* @return coordinate
	**/
	public Point3D pixelToCoords(double x, double y) {
		double x1 = (((f.getHeight()*xBottom) + (f.getHeight()-y)*(xTop-xBottom)) / f.getHeight());
		double y1 = (((f.getWidth()*yBottom) + x*(yTop-yBottom)) / f.getWidth());
		return new Point3D(x1, y1, 0);
	}


	
}