package com.zsy.flykite.actor;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.zsy.flykite.Assets;
import com.zsy.flykite.GameContant;
import com.zsy.flykite.World;

public class Kite {
	int SCREEN_H = 800;
	int SCREEN_W = 480;
	
	public int kiteType;
	public float x;
	public float y;
	public int width;
	public int height;
	
	public float life = 100;
	public float kiteheight = 500;		//风筝高度
	public float lineLenght = 1000;	//风筝初始线长
	public float tightness = lineLenght - kiteheight;
	public float velocity;
	public Vector2 direction = new Vector2(GameContant.DIR_STOP,GameContant.DIR_STOP);
	
	private int warnCount = 0;
	private World world;
	
	public Kite(World world,int kiteType,float x,float y){
		this.kiteType = kiteType;
		this.world = world;
		this.x = x;
		this.y = y;
		this.width = Assets.kiteRegions[kiteType].getRegionWidth();
		this.height = Assets.kiteRegions[kiteType].getRegionHeight();
	}
	
	public void draw(SpriteBatch batch){
		tightness = lineLenght - kiteheight;
		if(world.hasEnemy){
			batch.draw(Assets.kiteRegions[kiteType], x, y);
		}else{
			batch.draw(Assets.kiteNoEnemyRegions[kiteType], x, y);
		}
		drawLifeShow(batch);
		drawTightnessShow(batch);
	}
	
	private void drawLifeShow(SpriteBatch batch){
		
		//画theme footer
		if(world.mapTheme.equals("sky")){
			batch.draw(Assets.skyFooterRegion, 0, 0);
		}else if(world.mapTheme.equals("ocean")){
			batch.draw(Assets.oceanFooterRegion, 0, 0);
		}else if(world.mapTheme.equals("space")){
			batch.draw(Assets.spaceFooterRegion, 0, 0);
		}
		//画小风筝图标
		batch.draw(Assets.smallKiteRegions[kiteType], 25, 30);
		
		//画kite life
		
		batch.draw(Assets.gameKiteLifeRegion3, 105, 50);
		if(life > 0){
			batch.draw(Assets.gameKiteLifeRegion1, 105, 50);
			int w = Assets.gameKiteLifeRegion2.getRegionWidth();
			int h = Assets.gameKiteLifeRegion2.getRegionHeight();
			int kiteLife  = (int)(life * (float)w/ 100f);
			TextureRegion region2 = new TextureRegion(Assets.gameKiteLifeRegion2, w-kiteLife, 0, kiteLife, h);
			batch.draw(region2,105+Assets.gameKiteLifeRegion1.getRegionWidth(), 50);
		}
	}
	
	private void drawTightnessShow(SpriteBatch batch){
		float tightnessForShow = 0;
		if(tightness<=0){
			tightnessForShow=0;
		}else if(tightness>0&&tightness<=GameContant.TIGHTNESS_SPAN){
			tightnessForShow=tightness;
		}else if(tightness>GameContant.TIGHTNESS_SPAN){
			tightnessForShow=GameContant.TIGHTNESS_SPAN;
		}
		//绘制松紧度颜色柱(value的值在0到1000,值越小越紧)
		int h1 = Assets.tightnessStripRegion.getRegionHeight();
		float h2 = 800f - 80f - Assets.tightnessBiaoRegion.getRegionHeight()/2 - (tightnessForShow / 1000) * h1;
		
		//画警告
    	if(tightnessForShow < 100 || tightnessForShow > 900){
			if((warnCount++)%2 == 0){
				batch.draw(Assets.tightnessWarnRegion, 6.4f, 800 - 429 - 80 + 8.7f);
			}
    	}else{
    		warnCount = 0;
    	}
		
		batch.draw(Assets.tightnessStripRegion, 0, 800 - 414 - 80);
		batch.draw(Assets.tightnessBiaoRegion, 0, h2);
	}
}
