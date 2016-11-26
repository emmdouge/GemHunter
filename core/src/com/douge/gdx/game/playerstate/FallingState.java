package com.douge.gdx.game.playerstate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.objects.Player;
import com.douge.gdx.game.objects.VIEW_DIRECTION;
import com.douge.gdx.game.objects.platform.Platform;

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
		player.timeStunned = 0;
		player.timeAttacking = 0;
		player.timeJumping = player.JUMP_TIME_MAX;
		player.afterImageJump.head = null;
		player.afterImageJump.tail = null;
		player.afterImageDash.head = null;
		player.afterImageDash.tail = null;
		player.currentParticleEffect = Assets.instance.survivor.dustParticles;
		
		if (player.currentVelocity.x != 0) 
		{
			player.viewDirection = player.currentVelocity.x < 0 ? VIEW_DIRECTION.LEFT : VIEW_DIRECTION.RIGHT;
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
			// Make sure the object's velocity does not exceed the
			// positive or negative terminal velocity
			player.currentVelocity.x = MathUtils.clamp(player.currentVelocity.x, -player.maxVelocity.x, player.maxVelocity.x);
			if(player.activeMovement)
			{
				if(player.inContactWithPlatform)
				{
					player.currentAnimation = Assets.instance.survivor.runAnimation;
					player.currentParticleEffect.start();
				}
				else if(!player.inContactWithPlatform && player.currentVelocity.y != 0)
				{	
					player.currentAnimation = Assets.instance.survivor.fallingAnimation;
				}
			}
			else
			{
				player.currentAnimation = Assets.instance.survivor.standingAnimation;
			}
		}
		else if(player.currentVelocity.y == 0)
		{
			player.currentAnimation = Assets.instance.survivor.standingAnimation;
			player.timeJumping = player.JUMP_TIME_MAX;
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
		player.position.x += player.currentVelocity.x * deltaTime;
		
	}

	@Override
	public void onCollisionWith(Platform platform) 
	{
		//drawn starting from bottom left
		float diffBetweenTopOfPlayerAndBottomOfPlatform = Math.abs(player.position.y + player.bounds.height + .001f - platform.position.y);
		float diffBetweenLeftSideOfPlayerAndRightSideOfPlatform = Math.abs(player.position.x  - platform.bounds.width - platform.position.x);
		float diffBetweenBottomOfPlayerAndTopOfPlatform = Math.abs(platform.position.y + platform.bounds.height - player.position.y);
		float diffBetweenRightSideOfPlayerAndLeftSideOfPlatform = Math.abs(player.position.x + player.bounds.width - platform.position.x);
		
		boolean hitTop =  diffBetweenTopOfPlayerAndBottomOfPlatform <= 0.1f;
		boolean landOnTop =  diffBetweenBottomOfPlayerAndTopOfPlatform <= 0.1f;
		boolean hitLeftEdge = diffBetweenRightSideOfPlayerAndLeftSideOfPlatform <= 0.3f;
		boolean hitRightEdge = diffBetweenLeftSideOfPlayerAndRightSideOfPlatform <= 0.3f;
		
		if(landOnTop)
		{
			player.currentGravity = 0;
			player.position.y = platform.position.y + platform.bounds.height;
			player.friction = platform.body.getFixtureList().get(0).getFriction();
			player.currentVelocity.y = platform.currentVelocity.y;
			player.timeDashing = 0;
			if(platform.body.getLinearVelocity().y < 0)
			{
				player.position.y -= .01f;
			}
			if(platform.body.getLinearVelocity().x != 0)
			{
				player.currentVelocity.x = platform.body.getLinearVelocity().x;
			}
		}
		else if(hitTop)
		{
			player.timeJumping = player.JUMP_TIME_MAX;
			if(platform.body.getLinearVelocity().y < 0)
			{
				context.setPlayerState(context.getHurtState());
			}
		}
		else if(hitLeftEdge)
		{
			player.currentFriction = 0;
			player.currentVelocity.x = 0;
			player.position.x = platform.position.x - player.bounds.width;
			
			//increment the ram processors to use with the graphics apix
			player.maxVelocity.x = 0f;
			if(player.currentVelocity.y == 0)
			{
				player.maxVelocity.x = 3f;
				player.currentGravity = 0f;
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
				player.currentGravity = 0f;
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
