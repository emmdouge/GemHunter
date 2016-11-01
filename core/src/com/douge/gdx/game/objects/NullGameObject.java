package com.douge.gdx.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class NullGameObject extends AbstractGameObject
{
	public NullGameObject()
	{
	    bounds.set(0, 0, dimension.x, dimension.y); 
	}
	
	@Override
	public void render(SpriteBatch batch) 
	{
		//do nothing
	}

}
