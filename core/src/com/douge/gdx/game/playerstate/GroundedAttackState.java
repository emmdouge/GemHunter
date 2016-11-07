package com.douge.gdx.game.playerstate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.douge.gdx.game.VIEW_DIRECTION;
import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.objects.Platform;

public class GroundedAttackState extends PlayerState
{

	public GroundedAttackState(PlayerStateContext context) 
	{
		super(context);
	}
	
	@Override
	public void execute(float deltaTime) 
	{
		// Keep track of 
		player.timeAttacking += deltaTime;
		
		// Keep track of jump time
		player.timeJumping += deltaTime;
		
		player.currentAnimation = Assets.instance.survivor.groundedAttackAnimation;
	
		if(player.timeAttacking > player.TIME_BETWEEN_ATTACKS)
		{
			player.timeAttacking = 0;
			stateTime = 0;
			player.timeJumping = player.JUMP_TIME_MAX;
			context.setPlayerState(context.getFallingState());
		}
		
		
		if (player.currentVelocity.x != 0) 
		{
			// Apply friction
			if (player.currentVelocity.x > 0) 
			{
				player.currentVelocity.x = Math.max(player.currentVelocity.x - player.friction * deltaTime, 0);
			} 
			else 
			{
				player.currentVelocity.x = Math.min(player.currentVelocity.x + player.friction * deltaTime, 0);
			}
			player.viewDirection = player.currentVelocity.x < 0 ? VIEW_DIRECTION.LEFT : VIEW_DIRECTION.RIGHT;
		}
	
		// Make sure the object's velocity does not exceed the
		// positive or negative terminal velocity
		player.currentVelocity.x = MathUtils.clamp(player.currentVelocity.x, -player.maxVelocity.x, player.maxVelocity.x);
		
		if (player.timeLeftJumpPowerup > 0 && player.timeJumping < player.JUMP_TIME_MAX*1.5) 
		{
			player.timeLeftJumpPowerup -= deltaTime;
			player.afterImageJump.addNode(player, player.currentAnimation.getKeyFrame(player.stateTime));
		}
		
		if (player.timeLeftJumpPowerup < 0) 
		{
			// disable power-up
			player.timeLeftJumpPowerup = 0;
			player.setJumpPowerup(false);
		}
		
		// Move to new position
		player.position.x += player.currentVelocity.x * deltaTime;
		
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
	}

	@Override
	public void onCollisionWith(Platform platform) 
	{
		float diffBetweenRightSideOfPlayerAndLeftSideOfPlatform = platform.position.x - player.position.x - player.bounds.width;
		float diffBetweenLeftSideOfPlayerAndRightSideOfPlatform = platform.position.x + platform.bounds.width - player.position.x;
		float diffBetweenBottomOfPlayerAndTopOfPlatform = platform.position.y + platform.bounds.height - player.position.y;
		
		boolean hitLeftEdge = diffBetweenRightSideOfPlayerAndLeftSideOfPlatform <= 0.07f;
		boolean hitRightEdge = diffBetweenLeftSideOfPlayerAndRightSideOfPlatform <= 0.07f;
		boolean onTopOfRock =  diffBetweenBottomOfPlayerAndTopOfPlatform <= 0.07f;

		if(onTopOfRock)
		{
			player.position.y += .01f;
		}
		else if(hitLeftEdge)
		{
			player.currentFriction = 0;
			player.currentVelocity.x = 0;
			if(platform.currentVelocity.x != 0)
			{
				context.setPlayerState(context.getJumpFallingState());
			}
			
			//since the rocks are all linked together, rock's bound witdth is the entire platform
			player.position.x = platform.position.x - 1;
		}
		if(hitRightEdge)
		{
			player.currentFriction = 0;
			player.currentVelocity.x = 0;
			if(platform.currentVelocity.x != 0)
			{
				context.setPlayerState(context.getJumpFallingState());
			}
			player.position.x = platform.position.x + platform.bounds.width;
		}
	}

	@Override
	public void noPlatformCollision() 
	{
		context.noPlatformCollision();
	}
}
