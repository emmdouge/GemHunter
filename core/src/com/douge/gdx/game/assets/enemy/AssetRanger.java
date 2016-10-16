package com.douge.gdx.game.assets.enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;

public class AssetRanger 
{
	public final AtlasRegion reg;
	public final Animation moveAnimation;
	public final Animation standingAnimation;
	public final Animation fallingAnimation;
	public final Animation jumpingAnimation;
	
	public AssetRanger(TextureAtlas atlas) 
	{
		reg = atlas.findRegion("0");
		
		Array<AtlasRegion> regions = new Array<AtlasRegion>();
		
		regions.add(reg);
		standingAnimation = new Animation(1.0f/10.0f, regions, Animation.PlayMode.LOOP);
		
		regions = new Array<AtlasRegion>();
		
		regions.add(atlas.findRegion("6"));
		fallingAnimation = new Animation(1.0f/10.0f, regions, Animation.PlayMode.LOOP);
		
		regions = new Array<AtlasRegion>();
		
		regions.add(atlas.findRegion("1"));
		jumpingAnimation = new Animation(1.0f/10.0f, regions, Animation.PlayMode.LOOP);
		
		regions = new Array<AtlasRegion>();
		
		for(int i = 19; i <= 25; i++)
		{
			regions.add(atlas.findRegion(""+i));
		}
		moveAnimation = new Animation(1.0f / 10.0f, regions, Animation.PlayMode.LOOP);
		
		regions = new Array<AtlasRegion>();
	}
}
