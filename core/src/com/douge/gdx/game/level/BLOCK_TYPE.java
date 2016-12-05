package com.douge.gdx.game.level;

/**
 * types of blocks in level by color
 * @author Emmanuel
 *
 */
public enum BLOCK_TYPE 
{
	LEVEL1_BACK(0, 0, 0), // black
	LEVEL1_PLATFORM(0, 255, 0), // green
	
	LEVEL2_BACK(112, 146, 190),
	LEVEL2_PLATFORM(0, 255, 255),

	LEVEL3_BACK(200, 200, 200),
	LEVEL3_PLATFORM(127, 127, 127),
	
	LEVEL4_BACK(250, 148, 186),
	LEVEL4_PLATFORM(244, 30, 111),
	
	LEVEL5_BACK(255, 201, 14),
	LEVEL5_PLATFORM(155, 120, 0), 
	
	LEVEL6_BACK(51, 24, 63),
	LEVEL6_PLATFORM(104, 49, 128),
	
	LEVEL7_BACK(64, 128, 128),
	LEVEL7_PLATFORM(44, 88, 88),
	
	REVERSE_PLATFORM_VELOCITY(200, 191, 231),
	X_MOVING_PLATFORM(163, 73, 164), 
	Y_MOVING_PLATFORM(99, 44, 99), 
	
	PLAYER_SPAWNPOINT(255, 255, 255), // white
	WIZARD(44, 21, 93),
	
	ITEM_JUMP_GEM(255, 0, 255), // purple
	ITEM_GOLD_COIN(255, 255, 0), // yellow
	ITEM_LEVEL_GEM(255, 127, 39),
	
	ENEMY_MOUSE(195, 195, 195),
	ENEMY_BAT(128, 64, 0),
	ENEMY_HORSE(34, 177, 76),
	ENEMY_ROCK(87, 68, 0),
	ENEMY_SHADOW(133, 63, 163),
	ENEMY_BIGNOTE(0, 64, 128);
	
	private int color;
	
	private BLOCK_TYPE (int r, int g, int b) 
	{
		color = r << 24 | g << 16 | b << 8 | 0xff;
	}
	
	public boolean sameColor(int color) 
	{
		return this.color == color;
	}
		
	public int getColor () 
	{
		return color;
	}
	public static boolean validColor(int currentPixel) 
	{
		for (BLOCK_TYPE blockType : BLOCK_TYPE.values())
		{
			if(currentPixel == blockType.color)
			{
				return true;
			}
		}
		return false;
	}
}
