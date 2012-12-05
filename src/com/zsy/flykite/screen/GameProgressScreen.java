package com.zsy.flykite.screen;

import com.zsy.flykite.MapsUtils;

public class GameProgressScreen extends ProgressScreen{

	final int progNum = 3;
	String mapTheme;
	int level;
	int kiteType;
	public GameProgressScreen(Game game,String mapTheme,int level,int kiteType) {
		super(game);
		progressNum = progNum;
		this.mapTheme = mapTheme;
		this.level = level;
		this.kiteType = kiteType;
		System.out.println("GameProgressScreen-->canshu: " + mapTheme + " " + level+" " +kiteType);
	}

	@Override
	public void afterLoad() {
		game.setScreen(new GameScreen(game,mapTheme, level, kiteType));
	}

	@Override
	public void loadingAction(int progress) {
		MapsUtils.load(mapTheme, level, progress);
	}
}
