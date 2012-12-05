package com.zsy.flykite.actor;

public class Enemy {
	public int type;
	public float x;
	public float y;
	public float width;
	public float height;
	public boolean isEated;
	
	public Enemy(int type,int x, int y, float width, float height) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
}
