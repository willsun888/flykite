package com.zsy.flykite;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.math.Vector3;
import com.zsy.flykite.actor.DynamicObject;
import com.zsy.flykite.actor.Enemy;
import com.zsy.flykite.actor.Food;
import com.zsy.flykite.actor.Kite;
import com.zsy.flykite.actor.LineRoller;
import com.zsy.flykite.actor.Wind;
import com.zsy.flykite.android.ActionResolver;
import com.zsy.flykite.android.DialogFail;
import com.zsy.flykite.android.DialogFinal;
import com.zsy.flykite.android.DialogTongguan;
import com.zsy.flykite.android.DialogWin;
import com.zsy.flykite.screen.Game;
import com.zsy.flykite.screen.GameScreen;
import com.zsy.flykite.temp.TileMapRenderer;

public class World {
	
	static final int SCREEN_W = 480;
	static final int SCREEN_H = 800;
	
	public final static int TILE_WIDTH = 32;
	public final static int TILE_HEIGHT = 32;
	
	public final static int MAP_TILE_WIDTH = 22;
	public final static int MAP_TILE_HEIGHT = 250;
	
	public final static int MAP_WIDTH = 704;
	public final static int MAP_HEIGHT = 8000;
	
	public final static int LIFE_HARM = 5;
	public final static int WIND_VEL = 2;
	
	public int containState = 0;
	
	GameScreen gameScreen;
	Kite kite;
	LineRoller lineRoller;
	Button pauseButton;
	
	public OrthographicCamera cam;
	public OrthographicCamera mapCam;
	
	Vector3 moveVector = new Vector3(0,GameContant.MOVE_DOWN,0);
	
	int gainStar = 0;
	int totalStar = 20;

	TiledMap map; 
	TileMapRenderer tileMapRenderer;
	ArrayList<Enemy> enemyList;
	ArrayList<Food> foodList ;
	ArrayList<Food> eatedFoodList;
	ArrayList<DynamicObject> dynamicObjList;
	ArrayList<Wind> windList;
	
	public String mapTheme;
	public int level;
	
	UserAction userAction = UserAction.ACTION_NULL;
    boolean isShaking = false;
    int countShaking = 50;
    int countdown = 50;
    int flag;
    
    int dangerCount;
    
    float moveX;
    float moveY = GameContant.MOVE_DOWN;
    
    float windMoveX = 0;
    
    public int winddirection;
    
    public boolean hasEnemy = true;
    int noEnemyCount;
    
    ActionResolver actionResolver;
    Game game;
    
    public Music warnTightMusic;
	public World(Game game,GameScreen gameScreen,String mapTheme,int level,int kiteType){
		this.gameScreen = gameScreen;
		this.game = game;
		this.mapTheme = mapTheme;
		this.level = level;
		this.actionResolver = gameScreen.actionResolver;
		map = MapsUtils.currMap;
		tileMapRenderer = MapsUtils.currMapRenderer;
		
		this.cam = new OrthographicCamera(SCREEN_W, SCREEN_H);
		this.cam.position.set(SCREEN_W / 2, SCREEN_H / 2, 0);
		
		this.mapCam = new OrthographicCamera(SCREEN_W, SCREEN_H);
		this.mapCam.position.set(MAP_WIDTH / 2, SCREEN_H / 2, 0);
		
		kite = new Kite(this,kiteType, 150, 250);
		lineRoller = new LineRoller();
		pauseButton = new Button(new TextureRegion[]{Assets.gamePauseRegion, Assets.gamePauseRegion},410, 710);
		
		noEnemyCount = GameContant.NO_ENEMY_SPAN;
		
		eatedFoodList = new ArrayList<Food>();
		warnTightMusic = Assets.warnTightMusic;
		generateObjects();
	}
	
	public void generateObjects(){
		foodList = MapsUtils.foodList;
		enemyList = MapsUtils.enemyList;
		dynamicObjList = MapsUtils.getDynamicObjList(map);
		windList = MapsUtils.getWindList(map);
	}
	
	public void update (float deltaTime) {
		checkDead();
		updateDynaObjects(deltaTime);
		checkWind();
		checkDynamicObject();
		checkFood();
		checkEnemy();
		updateMoveVector();
		checkUserAction();
		updateKite(deltaTime);
		updateLineRoller();
	}
	
	private void updateMoveVector(){
		float valueX = Gdx.input.getAccelerometerX();
		float valueY = Gdx.input.getAccelerometerY();
		
		if(valueY > 5){
    		flag = 1; 
    	}
    	if(flag == 1){
    		if(valueY < 3){
			 	isShaking = true;
				flag = 0;
		 	}
    	}
    	
    	// 无风
		if(windMoveX == 0){
			if(valueX > 2.0){
				kite.velocity = (int)valueX + 1;
				kite.direction.x = GameContant.DIR_LEFT;
				moveVector.x = GameContant.MOVE_LEFT;
			}else if(valueX < -2.0){
				kite.velocity = (int)(0-valueX) + 1;
				kite.direction.x = GameContant.DIR_RIGHT;
				moveVector.x = GameContant.MOVE_RIGHT;
			}else {
				kite.velocity = 1;
				kite.direction.x = GameContant.DIR_STOP;
				moveVector.x = GameContant.MOVE_STOP;
			}
		}
		//有风
		else{
			if(valueX > 1.0){
				kite.velocity = (int)valueX + 1;
				kite.direction.x = GameContant.DIR_LEFT;
				moveVector.x = GameContant.MOVE_LEFT;
			}else if(valueX < -1.0){
				kite.velocity = (int)(0-valueX) + 1;
				kite.direction.x = GameContant.DIR_RIGHT;
				moveVector.x = GameContant.MOVE_RIGHT;
			}
		}
	}
	
	private void updateKite(float deltaTime){
		//检测边界（以风筝的中心为检测点）
    	float kiteX = kite.x + kite.width/2 ;
    	
    	Vector3 mapCamVector = mapCam.position;
    	moveX = moveVector.x*kite.velocity + windMoveX;
    	
    	//move left
    	if(moveVector.x == GameContant.MOVE_LEFT){
    		if(kiteX > GameContant.LEFT_MARGIN_LIMIT){
    			moveKite(kite, "x",moveX);
    		}else{
    			mapCamVector.add(moveX, 0, 0);
    			if(mapCamVector.x <=SCREEN_W/2){
        			mapCamVector.x = SCREEN_W/2;
        			moveKite(kite, "x",moveX);
        			checkBorderCollision();
        		}
    		}
    	}
    	//move right
    	else if(moveVector.x == GameContant.MOVE_RIGHT){
    		if(kiteX < GameContant.RIGHT_MARGIN_LIMIT){
    			moveKite(kite, "x",moveX);
    		}else{
    			mapCamVector.add(moveX, 0, 0);
    			if(mapCamVector.x >= MAP_WIDTH-SCREEN_W/2){
        			mapCamVector.x = MAP_WIDTH-SCREEN_W/2;
        			moveKite(kite, "x",moveX);
        			checkBorderCollision();
        		}
    		}
    	}
	}

	private void checkBorderCollision(){
		if(kite.x < GameContant.LEFT_HARM_LIMIT){
			if(hasEnemy){
				kite.life -= LIFE_HARM;
			}
			Assets.playSound(Assets.meetEnemySound, game.soundState);
			moveVector.x = GameContant.DIR_RIGHT;
			for (int i = 0; i < 20; i++) {
				kite.x += 2;
			}
			hasEnemy = false;
		}else if(kite.x + kite.width > GameContant.RIGHT_HARM_LIMIT){
			if(hasEnemy){
				kite.life -= LIFE_HARM;
			}
			Assets.playSound(Assets.meetEnemySound, game.soundState);
			moveVector.x = GameContant.DIR_LEFT;
			for (int i = 0; i < 20; i++) {
				kite.x -= 2;
			}
			hasEnemy = false;
		}
		if(!hasEnemy){
			noEnemyCount --;
			if(noEnemyCount == 0){
				hasEnemy = true;
				noEnemyCount = GameContant.NO_ENEMY_SPAN;
			}
		}
    }
	
	private void checkUserAction(){
    	if(isShaking){
			isShaking = false;
			countShaking = 120;		
		}
    	if(countShaking > 0){
    		moveY = 5 - 0.05f * (120 -countShaking);
    		if(kite.y <= GameContant.UP_MARGIN_LIMIT ){
    			moveKite(kite, "y",moveY);
    		}else{
        		mapCam.position.add(0,moveY,0);
        		if(mapCam.position.y >= MAP_HEIGHT-SCREEN_H/2){
        			mapCam.position.y = MAP_HEIGHT-SCREEN_H/2;
        			moveKite(kite, "y",moveY);
        			checkWin();
        		}
    		}
			kite.kiteheight += moveY/2;
			--countShaking;
			if(countShaking <= 0){
				moveY = GameContant.MOVE_DOWN;
			}
		}
    	
    	//收线
		if(userAction == UserAction.ACTION_SHOUXIAN){
	        countdown = 40;
	        countShaking = 0;
	        userAction = UserAction.ACTION_NULL;
		}
		//放线
		else if(userAction == UserAction.ACTION_FANGXIAN){
			userAction = UserAction.ACTION_NULL;		
		}else if(userAction == UserAction.ACTION_NULL && countdown == 0 && countShaking == 0){
			if(kite.y >= GameContant.DOWN_MARGIN_LIMIT){
				moveKite(kite, "y",GameContant.MOVE_DOWN);
			}else{
				mapCam.position.add(0,GameContant.MOVE_DOWN,0);
				if(mapCam.position.y <= SCREEN_H/2){
					mapCam.position.y = SCREEN_H/2;
					moveKite(kite, "y",GameContant.MOVE_DOWN);
				}
			}
			kite.kiteheight -= moveY/2;
		}
		
		if(countdown>0){
			moveY = 3 - 0.1f * (40 -countdown);
			if(kite.y >= GameContant.DOWN_MARGIN_LIMIT){
				moveKite(kite, "y",-moveY);
			}else{
				mapCam.position.add(0,-moveY,0);
				if(mapCam.position.y <= SCREEN_H/2){
					mapCam.position.y = SCREEN_H/2;
					moveKite(kite, "y",-moveY);
				}
			}
			kite.kiteheight -= moveY/2;
			--countdown;	
			if(countdown <=0){
				moveY = GameContant.MOVE_DOWN;
			}
		}
    }
	
	private void updateLineRoller(){
		float panDeltaX = Gdx.input.getDeltaX();
		//屏幕touch以左上角为原点
		//判断触摸是否在控制区
		float x = Gdx.input.getX(0)-SCREEN_W;
    	float y =  Gdx.input.getY(0)-(SCREEN_H-90);
    	//2500 22500
    	if( x * x + y * y >= Math.pow(GameContant.PICKUP_CONTROLLER_RADIUS1, 2) || 
    			x*x + y*y <= Math.pow(GameContant.PICKUP_CONTROLLER_RADIUS2, 2)){
    		return ;
    	}
    	//收线
		if (-panDeltaX > GameContant.FLING_MIN_DISTANCE) {
			System.out.println("shouxian");
            kite.lineLenght -= GameContant.LINE_STEP_SHOUXIAN;
            userAction = UserAction.ACTION_SHOUXIAN;
            lineRoller.shouXian();
	    } 
		//放线
		else if (panDeltaX > GameContant.FLING_MIN_DISTANCE) {
			System.out.println("fangxian");
            kite.lineLenght += GameContant.LINE_STEP_FANGXIAN;
            userAction = UserAction.ACTION_FANGXIAN;
            lineRoller.fangXian();
	    }
	}
	
	private void updateDynaObjects(float deltaTime){
		for (DynamicObject dynamicObj : dynamicObjList) {
			dynamicObj.mx += dynamicObj.velX ;
			dynamicObj.my += dynamicObj.velY ;
			
			dynamicObj.x = dynamicObj.mx -(mapCam.position.x - SCREEN_W/2);
			dynamicObj.y = dynamicObj.my - (mapCam.position.y - SCREEN_H/2);
			
			dynamicObj.update();
		}
	}

	private void checkFood(){
    	if(foodList.size() > 0){
			for (Food food : foodList) {
				if(contain(food)){
					System.out.println("eat food");
					Assets.playSound(Assets.eatFoodSound, game.soundState);
					eatedFoodList.add(food);
	    			if(food.type == GameContant.ACTOR_HEART){
			    		if(kite.life < 100){
			    			kite.life = 100;
			    		}
		    		}else if(food.type == GameContant.ACTOR_STAR){
		    			gainStar++;
		    		}else if(food.type == GameContant.ACTOR_KITE_1){
		    			kite.kiteType = 1;
		    			game.kiteType = 1;
		    			Preference.setKiteType(1);
		    			Preference.setKiteLock(1, false);
		    		}else if(food.type == GameContant.ACTOR_KITE_2){
		    			kite.kiteType = 2;
		    			game.kiteType = 2;
		    			Preference.setKiteType(2);
		    			Preference.setKiteLock(2, false);
		    		}else if(food.type == GameContant.ACTOR_KITE_3){
		    			kite.kiteType = 3;
		    			game.kiteType = 3;
		    			Preference.setKiteType(3);
		    			Preference.setKiteLock(3, false);
		    		}else if(food.type == GameContant.ACTOR_KITE_4){
		    			kite.kiteType = 4;
		    			game.kiteType = 4;
		    			Preference.setKiteType(4);
		    			Preference.setKiteLock(4, false);
		    		}
				}
			}
			for (Food food : eatedFoodList) {
				food.isEated = true;
				setActorNull((int) ((int)tileMapRenderer.getMapHeightUnits()/TILE_HEIGHT - food.y/TILE_HEIGHT - 1), (int)food.x/TILE_WIDTH);
			}
			foodList.removeAll(eatedFoodList);
			eatedFoodList.removeAll(eatedFoodList);
    	}
	}
	
	private void checkEnemy(){
    	if(enemyList.size() > 0){
    		for(int i=0;i<enemyList.size();i++){
    			Enemy enemy = enemyList.get(i);
    			if(contain(enemy)){
    				switch(containState){
    				case 1:
    					countShaking = 0;//禁止往上
    					for (int j = 0; j < 50; j++) {
    						kite.y -= 0.3;
    					}
    					break;
    				case 2:
    					countdown = 0; 
    					for (int j = 0; j < 50; j++) {
    						kite.y += 0.3;
    					}
    					break;
    					//禁止收线
    				case 3:
    					//moveVector.x = GameContant.DIR_RIGHT;
    					for (int j = 0; j < 5; j++) {
    						kite.x += 2;
    					}
    					break;
    					//禁止左移
    				case 4:
    					//moveVector.x = GameContant.DIR_LEFT;
    					for (int j = 0; j < 5; j++) {
    						kite.x -= 2;
    					}
    					break;
    					//禁止右移
    				case 5:
    					countShaking = 0;
    					//moveVector.x = GameContant.DIR_LEFT;
    					for (int j = 0; j < 5; j++) {
    						kite.x -= 2;
    					}
    					break;
    					
    					//禁止右移、往上
    				case 6:  
    					countdown = 0; 					
    					//moveVector.x = GameContant.DIR_LEFT;
    					for (int j = 0; j < 5; j++) {
						kite.x -= 2;
					}
    					break;
    					//禁止右移、收线
    				case 7:
    					countdown = 0;
    					//moveVector.x = GameContant.DIR_RIGHT;
    					for (int j = 0; j < 5; j++) {
    						kite.x += 2;
    					}
    					break;
    					//禁止左移、收线
    				case 8:
    					countShaking = 0;
    					//moveVector.x = GameContant.DIR_RIGHT;
    					for (int j = 0; j < 5; j++) {
    						kite.x += 2;
    					}
    					break;
    					//禁止左移、往上
    				}
    				if(hasEnemy){
	    				if(kite.life <= 100){
			    			kite.life -= LIFE_HARM;
			    		}
	    				System.out.println("meet enemy  " + containState);
	    				Assets.playSound(Assets.meetEnemySound, game.soundState);
    				}
    				hasEnemy = false;
    			}
    		}
    	}
		if(!hasEnemy){
			noEnemyCount --;
			if(noEnemyCount == 0){
				hasEnemy = true;
				noEnemyCount = GameContant.NO_ENEMY_SPAN;
			}
		}
	}
	
	private void checkDynamicObject(){
		if(hasEnemy){
			if(dynamicObjList.size() > 0){
				for(int i=0;i<dynamicObjList.size();i++){
					DynamicObject dynaObj = dynamicObjList.get(i);
					if(contain(dynaObj)){
						Assets.playSound(Assets.meetEnemySound, game.soundState);
						if(kite.life <= 100){
			    			kite.life -= LIFE_HARM;
			    		}
						hasEnemy = false;
					}
				}
			}
		}else{
			noEnemyCount --;
    		if(noEnemyCount == 0){
    			hasEnemy = true;
    			noEnemyCount = GameContant.NO_ENEMY_SPAN;
    		}
		}
	}
	
	private void checkWind(){
		if(windList.size() > 0){
			for (Wind wind : windList) {
	    		float Start = wind.startpoint - (mapCam.position.y - SCREEN_H/2);
	    		float End = wind.endpoint  - (mapCam.position.y - SCREEN_H/2);
				if(kite.y>=Start && kite.y<=End){
					if(wind.direction == GameContant.WIND_RIGHT){
						windMoveX = WIND_VEL;
						winddirection = GameContant.WIND_RIGHT;
					}else if(wind.direction ==GameContant.WIND_LEFT){
						windMoveX = -WIND_VEL;
						winddirection = GameContant.WIND_LEFT;
					}
					System.out.println("in the wind(direction,windMoveX): " + winddirection + " " + windMoveX);
					break;
				}
				else{
					windMoveX = 0;
					winddirection = GameContant.WIND_NULL;
				} 	
				
			}
		}
	}
	
	private void checkWin(){
		if(mapCam.position.y >= MAP_HEIGHT -SCREEN_H/2){
			if(kite.y >= SCREEN_H - kite.height -30){
				gameScreen.isPause = true;
				gameScreen.isDialog = true;
				if(gainStar < Preference.getGainStar(mapTheme, level)){
					gainStar = Preference.getGainStar(mapTheme, level);
				}
				Preference.setGainStar(mapTheme, level, gainStar);
				Preference.setLevelPlayState(mapTheme, level, true);
				//解锁level
				unlockLevel();
				if(Preference.getAllGainStars(mapTheme) >= GameContant.TREASURE_LIMIT[GameUtils.getMapTheme(mapTheme)] &&
						!Preference.getSmallTreasure(mapTheme) && Preference.getThemeLevelPlayed(mapTheme)){
					gameScreen.isTreasure = true;
					Preference.setSmallTreasure(mapTheme, true);
					return ;
				}else if(Preference.getAllGainStars(mapTheme) < GameContant.TREASURE_LIMIT[GameUtils.getMapTheme(mapTheme)] &&
						!Preference.getSmallTreasure(mapTheme) && Preference.getThemeLevelPlayed(mapTheme)){
					actionResolver.showAlertDialog(new DialogTongguan(game));
					return ;
				}
				if(level <=2){
					actionResolver.showAlertDialog(new DialogWin(game));
				}else{
					actionResolver.showAlertDialog(new DialogFinal(game));
				}
			}
		}
	}
	
	private void checkDead(){
		if(kite.life <=0){
			System.out.println("kite dead");
			gameScreen.isPause = true;
			gameScreen.isDialog = true;
			actionResolver.showAlertDialog(new DialogFail(game,GameContant.DIE_NO_LIFE));
		}
		if(kite.tightness < 100f|| kite.tightness > 900f){
    		dangerCount ++;
    		if(!warnTightMusic.isPlaying()){
        		Assets.playMusic(warnTightMusic, game.soundState);
    		}
    		if(dangerCount > GameContant.TIGHT_DANGER_SPAN ){
    			gameScreen.isPause = true;
    			gameScreen.isDialog = true;
    			if(kite.tightness < 100f)
    				actionResolver.showAlertDialog(new DialogFail(game,GameContant.DIE_TOO_TIGHT));
    			else if(kite.tightness > 900f)
    				actionResolver.showAlertDialog(new DialogFail(game,GameContant.DIE_TOO_LOOSE));
    			if(warnTightMusic.isPlaying()){
        			warnTightMusic.stop();
        		}
    		}
    	}else{
    		dangerCount = 0;
    		if(Assets.warnTightMusic.isPlaying()){
    			Assets.warnTightMusic.stop();
    		}
    	}
	}
	
    private boolean contain(Food food){
    	if(food.isEated== false){
    		float otherX = food.x - (mapCam.position.x - SCREEN_W/2);
    		float otherY = food.y - (mapCam.position.y - SCREEN_H/2);
    		if(GameUtils.isContain(kite.x , kite.y, (int)kite.width, (int)kite.height, 
    				otherX, otherY, (int)food.width, (int)food.height)){
    			return true;
    		}
    	}
    	return false;
    }
    
    private boolean contain(Enemy enemy){
    	float otherX = enemy.x - (mapCam.position.x - SCREEN_W/2);
		float otherY = enemy.y - (mapCam.position.y - SCREEN_H/2);
		if(GameUtils.isContain2(kite.x , kite.y, (int)kite.width, (int)kite.height, 
				otherX, otherY, (int)enemy.width, (int)enemy.height)){
			if(kite.y+kite.height>=otherY & kite.y+kite.height<=otherY+enemy.height & kite.x>=otherX &kite.x+kite.width<=otherX+enemy.width){
				containState = 1;//从下往上移动
			}
			else if(kite.y>=otherY & kite.y <= otherY+enemy.height & kite.x>=otherX &kite.x+kite.width<=otherX+enemy.width) {
				containState = 2;//从上往下
			}
			else if(kite.x+kite.width>=otherX+enemy.width & kite.x<=otherX+enemy.width & kite.y>=otherY &kite.y+kite.height<=otherY+enemy.height){
				containState = 3;//从右往左
			}
			else if(kite.x+kite.width>=otherX & kite.x<=otherX & kite.y>=otherY &kite.y+kite.height<=otherY+enemy.height){
				containState = 4;//从左往右
			}
			else if (kite.x+kite.width>otherX & kite.y+kite.height>otherY & kite.x<otherX &kite.y<otherY){
				containState = 5;//从障碍物左下
			}
			else if (kite.x+kite.width>otherX & kite.y<otherY+enemy.height & kite.x<otherX & kite.y+kite.height>otherY+enemy.height){
				containState = 6;//从障碍物左上
			}
			else if(kite.x<otherX+enemy.width & kite.y<otherY+enemy.height &kite.x+kite.width>otherX+enemy.width &kite.y+kite.height>otherY+enemy.height){
				containState = 7;//从障碍物右上
			}
			else if (kite.x<otherX+enemy.width & kite.y+kite.height>otherY &kite.x+kite.width>otherX+enemy.width & kite.y<otherY){
				containState = 8;
			}
			System.out.println("check contain enemy"+kite.x+" " +kite.y+" "+otherX+" "+otherY+" "+enemy.x+" "+enemy.y);
			return true;
		}
		return false;
    }
    
    private boolean contain(DynamicObject dynaObj){
    	float otherX = dynaObj.x;
		float otherY = dynaObj.y;
		if(GameUtils.isContain3(kite.x , kite.y, (int)kite.width, (int)kite.height, 
				otherX, otherY, (int)dynaObj.width, (int)dynaObj.height)){
				System.out.println("check contain dynaObj"+kite.x+" " +kite.y+" "+otherX+" "+otherY+" "+dynaObj.x+" "+dynaObj.y);
			return true;
		}
		return false;
    }
    
    private void setActorNull(int x,int y){
    	tileMapRenderer.udpateNormalCache(2, x, y,GameContant.ACTION_NULL);
    }

    private void unlockLevel(){
    	int m = GameUtils.getMapTheme(mapTheme);
    	if(level <=2){
    		Preference.setLevelLock(mapTheme, level+1, false);
    	}else{
    		if(m <=1){
    			Preference.setLevelLock(GameUtils.getMapTheme(m+1), 1, false);
    		}
    	}
    }
    
    private void moveKite(Kite kite,String xy,float value){
    	if(xy == "x"){
	    	kite.x += value;
	    	if(kite.x <= 0){
	    		kite.x = 0;
	    	}else if(kite.x >= SCREEN_W - kite.width){
	    		kite.x = SCREEN_W - kite.width;
	    	}
    	}else{
    		kite.y += value;
	    	if(kite.y <= 0){
	    		kite.y = 0;
	    	}else if(kite.y >= SCREEN_H - kite.height){
	    		kite.y = SCREEN_H - kite.height;
	    	}
    	}
    }
}
