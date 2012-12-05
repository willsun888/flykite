package com.zsy.flykite.screen;

import android.content.DialogInterface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.zsy.flykite.Assets;
import com.zsy.flykite.Button;
import com.zsy.flykite.FlyKiteGame;
import com.zsy.flykite.GestureDetector;
import com.zsy.flykite.Preference;
import com.zsy.flykite.android.ActionResolver;
import com.zsy.flykite.android.DialogNewGame;
import com.zsy.flykite.android.ActionResolver.IAlertDialogCallback;

public class MainScreen extends Screen{
	
	static final int SCREEN_W = 480;
	static final int SCREEN_H = 800;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Vector3 touchPoint;
	
	private Button newGameBtn;
	private Button teachBtn;
	private Button aboutBtn;
	private Button exitBtn;
	private Button soundBtn;
	private Button continueBtn;
	
	private GestureDetector gestureDetector = new GestureDetector();
	ActionResolver actionResolver;
	public MainScreen(Game game) {
		super(game);
		actionResolver = ((FlyKiteGame)game).actionResolver;
		camera = new OrthographicCamera(SCREEN_W,SCREEN_H);
		camera.position.set(SCREEN_W/2,SCREEN_H/2,0);
		batch = new SpriteBatch();
		touchPoint = new Vector3();
		continueBtn = new Button(new TextureRegion[]{Assets.mainContinueRegion,Assets.mainContinueClickRegion},144,800-330);
		newGameBtn = new Button(new TextureRegion[]{Assets.mainNewGameRegion,Assets.mainNewGameClickRegion},66,800-422);
		teachBtn = new Button(new TextureRegion[]{Assets.mainTeachRegion,Assets.mainTeachClickRegion},96,800-576);
		aboutBtn = new Button(new TextureRegion[]{Assets.mainAboutRegion,Assets.mainAboutClickRegion},244,800-490);
		exitBtn = new Button(new TextureRegion[]{Assets.mainExitRegion,Assets.mainExitClickRegion},166,800-680);
		
		soundBtn = new Button(new TextureRegion[]{Assets.soundOffRegion,Assets.soundOnRegion},15,15);
		soundBtn.isPressed = game.soundState;
//		Assets.playMusic(Assets.bgMusic, true);
	}

	@Override
	public void present(float deltaTime) {
		
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT); 
		camera.unproject(touchPoint);
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.disableBlending();
		batch.begin();
		batch.draw(Assets.mainBgRegion, 0, 0);
		batch.end();

		batch.enableBlending();
		batch.begin();
		newGameBtn.draw(batch);
		teachBtn.draw(batch); 
		aboutBtn.draw(batch); 
		exitBtn.draw(batch); 
		continueBtn.draw(batch);
		soundBtn.draw(batch);
		
		if(Preference.getFirstCreateState()){
			batch.draw(Assets.mainContinueGrayRegion,144,800-330);
		}
		batch.end();
	}
	
	@Override
	public void update(float deltaTime) {
		if(gestureDetector.isTouchOn()){
			System.out.println("gestureDetector.isTouchOn()");
			camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
			newGameBtn.isPressed = false;
			teachBtn.isPressed = false;
			aboutBtn.isPressed = false;
			exitBtn.isPressed = false;
			if(soundBtn.pointInTouch(touchPoint.x,touchPoint.y)){
				Assets.playSound(Assets.buttonClickSound, game.soundState);
				soundBtn.isPressed = !soundBtn.isPressed;
				game.soundState = soundBtn.isPressed; 
				if(game.soundState){
					System.out.println("touch soundBtn on");
				}else{
					System.out.println("touch soundBtn off");
				}
			}else if(newGameBtn.pointInTouch(touchPoint.x,touchPoint.y)){
				Assets.playSound(Assets.buttonClickSound, game.soundState);
				System.out.println("touch startBtn");
//				Preference.reset();
//				game.setScreen(new ClothAndLevelScreen(game));
				actionResolver.showAlertDialog(new DialogNewGame(game));
			}else if(teachBtn.pointInTouch(touchPoint.x,touchPoint.y)){
				Assets.playSound(Assets.buttonClickSound, game.soundState);
				System.out.println("touch teachBtn");
				game.setScreen(new TeachScreen(game));
			}else if(aboutBtn.pointInTouch(touchPoint.x,touchPoint.y)){
				Assets.playSound(Assets.buttonClickSound, game.soundState);
				System.out.println("touch aboutBtn");
				game.setScreen(new AboutScreen(game));
			}else if(continueBtn.pointInTouch(touchPoint.x, touchPoint.y) && !Preference.getFirstCreateState()){
				Assets.playSound(Assets.buttonClickSound, game.soundState);
				game.setScreen(new ClothAndLevelScreen(game));
			}else if(exitBtn.pointInTouch(touchPoint.x,touchPoint.y)){
				Assets.playSound(Assets.buttonClickSound, game.soundState);
				System.out.println("touch exitBtn");
				actionResolver.showAlertDialog(new IAlertDialogCallback() {
					
					@Override
					public void positiveClick(DialogInterface dialog, int which) {
						System.out.println("退出系统");
						System.exit(0);
					}
					
					@Override
					public void negativeClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
					
					@Override
					public String getTitle() {
						return "退出";
					}
					
					@Override
					public String getPositiveText() {
						return "确定";
					}
					
					@Override
					public String getNegativeText() {
						return "取消";
					}
					
					@Override
					public String getMessage() {
						return "确定退出么？";
					}
				});
			}
		}else if(gestureDetector.isTouchDown()){
			System.out.println("gestureDetector.isTouchDown()");
			camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
			newGameBtn.isPressed = newGameBtn.pointInTouch(touchPoint.x,touchPoint.y);
			teachBtn.isPressed = teachBtn.pointInTouch(touchPoint.x,touchPoint.y);
			aboutBtn.isPressed = aboutBtn.pointInTouch(touchPoint.x,touchPoint.y);
			continueBtn.isPressed = continueBtn.pointInTouch(touchPoint.x, touchPoint.y);
			exitBtn.isPressed = exitBtn.pointInTouch(touchPoint.x,touchPoint.y);
		}
	}
	
	@Override
	public void dispose() {
		Assets.bgMusic.stop();
	}

	@Override
	public void pause() {
		Assets.bgMusic.pause();
	}

	@Override
	public void resume() {
	}

}
