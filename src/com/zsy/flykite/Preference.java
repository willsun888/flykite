package com.zsy.flykite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources.Theme;

public class Preference {
	
	private static final String KITE_TYPE = "kiteType";
	private static final String MAP_THEME = "mapTheme";
	private static final String SOUND_STATE = "soundState";
	
	private static SharedPreferences preferences;
	
	private Preference() {
		
	}
	
	public static SharedPreferences instance(){
		if(null == preferences){
			return ((AndroidApplication)Gdx.app).getSharedPreferences("GamePrefs", Context.MODE_PRIVATE);
		}
		return preferences;
	}
	
	public static boolean getKiteLock(int kiteType){
		return instance().getBoolean("kite-"+kiteType, true);
	}
	
	public static void setKiteLock(int kiteType,boolean state){
		Editor editor = instance().edit();
		editor.putBoolean("kite-"+kiteType, state);
		editor.commit();
	}
	
	public static void setKiteLock(String kiteType,boolean state){
		Editor editor = instance().edit();
		editor.putBoolean(kiteType, state);
		editor.commit();
	}
	
	public static boolean getKiteLock(String kiteType){
		return instance().getBoolean(kiteType, true);
	}
	
	public static boolean getSoundState(){
		return instance().getBoolean(SOUND_STATE, true);
	}
	
	public static void setSoundState(boolean state){
		Editor editor = instance().edit();
		editor.putBoolean(SOUND_STATE, state);
		editor.commit();
	}
	
	public static int getKiteType(){
		return instance().getInt(KITE_TYPE, 0);
	}
	
	public static void setKiteType(int type){
		Editor editor = instance().edit();
		editor.putInt(KITE_TYPE, type);
		editor.commit();
	}
	
	public static String getMapTheme(){
		return instance().getString(MAP_THEME, "sky");
	}
	
	public static void setMapTheme(String theme){
		Editor editor = instance().edit();
		editor.putString(MAP_THEME, theme);
		editor.commit();
	}
	
	public static boolean getLevelLock(String mapTheme,int level){
		return instance().getBoolean(mapTheme+"-"+level, true);
	}
	
	public static void setLevelLock(String mapTheme,int level,boolean lock){
		Editor editor = instance().edit();
		editor.putBoolean(mapTheme+"-"+level, lock);
		editor.commit();
	}
	
	public static void setLevelPlayState(String mapTheme,int level,boolean state){
		Editor editor = instance().edit();
		editor.putBoolean(mapTheme+"-"+level+"played", state);
		editor.commit();
	}
	
	public static boolean getLevelPlayState(String mapTheme,int level){
		return instance().getBoolean(mapTheme+"-"+level+"played", false);
	}
	
	public static void setSmallTreasure(String mapTheme,boolean isTreasure){
		Editor editor = instance().edit();
		editor.putBoolean(mapTheme+"-treasure", isTreasure);
		editor.commit();
	}
	
	public static boolean getSmallTreasure(String mapTheme){
		return instance().getBoolean(mapTheme+"-treasure", false);
	}
	
	public static int getGainStar(String mapTheme,int level){
		return instance().getInt(mapTheme+"-"+level+"-star", 0);
	}
	
	public static void setGainStar(String mapTheme,int level,int gain){
		Editor editor = instance().edit();
		editor.putInt(mapTheme+"-"+level+"-star", gain);
		editor.commit();
	}
	
	public static int getAllGainStars(String mapTheme){
		int stars = 0;
		stars += Preference.getGainStar(mapTheme, 1);
		stars += Preference.getGainStar(mapTheme, 2);
		stars += Preference.getGainStar(mapTheme, 3);
		return stars;
	}
	
	public static boolean getFirstCreateState(){
		return instance().getBoolean("firstcreate", true);
	}
	
	public static void setFirstCreateState(boolean state){
		Editor editor = instance().edit();
		editor.putBoolean("firstcreate", state);
		editor.commit();
	}
	
	public static boolean getThemeLevelLock(String mapTheme){
		for(int i = 1;i<=3;i++){
			if(getLevelLock(mapTheme, i)){
				return true;
			}
		}
		return false;
	}
	
	public static boolean getThemeLevelPlayed(String mapTheme){
		for(int i = 1;i<=3;i++){
			if(!getLevelPlayState(mapTheme, i)){
				return false;
			}
		}
		return true;
	}
	
	public static void reset(){
		setMapTheme("sky");
		setKiteType(0);
		setFirstCreateState(true);
		setKiteLock(0, false);
		for(int i= 1;i<=4;i++){
			setKiteLock(i, true);
		}
		
		setSmallTreasure("sky",false);
		setSmallTreasure("ocean",false);
		setSmallTreasure("space",false);
		
		for(int i =0;i<3;i++){
			for(int j=1;j<=3;j++){
				setGainStar(GameUtils.getMapTheme(i), j, 0);
				setLevelPlayState(GameUtils.getMapTheme(i), j,false);	
				setLevelLock(GameUtils.getMapTheme(i), j,true);		
			}
		}
		
//		setLevelLock("sky", 1, false);
//		setLevelLock("sky", 2, false);
//		setLevelLock("sky", 3, false);
//		setLevelLock("ocean", 1, false);
//		setLevelPlayState("sky", 1, true);
//		setLevelPlayState("sky", 2, true);
//		setLevelPlayState("sky", 3, true);
//		setLevelPlayState("ocean", 1, true);
		
		//½âËøsky 1¹Ø
		setLevelLock("sky", 1, false);
	}
}
