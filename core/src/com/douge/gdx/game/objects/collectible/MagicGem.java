package com.douge.gdx.game.objects.collectible;

import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.effect.NullEffect;

public class MagicGem extends Collectible
{
	public MagicGem()
	{
		super(Assets.instance.gems.magicGem, new NullEffect());
	}
}
