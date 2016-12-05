package com.douge.gdx.game.objects.collectible;

import com.badlogic.gdx.graphics.Color;
import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.effect.NullEffect;
import com.douge.gdx.game.level.Level;

public class LevelGem extends Collectible
{
	public LevelGem(Level currentLevel)
	{
		super(currentLevel.goal, new NullEffect());
		dimension.set(1f, 1f);
		origin.x = dimension.x/2f;
		origin.y = dimension.x/2f;
	}
}
