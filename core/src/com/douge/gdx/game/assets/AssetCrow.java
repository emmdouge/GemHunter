package com.douge.gdx.game.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;

public class AssetCrow 
{
	public final Animation flying;
	public final ParticleEffect featherParticles;
	protected AssetCrow(TextureAtlas atlas)
	{
		featherParticles = new ParticleEffect();
		featherParticles.load(Gdx.files.internal("assets/particles/feather.pfx"), Gdx.files.internal("assets/particles"));
		
		Array<AtlasRegion> regions = new Array<AtlasRegion>();
		for(int i = 0; i <= 4; i++)
		{
			regions.add(atlas.findRegion("crow"+i));
		}
		flying = new Animation(1/15f, regions, Animation.PlayMode.LOOP);
	}
}
