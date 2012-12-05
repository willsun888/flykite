package com.zsy.flykite.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.zsy.flykite.Assets;
import com.zsy.flykite.Button;
import com.zsy.flykite.GameContant;
import com.zsy.flykite.GameUtils;
import com.zsy.flykite.GestureDetector;
import com.zsy.flykite.Preference;

public class ClothAndLevelScreen extends Screen {

	public static final int CLOTH_SCENE = 0;
	public static final int LEVEL_SCENE = 1;
	
	public static final int THEME_SKY = 0;
	public static final int THEME_OCEAN = 1;
	public static final int THEME_SPACE = 2;
	
	public static final int LEVEL_1 = 0;
	public static final int LEVEL_2 = 1;
	public static final int LEVEL_3 = 2;
	
	static final int SCREEN_W = 480;
	static final int SCREEN_H = 800;
	static final int DELTA_X = 40;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Vector3 touchPoint;
	
	private int currentScene = LEVEL_SCENE;
	public int currentTheme = 0;
	private int currentKiteType = 0;
	
	public int mapTheme;
	public int kiteType = 0;
	public int level;
	
	Rectangle leftFrameBounds;
	Rectangle rightFrameBounds;
	
	private boolean[] kiteLocked = new boolean[5];
	
	private GestureDetector gestureDetector;
	
	private Button clothAndLevelSwitchBtn;
	private Button backMainBtn;
	private Button level1Btn;
	private Button level2Btn;
	private Button level3Btn;
	private TextureRegion themeBgRegions[];
	private Button kiteClothBtns[];
	
	private float flingX = 0;
	private Thread flingThread;
	private volatile boolean isOnFling = false;
	private volatile boolean isLeftFrame = false;
	private volatile boolean isRightFrame = false;
	
	volatile boolean flingRuning = true;
	
	private boolean[][] levelLock = new boolean[3][3];
	
	public ClothAndLevelScreen(Game game) {
		super(game);
		camera = new OrthographicCamera(SCREEN_W,SCREEN_H);
		camera.position.set(SCREEN_W/2,SCREEN_H/2,0);
		batch = new SpriteBatch();
		touchPoint = new Vector3();
		gestureDetector = new GestureDetector();
		clothAndLevelSwitchBtn = new Button(new TextureRegion[]{Assets.ChooseLevel,Assets.ChooseCloth}, 65, 600);
		level1Btn = new Button(new TextureRegion[]{Assets.level1,Assets.level1Click},120,380);
		level2Btn = new Button(new TextureRegion[]{Assets.level2,Assets.level2Click},210,380);
		level3Btn = new Button(new TextureRegion[]{Assets.level3,Assets.level3Click},300,380);
		backMainBtn = new Button(new TextureRegion[]{Assets.chooseLevelBack,Assets.chooseLevelBackClick},10, 0);
		themeBgRegions = Assets.themeRegions;
		kiteClothBtns = new Button[5];
		
		for(int i=0;i<3;i++){
			levelLock[THEME_SKY][i] = Preference.getLevelLock(GameContant.THEME_SKY, i+1);
			levelLock[THEME_OCEAN][i] = Preference.getLevelLock(GameContant.THEME_OCEAN, i+1);
			levelLock[THEME_SPACE][i] = Preference.getLevelLock(GameContant.THEME_SPACE, i+1);
		}
		
		for(int i = 0 ;i<5;i++){
			kiteClothBtns[i] = new Button(new TextureRegion[]{Assets.clothRegions[i],Assets.clothchoosedRegions[i]},20, 280);
			//缩小点击风筝的区域
			kiteClothBtns[i].rectRegion.x += 80;
			kiteClothBtns[i].rectRegion.width -= 160;
			kiteLocked[i] = Preference.getKiteLock(i);
		}
		
		leftFrameBounds = new Rectangle(0,385, 100, 80);
		rightFrameBounds = new Rectangle(480-100, 385, 100, 80);
		//默认风筝选中
		if(Preference.getFirstCreateState()){
			game.kiteType=Preference.getKiteType();
			game.mapTheme = Preference.getMapTheme();
		}
		this.kiteType = game.kiteType;
		this.currentKiteType = game.kiteType;
		kiteClothBtns[game.kiteType].isPressed = true;
		flingThread = new Thread(flingRunnable);
		flingThread.start();
	}
	
	Runnable flingRunnable = new Runnable() {
		@Override
		public void run() {
			while (flingRuning){
				if(!isOnFling){
					continue;
				}
				if(currentScene == LEVEL_SCENE){
					if((Gdx.input.getDeltaX() < -DELTA_X || isRightFrame)&& currentTheme < 2){
						flingRuning = false;
						for(float i=0;i<400000;i++){
							flingX -= 0.0005;
						}
						flingX = 0;
						currentTheme++;
						flingRuning = true;
					}else if((Gdx.input.getDeltaX() > DELTA_X || isLeftFrame)&& currentTheme > 0){
						flingRuning = false;
						for(float i=0;i<400000;i++){
							flingX += 0.0005;
						}
						flingX = 0;
						currentTheme--;
						flingRuning = true;
					}
				}else{
					if((Gdx.input.getDeltaX() < -DELTA_X || isRightFrame)&& currentKiteType < 4){
						flingRuning = false;
						for(float i=0;i<400000;i++){
							flingX -= 0.0005;
						}
						flingX = 0;
						currentKiteType++;
						flingRuning = true;
					}else if((Gdx.input.getDeltaX() > DELTA_X || isLeftFrame)&& currentKiteType > 0){
						flingRuning = false;
						for(float i=0;i<400000;i++){
							flingX += 0.0005;
						}
						flingX = 0;
						currentKiteType--;
						flingRuning = true;
					}
				}
				isOnFling = false;
				isLeftFrame = false;
				isRightFrame = false;
			}
		}
	};
	
	@Override
	public void present(float deltaTime) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT); 
		camera.unproject(touchPoint);
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.disableBlending();
		batch.begin();
		batch.draw(Assets.chooseLevelBGround, 0, 0);
		batch.end();

		batch.enableBlending();
		batch.begin();
		clothAndLevelSwitchBtn.draw(batch);
		if(currentScene == LEVEL_SCENE){
			renderLevelUI();
		}else{
			renderClothUI();
		}
		backMainBtn.draw(batch);
		batch.end();
	}
	
	@Override
	public void update(float deltaTime) {
		if(currentScene == LEVEL_SCENE){
			handleLevelGesture();
		}else{
			handleClothGesture();
		}
	}
	
	private void renderLevelUI(){
		batch.draw(themeBgRegions[currentTheme],20+flingX, 280);
		
		level1Btn.draw(batch,flingX);
		if(levelLock[currentTheme][LEVEL_1]){
			batch.draw(Assets.levelLockRegion,120+flingX,380);
		}
		level2Btn.draw(batch,flingX);
		if(levelLock[currentTheme][LEVEL_2]){
			batch.draw(Assets.levelLockRegion,210+flingX,380);
		}
		level3Btn.draw(batch,flingX);
		if(levelLock[currentTheme][LEVEL_3]){
			batch.draw(Assets.levelLockRegion,300+flingX,380);
		}
		if(Preference.getSmallTreasure(GameUtils.getMapTheme(currentTheme))){
			batch.draw(Assets.treasureSmallRegion[currentTheme],363+flingX,478);
		}
		
		renderSmallStar();
		
		if(currentTheme == 2){
			batch.draw(Assets.FrameLeft,2,385 );
		}else if(currentTheme == 0){
			batch.draw(Assets.FrameRight,435, 385);
		}else{
			batch.draw(Assets.FrameRight,435, 385);
			batch.draw(Assets.FrameLeft,2,385 );
		}
	}
	
	private void renderClothUI(){
		kiteClothBtns[currentKiteType].draw(batch, flingX);
		
		if(kiteLocked[currentKiteType]){
			batch.draw(Assets.kiteLockedRegion,20+flingX, 280);
		}
		if(currentKiteType ==  0){
			batch.draw(Assets.FrameRight,435, 385);
		}else if(currentKiteType == 4){
			batch.draw(Assets.FrameLeft,2,385 );
		}else {
			batch.draw(Assets.FrameRight,435, 385);
			batch.draw(Assets.FrameLeft,2,385 );
		}
	}
	
	private void renderSmallStar(){
		String theme = GameUtils.getMapTheme(currentTheme);
		if(Preference.getLevelPlayState(theme, 1)){
			Assets.textFontSmall.draw(batch, String.valueOf(Preference.getGainStar(theme, 1)), 155-20+flingX, 360+20);
			batch.draw(Assets.smallStarRegion,155+flingX,360);
		}
		
		if(Preference.getLevelPlayState(theme, 2)){
			Assets.textFontSmall.draw(batch, String.valueOf(Preference.getGainStar(theme, 2)), 245-20+flingX, 360+20);
			batch.draw(Assets.smallStarRegion,245+flingX,360);
		}
		
		if(Preference.getLevelPlayState(theme, 3)){
			Assets.textFontSmall.draw(batch, String.valueOf(Preference.getGainStar(theme, 3)), 335-20+flingX, 360+20);
			batch.draw(Assets.smallStarRegion,335+flingX,360);
		}
	}
	
	private void handleLevelGesture(){
		if(gestureDetector.isTouchOn()){
			camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
			level1Btn.isPressed = false;
			level2Btn.isPressed = false;
			level3Btn.isPressed = false;
			backMainBtn.isPressed = false;
			isOnFling = false;
			isLeftFrame = false;
			isRightFrame = false;
			if(level1Btn.pointInTouch(touchPoint.x,touchPoint.y) && 
					!Preference.getLevelLock(GameUtils.getMapTheme(currentTheme), 1)){
				Assets.playSound(Assets.buttonClickSound, game.soundState);
				System.out.println("level1Btn touch");
				//如果第一次进入游戏
				if(currentTheme == 0){
					Preference.setFirstCreateState(false);
				}
				Preference.setKiteType(kiteType);
				Preference.setMapTheme(GameUtils.getMapTheme(currentTheme));
				game.setGameParams(GameUtils.getMapTheme(currentTheme), 1, kiteType);
				game.setScreen(new GameProgressScreen(game, GameUtils.getMapTheme(currentTheme), 1, kiteType));
			}else if(level2Btn.pointInTouch(touchPoint.x,touchPoint.y) && 
					!Preference.getLevelLock(GameUtils.getMapTheme(currentTheme), 2)){
				Assets.playSound(Assets.buttonClickSound, game.soundState);
				Preference.setKiteType(kiteType);
				Preference.setMapTheme(GameUtils.getMapTheme(currentTheme));
				game.setGameParams(GameUtils.getMapTheme(currentTheme), 2, kiteType);
				game.setScreen(new GameProgressScreen(game, GameUtils.getMapTheme(currentTheme), 2, kiteType));
				System.out.println("level2Btn touch");
			}else if(level3Btn.pointInTouch(touchPoint.x,touchPoint.y) && 
					!Preference.getLevelLock(GameUtils.getMapTheme(currentTheme), 3)){
				Assets.playSound(Assets.buttonClickSound, game.soundState);
				Preference.setKiteType(kiteType);
				Preference.setMapTheme(GameUtils.getMapTheme(currentTheme));
				game.setGameParams(GameUtils.getMapTheme(currentTheme), 3, kiteType);
				game.setScreen(new GameProgressScreen(game, GameUtils.getMapTheme(currentTheme), 3, kiteType));
				System.out.println("level3Btn touch");
			}else if(GameUtils.pointInRectangle(leftFrameBounds,touchPoint.x,touchPoint.y) && currentTheme !=0){
				System.out.println("left frame touch");
				Assets.playSound(Assets.buttonClickSound, game.soundState);
				isOnFling = true;
				isLeftFrame = true;
			}else if(GameUtils.pointInRectangle(rightFrameBounds,touchPoint.x,touchPoint.y) && currentTheme !=2){
				System.out.println("right frame touch");
				Assets.playSound(Assets.buttonClickSound, game.soundState);
				isOnFling = true;
				isRightFrame = true;
			}else if(backMainBtn.pointInTouch(touchPoint.x,touchPoint.y)){
				Assets.playSound(Assets.buttonClickSound, game.soundState);
				System.out.println("backMainBtn touch");
				game.setScreen(new MainScreen(game));
			}else if(clothAndLevelSwitchBtn.pointInTouch(touchPoint.x,touchPoint.y)){
				Assets.playSound(Assets.buttonClickSound, game.soundState);
				System.out.println("clothAndLevelBgBtn touch");	
				if(currentScene == LEVEL_SCENE){
					clothAndLevelSwitchBtn.isPressed = true;
					currentScene = CLOTH_SCENE;
				}else {
					clothAndLevelSwitchBtn.isPressed = false;
					currentScene = LEVEL_SCENE;
				}
			}
		}else if(gestureDetector.isTouchDown()){
			camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
			level1Btn.isPressed = level1Btn.pointInTouch(touchPoint.x,touchPoint.y);
			level2Btn.isPressed = level2Btn.pointInTouch(touchPoint.x,touchPoint.y);
			level3Btn.isPressed = level3Btn.pointInTouch(touchPoint.x,touchPoint.y);
			backMainBtn.isPressed = backMainBtn.pointInTouch(touchPoint.x,touchPoint.y);
			if(touchPoint.y < 800- 180 && touchPoint.y > 800 -280- 294){
				isOnFling = true;
			}
		}
	}
	
	private void handleClothGesture(){
		if(gestureDetector.isTouchOn()){
			camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
			backMainBtn.isPressed = false;
			isOnFling = false;
			if(kiteClothBtns[currentKiteType].pointInTouch(touchPoint.x,touchPoint.y) && !Preference.getKiteLock(currentKiteType)){
				unpressOtherKite(currentKiteType);
				System.out.println("kiteClothBtns touch");
				Assets.playSound(Assets.buttonClickSound, game.soundState);
				kiteClothBtns[currentKiteType].isPressed = true;
				kiteType = currentKiteType;
			}else if(GameUtils.pointInRectangle(leftFrameBounds,touchPoint.x,touchPoint.y) && currentKiteType !=0){
				System.out.println("left frame touch");
				Assets.playSound(Assets.buttonClickSound, game.soundState);
				isOnFling = true;
				isLeftFrame = true;
			}else if(GameUtils.pointInRectangle(rightFrameBounds,touchPoint.x,touchPoint.y) && currentKiteType !=4){
				System.out.println("right frame touch");
				Assets.playSound(Assets.buttonClickSound, game.soundState);
				isOnFling = true;
				isRightFrame = true;
			}else if(backMainBtn.pointInTouch(touchPoint.x,touchPoint.y)){
				System.out.println("backMainBtn touch");
				Assets.playSound(Assets.buttonClickSound, game.soundState);
				game.setScreen(new MainScreen(game));
			}else if(clothAndLevelSwitchBtn.pointInTouch(touchPoint.x,touchPoint.y)){
				System.out.println("clothAndLevelBgBtn touch");	
				Assets.playSound(Assets.buttonClickSound, game.soundState);
				if(currentScene == LEVEL_SCENE){
					clothAndLevelSwitchBtn.isPressed = true;
					currentScene = CLOTH_SCENE;
				}else {
					clothAndLevelSwitchBtn.isPressed = false;
					currentScene = LEVEL_SCENE;
				}
			}
		}else if(gestureDetector.isTouchDown()){
			camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
			backMainBtn.isPressed = backMainBtn.pointInTouch(touchPoint.x,touchPoint.y);
			if(touchPoint.y < 800- 180 && touchPoint.y > 800 -280- 294){
				isOnFling = true;
			}
		}
	}
	
	private void unpressOtherKite(int kite){
		for(int i=0;i<5;i++){
			if(kite == i){
				continue;
			}
			kiteClothBtns[i].isPressed = false;
		}
	}

	@Override
	public void dispose() {
		boolean retry = true;
		flingRuning = false;
		while (retry) {
           try {
               flingThread.join();
               retry = false;
           } 
           catch (InterruptedException e) {//不断地循环，直到刷帧线程结束
           }
       }
	}
	
	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
