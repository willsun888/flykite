package com.zsy.flykite;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer10;
import com.zsy.flykite.actor.DynamicObject;
import com.zsy.flykite.temp.TileMapRenderer;

public class WorldRender {
	static final int SCREEN_W = 480;
	static final int SCREEN_H = 800;
	
	public final static int TILE_WIDTH = 32;
	public final static int TILE_HEIGHT = 32;
	
	public final static int MAP_TILE_WIDTH = 22;
	public final static int MAP_TILE_HEIGHT = 250;
	
	public final static int MAP_WIDTH = 704;
	public final static int MAP_HEIGHT = 8000;
	
	public World world;
	SpriteBatch batch;
	TiledMap map; 
    TileMapRenderer tileMapRenderer;
    ImmediateModeRenderer10 renderer;
    ArrayList<DynamicObject> dynamicObjList;
    
	public WorldRender(SpriteBatch batch,World world) {
		this.world = world;
		this.batch = batch;
		map = world.map;
		tileMapRenderer = world.tileMapRenderer;
		renderer = new ImmediateModeRenderer10();
		dynamicObjList = world.dynamicObjList;
	}
	
	public void render(float deltaTime){
		world.mapCam.update();
		batch.setProjectionMatrix(world.mapCam.combined);
		
		batch.begin();
		renderMap();
		batch.end();

		world.cam.update();
		batch.setProjectionMatrix(world.cam.combined);
		
		batch.begin();
		renderDynamicObjects();
		
		renderLine2();
		renderGainStar();
		renderPauseButton();
		renderLineRoller();
		renderKite();
		batch.end();
	}
	
	private void renderMap(){
		tileMapRenderer.render(world.mapCam);
	}
	
	private void renderKite(){
		world.kite.draw(batch);
	}
	
	private void renderLine(){
		renderer.begin(GL10.GL_LINES);
		float tight = 0.5f;
		float posX =  world.kite.x+world.kite.width/2;
		float posY = world.kite.y+world.kite.height/2;
		boolean isLeft = posX > SCREEN_W/2 ? false : true;	//风筝左右位置
		//画线（松紧系数越大，线越松，越接近椭圆线；松紧系数越小，线越紧，越接直线）
        float pX = posX,pY = posY;
        float pX2,pY2;
        
        float x1,x2,y;	//变化的两点
        
        float a,b;	//椭圆短长径
        if(isLeft){
        	a = SCREEN_W/2 - posX;
        }else{
        	a = posX - SCREEN_W/2;
        }
    	b = 0 - posY;
    	
        for (y = posY + 1; y > 0; y--) {
        	if(isLeft){
				x1 = SCREEN_W/2 - (float)Math.sqrt( (1-Math.pow(y-posY,2)/(b*b))* (a*a) );
				x2 = SCREEN_W/2 - (SCREEN_W/2-posX)*(0-y)/(0-posY);
        	}else{
        		x1 = SCREEN_W/2 + (float)Math.sqrt( (1-Math.pow(y-posY,2)/(b*b))* (a*a) );
				x2 = SCREEN_W/2 + (posX - SCREEN_W/2)*(0-y)/(0-posY);
        	}
			pX2 = x1 * tight + (1-tight)*x2;
			pY2 = y;
			
			drawLine(pX, pY, pX2, pY2);
			pX = pX2;
			pY = pY2;
		}
        renderer.end();
	}
	
	private void renderLine2() {
		renderer.begin(GL10.GL_LINES);
		float posX =  world.kite.x+world.kite.width/2;
		float posY = world.kite.y+world.kite.height/2;
		drawLine(posX, posY, SCREEN_W/2, 0);
		renderer.end();
	}
	
	private void renderGainStar(){
		batch.draw(Assets.starRegion,40,790-40);
		Assets.textFont.draw(batch, world.gainStar+"/" +world.totalStar, 77, 790-12);
	}
	
	private void renderLineRoller(){
		world.lineRoller.draw(batch);
	}
	
	private void renderPauseButton(){
		world.pauseButton.draw(batch);
	}
	
	private void renderDynamicObjects(){
		for (DynamicObject dynaObj : dynamicObjList) {
			dynaObj.draw(batch);
		}
	}
	
	private void drawLine (float x1, float y1, float x2, float y2){
		float fr = 0.1f;
		float fg = 0.8f;
		float fb = 0.1f;
		renderer.color(fr, fg, fb, 1);
		renderer.vertex(x1, y1, 0);
		renderer.color(fr, fg, fb, 1);
		renderer.vertex(x2, y2, 0);
	}
	
}