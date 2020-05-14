package com.ui;

public class Ball {

	public static final double STEP_MOVE = 0.1;
	private double r;
	private double maxRadious;
	private double minRadious;
	public boolean isExpanding;
	
	public Ball(double rr, double maxRadious, double minRadious) {
		r = rr;
		isExpanding = true;
		this.maxRadious = maxRadious;
		this.minRadious = minRadious;
	}

	public double getRadious() {
		return r;
	}

	public void setMaxRadious(double mr) {
		maxRadious = mr;
	}

	public void move() {
		if (isExpanding) 
			r += STEP_MOVE;
		else
			r -= STEP_MOVE;
		verifyRadious();
	}
	
	private void verifyRadious() {
		if (r >= maxRadious)
			isExpanding = false;
		else if (r <= minRadious)
			isExpanding = true;
	}
}
