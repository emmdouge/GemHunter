package com.douge.gdx.game.objects.backgroundtile;

import com.douge.gdx.game.assets.Assets;

public class BackgroundLevel1 extends BackgroundTile
{
	public BackgroundLevel1()
	{
		super();
		regMiddle = Assets.instance.tiles.level1Sky;
	}
}
