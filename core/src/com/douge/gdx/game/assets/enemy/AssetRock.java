package com.douge.gdx.game.assets.enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;

public class AssetRock extends AssetEnemy
{
	public AssetRock(TextureAtlas atlas) 
	{	
		Array<AtlasRegion> regions = new Array<AtlasRegion>();
		
		for(int i = 0; i <= 5; i++)
		{
			regions.add(atlas.findRegion("rock"+i));
		}
		standingAnimation = new Animation(1.0f/10.0f, regions, Animation.PlayMode.LOOP);
		
		movingAnimation = standingAnimation;
		
		fallingAnimation = standingAnimation;
		
		attackingAnimation = standingAnimation;
		
		deadAnimation = standingAnimation;
	}
}