package gui;

import java.io.Serializable;
import java.util.List;

import dataStructure.node_data;
import utils.Point3D;

public class Node_metadata implements node_data, Serializable {

	/**
	 * This class contains data of a node.
	 * @author Yoni
	 */
	
	private static final long serialVersionUID = -5098381326948746081L;
	private Point3D location;
	private String info;
	private int key;

	
	public Node_metadata(int key, Point3D location) {
		this.location = location;
		this.key = key;		
	}
	
	@Override
	public int getKey() {
		return key;
	}

	@Override
	public Point3D getLocation() {
		return location;
	}

	@Override
	public void setLocation(Point3D p) {
		this.location = p;
	}

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return info;
	}

	@Override
	public void setInfo(String s) {
		// TODO Auto-generated method stub
		this.info = s;
	}

	@Override
	public int getTag() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setTag(int t) {
		// TODO Auto-generated method stub
		
	}

}
