package com.zsy.flykite;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObject;
import com.zsy.flykite.actor.Bird;
import com.zsy.flykite.actor.DynamicObject;
import com.zsy.flykite.actor.Enemy;
import com.zsy.flykite.actor.Fish;
import com.zsy.flykite.actor.Food;
import com.zsy.flykite.actor.KongMing;
import com.zsy.flykite.actor.Plane;
import com.zsy.flykite.actor.Satellite;
import com.zsy.flykite.actor.UFO;
import com.zsy.flykite.actor.Wind;
import com.zsy.flykite.temp.TileMapRenderer;

public class MapsUtils {
	
	public static final int TILE_WIDTH = 32;
	public static final int TILE_HEIGHT = 32;  
	
	public static final int MAP_TILE_HEIGHT = 250;
	public static final int MAP_TILE_WIDTH = 22;
	
	public static final int MAP_HEIGHT = 250 * 32;
	public static final int MAP_WIDTH = 22 * 32;

	//用于存放各关的TiledMap
	
	public static ArrayList<Food> foodList = new ArrayList<Food>();
	public static ArrayList<Enemy> enemyList = new ArrayList<Enemy>(); 
	public static ArrayList<DynamicObject> dynamicObjList = new ArrayList<DynamicObject>();
	public static ArrayList<Wind> windList = new ArrayList<Wind>();
	public static TiledMap currMap;
	public static TileMapRenderer currMapRenderer;
	
	public static TiledMap[] tiledMaps = new TiledMap[9];
	public static TileMapRenderer[] tileMapRenderers = new TileMapRenderer[9];
	
	public static void load(String mapTheme,int level,int count){
		int m =GameUtils.getMapTheme(mapTheme);
		switch (count) {
		case 1:
			if(tiledMaps[3*m+level-1]!=null){
				currMap = tiledMaps[3*m+level-1];
			}else{
				currMap = getTiledMap(mapTheme, level);
				tiledMaps[3*m+level-1] = currMap;
			}
			break;
		case 2:
			currMapRenderer = getTileMapRenderer(currMap,mapTheme);
			break;
		case 3:
			foodList = MapsUtils.getFoodList(currMap);
			enemyList = MapsUtils.getEnemyList(currMap);
			dynamicObjList = MapsUtils.getDynamicObjList(currMap);
			windList = MapsUtils.getWindList(currMap);
			break;
		}
	}
	
	public static TiledMap getTiledMap(String mapTheme,int level){
		TiledMap map = null;
		String path = "data/map/" + mapTheme + "/"+mapTheme+level+".tmx";
		FileHandle mapHandle = Gdx.files.internal(path); 
        map = TiledLoader.createMap(mapHandle); 
        return map;
	}
	
	public static TileMapRenderer getTileMapRenderer(TiledMap map,String mapTheme,int level){
		String path = "data/map/" + mapTheme + "/" + level;
        return new TileMapRenderer(map, new TileAtlas(map,Gdx.files.internal(path)), 5, 5); 
	}
	
	public static TileMapRenderer getTileMapRenderer(TiledMap map,String mapTheme){
		String path = "data/map/" + mapTheme;
        return new TileMapRenderer(map, new TileAtlas(map,Gdx.files.internal(path)), 5, 5); 
	}
	
	public static int[][][] getTileMap(TiledMap map){
		int layers = map.layers.size();
		int[][][] tileMap = new int[layers][][];
		for(int i = 0;i<layers;i++){
			tileMap[i] = map.layers.get(i).tiles;
		}
		return tileMap;
	}
	
	public static int[][] getActorTileMap(TiledMap map){
		int j,k;
    	int[][] actorTileMap = new int[MAP_TILE_HEIGHT][MAP_TILE_WIDTH];
		for(j=0;j<MAP_TILE_HEIGHT;j++){
			for(k=0;k<MAP_TILE_WIDTH;k++){
				actorTileMap[j][k] = map.layers.get(2).tiles[j][k];
			}
		}
		return actorTileMap;
	}
	
	public static int[][] getCollisionTileMap(TiledMap map){
		int j,k;
    	int[][] collisionTileMap = new int[MAP_TILE_HEIGHT][MAP_TILE_WIDTH];
		for(j=0;j<MAP_TILE_HEIGHT;j++){
			for(k=0;k<MAP_TILE_WIDTH;k++){
				collisionTileMap[j][k] = map.layers.get(0).tiles[j][k];
			}
		}
		return collisionTileMap;
	}
	
	public static ArrayList<Food> getFoodList(TiledMap map){
		//提取food

		ArrayList<Food> foodList = new ArrayList<Food>();
		ArrayList<TiledObject> tiledObjects = map.objectGroups.get(1).objects;
    	for (TiledObject object : tiledObjects) {
    		if(object != null){
				if(("star").equals(object.name)){
					foodList.add(new Food(GameContant.ACTOR_STAR, object.x,MAP_HEIGHT-object.y-object.height, object.width,object.height));
				}
				else if(("heart").equals(object.name)){
					foodList.add(new Food(GameContant.ACTOR_HEART, object.x,MAP_HEIGHT-object.y-object.height, object.width,object.height));
				}
				else if(("kite-1").equals(object.name)){
					foodList.add(new Food(GameContant.ACTOR_KITE_1, object.x,MAP_HEIGHT-object.y-object.height, object.width,object.height));
				}
				else if(("kite-2").equals(object.name)){
					foodList.add(new Food(GameContant.ACTOR_KITE_2, object.x,MAP_HEIGHT-object.y-object.height, object.width,object.height));
				}
				else if(("kite-3").equals(object.name)){
					foodList.add(new Food(GameContant.ACTOR_KITE_3, object.x,MAP_HEIGHT-object.y-object.height, object.width,object.height));
				}
				else if(("kite-4").equals(object.name)){
					foodList.add(new Food(GameContant.ACTOR_KITE_4, object.x,MAP_HEIGHT-object.y-object.height, object.width,object.height));
				}
    		}
		}
    	return foodList;
	}
	
	public static ArrayList<Enemy> getEnemyList(TiledMap map){
		//提取enemy
		ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
		ArrayList<TiledObject> tiledObjects = map.objectGroups.get(0).objects;
    	for (TiledObject object : tiledObjects) {
    		if(object != null && object.name.equals("obstacle")){
				Enemy enemy = new Enemy(GameContant.ACTOR_COLLISION, 
				object.x,MAP_HEIGHT-object.y-object.height, object.width,object.height);
				enemyList.add(enemy);
				System.out.println("enemy: " + enemy.x+" " +enemy.y+" "+enemy.width+" " +enemy.height);
    		}
		}
    	return enemyList;
	}
	
	public static ArrayList<DynamicObject> getDynamicObjList(TiledMap map){
		ArrayList<DynamicObject> dynamicObjList = new ArrayList<DynamicObject>();
		ArrayList<TiledObject> tiledObjects = map.objectGroups.get(2).objects;
		System.out.println("getDynamicObjectList");
    	for (TiledObject object : tiledObjects) {
    		if("plane".equals(object.name)){
    			Plane plane = null;
    			if(isLeftObject(object.x)){
    				plane = new Plane(object.x,MAP_HEIGHT-object.y-object.height, Assets.PLANE_WIDTH,0,2,0,Assets.plane2Anim);
    			}else{
    				plane = new Plane(object.x,MAP_HEIGHT-object.y-object.height, Assets.PLANE_WIDTH,0,2,0,Assets.planeAnim);
    			}
				dynamicObjList.add(plane);
    		}else if("bird".equals(object.name)){
    			Bird bird = null;
    			if(isLeftObject(object.x)){
    				bird = new Bird(object.x,MAP_HEIGHT-object.y-object.height, Assets.BIRD_WIDTH,0,2,0,Assets.birdAnim);
    			}else{
    				bird = new Bird(object.x,MAP_HEIGHT-object.y-object.height, Assets.BIRD_WIDTH,0,2,0,Assets.bird2Anim);
    			}
				dynamicObjList.add(bird);
    		}else if("kongming".equals(object.name)){
    			KongMing kongming = new KongMing(object.x,MAP_HEIGHT-object.y-object.height, Assets.KONGMING_WIDTH,0,2,0,Assets.kongmingAnim);
				dynamicObjList.add(kongming);
    		}else if("ufo".equals(object.name)){
    			UFO ufo = new UFO(object.x,MAP_HEIGHT-object.y-object.height, Assets.UFO_WIDTH,0,2,0,Assets.ufoAnim);
				dynamicObjList.add(ufo);
    		}else if("satellite".equals(object.name)){
    			Satellite sa = new Satellite(object.x,MAP_HEIGHT-object.y-object.height, Assets.SATELLITE_WIDTH,0,2,0,Assets.satelliteAnim);
				dynamicObjList.add(sa);
    		}else if("fish".equals(object.name)){
    			Fish fish = null;
    			if(isLeftObject(object.x)){
    				fish= new Fish(object.x,MAP_HEIGHT-object.y-object.height, Assets.FISH2_WIDTH,0,2,0,Assets.fish2Anim);
    			}else{
    				fish= new Fish(object.x,MAP_HEIGHT-object.y-object.height, Assets.FISH1_WIDTH,0,2,0,Assets.fish1Anim);
    			}
				dynamicObjList.add(fish);
    		}
    	}
		return dynamicObjList;
	}
	
	public static ArrayList<Wind> getWindList(TiledMap map){
		ArrayList<Wind> windList = new ArrayList<Wind>();
		ArrayList<TiledObject> tiledObjects = map.objectGroups.get(0).objects;
		int startpoint = 0;
		int endpoint = 0;
		int direction = 0;
		int row;
		int num = (tiledObjects.size()-enemyList.size())/2;
		int windarray[][] = new int[num][3];
		for (TiledObject object : tiledObjects) {
			if(object.name.contains("Wind")){
				row = Integer.parseInt(object.name.substring(object.name.length()-1, object.name.length()));
				if(object.name.contains("Start")){
					startpoint = MAP_HEIGHT-object.y-object.height;
					
					windarray[row][0] = startpoint;
				}
				if(object.name.contains("End")){
					endpoint = MAP_HEIGHT-object.y-object.height;
					if(object.name.contains("right")){
						direction = GameContant.WIND_RIGHT;
						windarray[row][1] = endpoint;
						windarray[row][2] = direction;
					}
					else if(object.name.contains("left")){
						direction = GameContant.WIND_LEFT;
						windarray[row][1] = endpoint;
						windarray[row][2] = direction;
					}

				}
				
			}		
    		
		}
		for(int i=0;i<num;i++){
			Wind wind = new Wind(windarray[i][0],windarray[i][1],windarray[i][2]);
			windList.add(wind);
			System.out.println("wind: " + startpoint+" " +endpoint+" "+direction);
		}
		return windList;
	}
	
	public static void printTileMap(TiledMap map){
		String string = "";
		int i = 0;
		int j = 0;
		for ( i = 0; i < map.height; i++) {
			for( j = 0; j < map.width; j++){
				string += map.layers.get(0).tiles[i][j] + " ";
			}
			string += "\n";
		}
		System.out.println(string);
	}
	
	private static boolean isLeftObject(float x){
		if(x < MAP_WIDTH/2){
			return true;
		}
		return false;
	}
	
}
