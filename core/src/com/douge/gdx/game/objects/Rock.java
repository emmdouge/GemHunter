package com.douge.gdx.game.objects;

import com.douge.gdx.game.assets.Assets;

public class Rock extends Platform
{
	public Rock()
	{
		super();
		regMiddle = Assets.instance.tiles.tileRockTop;
	}
}
