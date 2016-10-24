package com.douge.gdx.game.enemy;

import com.douge.gdx.game.VIEW_DIRECTION;
import com.douge.gdx.game.assets.enemy.AssetGoblin;
import com.douge.gdx.game.assets.enemy.AssetSkeleton;

public class Goblin extends Enemy
{
	public Goblin(AssetGoblin assets) 
	{
		super(assets, 1f, VIEW_DIRECTION.LEFT);
	}
}
