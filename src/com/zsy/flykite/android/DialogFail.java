package com.zsy.flykite.android;

import android.content.DialogInterface;

import com.zsy.flykite.GameContant;
import com.zsy.flykite.android.ActionResolver.IAlertDialogCallback;
import com.zsy.flykite.screen.ClothAndLevelScreen;
import com.zsy.flykite.screen.Game;
import com.zsy.flykite.screen.GameProgressScreen;

public class DialogFail implements IAlertDialogCallback{

	private Game game;
	private String message;
	private String negativeText;
	private String positiveText;
	private String title;
	private int dieReason;
	public DialogFail(Game game,int dieReason){
		this.game = game;
		this.dieReason = dieReason;
		setDialog();
	}
	
	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String getNegativeText() {
		return negativeText;
	}

	@Override
	public String getPositiveText() {
		return positiveText;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void negativeClick(DialogInterface dialog, int which) {
		setNegativeClick();
		dialog.dismiss();
	}

	@Override
	public void positiveClick(DialogInterface dialog, int which) {
		setPositiveClick();
		dialog.dismiss();
	}
	
	private void setDialog(){
		switch (dieReason) {
		case GameContant.DIE_NO_LIFE:
			message = "����ʧ�ܣ�";
			negativeText = "����";
			positiveText = "���¿�ʼ";
			title = "��ʾ";
			break;
		case GameContant.DIE_TOO_LOOSE:
			message = "�߹��ɣ����ݵ������ˣ�";
			negativeText = "����";
			positiveText = "���¿�ʼ";
			title = "��ʾ";
			break;
		case GameContant.DIE_TOO_TIGHT:
			message = "�߹��������ݵ������ˣ�";
			negativeText = "����";
			positiveText = "���¿�ʼ";
			title = "��ʾ";
			break;
		}
	}
	
	private void setNegativeClick(){
		game.setScreen(new ClothAndLevelScreen(game));
	}
	
	private void setPositiveClick(){
		game.setScreen(new GameProgressScreen(game, game.mapTheme, game.level, game.kiteType));
	}
}
