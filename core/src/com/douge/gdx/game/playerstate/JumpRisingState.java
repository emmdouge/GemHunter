package com.douge.gdx.game.playerstate;

import com.badlogic.gdx.math.MathUtils;
import com.douge.gdx.game.objects.Survivor;
import com.douge.gdx.game.objects.Survivor.VIEW_DIRECTION;
import com.douge.gdx.game.objects.Rock;

public class JumpRisingState extends PlayerState
{

	public JumpRisingState(Survivor astronaut, PlayerStateContext context)
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
		
		// Move to new position
		player.position.x += player.velocity.x * deltaTime;
		player.position.y += player.velocity.y * deltaTime;
		
		// Keep track of jump time
		player.timeJumping += deltaTime;

		// Jump time left?
		if (player.timeJumping <= player.JUMP_TIME_MAX) 
		{
			// Still jumping
			player.velocity.y = player.maxVelocity.y;
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
		player.velocity.y += player.gravity.y * deltaTime;
		
		// Make sure the object's velocity does not exceed the
		// positive or negative terminal velocity
		player.velocity.y = MathUtils.clamp(player.velocity.y, -player.maxVelocity.y, player.maxVelocity.y);
		
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
		if (!jumpKeyPressed)
		{
			context.setPlayerState(context.getJumpFallingState());
		}
	}

	@Override
	public void onCollisionWith(Rock rock) 
	{
		player.position.y = rock.position.y + player.bounds.height + player.origin.y;
	}

}
