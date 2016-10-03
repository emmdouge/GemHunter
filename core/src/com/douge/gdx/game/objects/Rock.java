package com.douge.gdx.game.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.douge.gdx.game.assets.Assets;

public class Rock extends AbstractGameObject
{
	private TextureRegion regMiddle;
	
	private int length;
	
	public Rock () 
	{
		init();
	}
	
	private void init () 
	{
		dimension.set(1, 1);
		regMiddle = Assets.instance.tiles.tileTop;
		// Start length of this rock
		setLength(1);
	}
	
	public void setLength (int length) 
	{
		this.length = length;
		
	    // Update bounding box for collision detection 
	    bounds.set(0, 0, dimension.x * length, dimension.y); 
	}
	public void increaseLength (int amount) 
	{
		setLength(length + amount);
	}

	@Override
	public void render(SpriteBatch batch) 
	{
		batch.setColor(Color.GRAY);
		
		TextureRegion reg = null;
		float relX = 0;
		float relY = 0;
		
		// Draw middle
		relX = 0;
		reg = regMiddle;
		for (int i = 0; i < length; i++) 
		{
			batch.draw(reg.getTexture(), 
					position.x + relX, position.y + relY, 
					origin.x, origin.y, 
					dimension.x, dimension.y,
					scale.x, scale.y, 
					rotation, 
					reg.getRegionX(), reg.getRegionY(),
					reg.getRegionWidth(), reg.getRegionHeight(), 
					false, false);
			
			relX += dimension.x;
		}
	}

}
