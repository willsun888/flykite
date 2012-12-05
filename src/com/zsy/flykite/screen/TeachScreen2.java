package com.zsy.flykite.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.zsy.flykite.Assets;
import com.zsy.flykite.GestureDetector;

public class TeachScreen2 extends Screen{
	static final int SCREEN_W = 480;
	static final int SCREEN_H = 800;
	
	private GestureDetector gestureDetector;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Vector3 touchPoint;
	
	private TextureRegion regions[];
	                              
	private int currentImage;
	
	public TeachScreen2(Game game){
		super(game);
		this.regions = Assets.teachRegions;
		camera = new OrthographicCamera(SCREEN_W,SCREEN_H);
		camera.position.set(SCREEN_W/2,SCREEN_H/2,0);
		batch = new SpriteBatch();
		touchPoint = new Vector3();
		gestureDetector = new GestureDetector();
	}
	

	@Override
	public void present(float deltaTime) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT); 
		camera.unproject(touchPoint);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		batch.draw(regions[currentImage], 0, 0);
		batch.end();
	}

	@Override
	public void update(float deltaTime) {
		if(gestureDetector.isTouchOn()){
			camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
			Assets.playSound(Assets.buttonClickSound, game.soundState);
			currentImage++;
			if(currentImage >=8){
				game.setScreen(new ClothAndLevelScreen(game));
			}
		}
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
}
