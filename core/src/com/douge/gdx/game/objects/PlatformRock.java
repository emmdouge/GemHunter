package com.douge.gdx.game.objects;

import com.douge.gdx.game.assets.Assets;

public class PlatformRock extends Platform
{
	public PlatformRock()
	{
		super();
		regMiddle = Assets.instance.tiles.tileRockTop;
	}
}
