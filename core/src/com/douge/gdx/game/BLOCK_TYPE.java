package com.douge.gdx.game;

/**
 * types of blocks in level by color
 * @author Emmanuel
 *
 */
public enum BLOCK_TYPE 
{
	ROCK_BACK(0, 0, 0), // black
	ROCK_PLATFORM(0, 255, 0), // green
	
	STAR_BACK(112, 146, 190),
	SNOW_PLATFORM(0, 255, 255),
	
	PLAYER_SPAWNPOINT(255, 255, 255), // white
	
	ITEM_JUMP_POTION(255, 0, 255), // purple
	ITEM_GOLD_COIN(255, 255, 0), // yellow
	
	ENEMY_SKELETON(195, 195, 195),
	ENEMY_BAT(128, 64, 0),
	ENEMY_SLIME(34, 177, 76),
	ENEMY_GOBLIN(237, 28, 36);
	
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
