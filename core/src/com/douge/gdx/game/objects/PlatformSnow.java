package com.douge.gdx.game.objects;

import com.douge.gdx.game.assets.Assets;

public class PlatformSnow extends Platform
{
	public PlatformSnow()
	{
		super();
		int randomStarTile = (int) (Math.random()*2);
		if(randomStarTile == 0)
		{
			regMiddle = Assets.instance.tiles.snow1;
		}
		else if(randomStarTile == 1)
		{
			regMiddle = Assets.instance.tiles.snow2;
		}
	}
}
