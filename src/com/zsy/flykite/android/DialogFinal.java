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
		return "��ϲ�������سɹ���";
	}

	@Override
	public String getPositiveText() {
		return "ȷ��";
	}

	@Override
	public String getTitle() {
		return "��ʾ";
	}

	@Override
	public void positiveClick(DialogInterface dialog, int which) {
		dialog.dismiss();
		ClothAndLevelScreen screen = new ClothAndLevelScreen(game);
		screen.currentTheme = GameUtils.getMapTheme(game.mapTheme);
		game.setScreen(screen);
	}
}
