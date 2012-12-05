package com.zsy.flykite;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;

public class GameUtils {
	
	//判断两个矩形是否碰撞(重叠面积超20%则判定为碰撞)
	//用于enemy的碰撞
	public static boolean isContain2(float thisX,float thisY,int thisWidth,int thisHeight,float otherX, float otherY, int otherWidth, int otherHeight){

		float xd = 0;//大的x
		float yd = 0;//大的y
		float xx = 0;//小的x
		float yx = 0;//小的y
		int width = 0;
		int height = 0;
		boolean xFlag = true;
		boolean yFlag = true;
		if(thisX >= otherX){
			xd = thisX;
			xx = otherX;
			xFlag = false;
		}else{
			xd = otherX;
			xx = thisX;
			xFlag = true;
		}
		if(thisY >= otherY){
			yd = thisY;
			yx = otherY;
			yFlag = false;
		}else{
			yd = otherY;
			yx = thisY;
			yFlag = true;
		}
		if(xFlag == true){
			width = thisWidth;
		}else {
			width = otherWidth;
		}
		if(yFlag == true){
			height = thisHeight;
		}else{
			height = otherHeight;
		}
		if(xd>=xx&&xd<=xx+width-1&&
				yd>=yx&&yd<=yx+height-1){//首先判断两个矩形有否重叠
		    double Dwidth=width-xd+xx;   //重叠区域宽度		
			double Dheight=height-yd+yx; //重叠区域高度
			if(Dwidth*Dheight/(thisWidth*thisHeight)>=0.20){//重叠面积超20%则判定为碰撞
				return true;
			}
		}
		return false;
	}

	//判断两个矩形是否碰撞(重叠面积超40%则判定为碰撞)
	//用于food的碰撞
	public static boolean isContain(float thisX,float thisY,int thisWidth,int thisHeight,float otherX, float otherY, int otherWidth, int otherHeight){
		float xd = 0;//大的x
		float yd = 0;//大的y
		float xx = 0;//小的x
		float yx = 0;//小的y
		int width = 0;
		int height = 0;
		boolean xFlag = true;
		boolean yFlag = true;
		if(thisX >= otherX){
			xd = thisX;
			xx = otherX;
			xFlag = false;
		}else{
			xd = otherX;
			xx = thisX;
			xFlag = true;
		}
		if(thisY >= otherY){
			yd = thisY;
			yx = otherY;
			yFlag = false;
		}else{
			yd = otherY;
			yx = thisY;
			yFlag = true;
		}
		if(xFlag == true){
			width = thisWidth;
		}else {
			width = otherWidth;
		}
		if(yFlag == true){
			height = thisHeight;
		}else{
			height = otherHeight;
		}
		if(xd>=xx&&xd<=xx+width-1&&
				yd>=yx&&yd<=yx+height-1){//首先判断两个矩形有否重叠
		    double Dwidth=width-xd+xx;   //重叠区域宽度		
			double Dheight=height-yd+yx; //重叠区域高度
			if(Dwidth*Dheight/(otherWidth*otherHeight)>=0.40){//重叠面积超20%则判定为碰撞
				return true;
			}
		}
		return false;
	}
	
	//判断两个矩形是否碰撞(重叠面积超40%则判定为碰撞)
	//用于动态enemy的碰撞
	public static boolean isContain3(float thisX,float thisY,int thisWidth,int thisHeight,float otherX, float otherY, int otherWidth, int otherHeight){
		float xd = 0;//大的x
		float yd = 0;//大的y
		float xx = 0;//小的x
		float yx = 0;//小的y
		int width = 0;
		int height = 0;
		boolean xFlag = true;
		boolean yFlag = true;
		if(thisX >= otherX){
			xd = thisX;
			xx = otherX;
			xFlag = false;
		}else{
			xd = otherX;
			xx = thisX;
			xFlag = true;
		}
		if(thisY >= otherY){
			yd = thisY;
			yx = otherY;
			yFlag = false;
		}else{
			yd = otherY;
			yx = thisY;
			yFlag = true;
		}
		if(xFlag == true){
			width = thisWidth;
		}else {
			width = otherWidth;
		}
		if(yFlag == true){
			height = thisHeight;
		}else{
			height = otherHeight;
		}
		if(xd>=xx&&xd<=xx+width-1&&
				yd>=yx&&yd<=yx+height-1){//首先判断两个矩形有否重叠
		    double Dwidth=width-xd+xx;   //重叠区域宽度		
			double Dheight=height-yd+yx; //重叠区域高度
			if(Dwidth*Dheight/(otherWidth*otherHeight)>=0.20){//重叠面积超20%则判定为碰撞
				return true;
			}
		}
		return false;
	}

	public static Button createButton(int x,int y,int width,int height,TextureRegion region1,TextureRegion region2){
		NinePatch n1 = new NinePatch(region1);
		NinePatch n2 = new NinePatch(region2);
		ButtonStyle style = new ButtonStyle(n1, n2, n2, 0f, 0f, 0f, 0f, null, null);
		Button button = new Button(style);
		button.x = x;
		button.y = y;
		button.width = width;
		button.height = height;
		return button;
	}
	
	public static ButtonStyle createButtonStyle(TextureRegion region1,TextureRegion region2){
		NinePatch n1 = new NinePatch(region1);
		NinePatch n2 = new NinePatch(region2);
		ButtonStyle style = new ButtonStyle(n1, n2, n2, 0f, 0f, 0f, 0f, null, null);
		return style;
	}
	
	public static ButtonStyle createButtonStyle(TextureRegion downRegion,TextureRegion upRegion,TextureRegion checkedRegion){
		NinePatch n1 = new NinePatch(downRegion);
		NinePatch n2 = new NinePatch(upRegion);
		NinePatch n3 = new NinePatch(checkedRegion);
		ButtonStyle style = new ButtonStyle(n1, n2, n3, 0f, 0f, 0f, 0f, null, null);
		return style;
	}
	
	public static String getMapTheme(int theme){
		switch (theme) {
		case 0:
			return GameContant.THEME_SKY;
		case 1:
			return GameContant.THEME_OCEAN;
		default:
			return GameContant.THEME_SPACE;
		}
	}
	
	public static int getMapTheme(String theme){
		if(theme.equals("sky")){
			return 0;
		}else if(theme.equals("ocean")){
			return 1;
		}else if(theme.equals("space")){
			return 2;
		}
		return 0;
	}
	
	public static boolean pointInRectangle(Rectangle r,float x,float y){
		return r.x <= x && r.x + r.width >= x && r.y <= y && r.y + r.height >= y;
	}

}
