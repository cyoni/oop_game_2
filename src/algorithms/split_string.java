package algorithms;

import javax.swing.JFrame;

import utils.Point3D;

public class split_string {

	
	public static Point3D get_pos_frm_str_and_convert(JFrame f, String pos) {
		
		String point[] = pos.split(",");
		double x = Double.parseDouble(point[0]);
		double y = Double.parseDouble(point[1]);
		double z = Double.parseDouble(point[2]); // not necessary yet
		
		return new converter(f).coordsToPixel(y, x);
	}
}
