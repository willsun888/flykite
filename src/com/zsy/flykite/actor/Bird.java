package com.zsy.flykite.actor;

import com.badlogic.gdx.graphics.g2d.Animation;

public class Bird extends DynamicObject{
	private final float initMx;
	public Bird(float mx, float my, float width, float height, int velX,
			int velY, Animation anim) {
		super(mx, my, width, height, velX, velY, anim);
		initMx = this.mx;
	}

	@Override
	public void update() {
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
