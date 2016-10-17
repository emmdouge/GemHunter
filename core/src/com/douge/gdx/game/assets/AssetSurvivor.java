package com.douge.gdx.game.assets;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;

public class AssetSurvivor
{

	public final AtlasRegion survivor;
	public final Animation runAnimation;
	public final Animation standingAnimation;
	public final Animation fallingAnimation;
	public final Animation jumpingAnimation;
	public final Animation dashingAnimation;
	public final Animation hurtAnimation;
	
	protected AssetSurvivor(TextureAtlas atlas) 
	{
		survivor = atlas.findRegion("0");
		
		Array<AtlasRegion> regions = new Array<AtlasRegion>();
		
		regions.add(survivor);
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
		runAnimation = new Animation(1.0f / 10.0f, regions, Animation.PlayMode.LOOP);
		
		regions = new Array<AtlasRegion>();
		
		regions.add(atlas.findRegion("19"));
		dashingAnimation = new Animation(1.0f/10.0f, regions, Animation.PlayMode.LOOP);
		
		regions = new Array<AtlasRegion>();
		
		regions.add(atlas.findRegion("11"));
		hurtAnimation = new Animation(1.0f/10.0f, regions, Animation.PlayMode.LOOP);
	}

}
