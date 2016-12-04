package com.douge.gdx.game.objects.platform;

import com.douge.gdx.game.assets.Assets;

public class PlatformLevel1 extends Platform
{
	public PlatformLevel1()
	{
		super();
		regMiddle = Assets.instance.tiles.level1Ground;
	}
}
