package com.douge.gdx.game.objects.collectible;

import com.badlogic.gdx.graphics.Color;
import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.effect.NullEffect;

public class LevelGem extends Collectible
{
	public LevelGem()
	{
		super(Assets.instance.gems.levelGem, new NullEffect());
		dimension.set(1f, 1f);
		origin.x = dimension.x/2f;
		origin.y = dimension.x/2f;
	}
}
