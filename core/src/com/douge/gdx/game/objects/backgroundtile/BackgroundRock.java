package com.douge.gdx.game.objects.backgroundtile;

import com.douge.gdx.game.assets.Assets;

public class BackgroundRock extends BackgroundTile
{
	public BackgroundRock()
	{
		super();
		regMiddle = Assets.instance.tiles.tileRockBottom;
	}
}
