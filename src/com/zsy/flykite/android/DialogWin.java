package com.zsy.flykite.android;

import android.content.DialogInterface;

import com.zsy.flykite.GameUtils;
import com.zsy.flykite.android.ActionResolver.IAlertDialogCallback;
import com.zsy.flykite.screen.ClothAndLevelScreen;
import com.zsy.flykite.screen.Game;
import com.zsy.flykite.screen.GameProgressScreen;

public class DialogWin implements IAlertDialogCallback{

	private Game game;
	public DialogWin(Game game){
		this.game = game;
	}
	
	@Override
	public void positiveClick(DialogInterface dialog, int which) {
		System.out.println("进入下一关");
		dialog.dismiss();
		int m = GameUtils.getMapTheme(game.mapTheme);
		if(game.level <=2){
			game.level += 1;
			game.setScreen(new GameProgressScreen(game, game.mapTheme, game.level, game.kiteType));
    	}else{
    		if(m <=1){
    			game.setScreen(new GameProgressScreen(game, GameUtils.getMapTheme(m+1), 1, game.kiteType));
    		}
    	}
		System.out.println("parms: " +game.mapTheme+" " +game.level+" " +game.kiteType);
	}
	
	@Override
	public void negativeClick(DialogInterface dialog, int which) {
		dialog.dismiss();
		game.setScreen(new ClothAndLevelScreen(game));
	}
	
	@Override
	public String getTitle() {
		return "退出";
	}
	
	@Override
	public String getPositiveText() {
		return "进入下一关";
	}
	
	@Override
	public String getNegativeText() {
		return "返回";
	}
	
	@Override
	public String getMessage() {
		return "成功闯关，恭喜！";
	}
}
