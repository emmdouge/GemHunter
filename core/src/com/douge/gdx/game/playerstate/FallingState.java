package com.douge.gdx.game.playerstate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.objects.Player;
import com.douge.gdx.game.VIEW_DIRECTION;
import com.douge.gdx.game.objects.Platform;

public class FallingState extends PlayerState
{
	public FallingState(PlayerStateContext context)
	{
		super(context);
		tag = this.getClass().getName();
	}
	
	@Override
	public void execute(float deltaTime) 
	{
		player.currentAnimation = Assets.instance.survivor.fallingAnimation;
		if (player.currentVelocity.x != 0) 
		{
			player.viewDirection = player.currentVelocity.x < 0 ? VIEW_DIRECTION.LEFT : VIEW_DIRECTION.RIGHT;
			// Apply friction
			if (player.currentVelocity.x > 0) 
			{
				player.currentVelocity.x = Math.max(player.currentVelocity.x - player.friction * deltaTime, 0);
			} 
			else 
			{
				player.currentVelocity.x = Math.min(player.currentVelocity.x + player.friction * deltaTime, 0);
			}
			// Make sure the object's velocity does not exceed the
			// positive or negative terminal velocity
			player.currentVelocity.x = MathUtils.clamp(player.currentVelocity.x, -player.maxVelocity.x, player.maxVelocity.x);
			// Move to new position
			player.position.x += player.currentVelocity.x * deltaTime;
		}


		
		// Add delta times to track jump time
		player.timeJumping += deltaTime;

		// Jump to minimal height if jump key was pressed too short
		if (player.JUMP_TIME_MIN < player.timeJumping && player.timeJumping <= player.JUMP_TIME_MAX) 
		{
			// Still jumping
			player.currentVelocity.y = player.maxVelocity.y;
		}
		
		// Apply acceleration
		player.currentVelocity.y += player.currentGravity * deltaTime;
		
		// Make sure the object's velocity does not exceed the
		// positive or negative terminal velocity
		player.currentVelocity.y = MathUtils.clamp(player.currentVelocity.y, -player.maxVelocity.y, player.maxVelocity.y);
		
		if (player.timeLeftJumpPowerup > 0 && player.timeJumping < player.JUMP_TIME_MAX) 
		{
			player.timeLeftJumpPowerup -= deltaTime;
		}
		if (player.timeLeftJumpPowerup < 0) 
		{
			// disable power-up
			player.timeLeftJumpPowerup = 0;
			player.setJumpPowerup(false);
		}
		

		player.position.y += player.currentVelocity.y * deltaTime;
		
	}

	@Override
	public void onCollisionWith(Platform platform) 
	{
		//drawn starting from bottom left
		float diffBetweenBottomOfPlayerAndTopOfPlatform = platform.position.y + platform.bounds.height - player.position.y;
		float diffBetweenLeftSideOfPlayerAndRightSideOfPlatform = platform.position.x + platform.bounds.width - player.position.x;
		float diffBetweenRightSideOfPlayerAndLeftSideOfPlatform = player.position.x + player.bounds.width - platform.position.x;
		float diffBetweenTopOfPlayerAndBottomOfPlatform = player.position.y + player.bounds.height + .001f - platform.position.y;
		
		boolean hitTop =  diffBetweenTopOfPlayerAndBottomOfPlatform <= 0.07f;
		boolean landOnTop =  diffBetweenBottomOfPlayerAndTopOfPlatform <= 0.2f;
		boolean hitLeftEdge = diffBetweenRightSideOfPlayerAndLeftSideOfPlatform <= 0.07f;
		boolean hitRightEdge = diffBetweenLeftSideOfPlayerAndRightSideOfPlatform <= 0.07f;
		
		if(landOnTop)
		{
			player.currentGravity = 0;
			player.currentVelocity.y = 0;
			player.position.y = platform.position.y + platform.bounds.height - 0.001f;
			player.friction = platform.body.getFixtureList().get(0).getFriction();
			if(platform.currentVelocity.y >= 0)
			{
				player.currentVelocity.y = platform.currentVelocity.y;
			}
			else
			{
				player.position.y = platform.body.getPosition().y+platform.dimension.y-.03f;
			}
			context.setPlayerState(context.getGroundState());
		}
		else if(hitTop)
		{
			player.currentGravity = 0;
			player.currentVelocity.y = 0;
			player.timeJumping = player.JUMP_TIME_MAX;
			if(platform.body.getLinearVelocity().y >= 0)
			{
				player.position.y = platform.position.y - player.bounds.height - .1f;
				context.setPlayerState(context.getFallingState());
			}
			else if(platform.body.getLinearVelocity().y < 0)
			{
				context.setPlayerState(context.getHurtState());
			}
		}
		else if(hitLeftEdge)
		{
			player.currentFriction = 0;
			player.currentVelocity.x = 0;
			
			//since the rocks are all linked together, rock's bound witdth is the entire platform
			player.position.x = platform.position.x - player.bounds.width;
			
			player.maxVelocity.x = 0f;
			if(player.currentVelocity.y == 0)
			{
				player.maxVelocity.x = 3f;
				context.setPlayerState(context.getGroundState());
			}
		}
		else if(hitRightEdge)
		{
			player.currentFriction = 0;
			player.currentVelocity.x = 0;
			player.position.x = platform.position.x + platform.bounds.width;
			player.maxVelocity.x = 0f;
			if(player.currentVelocity.y == 0)
			{
				player.maxVelocity.x = 3f;
				context.setPlayerState(context.getGroundState());
			}

		}
	}

	@Override
	public void noPlatformCollision() 
	{
		context.noPlatformCollision();
		player.maxVelocity.x = 3f;
	}
	
}
