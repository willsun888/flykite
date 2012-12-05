package com.zsy.flykite;

import com.badlogic.gdx.Gdx;

public class GestureDetector {
	public static final int FLING_LEFT = 0;
	public static final int FLING_RIGHT = 1;
	
	private static final long maxFlingDelay = (long)(0.15f * 1000000000l);
	private static final long tapCountInterval = (long)(0.4f * 1000000000l);
	private static final long longPressDuration = (long)(1.5f * 1000000000l);
	private static final float deltaX = 10f;
	private boolean isTouched = false;
	
	public GestureDetector(){
		
	}
	
	public boolean isTouchDown(){
		if(Gdx.input.isTouched()){
			return true;
		}
		return false;
	}
	
	public  boolean isTouchOn(){
		if(!Gdx.input.isTouched() ){
			if(isTouched){
				if (System.nanoTime() - Gdx.input.getCurrentEventTime() > tapCountInterval) {
					System.out.println("Gdx.input.isOnTouch");
					isTouched = false;
					return true;
				}
			}
		}else{
			isTouched = true;
		}
		return false;
	}
	
	public boolean isFling(int dir){
		if(dir == FLING_RIGHT){	//ÓÒ
			if(Gdx.input.getDeltaX() > 0){
				return true;
			}
		}else{			//×ó
			if(Gdx.input.getDeltaX() < 0){
				return true;
			}
		}
		return false;
	}
}
