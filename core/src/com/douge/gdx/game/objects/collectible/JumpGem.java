package com.douge.gdx.game.objects.collectible;

import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.effect.JumpBoostEffect;

public class JumpGem extends Collectible
{
	public JumpGem()
	{
		super(Assets.instance.gems.jumpGem, new JumpBoostEffect());
	}
}
