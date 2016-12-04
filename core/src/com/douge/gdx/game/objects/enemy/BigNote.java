package com.douge.gdx.game.objects.enemy;

import com.douge.gdx.game.assets.enemy.AssetNote;
import com.douge.gdx.game.objects.VIEW_DIRECTION;

public class BigNote extends Enemy
{
	public BigNote(AssetNote assets) 
	{
		super(assets, 1f, VIEW_DIRECTION.RIGHT);
		int multiplier = 1;
		dimension.x *= multiplier;
		dimension.y *= multiplier;
		bounds.height = dimension.y - 1;
		bounds.width += (.25f * dimension.x);
		bounds.x += (.25f * dimension.x);
		origin.x = dimension.x/2;
		origin.y = dimension.y/2;
	}
}
