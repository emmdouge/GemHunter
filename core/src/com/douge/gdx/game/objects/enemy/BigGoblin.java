package com.douge.gdx.game.objects.enemy;

import com.douge.gdx.game.VIEW_DIRECTION;
import com.douge.gdx.game.assets.enemy.AssetGoblin;

public class BigGoblin extends Enemy
{
	public BigGoblin(AssetGoblin assets) 
	{
		super(assets, 1f, VIEW_DIRECTION.LEFT);
		int multiplier = 4;
		isBoss = true;
		dimension.x *= multiplier;
		dimension.y *= multiplier;
		bounds.height = dimension.y - 1;
		bounds.width += (.25f * dimension.x);
		bounds.x += (.25f * dimension.x);
		origin.x = dimension.x/2;
		origin.y = dimension.y/2;
	}
}
