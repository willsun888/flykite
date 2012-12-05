package com.zsy.flykite;

import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;

public class TileMapHelp {
	public static final int MAP_HEIGHT = 250 * 32;
	public static final int MAP_WIDTH = 22 * 32;
	
	public static int[][][] getMapTileMap(TiledMap map){
		int[][][] tileMap = new int[map.layers.size()][][];
		for (int i = 0; i < map.layers.size(); i++) {
			tileMap[i] = map.layers.get(i).tiles;
		}
		return tileMap;
	}
	
	public static int[] getMapAllLayers(TiledMap map){
		int[][][] tileMap = new int[map.layers.size()][][];
		for (int i = 0; i < map.layers.size(); i++) {
			tileMap[i] = map.layers.get(i).tiles;
		}
		int[] allLayers = new int[tileMap.length];
		
		return allLayers;
	}
	
}
