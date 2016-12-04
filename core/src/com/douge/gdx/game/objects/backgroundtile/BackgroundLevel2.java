package com.douge.gdx.game.objects.backgroundtile;

import com.douge.gdx.game.assets.Assets;

public class BackgroundLevel2 extends BackgroundTile
{
	public BackgroundLevel2()
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
