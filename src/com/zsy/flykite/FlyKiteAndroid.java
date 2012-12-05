package com.zsy.flykite;

import android.os.Bundle;
import android.view.WindowManager;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.zsy.flykite.android.ActionResolver;
import com.zsy.flykite.android.ActionResolverAndroid;

public class FlyKiteAndroid extends AndroidApplication{
	
	ActionResolver actionResolver;
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		actionResolver = new ActionResolverAndroid(this);
		
		FlyKiteGame flyKiteGame = new FlyKiteGame(this);
		flyKiteGame.actionResolver = actionResolver;
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = true;
		config.useWakelock = true;
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		initialize(flyKiteGame, config);
	}
}
