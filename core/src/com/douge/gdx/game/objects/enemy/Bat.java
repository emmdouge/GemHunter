package com.douge.gdx.game.objects.enemy;

import com.douge.gdx.game.assets.enemy.AssetBat;
import com.douge.gdx.game.objects.VIEW_DIRECTION;

public class Bat extends Enemy
{
	private final float flightHeight = 1;
	
	public Bat(AssetBat assets) 
	{
		super(assets, 1f, VIEW_DIRECTION.RIGHT);
		canFly = true;
		gravity = 0f;
		currentGravity = 0f;
		currentVelocity.x = 1f;
		position.y += flightHeight;
	}
}
