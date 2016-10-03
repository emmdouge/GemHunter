package com.douge.gdx.game;

public class Constants 
{
	//Visible world in pixels
	public static final float VIEWPORT_WIDTH = 5.0f;
	public static final float VIEWPORT_HEIGHT = 5.0f;
	
	// GUI in pixels
	public static final float VIEWPORT_GUI_WIDTH = 800.0f;
	public static final float VIEWPORT_GUI_HEIGHT = 480.0f;
	
	//MIN MAX of world
	public static final float CAMERA_X_MIN = 3.41f;
	public static final float CAMERA_X_MAX = 124.7f;
	public static final float CAMERA_Y_MIN = 1f;
	public static final float CAMERA_Y_MAX = 28f;
	
	// Delay after game over 
	public static final float TIME_DELAY_GAME_OVER = 3; 
	   
	public static final float ITEM_GREENHEART_POWERUP_DURATION = 9; 
	
	// Amount of extra lives at level start
	public static final int LIVES_START = 3;
	
	public static final String ATLAS_PATH = "../core/assets/images/mygame.atlas";
	public static final String CANYONBUNNY_PATH = "../core/assets/images/canyonbunny.atlas";
	public static final String ENVIRONMENT_ATLAS_PATH = "../core/assets/images/environment.atlas";
	public static final String ASTRONAUT_ATLAS_PATH = "../core/assets/images/astronaut.atlas";
	public static final String HEART_ATLAS_PATH = "../core/assets/images/heart.atlas";
	public static final String SURVIVOR_ATLAS_PATH = "../core/assets/images/survivor.atlas";
	public static final String COIN_ATLAS_PATH = "../core/assets/images/coin.atlas";
	public static final String TILE_ATLAS_PATH = "../core/assets/images/tiles.atlas";
	public static final String LEVEL_01_PATH = "../core/assets/levels/level_01.png";
}
