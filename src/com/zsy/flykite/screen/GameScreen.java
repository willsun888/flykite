package com.zsy.flykite.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.zsy.flykite.Assets;
import com.zsy.flykite.Button;
import com.zsy.flykite.FlyKiteGame;
import com.zsy.flykite.GameUtils;
import com.zsy.flykite.GestureDetector;
import com.zsy.flykite.Preference;
import com.zsy.flykite.World;
import com.zsy.flykite.WorldRender;
import com.zsy.flykite.android.ActionResolver;
import com.zsy.flykite.android.DialogWin;


public class GameScreen extends Screen{
	static final int SCREEN_W = 480;
	static final int SCREEN_H = 800;
	
	World world;
	WorldRender worldRender;
	
	OrthographicCamera camera;
	Vector3 touchPoint;
	SpriteBatch batcher;
	
	Rectangle pauseBounds;
	Rectangle treasureBounds;
	Rectangle finalBounds;
	
	Button pauseBackButton;
	Button pauseRestartButton;
	Button pauseStartButton;
	Button pauseSoundButton;
	Button pauseQuietButton;
	
	GestureDetector gestureDetector;
	private Music bgMusic;
	
	public boolean isPause;
	public boolean isDialog;
	public boolean isTreasure;
	public boolean isTreasureSound;
	public boolean isFinal;
	private int treasureType;
	private boolean wasBackPressed;
	public ActionResolver actionResolver;
	
	public GameScreen(Game game,String mapTheme,int level,int kiteType) {
		super(game);
		actionResolver = ((FlyKiteGame)game).actionResolver;
		batcher = new SpriteBatch();
		touchPoint = new Vector3();
		camera = new OrthographicCamera(480, 800);
		gestureDetector = new GestureDetector();
		if(mapTheme.equals("sky")){
			treasureType = 0;
		}else if(mapTheme.equals("ocean")){
			treasureType = 1;
		}else if(mapTheme.equals("space")){
			treasureType = 2;
		}
		world = new World(game,this, mapTheme, level, kiteType);
		worldRender = new WorldRender(batcher, world);
		pauseBounds = new Rectangle(410, 700, 70, 100);
		treasureBounds = new Rectangle(70,280,362,300);
		pauseBackButton = new Button(new TextureRegion[]{Assets.gamePauseBackRegion, Assets.gamePauseBackClickRegion},325, 341);
		pauseRestartButton = new Button( new TextureRegion[]{Assets.gamePauseRestartRegion, Assets.gamePauseRestartClickRegion},  51, 341);
		
		finalBounds = new Rectangle(0,0,480,800);
		
		pauseStartButton = new Button(new TextureRegion[]{Assets.gamePauseStartRegion, Assets.gamePauseStartClickRegion},  189, 451);
		pauseSoundButton = new Button( new TextureRegion[]{Assets.gamePauseSoundRegion, Assets.gamePauseSoundClickRegion},  189, 231);
		pauseQuietButton = new Button( new TextureRegion[]{Assets.gamePauseQuietRegion, Assets.gamePauseQuietClickRegion},  189, 231);
		 
		Gdx.input.setCatchBackKey(true);
		if(mapTheme.equals("sky")){
			bgMusic = Assets.skyBgMusic;
		}else if(mapTheme.equals("ocean")){
			bgMusic = Assets.oceanBgMusic;
		}else {
			bgMusic = Assets.spaceBgMusic;
		}
		Assets.playMusic(bgMusic,game.soundState);
		isTreasureSound = true;
	}

	@Override
	public void present(float deltaTime) {
		GLCommon gl = Gdx.gl;
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		camera.update();

		worldRender.render(deltaTime);
		if(isTreasure){
			batcher.begin();
			renderTreasure(treasureType);
			if(isTreasureSound){
				Assets.playSound(Assets.treasureSound, game.soundState);
				isTreasureSound = false;
			}
			batcher.end();
		}else if(isFinal){
			batcher.begin();
			renderFinal();
			batcher.end();
		}else{
			if(isPause && !isDialog){
				batcher.begin();
				renderGamePause();
				batcher.end();
			}
		}
		if((isPause || !game.soundState) && bgMusic.isPlaying()){
			bgMusic.pause();
		}else if(!isPause && !bgMusic.isPlaying() && game.soundState){
			Assets.playMusic(bgMusic,game.soundState);
		}
	}

	@Override
	public void update(float deltaTime) {
		checkKeyBackPress();
		updateGamePause();
		
		if(!isPause){
			world.update(deltaTime);
		}else{
			world.warnTightMusic.pause();
		}
	}
	
	public void renderGamePause(){
		batcher.draw(Assets.gamePauseBgRegion, 0, 0);
		pauseBackButton.draw(batcher);
		pauseStartButton.draw(batcher);
		pauseRestartButton.draw(batcher);
		if(game.soundState){
			pauseSoundButton.draw(batcher);
		}else{
			pauseQuietButton.draw(batcher);
		}
	}
	
	public void renderTreasure(int tType){
		batcher.draw(Assets.gamePauseBgRegion, 0, 0);
		batcher.draw(Assets.treasureRegion[tType],70,280);
	}
	
	public void renderFinal(){
		batcher.draw(Assets.tongguanRegion,0,0);
	}
	
	public void updateGamePause(){
		unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
		if (Gdx.input.justTouched()) {
			if (GameUtils.pointInRectangle(pauseBounds, touchPoint.x, touchPoint.y) && !isPause) {
				Assets.playSound(Assets.buttonClickSound,true);
				isPause = true;
				return;
			}
		}
		
		if(gestureDetector.isTouchOn()){
			System.out.println("touch xy: " + touchPoint.x + " "+touchPoint.y);
			pauseBackButton.isPressed = false;
			pauseStartButton.isPressed = false;
			pauseRestartButton.isPressed = false;
			pauseQuietButton.isPressed = false;
			pauseSoundButton.isPressed = false;
			if(isPause && GameUtils.pointInRectangle(pauseBackButton.rectRegion,touchPoint.x,touchPoint.y)){
				System.out.println("pauseBackButton touch");
				Assets.playSound(Assets.buttonClickSound, game.soundState);
				game.setScreen(new ClothAndLevelScreen(game));
			}else if(isPause && GameUtils.pointInRectangle(pauseStartButton.rectRegion,touchPoint.x,touchPoint.y)){
				System.out.println("pauseStartButton touch");
				Assets.playSound(Assets.buttonClickSound, game.soundState);
				isPause = false;
			}else if(isPause && GameUtils.pointInRectangle(pauseRestartButton.rectRegion,touchPoint.x,touchPoint.y)){
				System.out.println("pauseRestartButton touch");
				Assets.playSound(Assets.buttonClickSound, game.soundState);
				game.setScreen(new GameScreen(game, game.mapTheme, game.level, game.kiteType));
			}else if(isTreasure && GameUtils.pointInRectangle(treasureBounds, touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.buttonClickSound,true);
				isTreasure = false;
				isDialog = true;
				// ×îÖÕ¹Ø
//				if(game.mapTheme.equals("space") && game.level == 3){
//					isFinal = true;
//					return;
//				}
				int theme = GameUtils.getMapTheme(game.mapTheme)+1;
				if(theme >=4){
					isFinal = true;
					return;
				}
				ClothAndLevelScreen screen = new ClothAndLevelScreen(game);
				screen.currentTheme = theme;
				game.setScreen(screen);
				return ;
			}else if(isFinal && GameUtils.pointInRectangle(finalBounds,touchPoint.x,touchPoint.y)){
				Assets.playSound(Assets.buttonClickSound,true);
				isFinal = false;
				game.setScreen(new ClothAndLevelScreen(game));
			}else if(isPause && GameUtils.pointInRectangle(pauseSoundButton.rectRegion,touchPoint.x,touchPoint.y)){
				System.out.println("pauseSoundButton touch");
				Assets.playSound(Assets.buttonClickSound, game.soundState);
				if(!game.soundState){
					game.soundState = true;
					Assets.playMusic(bgMusic, game.soundState);
				}else {
					game.soundState = false;
					Assets.playMusic(bgMusic, game.soundState);
				}
			}
		}else if(gestureDetector.isTouchDown()){
			System.out.println("touch xy: " + touchPoint.x + " "+touchPoint.y);
			pauseBackButton.isPressed = GameUtils.pointInRectangle(pauseBackButton.rectRegion,touchPoint.x,touchPoint.y);
			pauseStartButton.isPressed = GameUtils.pointInRectangle(pauseStartButton.rectRegion,touchPoint.x,touchPoint.y);
			pauseRestartButton.isPressed =GameUtils.pointInRectangle(pauseRestartButton.rectRegion,touchPoint.x,touchPoint.y);
			if(game.soundState)
				pauseSoundButton.isPressed =GameUtils.pointInRectangle(pauseSoundButton.rectRegion,touchPoint.x,touchPoint.y);
			else
				pauseQuietButton.isPressed =GameUtils.pointInRectangle(pauseQuietButton.rectRegion,touchPoint.x,touchPoint.y);
		}
	}
	
	private void checkKeyBackPress(){
    	boolean isBackPressed = Gdx.input.isKeyPressed(Input.Keys.BACK);
    	if((isDialog || isTreasure) && isBackPressed){
			game.setScreen(new ClothAndLevelScreen(game));
		}
    	if (!wasBackPressed && isBackPressed) {
    		isPause = !isPause;
    	}
    	wasBackPressed = isBackPressed;
    }
	
	private void unproject(Vector3 vector){
		float x = vector.x;
		float y = vector.y;
		vector.set(x, 800-y-1, 0);
	}
	
	@Override
	public void dispose() {
		bgMusic.stop();
		Assets.treasureSound.stop();
	}

	@Override
	public void pause() {
		bgMusic.pause();
	}

	@Override
	public void resume() {
	}
}