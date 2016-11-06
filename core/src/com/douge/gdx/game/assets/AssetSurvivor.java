package com.douge.gdx.game.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
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
	public final ParticleEffect dustParticles;
	public final Animation jumpAttackAnimation;
	public final Animation groundedAttackAnimation;
	
	protected AssetSurvivor(TextureAtlas atlas) 
	{
		survivor = atlas.findRegion("survivor0");
		
		// Particles
		dustParticles = new ParticleEffect();
		dustParticles.load(Gdx.files.internal("../core/assets/particles/dust.pfx"), Gdx.files.internal("../core/assets/particles"));
		
		Array<AtlasRegion> regions = new Array<AtlasRegion>();
		
		regions.add(survivor);
		standingAnimation = new Animation(1.0f/10.0f, regions, Animation.PlayMode.LOOP);
		
		regions = new Array<AtlasRegion>();
		
		regions.add(atlas.findRegion("survivor6"));
		fallingAnimation = new Animation(1.0f/10.0f, regions, Animation.PlayMode.LOOP);
		
		regions = new Array<AtlasRegion>();
		
		regions.add(atlas.findRegion("survivor1"));
		jumpingAnimation = new Animation(1.0f/10.0f, regions, Animation.PlayMode.LOOP);
		
		regions = new Array<AtlasRegion>();
		
		for(int i = 19; i <= 25; i++)
		{
			regions.add(atlas.findRegion("survivor"+i));
		}
		runAnimation = new Animation(1.0f / 10.0f, regions, Animation.PlayMode.LOOP);
		
		regions = new Array<AtlasRegion>();
		
		regions.add(atlas.findRegion("survivor19"));
		dashingAnimation = new Animation(1.0f/10.0f, regions, Animation.PlayMode.LOOP);
		
		regions = new Array<AtlasRegion>();
		
		regions.add(atlas.findRegion("survivor11"));
		hurtAnimation = new Animation(1.0f/10.0f, regions, Animation.PlayMode.LOOP);
		
		regions = new Array<AtlasRegion>();
		
		for(int i = 2; i <= 5; i++)
		{
			regions.add(atlas.findRegion("survivor"+i));
		}
		groundedAttackAnimation = new Animation(1.0f / 10.0f, regions, Animation.PlayMode.NORMAL);
		
		regions = new Array<AtlasRegion>();
		for(int i = 8; i <= 10; i++)
		{
			regions.add(atlas.findRegion("survivor"+i));
		}
		jumpAttackAnimation = new Animation(1.0f / 10.0f, regions, Animation.PlayMode.NORMAL);
	}

}
