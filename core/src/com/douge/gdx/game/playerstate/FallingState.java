package com.douge.gdx.game.playerstate;

import com.badlogic.gdx.math.MathUtils;
import com.douge.gdx.game.objects.Survivor;
import com.douge.gdx.game.objects.Survivor.VIEW_DIRECTION;
import com.douge.gdx.game.objects.Rock;

public class FallingState extends PlayerState
{
	public FallingState(Survivor astronaut, PlayerStateContext context)
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
		player.velocity.x += player.acceleration.x * deltaTime;
		
		// Make sure the object's velocity does not exceed the
		// positive or negative terminal velocity
		player.velocity.x = MathUtils.clamp(player.velocity.x, -player.terminalVelocity.x, player.terminalVelocity.x);
		
		// Move to new position
		player.position.x += player.velocity.x * deltaTime;
		player.position.y += player.velocity.y * deltaTime;
		
		// Add delta times to track jump time
		player.timeJumping += deltaTime;

		// Jump to minimal height if jump key was pressed too short
		if (player.timeJumping > 0 && player.timeJumping <= player.JUMP_TIME_MIN) 
		{
			// Still jumping
			player.velocity.y = player.terminalVelocity.y;
		}
		
		if (player.velocity.y != 0) 
		{
			// Apply friction
			if (player.velocity.y > 0) 
			{
				player.velocity.y = Math.max(player.velocity.y - player.friction.y * deltaTime, 0);
			} 
			else 
			{
				player.velocity.y = Math.min(player.velocity.y + player.friction.y * deltaTime, 0);
			}
		}
		
		// Apply acceleration
		player.velocity.y += player.acceleration.y * deltaTime;
		
		// Make sure the object's velocity does not exceed the
		// positive or negative terminal velocity
		player.velocity.y = MathUtils.clamp(player.velocity.y, -player.terminalVelocity.y, player.terminalVelocity.y);
		
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
		if (jumpKeyPressed && player.hasGreenHeartPowerup) 
		{
			player.timeJumping = player.JUMP_TIME_OFFSET_FLYING;
			context.setPlayerState(context.getJumpRisingState());
		}
	}

	@Override
	public void onCollisionWith(Rock rock) 
	{
		player.position.y = rock.position.y + player.bounds.height;
		context.setPlayerState(context.getGroundState());
	}
	
}
