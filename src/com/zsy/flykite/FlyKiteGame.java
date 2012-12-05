package com.zsy.flykite;

import com.zsy.flykite.android.ActionResolver;
import com.zsy.flykite.screen.Game;
import com.zsy.flykite.screen.Screen;
import com.zsy.flykite.screen.StartProgressScreen;

public class FlyKiteGame extends Game{
	public ActionResolver actionResolver;
	public FlyKiteGame(FlyKiteAndroid app){
		actionResolver = app.actionResolver;
	}
	
	@Override
	public Screen getStartScreen() {
		return new StartProgressScreen(this);
	}

	@Override
	public void create() {
		this.kiteType = Preference.getKiteType();
		this.mapTheme = Preference.getMapTheme();
		this.soundState = Preference.getSoundState();
		//设置sky第一关没有锁
		Preference.setLevelLock(GameContant.THEME_SKY, 1, false);
		super.create();
	}
}
