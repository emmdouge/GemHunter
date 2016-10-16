package com.douge.gdx.game.assets.enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;

public class AssetSlime extends AssetEnemy
{	
	public AssetSlime(TextureAtlas atlas) 
	{	
		Array<AtlasRegion> regions = new Array<AtlasRegion>();
		
		for(int i = 0; i <= 20; i++)
		{
			regions.add(atlas.findRegion(""+i));
		}
		standingAnimation = new Animation(1.0f/10.0f, regions, Animation.PlayMode.LOOP);
		
		regions = new Array<AtlasRegion>();
		
		for(int i = 21; i <= 30; i++)
		{
			regions.add(atlas.findRegion(""+i));
		}
		movingAnimation = new Animation(1.0f/10.0f, regions, Animation.PlayMode.LOOP);
		
		regions = new Array<AtlasRegion>();
		
		for(int i = 11; i <= 20; i++)
		{
			regions.add(atlas.findRegion(""+i));
		}
		fallingAnimation = new Animation(1.0f/10.0f, regions, Animation.PlayMode.LOOP);
//		
//		regions.add(reg);
//		standingAnimation = new Animation(1.0f/10.0f, regions, Animation.PlayMode.LOOP);
//		
//		regions = new Array<AtlasRegion>();
//		
//		regions.add(atlas.findRegion("6"));
//		fallingAnimation = new Animation(1.0f/10.0f, regions, Animation.PlayMode.LOOP);
//		
//		regions = new Array<AtlasRegion>();
//		
//		regions.add(atlas.findRegion("1"));
//		jumpingAnimation = new Animation(1.0f/10.0f, regions, Animation.PlayMode.LOOP);
//		
//		regions = new Array<AtlasRegion>();
//		
//		for(int i = 19; i <= 25; i++)
//		{
//			regions.add(atlas.findRegion(""+i));
//		}
//		moveAnimation = new Animation(1.0f / 10.0f, regions, Animation.PlayMode.LOOP);
//		
//		regions = new Array<AtlasRegion>();
	}
}
