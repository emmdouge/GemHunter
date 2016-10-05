package com.douge.gdx.game.playerstate;

import com.badlogic.gdx.math.MathUtils;
import com.douge.gdx.game.objects.Survivor;
import com.douge.gdx.game.objects.Survivor.VIEW_DIRECTION;
import com.douge.gdx.game.objects.Rock;

public class GroundedState extends PlayerState 
{
	public GroundedState(Survivor astronaut, PlayerStateContext context)
	{
		super(astronaut, context);
		tag = this.getClass().getName();
	}
	
	@Override
	public void execute(float deltaTime) 
	{
		if (player.velocity.x != 0) 
		{
			// Apply friction
			if (player.velocity.x > 0) 
			{
				player.velocity.x = Math.max(player.velocity.x - player.friction.x * deltaTime, 0);
			} 
			else 
			{
				player.velocity.x = Math.min(player.velocity.x + player.friction.x * deltaTime, 0);
			}
		}
		
		// Apply acceleration
		player.velocity.x += player.gravity.x * deltaTime;
		
		// Make sure the object's velocity does not exceed the
		// positive or negative terminal velocity
		player.velocity.x = MathUtils.clamp(player.velocity.x, -player.maxVelocity.x, player.maxVelocity.x);
		
		context.setPlayerState(context.getFallingState());
		
		if (player.velocity.x != 0) 
		{
			player.viewDirection = player.velocity.x < 0 ? VIEW_DIRECTION.LEFT : VIEW_DIRECTION.RIGHT;
		}
		
		if (player.timeLeftGreenHeartPowerup > 0) 
		{
			player.timeLeftGreenHeartPowerup -= deltaTime;
		}
		if (player.timeLeftGreenHeartPowerup < 0) 
		{
			// disable power-up
			player.timeLeftGreenHeartPowerup = 0;
			player.setGreenHeartPowerup(false);
		}
	}

	@Override
	public void setStateBasedOnInput(boolean jumpKeyPressed) 
	{
		if (jumpKeyPressed) 
		{
			// Start counting jump time from the beginning
			player.timeJumping = 0;
			context.setPlayerState(context.getJumpRisingState());
		}
	}

	@Override
	public void onCollisionWith(Rock rock) 
	{
		//do nothing
	}
	
}
