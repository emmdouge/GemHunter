package com.douge.gdx.game.assets;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;

public class AssetFireball 
{

	public final Animation movingAnimation;
	public final Animation hitAnimation;
	
	public AssetFireball(TextureAtlas atlas)
	{
		Array<AtlasRegion> regions = new Array<AtlasRegion>();
		
		for(int i = 0; i <= 7; i++)
		{
			regions.add(atlas.findRegion("fireball"+i));
		}
		movingAnimation = new Animation(1.0f / 15.0f, regions, Animation.PlayMode.LOOP);		
		
		regions = new Array<AtlasRegion>();
		
		for(int i = 8; i <= 11; i++)
		{
			regions.add(atlas.findRegion("fireball"+i));
		}
		hitAnimation = new Animation(1.0f / 15.0f, regions, Animation.PlayMode.NORMAL);		
	}
	
}
