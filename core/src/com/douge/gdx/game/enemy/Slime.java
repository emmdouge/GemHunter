package com.douge.gdx.game.enemy;

import com.badlogic.gdx.utils.Array;
import com.douge.gdx.game.VIEW_DIRECTION;
import com.douge.gdx.game.assets.enemy.AssetSlime;

public class Slime extends Enemy
{

	public Slime(AssetSlime assets, Array<Enemy> enemies) 
	{
		super(assets, enemies, 0.5f, VIEW_DIRECTION.RIGHT);
	}
	
}
