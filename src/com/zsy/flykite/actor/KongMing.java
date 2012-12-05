package com.zsy.flykite.actor;

import com.badlogic.gdx.graphics.g2d.Animation;

public class KongMing extends DynamicObject{
	private final float initMx;
	private final float initMy;
	public KongMing(float mx, float my, float width, float height, int velX,
			int velY, Animation anim) {
		super(mx, my, width, height, velX, velY, anim);
		initMx =  this.mx;
		initMy =  this.my;
	}

	@Override
	public void update() {
		if(my-initMy>=100 || my-initMy <=-100){
			velY = -velY;
		}
		if(dir == DIR_RIGHT){
			if(mx > MAP_WIDTH ){
				mx = initMx;
			}
		}else{
			if(mx < -width){
				mx = initMx;
			}
		}
	}
	
}
