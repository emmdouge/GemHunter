package com.douge.gdx.game.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.douge.gdx.game.assets.Assets;

public class GreenHeart extends AbstractGameObject
{
	private TextureRegion regFeather;
	
	public boolean collected;
	
	public GreenHeart() 
	{
		init();
	}
	private void init () 
	{
		dimension.set(0.5f, 0.5f);
		regFeather = Assets.instance.heart.heart;
	
		// Set bounding box for collision detection
		bounds.set(0, 0, dimension.x, dimension.y);
		collected = false;
	}
	
	public void render (SpriteBatch batch) 
	{
		batch.setColor(Color.GREEN);
		if (collected) 
		{
			return;
		}
		
		TextureRegion reg = regFeather;
		
		batch.draw(reg.getTexture(), 
				position.x, position.y,
				origin.x, origin.y, 
				dimension.x, dimension.y, 
				scale.x, scale.y,
				rotation, 
				reg.getRegionX(), reg.getRegionY(),
				reg.getRegionWidth(), reg.getRegionHeight(),
				false, false);
	}
	
	public int getScore() 
	{
		return 250;
	}
}