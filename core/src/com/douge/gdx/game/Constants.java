package com.douge.gdx.game;

public class Constants 
{
	//Visible world in meters
	public static final float VIEWPORT_WIDTH = 4.0f;
	public static final float VIEWPORT_HEIGHT = 4.0f;
	
	//GUI in pixels
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
	public static final int MAX_LIVES = 10;
	
	public static final String GEM_HUNTER_ATLAS_PATH = "images/gemhunter.atlas";
	
	public static final String TEXTURE_ATLAS_UI = "images/game-ui.atlas";
	public static final String TEXTURE_ATLAS_LIBGDX_UI = "images/uiskin.atlas";
	public static final String SKIN_LIBGDX_UI = "images/uiskin.json";
	public static final String SKIN_CANYONBUNNY_UI = "images/game-ui.json";
	
	public static final String PREFERENCES = "canyonbunny.prefs";
	public static final int FIREBALLS_START = 10;
}
