package com.zsy.flykite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Button {
	//BUTTON的坐标以左上角为起点
	TextureRegion[] regions;
	public boolean isPressed;
	public Rectangle rectRegion;
	float x;
	float y;
	float width;
	float height;
	boolean visible;
	public Button(TextureRegion[] regions,int x,int y){
		this.regions = regions;
		this.x = x;
		this.y = y;
		this.width = regions[0].getRegionWidth();
		this.height = regions[0].getRegionHeight();
		rectRegion = new Rectangle(x,y,width,height);
	}
	
	public void draw(SpriteBatch batch,float x,float y,int width,int height,boolean isPressed){
		int num = 0;
		if(isPressed)	num = 1;
		batch.draw(regions[num],x,y,width,height);
	}
	
	public void draw(SpriteBatch batch,float x,float y,boolean isPressed){
		int num = 0;
		if(isPressed)	num = 1;
		batch.draw(regions[num],x,y);
	}
	
	public void draw(SpriteBatch batch){
		int num = 0;
		if(isPressed)	num = 1;
		batch.draw(regions[num],x,y);
	}
	
	public void draw(SpriteBatch batch,float flingX){
		int num = 0;
		if(isPressed)	num = 1;
		batch.draw(regions[num],x+flingX,y);
	}
	
	public boolean pointInTouch(float xx,float yy){
		return rectRegion.x <= xx && rectRegion.x + rectRegion.width >= xx 
		&& rectRegion.y <= yy && rectRegion.y + rectRegion.height >= yy;
	}
}
