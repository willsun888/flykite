package com.zsy.flykite.screen;

import com.zsy.flykite.Assets;

public class StartProgressScreen extends ProgressScreen{

	final int progNum = 5;
	public StartProgressScreen(Game game) {
		super(game);
		progressNum = progNum;
	}

	@Override
	public void afterLoad() {
		game.setScreen(new MainScreen(game));
	}

	@Override
	public void loadingAction(int progress) {
		Assets.load(progress);
	}
}
