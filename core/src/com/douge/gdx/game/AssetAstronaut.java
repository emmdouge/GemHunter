package com.douge.gdx.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetAstronaut 
{
	public AtlasRegion astronaut;
	
	public AssetAstronaut(TextureAtlas atlas)
	{
		astronaut = atlas.findRegion("0000");
	}
}
