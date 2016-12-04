package com.douge.gdx.game.objects.enemy;

import com.douge.gdx.game.assets.enemy.AssetMouse;
import com.douge.gdx.game.assets.enemy.AssetShadow;
import com.douge.gdx.game.objects.VIEW_DIRECTION;

public class Shadow extends Enemy
{
	public Shadow(AssetShadow assets) 
	{
		super(assets, 1f, VIEW_DIRECTION.LEFT);
	}
}
