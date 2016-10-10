package com.douge.gdx.game.playerstate;

import com.badlogic.gdx.math.MathUtils;
import com.douge.gdx.game.objects.Survivor;
import com.douge.gdx.game.objects.Survivor.VIEW_DIRECTION;
import com.douge.gdx.game.objects.Rock;

public class JumpFallingState extends PlayerState
{
	public JumpFallingState(Survivor astronaut, PlayerStateContext context)
	{
		super(astronaut, context);
		tag = this.getClass().getName();
	}
	
	@Override
	public void execute(float deltaTime) 
	{
		if (player.currentVelocity.x != 0) 
		{
			// Apply friction
			if (player.currentVelocity.x > 0) 
			{
				player.currentVelocity.x = Math.max(player.currentVelocity.x - player.friction.x * deltaTime, 0);
			} 
			else 
			{
				player.currentVelocity.x = Math.min(player.currentVelocity.x + player.friction.x * deltaTime, 0);
			}
		}
		
		// Apply acceleration
		player.currentVelocity.x += player.gravity.x * deltaTime;
		
		// Make sure the object's velocity does not exceed the
		// positive or negative terminal velocity
		player.currentVelocity.x = MathUtils.clamp(player.currentVelocity.x, -player.maxVelocity.x, player.maxVelocity.x);
		
		// Move to new position
		player.position.x += player.currentVelocity.x * deltaTime;
		player.position.y += player.currentVelocity.y * deltaTime;
		
		// Add delta times to track jump time
		player.timeJumping += deltaTime;

		// Jump to minimal height if jump key was pressed too short
		if (player.timeJumping > 0 && player.timeJumping <= player.JUMP_TIME_MIN) 
		{
			// Still jumping
			player.currentVelocity.y = player.maxVelocity.y;
		}
		
		if (player.currentVelocity.y != 0) 
		{
			// Apply friction
			if (player.currentVelocity.y > 0) 
			{
				player.currentVelocity.y = Math.max(player.currentVelocity.y - player.friction.y * deltaTime, 0);
			} 
			else 
			{
				player.currentVelocity.y = Math.min(player.currentVelocity.y + player.friction.y * deltaTime, 0);
			}
		}
		
		// Apply acceleration
		player.currentVelocity.y += player.gravity.y * deltaTime;
		
		// Make sure the object's velocity does not exceed the
		// positive or negative terminal velocity
		player.currentVelocity.y = MathUtils.clamp(player.currentVelocity.y, -player.maxVelocity.y, player.maxVelocity.y);
		
		if (player.currentVelocity.x != 0) 
		{
			player.viewDirection = player.currentVelocity.x < 0 ? VIEW_DIRECTION.LEFT : VIEW_DIRECTION.RIGHT;
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
	public void onCollisionWith(Rock rock) 
	{
		player.position.y = rock.position.y + player.bounds.height;
		context.setPlayerState(context.getGroundState());
	}

}
