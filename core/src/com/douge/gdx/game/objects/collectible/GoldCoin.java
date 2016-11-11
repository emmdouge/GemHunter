package com.douge.gdx.game.objects.collectible;

import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.effect.NullEffect;

public class GoldCoin extends Collectible
{
	public GoldCoin()
	{
		super(Assets.instance.goldCoin.spinning, new NullEffect());
	}
}
