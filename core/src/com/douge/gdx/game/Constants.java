package com.douge.gdx.game;

public class Constants 
{
	//Visible world in meters
	public static final float VIEWPORT_WIDTH = 5.0f;
	public static final float VIEWPORT_HEIGHT = 5.0f;
	
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
	
	public static final String ATLAS_PATH = "../core/assets/images/mygame.atlas";
	public static final String CANYONBUNNY_PATH = "../core/assets/images/canyonbunny.atlas";
	public static final String GEM_ATLAS_PATH = "../core/assets/images/gems.atlas";
	public static final String ENVIRONMENT_ATLAS_PATH = "../core/assets/images/environment.atlas";
	public static final String ASTRONAUT_ATLAS_PATH = "../core/assets/images/astronaut.atlas";
	public static final String HEART_ATLAS_PATH = "../core/assets/images/heart.atlas";
	public static final String SURVIVOR_ATLAS_PATH = "../core/assets/images/survivor.atlas";
	public static final String COIN_ATLAS_PATH = "../core/assets/images/coin.atlas";
	public static final String TILE_ATLAS_PATH = "../core/assets/images/tiles.atlas";
	public static final String SLIME_ATLAS_PATH = "../core/assets/images/slime.atlas";
	public static final String BAT_ATLAS_PATH = "../core/assets/images/bat.atlas";
	public static final String SKELETON_ATLAS_PATH = "../core/assets/images/skeleton.atlas";
	public static final String RANGER_ATLAS_PATH = "../core/assets/images/ranger.atlas";
	public static final String GOBLIN_ATLAS_PATH = "../core/assets/images/goblin.atlas";
	
	public static final String TEXTURE_ATLAS_UI = "../core/assets/images/game-ui.atlas";
	public static final String TEXTURE_ATLAS_LIBGDX_UI = "../core/assets/images/uiskin.atlas";
	public static final String SKIN_LIBGDX_UI = "../core/assets/images/uiskin.json";
	public static final String SKIN_CANYONBUNNY_UI = "../core/assets/images/game-ui.json";
	
	public static final String PREFERENCES = "canyonbunny.prefs";
	public static final int FIREBALLS_START = 10;
}
