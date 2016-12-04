package com.douge.gdx.game.objects.enemy;

import com.badlogic.gdx.utils.Array;
import com.douge.gdx.game.assets.enemy.AssetHorse;
import com.douge.gdx.game.objects.VIEW_DIRECTION;

public class Horse extends Enemy
{

	public Horse(AssetHorse assets) 
	{
		super(assets, 0.5f, VIEW_DIRECTION.RIGHT);
		this.dimension.x = 2f;
		this.dimension.y = 1.5f;
		this.isHorse = true;
		dropsHealth = true;
	}
	
}
