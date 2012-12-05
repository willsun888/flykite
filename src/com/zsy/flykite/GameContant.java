package com.zsy.flykite;


public class GameContant {
	
	public static final int PICKUP_CONTROLLER_RADIUS1 = 160;	//控制盘半径(大)
	public static final int PICKUP_CONTROLLER_RADIUS2 = 50;		//控制盘半径（小）
	public static final int LINE_STEP_SHOUXIAN = 60;			//收线数值
	public static final int LINE_STEP_FANGXIAN = 120;			//放线数值
	
	//对风筝的操作
	public static final int ACTION_NULL = 2;
	public static final int ACTION_SHOUXIAN = 1;
	public static final int ACTION_FANGXIAN = 0;
	public static final int ACTION_SHAKING = 3;
	
	//	下面是游戏中所用到的方向常量
	//	0静止，1上, 2右上，3右，4右下，5下，6左下，7左，8左上
	public static final int DIR_STOP = 0;
	public static final int DIR_UP = 1;
	public static final int DIR_RIGHT_UP = 2;
	public static final int DIR_RIGHT = 3;
	public static final int DIR_RIGHT_DOWN = 4;
	public static final int DIR_DOWN = 5;
	public static final int DIR_LEFT_DOWN = 6;
	public static final int DIR_LEFT = 7;
	public static final int DIR_LEFT_UP = 8;
	
	//上下左右移动
	public static final int MOVE_STOP = 0;
	public static final int MOVE_UP = 1;
	public static final int MOVE_DOWN = -1;
	public static final int MOVE_LEFT = -1;
	public static final int MOVE_RIGHT = 1;
	
	//屏幕三种背景
	public static final int GAME_BG_SKY = 0;
	public static final int GAME_BG_OCEAN = 1;
	public static final int GAME_BG_SPACE = 2;
	
	public static final int LEFT_MARGIN_LIMIT = 180;
	public static final int RIGHT_MARGIN_LIMIT = 480 - LEFT_MARGIN_LIMIT;
	public static final int UP_MARGIN_LIMIT = 300;
	public static final int DOWN_MARGIN_LIMIT = 100;
	
	public static final int FLING_MIN_DISTANCE = 10;
    public static final int FLING_MIN_VELOCITY = 30;
    
    //实体层物体标号
	public static final int ACTOR_NULL = 0;
	public static final int ACTOR_HEART = 13;
	public static final int ACTOR_STAR = 45;
	public static final int ACTOR_KITE_1 = 1;
	public static final int ACTOR_KITE_2 = 2;
	public static final int ACTOR_KITE_3 = 3;
	public static final int ACTOR_KITE_4 = 4;
	
	//碰撞检测层标号
	public static final int ACTOR_RATTAN = 1;
	public static final int ACTOR_HOTBALL = 2;
	public static final int ACTOR_COLLISION = 6;
	
	//松紧度危险时间间隙
	public static final int TIGHT_DANGER_SPAN = 120;
	
	public static final int TIGHTNESS_SPAN = 1000;			//松紧度的范围
	
	
	public static final String THEME_SKY = "sky";
	public static final String THEME_OCEAN = "ocean";
	public static final String THEME_SPACE = "space";
	
	public static final int LEFT_HARM_LIMIT = 30;
	public static final int RIGHT_HARM_LIMIT = 480 - 30;
	
	public static final int KITE_NUM = 5;
	
	//无敌时间间隙
	public static final int NO_ENEMY_SPAN = 120;
	
	public static final int WIND_RIGHT = 1;
	public static final int WIND_LEFT = -1;
	public static final int WIND_NULL = 0;
	
	public static final int DIE_TOO_TIGHT = 0;
	public static final int DIE_TOO_LOOSE = 1;
	public static final int DIE_NO_LIFE = 3;
	
	public static final int[] TREASURE_LIMIT = new int[]{30,35,40};
	
}
