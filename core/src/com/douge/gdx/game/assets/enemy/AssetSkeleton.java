package com.douge.gdx.game.assets.enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;

public class AssetSkeleton extends AssetEnemy
{
	public AssetSkeleton(TextureAtlas atlas) 
	{
		Array<AtlasRegion> regions = new Array<AtlasRegion>();
		
		for(int i = 0; i <= 20; i++)
		{
			regions.add(atlas.findRegion("skeleton"+i));
		}
		standingAnimation = new Animation(1.0f/10.0f, regions, Animation.PlayMode.LOOP);
		
		regions = new Array<AtlasRegion>();
		
		for(int i = 21; i <= 30; i++)
		{
			regions.add(atlas.findRegion("skeleton"+i));
		}
		movingAnimation = new Animation(1.0f/10.0f, regions, Animation.PlayMode.LOOP);
		
		regions = new Array<AtlasRegion>();
		
		for(int i = 11; i <= 20; i++)
		{
			regions.add(atlas.findRegion("skeleton"+i));
		}
		fallingAnimation = new Animation(1.0f/10.0f, regions, Animation.PlayMode.LOOP);
		
		regions = new Array<AtlasRegion>();
		
		for(int i = 31; i <= 40; i++)
		{
			regions.add(atlas.findRegion("skeleton"+i));
		}
		attackingAnimation = new Animation(1.0f/10.0f, regions, Animation.PlayMode.LOOP);
		
		regions = new Array<AtlasRegion>();
		
		for(int i = 41; i <= 49; i++)
		{
			regions.add(atlas.findRegion("skeleton"+i));
		}
		deadAnimation = new Animation(1.0f/10.0f, regions, Animation.PlayMode.NORMAL);
	}
}
