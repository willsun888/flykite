package com.zsy.flykite.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.zsy.flykite.Assets;
import com.zsy.flykite.Button;
import com.zsy.flykite.GestureDetector;

public class AboutScreen extends Screen{
	static final int SCREEN_W = 480;
	static final int SCREEN_H = 800;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Vector3 touchPoint;
	
	private GestureDetector gestureDetector;
	private Button backMainBtn;
	
	public AboutScreen(Game game) {
		super(game);
		camera = new OrthographicCamera(SCREEN_W,SCREEN_H);
		camera.position.set(SCREEN_W/2,SCREEN_H/2,0);
		batch = new SpriteBatch();
		touchPoint = new Vector3();
		gestureDetector = new GestureDetector();
		backMainBtn = new Button(new TextureRegion[]{Assets.chooseLevelBack,Assets.chooseLevelBackClick},10, 0);
	}

	@Override
	public void present(float deltaTime) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT); 
		camera.unproject(touchPoint);
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.disableBlending();
		batch.begin();
		batch.draw(Assets.aboucBgRegion, 0, 0);
		batch.end();
		
		batch.enableBlending();
		batch.begin();
		backMainBtn.draw(batch);
		batch.end();
	}
	
	@Override
	public void update(float deltaTime) {
		if(gestureDetector.isTouchOn()){
			camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
			backMainBtn.isPressed = false;
			if(backMainBtn.pointInTouch(touchPoint.x,touchPoint.y)){
				Assets.playSound(Assets.buttonClickSound, game.soundState);
				System.out.println("backMainBtn touch");
				game.setScreen(new MainScreen(game));
			}
		}else if(gestureDetector.isTouchDown()){
			camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
			backMainBtn.isPressed = backMainBtn.pointInTouch(touchPoint.x,touchPoint.y);
		}
	}
	
	@Override
	public void dispose() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	
}
