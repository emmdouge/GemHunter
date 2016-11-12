package com.douge.gdx.game.objects.enemy;

import com.badlogic.gdx.utils.Array;
import com.douge.gdx.game.assets.enemy.AssetSlime;
import com.douge.gdx.game.objects.VIEW_DIRECTION;

public class Slime extends Enemy
{

	public Slime(AssetSlime assets) 
	{
		super(assets, 0.5f, VIEW_DIRECTION.RIGHT);
		dropsHealth = true;
	}
	
}
