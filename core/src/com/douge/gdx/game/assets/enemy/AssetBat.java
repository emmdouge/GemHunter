package com.douge.gdx.game.assets.enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;

public class AssetBat extends AssetEnemy
{
	public AssetBat(TextureAtlas atlas) 
	{
		Array<AtlasRegion> regions = new Array<AtlasRegion>();
		
		for(int i = 50; i <= 70; i++)
		{
			regions.add(atlas.findRegion("bat"+i));
		}
		standingAnimation = new Animation(1.0f/10.0f, regions, Animation.PlayMode.LOOP);
		
		regions = new Array<AtlasRegion>();
		
		for(int i = 50; i <= 59; i++)
		{
			regions.add(atlas.findRegion("bat"+i));
		}
		movingAnimation = new Animation(1.0f/10.0f, regions, Animation.PlayMode.LOOP);
		
		regions = new Array<AtlasRegion>();
		
		for(int i = 61; i <= 70; i++)
		{
			regions.add(atlas.findRegion("bat"+i));
		}
		fallingAnimation = new Animation(1.0f/10.0f, regions, Animation.PlayMode.LOOP);
		
		regions = new Array<AtlasRegion>();
		
		for(int i = 80; i <= 89; i++)
		{
			regions.add(atlas.findRegion("bat"+i));
		}
		attackingAnimation = new Animation(1.0f/10.0f, regions, Animation.PlayMode.LOOP);
		
		regions = new Array<AtlasRegion>();
		
		for(int i = 91; i <= 99; i++)
		{
			regions.add(atlas.findRegion("bat"+i));
		}
		deadAnimation = new Animation(1.0f/10.0f, regions, Animation.PlayMode.NORMAL);
	}

}
