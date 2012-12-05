package com.zsy.flykite.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.zsy.flykite.Assets;

public class LineRoller{

	private TextureRegion lineRollerRegion;
	private Sprite lineRollerSprite;
	
	public int spriteCount = 30;
	private float degree = 0;
	
	private Animation animationShouxian;
	private Animation animationFangxian;
	private float stateTime;
	private Sprite[] shouxianSprites;
	private Sprite[] fangxianSprites;
	public int countShouxian = 0;
	public int countFangxian = 0;
	
	public LineRoller(){
		lineRollerRegion = Assets.lineRollerRegion;
		lineRollerSprite = new Sprite(lineRollerRegion);
		int width = lineRollerRegion.getRegionWidth();
		int height = lineRollerRegion.getRegionHeight();
		lineRollerSprite.setPosition(480 - width/2, 80-height/2);
		shouxianSprites = new Sprite[spriteCount];
		fangxianSprites = new Sprite[spriteCount];
		for (int i = 0; i < spriteCount; i++) {
			Sprite sprite = new Sprite(lineRollerRegion);
			sprite.setPosition(480 - width/2, 80-height/2);
			shouxianSprites[i] = sprite;
			fangxianSprites[i] = sprite;
		}
		animationShouxian = new Animation(0.03f, shouxianSprites);
		animationFangxian = new Animation(0.03f, fangxianSprites);
	}

	public void draw(SpriteBatch batch) {
		drawLineRoller(batch);
	}
	
    private void drawLineRoller(SpriteBatch batch){
    	if(countShouxian > 0){
	    	stateTime += Gdx.graphics.getDeltaTime();
			Sprite currentSprite = (Sprite) animationShouxian.getKeyFrame(stateTime, true);
			currentSprite.setRotation(degree+(spriteCount-countShouxian+1)*5);
			currentSprite.draw(batch);
			lineRollerSprite = currentSprite;
			countShouxian--;
    	}else if(countFangxian > 0){
    		stateTime += Gdx.graphics.getDeltaTime();
			Sprite currentSprite = (Sprite) animationFangxian.getKeyFrame(stateTime, true);
			currentSprite.setRotation(degree-(spriteCount-countFangxian+1)*5);
			currentSprite.draw(batch);
			lineRollerSprite = currentSprite;
			countFangxian--;
    	}else {
    		lineRollerSprite.draw(batch);
    	}
    }
    
    public void shouXian(){
    	countShouxian = spriteCount;
    	countFangxian = 0;
    }
    
    public void fangXian(){
    	countFangxian = spriteCount;
    	countShouxian = 0;
    }
}
