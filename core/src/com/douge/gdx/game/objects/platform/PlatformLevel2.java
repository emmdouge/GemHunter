package com.douge.gdx.game.objects.platform;

import com.douge.gdx.game.assets.Assets;

public class PlatformLevel2 extends Platform
{
	public PlatformLevel2()
	{
		super();
		regMiddle = Assets.instance.tiles.level2Ground;
	}
}
