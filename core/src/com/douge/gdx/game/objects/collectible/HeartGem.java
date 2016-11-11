package com.douge.gdx.game.objects.collectible;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.effect.Effect;
import com.douge.gdx.game.effect.HealthBoostEffect;

public class HeartGem extends Collectible
{

	public HeartGem() 
	{
		super(Assets.instance.gems.heartGem, new HealthBoostEffect());
	}

}
