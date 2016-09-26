package com.douge.gdx.game.assets;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetAstronaut 
{
	public AtlasRegion astronaut;
	
	protected AssetAstronaut(TextureAtlas atlas)
	{
		astronaut = atlas.findRegion("0000");
	}
}
