package com.zsy.flykite.android;

import android.content.DialogInterface;

import com.zsy.flykite.Preference;
import com.zsy.flykite.android.ActionResolver.IAlertDialogCallback;
import com.zsy.flykite.screen.Game;
import com.zsy.flykite.screen.TeachScreen2;

public class DialogNewGame implements IAlertDialogCallback{

	private Game game;
	public DialogNewGame(Game game){
		this.game = game;
	}
	
	@Override
	public String getMessage() {
		return "你将开启新游戏的征程";
	}

	@Override
	public String getNegativeText() {
		return "返回";
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
	public void negativeClick(DialogInterface dialog, int which) {
		dialog.dismiss();
	}

	@Override
	public void positiveClick(DialogInterface dialog, int which) {
		dialog.dismiss();
		Preference.reset();
		game.setScreen(new TeachScreen2(game));
	}

}
