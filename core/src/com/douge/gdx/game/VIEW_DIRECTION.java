package com.douge.gdx.game;


public enum VIEW_DIRECTION 
{
	LEFT, 
	RIGHT;
	
	public static int getInt(VIEW_DIRECTION direction)
	{
		if(direction == LEFT)
		{
			return -1;
		}
		else
		{
			return 1;
		}
	}

	public static VIEW_DIRECTION opposite(VIEW_DIRECTION viewDirection) 
	{
		if(viewDirection == LEFT)
		{
			return RIGHT;
		}
		else
		{
			return LEFT;
		}
	}
}
