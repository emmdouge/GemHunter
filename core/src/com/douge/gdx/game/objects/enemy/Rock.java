package com.douge.gdx.game.objects.enemy;

import com.douge.gdx.game.assets.enemy.AssetHorse;
import com.douge.gdx.game.assets.enemy.AssetRock;
import com.douge.gdx.game.objects.VIEW_DIRECTION;

public class Rock extends Enemy
{
	public Rock(AssetRock rock) 
	{
		super(rock, 0.5f, VIEW_DIRECTION.RIGHT);
		this.dimension.x = 2f;
		this.dimension.y = 1.5f;
		this.isHorse = true;
		dropsHealth = true;
	}
}
