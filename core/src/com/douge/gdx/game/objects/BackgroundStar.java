package com.douge.gdx.game.objects;

import com.douge.gdx.game.assets.Assets;

public class BackgroundStar extends BackgroundTile
{
	public BackgroundStar()
	{
		super();
		int randomStarTile = (int) (Math.random()*3);
		if(randomStarTile == 0)
		{
			regMiddle = Assets.instance.tiles.tileStar1;
		}
		else if(randomStarTile == 1)
		{
			regMiddle = Assets.instance.tiles.tileStar2;
		}
		else if(randomStarTile == 2)
		{
			regMiddle = Assets.instance.tiles.tileStar3;
		}
	}
}
