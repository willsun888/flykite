package com.zsy.flykite.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.zsy.flykite.Assets;

public abstract class ProgressScreen extends Screen{
	
	static final int SCREEN_W = 480;
	static final int SCREEN_H = 800;
	
	private int progress = 1;
	private int progressBar = 0;
	
	private TextureRegion loadingScrollRegion;
	private TextureRegion loadingBgRegion;
	
	protected int progressNum;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	String mapTheme;
	int level;
	int kiteType;
	int scrollWidth;
	
	public ProgressScreen(Game game){
		super(game);
		batch = new SpriteBatch();
		camera = new OrthographicCamera(SCREEN_W,SCREEN_H);
		camera.position.set(SCREEN_W/2,SCREEN_H/2,0);
		if(Assets.loadingBgRegion == null){
			loadingBgRegion = new TextureRegion(new Texture(Gdx.files.internal("data/loading.png")), 0, 0, 480, 800);
		}else{
			loadingBgRegion = Assets.loadingBgRegion;
		}
		if(Assets.loadingScrollRegion == null){
			loadingScrollRegion = new TextureRegion(new Texture(Gdx.files.internal("data/loading-scroll.png")), 0, 0, 480, 89);
		}else{
			loadingScrollRegion = Assets.loadingScrollRegion;
		}
		scrollWidth = loadingScrollRegion.getRegionWidth();
	} 

	@Override
	public void present(float deltaTime) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT); 
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		batch.disableBlending();
		batch.begin();
		batch.draw(loadingBgRegion, 0, 0);
		batch.end();

		batch.enableBlending();
		batch.begin();
		batch.draw(loadingScrollRegion, 0, 200,
				loadingScrollRegion.getRegionWidth() - 480 * progressBar/100f,0,480 * progressBar/100f,
				loadingScrollRegion.getRegionHeight()
				,1,1,0);
//		loadingScrollRegion.setRegionX((int)(scrollWidth - 480 * progressBar/100f));
//		loadingScrollRegion.setRegionY(0);
//		loadingScrollRegion.setRegionWidth((int)(480 * progressBar/100f));
//		batch.draw(loadingScrollRegion,0,200);
		
		batch.end();
	}
	
	@Override
	public void update(float deltaTime) {
		loading();
	}
	
	abstract public void loadingAction(int progress);
	abstract public void afterLoad();
	
	private void loading(){
		loadingAction(progress);
		progressBar = (100 / progressNum) * progress;
		progress++;
		if(progressBar >=100){
			afterLoad();
			System.out.println("finish load");
		}
	}
	
	@Override
	public void dispose() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
