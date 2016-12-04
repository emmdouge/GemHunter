package com.douge.gdx.game.objects.platform;

import com.douge.gdx.game.assets.Assets;

public class PlatformLevel3 extends Platform
{
	public PlatformLevel3()
	{
		super();
		int random = (int) (Math.random()*2);
		if(random == 0)
		{
			regMiddle = Assets.instance.tiles.level3Ground1;
		}
		else
		{
			regMiddle = Assets.instance.tiles.level3Ground2;
		}
	}
}
