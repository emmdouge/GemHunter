package com.douge.gdx.game.playerstate;

import com.badlogic.gdx.math.MathUtils;
import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.objects.Player;
import com.douge.gdx.game.VIEW_DIRECTION;
import com.douge.gdx.game.objects.Platform;

public class GroundedState extends PlayerState 
{
	public GroundedState(Player astronaut, PlayerStateContext context)
	{
		super(astronaut, context);
		tag = this.getClass().getName();
	}
	
	@Override
	public void execute(float deltaTime) 
	{
		player.timeDashing = 0;
		player.timeStunned = 0;
		player.timeJumping = 0;
		player.afterImageJump.head = null;
		player.afterImageJump.tail = null;
		player.afterImageDash.head = null;
		player.afterImageDash.tail = null;
		player.currentAnimation = Assets.instance.survivor.standingAnimation;
		player.currentParticleEffect = Assets.instance.survivor.dustParticles;
		
		if (player.currentVelocity.x != 0) 
		{
			// Apply friction
			if (player.currentVelocity.x > 0) 
			{
				player.currentVelocity.x = Math.max(player.currentVelocity.x - player.currentFriction * deltaTime, 0);
				player.currentParticleEffect.setPosition(player.position.x, player.position.y);
			} 
			else 
			{
				player.currentVelocity.x = Math.min(player.currentVelocity.x + player.currentFriction * deltaTime, 0);
				player.currentParticleEffect.setPosition(player.position.x + player.bounds.width, player.position.y);
			}
			player.currentParticleEffect.start();
		}

		// Make sure the object's velocity does not exceed the
		// positive or negative terminal velocity
		player.currentVelocity.x = MathUtils.clamp(player.currentVelocity.x, -player.maxVelocity.x, player.maxVelocity.x);
		
		if (player.currentVelocity.x != 0) 
		{
			player.viewDirection = player.currentVelocity.x < 0 ? VIEW_DIRECTION.LEFT : VIEW_DIRECTION.RIGHT;
			player.currentAnimation = Assets.instance.survivor.runAnimation;
		}
		
		if (player.timeLeftJumpPowerup > 0) 
		{
			player.timeLeftJumpPowerup -= deltaTime;
		}
		if (player.timeLeftJumpPowerup < 0) 
		{
			// disable power-up
			player.timeLeftJumpPowerup = 0;
			player.setJumpPowerup(false);
		}
		
		// Move to new position
		player.position.x += player.currentVelocity.x * deltaTime;
		player.position.y += player.currentVelocity.y * deltaTime;
	}

	@Override
	public void onCollisionWith(Platform platform) 
	{
		if(platform.currentVelocity.x != 0)
		{
			player.currentVelocity.x = platform.currentVelocity.x;
			player.currentFriction = 0;
		}
	}

	@Override
	public void noPlatformCollision() 
	{
		player.timeJumping = player.JUMP_TIME_MAX;
		context.noPlatformCollision();
		context.setPlayerState(context.getFallingState());
	}
	
}
