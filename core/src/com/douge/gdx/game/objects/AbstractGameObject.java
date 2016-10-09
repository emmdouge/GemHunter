package com.douge.gdx.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class AbstractGameObject 
{
	public Vector2 position;
	public Vector2 dimension;
	public Vector2 origin;
	public Vector2 scale;
	public float rotation;
	
	public Vector2 currentVelocity; 
	public Vector2 maxVelocity; 
	public Vector2 friction; 
	public Vector2 gravity; 
	public Rectangle bounds; 
	
	public AbstractGameObject () 
	{
		position = new Vector2();
		dimension = new Vector2(1, 1);
		origin = new Vector2();
		scale = new Vector2(1, 1);
		rotation = 0;
		
	    currentVelocity = new Vector2(); 
	    maxVelocity = new Vector2(1, 1); 
	    friction = new Vector2(); 
	    gravity = new Vector2(); 
	    bounds = new Rectangle(); 
	}
	
	//objects that don't override this method won't update
	//objects that don't override this method won't update
	public void update (float deltaTime) 
	{
		updateMotionX(deltaTime);
		updateMotionY(deltaTime);
		
		// Move to new position
		position.x += currentVelocity.x * deltaTime;
		position.y += currentVelocity.y * deltaTime;
	}
	

	protected void updateMotionX (float deltaTime) 
	{
		if (currentVelocity.x != 0) 
		{
			// Apply friction
			if (currentVelocity.x > 0) 
			{
				currentVelocity.x = Math.max(currentVelocity.x - friction.x * deltaTime, 0);
			} 
			else 
			{
				currentVelocity.x = Math.min(currentVelocity.x + friction.x * deltaTime, 0);
			}
		}
		
		// Apply acceleration
		currentVelocity.x += gravity.x * deltaTime;
		
		// Make sure the object's velocity does not exceed the
		// positive or negative terminal velocity
		currentVelocity.x = MathUtils.clamp(currentVelocity.x, -maxVelocity.x, maxVelocity.x);
	}
	
	protected void updateMotionY (float deltaTime) 
	{
		if (currentVelocity.y != 0) 
		{
			// Apply friction
			if (currentVelocity.y > 0) 
			{
				currentVelocity.y = Math.max(currentVelocity.y - friction.y * deltaTime, 0);
			} 
			else 
			{
				currentVelocity.y = Math.min(currentVelocity.y + friction.y * deltaTime, 0);
			}
		}
		
		// Apply acceleration
		currentVelocity.y += gravity.y * deltaTime;
		
		// Make sure the object's velocity does not exceed the
		// positive or negative terminal velocity
		currentVelocity.y = MathUtils.clamp(currentVelocity.y, -maxVelocity.y, maxVelocity.y);
	}
	
	public abstract void render (SpriteBatch batch);
}