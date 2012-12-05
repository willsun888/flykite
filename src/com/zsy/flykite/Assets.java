package com.zsy.flykite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
	
	private static TextureAtlas atlas;
	public static TextureRegion starRegion;
	public static TextureRegion[] kiteRegions = new TextureRegion[5];
	public static TextureRegion[] kiteNoEnemyRegions = new TextureRegion[5];
	public static TextureRegion[] smallKiteRegions = new TextureRegion[5];
	public static TextureRegion mainBgRegion;
	public static TextureRegion aboucBgRegion;
	public static TextureRegion mainStartRegion;
	public static TextureRegion mainStartClickRegion;
	public static TextureRegion mainContinueRegion;
	public static TextureRegion mainContinueClickRegion;
	public static TextureRegion mainNewGameRegion;
	public static TextureRegion mainNewGameClickRegion;
	public static TextureRegion mainTeachRegion;
	public static TextureRegion mainTeachClickRegion;
	public static TextureRegion mainAboutRegion;
	public static TextureRegion mainAboutClickRegion;
	public static TextureRegion mainExitRegion;
	public static TextureRegion mainExitClickRegion;
	public static TextureRegion mainContinueGrayRegion;
	
	public static TextureRegion soundOnRegion;
	public static TextureRegion soundOffRegion;
	
	
	//游戏场景
	public static TextureRegion gamePauseRegion;
	public static TextureRegion skyFooterRegion;
	public static TextureRegion oceanFooterRegion;
	public static TextureRegion spaceFooterRegion;
	public static TextureRegion gameKiteLifeRegion1;
	public static TextureRegion gameKiteLifeRegion2;
	public static TextureRegion gameKiteLifeRegion3;
	public static TextureRegion lineRollerRegion;
	public static TextureRegion gamePauseBgRegion;
	public static TextureRegion tightnessWarnRegion;
	public static TextureRegion tightnessStripRegion;
	public static TextureRegion tightnessBiaoRegion;
	
	public static TextureRegion gamePauseBackRegion;
	public static TextureRegion gamePauseRestartRegion;
	public static TextureRegion gamePauseStartRegion;
	public static TextureRegion gamePauseBackClickRegion;
	public static TextureRegion gamePauseRestartClickRegion;
	public static TextureRegion gamePauseStartClickRegion;
	public static TextureRegion gamePauseQuietClickRegion;
	public static TextureRegion gamePauseQuietRegion;
	public static TextureRegion gamePauseSoundClickRegion;
	public static TextureRegion gamePauseSoundRegion;
	
    public static TextureRegion chooseLevelBack;
    public static TextureRegion chooseLevelBackClick;
    public static TextureRegion chooseLevelBGround;
    public static TextureRegion levelRegions1;
    public static TextureRegion levelRegions2;
    public static TextureRegion levelRegions3;
    public static TextureRegion themeRegions[] = new TextureRegion[3];
    
    public static TextureRegion level1;
    public static TextureRegion level2;
    public static TextureRegion level3;
    public static TextureRegion level1Click;
    public static TextureRegion level2Click;
    public static TextureRegion level3Click;
    
    public static TextureRegion FrameLeft;
    public static TextureRegion FrameRight;
    public static TextureRegion ChooseLevel;
    public static TextureRegion ChooseCloth;
    
    public static TextureRegion[] clothRegions = new TextureRegion[5];
    public static TextureRegion[] clothchoosedRegions = new TextureRegion[5];
    
    public static TextureRegion kiteLockedRegion;
    
    public static TextureRegion loadingBgRegion;
    public static TextureRegion loadingScrollRegion;
    
    public static TextureRegion teachRegions[] = new TextureRegion[8];;
    
    public static TextureRegion levelLockRegion;
    
    public static TextureRegion treasureRegion[];
    public static TextureRegion treasureSmallRegion[];
    public static TextureRegion smallStarRegion;
    
    public static TextureRegion tongguanRegion;
	
	//声音
	public static Sound buttonClickSound;
	public static Sound eatFoodSound;
	public static Sound meetEnemySound;
	public static Sound treasureSound;
	public static Music warnTightMusic;
	public static Music oceanBgMusic;
	public static Music spaceBgMusic;
	public static Music skyBgMusic;
	public static Music bgMusic;
	
	
	//字体
	public static BitmapFont textFont;
	public static BitmapFont textFontSmall;
	
	//动画
	public static Animation planeAnim;
	public static Animation plane2Anim;
	public static Animation birdAnim;
	public static Animation bird2Anim;
	public static Animation kongmingAnim;
	
	public static Animation fish1Anim;
	public static Animation fish2Anim;
	
	public static Animation ufoAnim;
	public static Animation satelliteAnim;
	
	
	//一些常量
	public static int BIRD_WIDTH;
	public static int PLANE_WIDTH;
	public static int UFO_WIDTH;
	public static int SATELLITE_WIDTH;
	public static int KONGMING_WIDTH;
	public static int FISH1_WIDTH;
	public static int FISH2_WIDTH;
	
	public static void load(int count){
		if(atlas == null){
			atlas = new TextureAtlas(Gdx.files.internal("data/textures/pack"), Gdx.files.internal("data/textures/"));
		}
		switch (count) {
		case 1:
			loadTextures(1);
			break;
		case 2:
			loadTextures(2);
			break;
		case 3:
			loadTextures(3);
			break;
		case 4:
			loadTextures(4);
			break;
		case 5:
			loadSounds();
			loadFonts();
			break;
		}
	}
	
	public static void loadInitResources(){
		if(atlas == null){
			atlas = new TextureAtlas(Gdx.files.internal("data/textures/pack"), Gdx.files.internal("data/textures/"));
		}
		loadingBgRegion = atlas.findRegion("loading");
        loadingScrollRegion = atlas.findRegion("loading-scroll");
	}
	
	private static void loadTextures(int count){
		switch (count) {
		case 1:
			aboucBgRegion = atlas.findRegion("about");
			mainBgRegion = atlas.findRegion("bg-start");
			mainStartRegion = atlas.findRegion("start-start");
			mainStartClickRegion = atlas.findRegion("start-start-click");
			mainTeachRegion = atlas.findRegion("start-teach");
			mainTeachClickRegion = atlas.findRegion("start-teach-click");
			mainAboutRegion = atlas.findRegion("start-about");
			mainAboutClickRegion = atlas.findRegion("start-about-click");
			mainExitRegion = atlas.findRegion("start-exit");
			mainExitClickRegion = atlas.findRegion("start-exit-click");
			mainContinueClickRegion = atlas.findRegion("start-continue-click");
			mainContinueRegion = atlas.findRegion("start-continue");
			mainNewGameClickRegion = atlas.findRegion("start-newgame-click");
			mainNewGameRegion = atlas.findRegion("start-newgame");
			mainContinueGrayRegion = atlas.findRegion("start-continue-gray");
			
			gamePauseBackClickRegion = atlas.findRegion("gamepause-back-click");
			gamePauseBackRegion = atlas.findRegion("gamepause-back");
			gamePauseStartClickRegion = atlas.findRegion("gamepause-start-click");
			gamePauseStartRegion = atlas.findRegion("gamepause-start");
			gamePauseRestartClickRegion = atlas.findRegion("gamepause-restart-click");
			gamePauseRestartRegion = atlas.findRegion("gamepause-restart");
			gamePauseQuietClickRegion =  atlas.findRegion("gamepause-quiet-click");
			gamePauseQuietRegion =  atlas.findRegion("gamepause-quiet");
			gamePauseSoundClickRegion = atlas.findRegion("gamepause-sound-click");
			gamePauseSoundRegion = atlas.findRegion("gamepause-sound");
			
			for(int i = 0;i<5;i++){
				kiteRegions[i] = atlas.findRegion("kite-" + i);
				kiteNoEnemyRegions[i] = atlas.findRegion("kite-" + i+"-noenemy");
			}
			break;

		case 2:
			starRegion = atlas.findRegion("star-s");
			
			skyFooterRegion =  atlas.findRegion("sky-footer");
			oceanFooterRegion =  atlas.findRegion("ocean-footer");
			spaceFooterRegion =  atlas.findRegion("space-footer");
			gameKiteLifeRegion1 =  atlas.findRegion("life1");
			gameKiteLifeRegion2 =  atlas.findRegion("life2");
			gameKiteLifeRegion3 = atlas.findRegion("life-bg");
			gamePauseRegion =  atlas.findRegion("game-pause");
			gamePauseBgRegion = atlas.findRegion("bg-gamepause");
			
			lineRollerRegion =  atlas.findRegion("line-roller");
			smallKiteRegions[0] =  atlas.findRegion("kite-0-s");
			smallKiteRegions[1] =  atlas.findRegion("kite-1-s");
			smallKiteRegions[2] =  atlas.findRegion("kite-2-s");
			smallKiteRegions[3] =  atlas.findRegion("kite-3-s");
			smallKiteRegions[4] =  atlas.findRegion("kite-4-s");
			
			tightnessBiaoRegion = atlas.findRegion("biao");
			tightnessStripRegion = atlas.findRegion("zhuzi");
			tightnessWarnRegion = atlas.findRegion("tightness-warn");
			
	        chooseLevelBack = atlas.findRegion("chooselevel-back");
	        chooseLevelBackClick = atlas.findRegion("chooselevel-back-click");
	        chooseLevelBGround = atlas.findRegion("bg-chooselevel");
	        levelRegions1 = atlas.findRegion("chooselevel-skyframe");
	        levelRegions2 = atlas.findRegion("chooselevel-oceanframe");
	        levelRegions3 = atlas.findRegion("chooselevel-spaceframe");
			break;
		case 3:
			themeRegions[0] = atlas.findRegion("chooselevel-skyframe");
	        themeRegions[1] = atlas.findRegion("chooselevel-oceanframe");
	        themeRegions[2] = atlas.findRegion("chooselevel-spaceframe");
	        
	        level1 = atlas.findRegion("chooselevel-number1");
	        level2 = atlas.findRegion("chooselevel-number2");
	        level3 = atlas.findRegion("chooselevel-number3");
	        
	        level1Click = atlas.findRegion("chooselevel-number1-click");
	        level2Click = atlas.findRegion("chooselevel-number2-click");
	        level3Click = atlas.findRegion("chooselevel-number3-click");
	        
	        FrameLeft = atlas.findRegion("frame-left");
	        FrameRight = atlas.findRegion("frame-right");
	        
	        ChooseLevel = atlas.findRegion("chooselevel-chooselevel");
	        ChooseCloth = atlas.findRegion("chooselevel-choosecloth");
	        
	        clothRegions[0] =atlas.findRegion("choosecloth-0");
	        clothRegions[1] =atlas.findRegion("choosecloth-1");
	        clothRegions[2] =atlas.findRegion("choosecloth-2");
	        clothRegions[3] =atlas.findRegion("choosecloth-3");
	        clothRegions[4] =atlas.findRegion("choosecloth-4");
	        
	        clothchoosedRegions[0] =atlas.findRegion("choosecloth-0-click");
	        clothchoosedRegions[1] =atlas.findRegion("choosecloth-1-click");
	        clothchoosedRegions[2] =atlas.findRegion("choosecloth-2-click");
	        clothchoosedRegions[3] =atlas.findRegion("choosecloth-3-click");
	        clothchoosedRegions[4] =atlas.findRegion("choosecloth-4-click");
			break;
		case 4:
			kiteLockedRegion = atlas.findRegion("kite-lock");
	        
	        loadingBgRegion = atlas.findRegion("loading");
	        loadingScrollRegion = atlas.findRegion("loading-scroll");
	        teachRegions[0] = atlas.findRegion("manhua1");
	        teachRegions[1] = atlas.findRegion("manhua2");
	        teachRegions[2] = atlas.findRegion("teach-0");
	        teachRegions[3] = atlas.findRegion("teach-1");
	        teachRegions[4] = atlas.findRegion("teach-2");
	        teachRegions[5] = atlas.findRegion("teach-3");
	        teachRegions[6] = atlas.findRegion("teach-4");
	        teachRegions[7] = atlas.findRegion("teach-5");
	        
	        tongguanRegion = atlas.findRegion("tongguan");
	        
	        levelLockRegion = atlas.findRegion("level-lock");
	        
	        soundOnRegion = atlas.findRegion("sound-on");
	        soundOffRegion = atlas.findRegion("sound-off");
	        
	        treasureRegion = new TextureRegion[3];
	        treasureSmallRegion = new TextureRegion[3];
	        treasureRegion[0] = atlas.findRegion("kite-zhi-xing");
	        treasureRegion[1] = atlas.findRegion("kite-zhi-shi");
	        treasureRegion[2] = atlas.findRegion("kite-zhi-chen");
	        
	        treasureSmallRegion[0] = atlas.findRegion("kite-zhi-xing-s");
	        treasureSmallRegion[1] = atlas.findRegion("kite-zhi-shi-s");
	        treasureSmallRegion[2] = atlas.findRegion("kite-zhi-chen-s");
	        smallStarRegion = atlas.findRegion("star-ss");
	        
	        kongmingAnim = new Animation(0.1f, new TextureRegion[]{atlas.findRegion("kongming-0")}) ;
	        
	        planeAnim = new Animation(0.1f, new TextureRegion[]{atlas.findRegion("plane0-0"),atlas.findRegion("plane0-1")}) ;
	        plane2Anim = new Animation(0.1f, new TextureRegion[]{atlas.findRegion("plane1-0"),atlas.findRegion("plane1-1")}) ;
	        PLANE_WIDTH = atlas.findRegion("plane0-0").getRegionWidth();
	        birdAnim = new Animation(0.1f, new TextureRegion[]{
	        		atlas.findRegion("bird0-0"),atlas.findRegion("bird0-1"),atlas.findRegion("bird0-2")}) ;
	        bird2Anim = new Animation(0.1f, new TextureRegion[]{
	        		atlas.findRegion("bird1-0"),atlas.findRegion("bird1-1"),atlas.findRegion("bird1-2")}) ;
	        BIRD_WIDTH = atlas.findRegion("bird0-1").getRegionWidth();
	        ufoAnim = new Animation(0.1f, new TextureRegion[]{atlas.findRegion("UFO1-0"),atlas.findRegion("UFO1-1")}) ;
	        UFO_WIDTH = atlas.findRegion("UFO1-0").getRegionWidth();
	        satelliteAnim = new Animation(0.1f, new TextureRegion[]{atlas.findRegion("satellite-0"),atlas.findRegion("satellite-1"),atlas.findRegion("satellite-2")}) ;
	        SATELLITE_WIDTH = atlas.findRegion("satellite-0").getRegionWidth();
	        fish1Anim = new Animation(0.1f, new TextureRegion[]{
	        		atlas.findRegion("fish1-0"),atlas.findRegion("fish1-1"),atlas.findRegion("fish1-2"),atlas.findRegion("fish1-3")}) ;
	        FISH1_WIDTH = atlas.findRegion("fish1-1").getRegionWidth();
	        fish2Anim = new Animation(0.1f, new TextureRegion[]{
	        		atlas.findRegion("fish2-0"),atlas.findRegion("fish2-1"),atlas.findRegion("fish2-2")}) ;
	        FISH2_WIDTH = atlas.findRegion("fish2-0").getRegionWidth();
	        break;
		}
	}
	
	private static void loadFonts(){
		textFont = new BitmapFont(Gdx.files.internal("data/fonts/font.fnt"), false);
		textFontSmall = new BitmapFont(Gdx.files.internal("data/fonts/font-s.fnt"), false); 
	}

	private static void loadSounds(){
		buttonClickSound = loadSound("ButtonClick.ogg");
		meetEnemySound = loadSound("bleep.ogg");
		eatFoodSound = loadSound("StarHeartSkin.ogg");
		treasureSound = loadSound("Treasure.ogg");
		warnTightMusic = loadMusic("warn.ogg");
		bgMusic = loadMusic("bgsound.ogg");
		bgMusic.setVolume(0.7f);
		bgMusic.setLooping(true);
		warnTightMusic.setLooping(true);
		warnTightMusic.setVolume(0.7f);
		oceanBgMusic = loadMusic("BackgroundOcean.ogg");
		oceanBgMusic.setLooping(true);
		oceanBgMusic.setVolume(0.7f);
		spaceBgMusic = loadMusic("BackgroundSpace.ogg");
		spaceBgMusic.setLooping(true);
		spaceBgMusic.setVolume(0.7f);
		skyBgMusic = loadMusic("BackgroundSky.ogg");
		skyBgMusic.setLooping(true);
		skyBgMusic.setVolume(0.7f);
	}
	
	private static Sound loadSound (String filename) {
		return Gdx.audio.newSound(Gdx.files.internal("data/sounds/" + filename));
	}
	
	private static Music loadMusic(String filename) {
		return Gdx.audio.newMusic(Gdx.files.internal("data/sounds/" + filename));
	}

	public static void playSound (Sound sound,boolean isSound) {
		if(isSound){
			sound.play(1);
		}
	}
	
	public static void playMusic (Music music,boolean isSound) {
		if(isSound){
			music.play();
		}
	}
}
