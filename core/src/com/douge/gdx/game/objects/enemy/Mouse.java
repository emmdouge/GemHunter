package com.douge.gdx.game.objects.enemy;

import com.badlogic.gdx.utils.Array;
import com.douge.gdx.game.assets.enemy.AssetMouse;
import com.douge.gdx.game.objects.VIEW_DIRECTION;

public class Mouse extends Enemy
{

	public Mouse(AssetMouse assets) 
	{
		super(assets, 1f, VIEW_DIRECTION.LEFT);
	}

}
