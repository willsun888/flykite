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
		return "�㽫��������Ϸ������";
	}

	@Override
	public String getNegativeText() {
		return "����";
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
