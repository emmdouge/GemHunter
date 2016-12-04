package com.douge.gdx.game.objects.platform;

import com.douge.gdx.game.assets.Assets;

public class PlatformLevel5 extends Platform
{
	public PlatformLevel5()
	{
		super();
		int random = (int) (Math.random()*2);
		if(random == 0)
		{
			regMiddle = Assets.instance.tiles.level5Ground1;
		}
		else
		{
			regMiddle = Assets.instance.tiles.level5Ground2;
		}
	}
}
