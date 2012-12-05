/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.zsy.flykite.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class DynamicObject {
	public static int SCREEN_W = 480;
	public static int SCREEN_H = 800;
	public static int MAP_HEIGHT = 8000;
	public static int MAP_WIDTH = 704;
	
	
	public static final int DIR_RIGHT = 1;
	public static final int DIR_LEFT = -1;
	
	float stateTime;
	public float mx;	//地图中的x，y
	public float my;
	public float x;		//屏幕中的x，y
	public float y;
	public float width;
	public float height;
	public Animation anim;	
	public int velX;	//x，y方向运动速度
	public int velY;
	public int dir;		//飞行方向
	
	public DynamicObject(float mx, float my, float width, float height,
			int velX, int velY, Animation anim) {
		this.mx = mx;
		this.my = my;
		this.width = width;
		this.height = height;
		this.anim = anim;
		this.velX = velX;
		this.velY = velY;
		
		//物体在左侧
		if(mx < MAP_WIDTH/2){
			this.velX = 2;
			this.mx = mx - width;
			this.dir = DIR_RIGHT;
		}
		//物体在右侧
		else{
			this.velX = -2;
			this.mx = mx + 32;
			this.dir = DIR_LEFT;
		}
		this.velY = 0;
	}

	public void draw(SpriteBatch batch){
		if(x < SCREEN_W && y < SCREEN_H){
			stateTime += Gdx.graphics.getDeltaTime();
			TextureRegion keyFrame = anim.getKeyFrame(stateTime, true);
			batch.draw(keyFrame, this.x, this.y);
		}
	}
	
	abstract public void update();
}
