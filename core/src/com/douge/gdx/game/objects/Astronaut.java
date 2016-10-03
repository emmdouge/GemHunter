package com.douge.gdx.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.douge.gdx.game.Constants;
import com.douge.gdx.game.assets.Assets;

public class Astronaut extends AbstractGameObject
{
	public static final String TAG = Astronaut.class.getName();
	private final float JUMP_TIME_MAX = 0.3f;
	private final float JUMP_TIME_MIN = 0.1f;
	private final float JUMP_TIME_OFFSET_FLYING = JUMP_TIME_MAX - 0.018f;
	
	public enum VIEW_DIRECTION 
	{ 
		LEFT, 
		RIGHT 
	}
	
	public enum JUMP_STATE 
	{
		GROUNDED, 
		FALLING, 
		JUMP_RISING, 
		JUMP_FALLING
	}
	
	private TextureRegion regAstronaut;
	public VIEW_DIRECTION viewDirection;
	public float timeJumping;
	public JUMP_STATE jumpState;
	public boolean hasGreenHeartPowerup;
	public float timeLeftGreenHeartPowerup;
	
	public Astronaut() 
	{
		init();
	}
	public void init() 
	{
		dimension.set(1, 1);
		regAstronaut = Assets.instance.astronaut.astronaut;
		
		// Center image on game object
		origin.set(dimension.x / 2, dimension.y / 2);
		
		// Bounding box for collision detection
		bounds.set(0, 0, dimension.x, dimension.y);
		
		// Set physics values
		terminalVelocity.set(3.0f, 4.0f);
		friction.set(12.0f, 0.0f);
		acceleration.set(0.0f, -25.0f);
		
		// View direction
		viewDirection = VIEW_DIRECTION.RIGHT;
		
		// Jump state
		jumpState = JUMP_STATE.FALLING;
		timeJumping = 0;
		
		// Power-ups
		hasGreenHeartPowerup = false;
		timeLeftGreenHeartPowerup = 0;		
	};
	
	public void update(float deltaTime)
	{
		//calls updateMotionX and updateMotionY and moves them
		super.update(deltaTime);
		
		if (velocity.x != 0) 
		{
			viewDirection = velocity.x < 0 ? VIEW_DIRECTION.LEFT : VIEW_DIRECTION.RIGHT;
		}
		if (timeLeftGreenHeartPowerup > 0) 
		{
			timeLeftGreenHeartPowerup -= deltaTime;
		}
		if (timeLeftGreenHeartPowerup < 0) 
		{
			// disable power-up
			timeLeftGreenHeartPowerup = 0;
			setGreenHeartPowerup(false);
		}
	}
	
	public void updateMotionX(float deltaTime)
	{
		switch (jumpState) 
		{
			case GROUNDED:
				jumpState = JUMP_STATE.FALLING;
			break;
			case JUMP_RISING:
				// Keep track of jump time
				timeJumping += deltaTime;
		
				// Jump time left?
				if (timeJumping <= JUMP_TIME_MAX) 
				{
					// Still jumping
					velocity.y = terminalVelocity.y;
				}
			break;
			case FALLING:

			case JUMP_FALLING:
				// Add delta times to track jump time
				timeJumping += deltaTime;
		
				// Jump to minimal height if jump key was pressed too short
				if (timeJumping > 0 && timeJumping <= JUMP_TIME_MIN) 
				{
					// Still jumping
					velocity.y = terminalVelocity.y;
				}
		}
		
		if (jumpState != JUMP_STATE.GROUNDED)
		{
			super.updateMotionY(deltaTime);		
		}
	}
	
	public void render(SpriteBatch batch)
	{
		TextureRegion reg = regAstronaut;
		
		// Set special color when game object has a feather power-up
		if (hasGreenHeartPowerup) 
		{
			batch.setColor(1.0f, 0.8f, 0.0f, 1.0f);
		}
		
		// Draw image
		batch.draw(reg.getTexture(), 
				position.x, position.y, 
				origin.x, origin.y, 
				dimension.x, dimension.y, 
				scale.x, scale.y, 
				rotation,
				reg.getRegionX(), reg.getRegionY(), 
				reg.getRegionWidth(), reg.getRegionHeight(), 
				viewDirection == VIEW_DIRECTION.LEFT, false);
		
		// Reset color to white
		batch.setColor(1, 1, 1, 1);
	}
	
	public void setJumping (boolean jumpKeyPressed) 
	{
		switch (jumpState) 
		{
			case GROUNDED: // Character is standing on a platform
				if (jumpKeyPressed) 
				{
					// Start counting jump time from the beginning
					timeJumping = 0;
					jumpState = JUMP_STATE.JUMP_RISING;
				}
				break;
			case JUMP_RISING: // Rising in the air
				if (!jumpKeyPressed)
				{
					jumpState = JUMP_STATE.JUMP_FALLING;
				}
				break;
			case FALLING:// Falling down
			case JUMP_FALLING: // Falling down after jump
				if (jumpKeyPressed && hasGreenHeartPowerup) 
				{
					timeJumping = JUMP_TIME_OFFSET_FLYING;
					jumpState = JUMP_STATE.JUMP_RISING;
				}
				break;
		}
	};
	
	public void setGreenHeartPowerup (boolean pickedUp) 
	{
		hasGreenHeartPowerup = pickedUp;
		if (pickedUp) 
		{
			timeLeftGreenHeartPowerup = Constants.ITEM_GREENHEART_POWERUP_DURATION;
		}	
	};
	
	public boolean hasGreenHeartPowerup () 
	{
		return hasGreenHeartPowerup && timeLeftGreenHeartPowerup > 0;
	};
}
