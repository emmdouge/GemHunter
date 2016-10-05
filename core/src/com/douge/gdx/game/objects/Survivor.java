package com.douge.gdx.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.douge.gdx.game.Constants;
import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.playerstate.PlayerStateContext;
import com.sun.xml.internal.bind.CycleRecoverable.Context;

public class Survivor extends AbstractGameObject
{
	public static final String TAG = Survivor.class.getName();
	public final float JUMP_TIME_MAX = 0.3f;
	public final float JUMP_TIME_MIN = 0.1f;
	public final float JUMP_TIME_OFFSET_FLYING = JUMP_TIME_MAX - 0.018f;
	
	public PlayerStateContext context;
	
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
	
	private TextureRegion regSurvivor;
	public VIEW_DIRECTION viewDirection;
	public float timeJumping;
	public JUMP_STATE jumpState;
	public boolean hasGreenHeartPowerup;
	public float timeLeftGreenHeartPowerup;
	
	public Survivor() 
	{
		init();
	}
	public void init() 
	{
		dimension.set(1, 1);
		regSurvivor = Assets.instance.survivor.survivor;
		
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
		context = new PlayerStateContext(this);
		timeJumping = 0;
		
		// Power-ups
		hasGreenHeartPowerup = false;
		timeLeftGreenHeartPowerup = 0;		
	};
	
	public void update(float deltaTime)
	{
		context.getCurrentState().execute(deltaTime);
	}
	
	public void render(SpriteBatch batch)
	{
		TextureRegion reg = regSurvivor;
		
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
