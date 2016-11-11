package com.douge.gdx.game.objects.enemy;

import com.badlogic.gdx.utils.Array;
import com.douge.gdx.game.VIEW_DIRECTION;
import com.douge.gdx.game.assets.enemy.AssetSkeleton;

public class Skeleton extends Enemy
{

	public Skeleton(AssetSkeleton assets) 
	{
		super(assets, 1f, VIEW_DIRECTION.LEFT);
	}

}
