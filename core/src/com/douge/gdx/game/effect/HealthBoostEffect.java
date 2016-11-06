package com.douge.gdx.game.effect;

import com.douge.gdx.game.objects.Player;

public class HealthBoostEffect implements Effect
{

	@Override
	public void activate(Player player) 
	{
		player.numLives++;
	}

}
