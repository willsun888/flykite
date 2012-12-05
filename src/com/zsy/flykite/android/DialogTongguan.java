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
	
	private String getMsg(){
		if(game.mapTheme.equals("sky")){
			return "必须获得30个星星才能解锁海洋关"+"\n"+
			"继续努力哦~~";
		}else if(game.mapTheme.equals("ocean")){
			return "必须获得35个星星才能解锁太空关"+"\n"+
			"继续努力哦~~";
		}else if(game.mapTheme.equals("space")){
			return "必须获得40个星星才能获得奖章"+"\n"+
			"集齐三个奖章就可以救活飞飞啦！"+"\n"+
			"fighting~~";
		}
		return null;
	}
}
