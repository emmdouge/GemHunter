package com.douge.gdx.game.collectible;

import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.effect.NullEffect;

public class MagicGem extends Collectible
{
	public MagicGem()
	{
		super(Assets.instance.gems.magicGem, new NullEffect());
	}
}
