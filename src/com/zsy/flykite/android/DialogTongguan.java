package com.zsy.flykite.android;

import android.content.DialogInterface;

import com.zsy.flykite.GameUtils;
import com.zsy.flykite.android.ActionResolver.IAlertDialogCallback2;
import com.zsy.flykite.screen.ClothAndLevelScreen;
import com.zsy.flykite.screen.Game;

public class DialogTongguan implements IAlertDialogCallback2{

	private Game game;
	public DialogTongguan(Game game){
		this.game = game;
	}
	
	@Override
	public String getMessage() {
		return getMsg();
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
	
	private String getMsg(){
		if(game.mapTheme.equals("sky")){
			return "������30�����ǲ��ܽ��������"+"\n"+
			"����Ŭ��Ŷ~~";
		}else if(game.mapTheme.equals("ocean")){
			return "������35�����ǲ��ܽ���̫�չ�"+"\n"+
			"����Ŭ��Ŷ~~";
		}else if(game.mapTheme.equals("space")){
			return "������40�����ǲ��ܻ�ý���"+"\n"+
			"�����������¾Ϳ��ԾȻ�ɷ�����"+"\n"+
			"fighting~~";
		}
		return null;
	}
}
