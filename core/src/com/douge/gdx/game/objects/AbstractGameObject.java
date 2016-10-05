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
	
	public Vector2 velocity; 
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
		
	    velocity = new Vector2(); 
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
		position.x += velocity.x * deltaTime;
		position.y += velocity.y * deltaTime;
	}
	

	protected void updateMotionX (float deltaTime) 
	{
		if (velocity.x != 0) 
		{
			// Apply friction
			if (velocity.x > 0) 
			{
				velocity.x = Math.max(velocity.x - friction.x * deltaTime, 0);
			} 
			else 
			{
				velocity.x = Math.min(velocity.x + friction.x * deltaTime, 0);
			}
		}
		
		// Apply acceleration
		velocity.x += gravity.x * deltaTime;
		
		// Make sure the object's velocity does not exceed the
		// positive or negative terminal velocity
		velocity.x = MathUtils.clamp(velocity.x, -maxVelocity.x, maxVelocity.x);
	}
	
	protected void updateMotionY (float deltaTime) 
	{
		if (velocity.y != 0) 
		{
			// Apply friction
			if (velocity.y > 0) 
			{
				velocity.y = Math.max(velocity.y - friction.y * deltaTime, 0);
			} 
			else 
			{
				velocity.y = Math.min(velocity.y + friction.y * deltaTime, 0);
			}
		}
		
		// Apply acceleration
		velocity.y += gravity.y * deltaTime;
		
		// Make sure the object's velocity does not exceed the
		// positive or negative terminal velocity
		velocity.y = MathUtils.clamp(velocity.y, -maxVelocity.y, maxVelocity.y);
	}
	
	public abstract void render (SpriteBatch batch);
}