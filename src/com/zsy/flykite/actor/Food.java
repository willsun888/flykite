package com.zsy.flykite.actor;

import com.badlogic.gdx.math.Rectangle;

public class Food {
	public int type;
	public float x;
	public float y;
	public float width;
	public float height;
	public boolean isEated;
	public Rectangle bounds;
	
	public Food(int type,int x, int y, float width, float height) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		bounds = new Rectangle(x, y, width, height);
	}
}
