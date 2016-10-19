package com.douge.gdx.game;


/**
 * Direction object is turned towards
 * @author Emmanuel
 *
 */
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
	
	public static int getOppositeInt(VIEW_DIRECTION direction) 
	{
		if(direction == LEFT)
		{
			return 1;
		}
		else
		{
			return -1;
		}
	}
}
