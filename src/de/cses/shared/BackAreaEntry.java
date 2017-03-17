/*
 * Copyright 2016 
 * Saxon Academy of Science in Leipzig, Germany
 * 
 * This is free software: you can redistribute it and/or modify it under the terms of the 
 * GNU General Public License version 3 (GPL v3) as published by the Free Software Foundation.
 * 
 * This software is distributed WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. Please read the GPL v3 for more details.
 * 
 * You should have received a copy of the GPL v3 along with the software. 
 * If not, you can access it from here: <https://www.gnu.org/licenses/gpl-3.0.txt>.
 */
package de.cses.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author alingnau
 *
 */
public class BackAreaEntry implements IsSerializable {
	
	private int backAreaID, leftCorridorOuterWallID, leftCorridorInnerWallID, rightCorridorInnerWallID, rightCorridorOuterWallID, innerWallID, leftWallID, rightWallID, outerWallID;
	private boolean isBackChamber;
	private double height, width, depth;

	/**
	 * 
	 */
	public BackAreaEntry() {
	}

	public BackAreaEntry(int backAreaID, int leftCorridorOuterWallID, int leftCorridorInnerWallID, int rightCorridorInnerWallID,
			int rightCorridorOuterWallID, int innerWallID, int leftWallID, int rightWallID, int outerWallID, boolean isBackChamber,
			double height, double width, double depth) {
		super();
		this.backAreaID = backAreaID;
		this.leftCorridorOuterWallID = leftCorridorOuterWallID;
		this.leftCorridorInnerWallID = leftCorridorInnerWallID;
		this.rightCorridorInnerWallID = rightCorridorInnerWallID;
		this.rightCorridorOuterWallID = rightCorridorOuterWallID;
		this.innerWallID = innerWallID;
		this.leftWallID = leftWallID;
		this.rightWallID = rightWallID;
		this.outerWallID = outerWallID;
		this.isBackChamber = isBackChamber;
		this.height = height;
		this.width = width;
		this.depth = depth;
	}

	public int getBackAreaID() {
		return backAreaID;
	}

	public void setBackAreaID(int backAreaID) {
		this.backAreaID = backAreaID;
	}

	public int getLeftCorridorOuterWallID() {
		return leftCorridorOuterWallID;
	}

	public void setLeftCorridorOuterWallID(int leftCorridorOuterWallID) {
		this.leftCorridorOuterWallID = leftCorridorOuterWallID;
	}

	public int getLeftCorridorInnerWallID() {
		return leftCorridorInnerWallID;
	}

	public void setLeftCorridorInnerWallID(int leftCorridorInnerWallID) {
		this.leftCorridorInnerWallID = leftCorridorInnerWallID;
	}

	public int getRightCorridorInnerWallID() {
		return rightCorridorInnerWallID;
	}

	public void setRightCorridorInnerWallID(int rightCorridorInnerWallID) {
		this.rightCorridorInnerWallID = rightCorridorInnerWallID;
	}

	public int getRightCorridorOuterWallID() {
		return rightCorridorOuterWallID;
	}

	public void setRightCorridorOuterWallID(int rightCorridorOuterWallID) {
		this.rightCorridorOuterWallID = rightCorridorOuterWallID;
	}

	public int getInnerWallID() {
		return innerWallID;
	}

	public void setInnerWallID(int innerWallID) {
		this.innerWallID = innerWallID;
	}

	public int getLeftWallID() {
		return leftWallID;
	}

	public void setLeftWallID(int leftWallID) {
		this.leftWallID = leftWallID;
	}

	public int getRightWallID() {
		return rightWallID;
	}

	public void setRightWallID(int rightWallID) {
		this.rightWallID = rightWallID;
	}

	public int getOuterWallID() {
		return outerWallID;
	}

	public void setOuterWallID(int outerWallID) {
		this.outerWallID = outerWallID;
	}

	public boolean isBackChamber() {
		return isBackChamber;
	}

	public void setBackChamber(boolean isBackChamber) {
		this.isBackChamber = isBackChamber;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getDepth() {
		return depth;
	}

	public void setDepth(double depth) {
		this.depth = depth;
	}

}