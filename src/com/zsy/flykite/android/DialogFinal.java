package com.zsy.flykite.android;

import android.content.DialogInterface;

import com.zsy.flykite.GameUtils;
import com.zsy.flykite.android.ActionResolver.IAlertDialogCallback2;
import com.zsy.flykite.screen.ClothAndLevelScreen;
import com.zsy.flykite.screen.Game;

public class DialogFinal implements IAlertDialogCallback2{

	private Game game;
	public DialogFinal(Game game){
		this.game = game;
	}
	
	@Override
	public String getMessage() {
		return "恭喜您，闯关成功！";
	}

	@Override
	public String getPositiveText() {
		return "确定";
	}

	@Override
	public String getTitle() {
		return "提示";
	}

	@Override
	public void positiveClick(DialogInterface dialog, int which) {
		dialog.dismiss();
		ClothAndLevelScreen screen = new ClothAndLevelScreen(game);
		screen.currentTheme = GameUtils.getMapTheme(game.mapTheme);
		game.setScreen(screen);
	}
}
