package com.douge.gdx.game.objects.backgroundtile;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.objects.AbstractGameObject;

public abstract class BackgroundTile extends AbstractGameObject{
	protected TextureRegion regMiddle;
	
	private int length;
	
	public BackgroundTile () 
	{
		init();
	}
	
	private void init () 
	{
		dimension.set(1, 1);
		// Start length of this rock
		setLength(1);
	}
	
	public void setLength (int length) 
	{
		this.length = length;
	}
	public void increaseLength (int amount) 
	{
		setLength(length + amount);
	}

	@Override
	public void render(SpriteBatch batch) 
	{
		if(this instanceof BackgroundLevel5 || this instanceof BackgroundLevel7)
		{
			batch.setColor(Color.WHITE);
		}
		else
		{
			batch.setColor(Color.GRAY);
		}
		float relX = 0;
		float relY = 0;

		TextureRegion reg = regMiddle;
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
