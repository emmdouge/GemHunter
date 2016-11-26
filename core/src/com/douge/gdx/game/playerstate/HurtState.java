package com.douge.gdx.game.playerstate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.objects.Player;
import com.douge.gdx.game.objects.VIEW_DIRECTION;
import com.douge.gdx.game.objects.platform.Platform;

public class HurtState extends PlayerState
{

	public HurtState(PlayerStateContext context) 
	{
		super(context);
	}

	@Override
	public void execute(float deltaTime) 
	{			
		player.timeStunned += deltaTime;
		player.currentAnimation = Assets.instance.survivor.hurtAnimation;
		player.timeJumping = player.JUMP_TIME_MAX;
		if(player.isStunned)
		{
			if(player.timeStunned < player.STUN_TIME_MAX)
			{
				if (player.currentVelocity.x != 0) 
				{
					// Apply friction
					if (player.currentVelocity.x > 0) 
					{
						player.currentVelocity.x = Math.max(player.currentVelocity.x + 1 * deltaTime, 0);
					} 
					else 
					{
						//Gdx.app.log("", ""+player.currentVelocity.x);
						player.currentVelocity.x = Math.min(player.currentVelocity.x - 1 * deltaTime, 0);
					}
					player.currentVelocity.x = MathUtils.clamp(player.currentVelocity.x, -player.maxVelocity.x, player.maxVelocity.x);
					player.viewDirection = player.currentVelocity.x < 0 ? VIEW_DIRECTION.LEFT : VIEW_DIRECTION.RIGHT;
				}
				
				player.currentGravity = -.5f;
				player.currentVelocity.y += player.currentGravity;
				player.currentVelocity.y = MathUtils.clamp(player.currentVelocity.y, -player.maxVelocity.y, player.maxVelocity.y*.75f);
				
				// Move to new position
				player.position.x += player.currentVelocity.x * deltaTime;
				player.position.y += player.currentVelocity.y * deltaTime;
			}
			else
			{
				//after stun
				player.isStunned = false;
				player.timeJumping = player.JUMP_TIME_MAX;
				player.timeStunned = 0;
				player.context.setPlayerState(player.context.getFallingState());
			}
		}
		else
		{
			//before stun
			player.timeInvincibleLeft = player.INVINCIBLE_TIME_MAX;
			player.numLives--;
			player.isStunned = true;
			player.currentVelocity.x = player.maxVelocity.x*VIEW_DIRECTION.getOppositeInt(player.viewDirection);
			player.currentVelocity.y = player.maxVelocity.y*.75f;
		}
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
		boolean onTopOfRock =  diffBetweenBottomOfPlayerAndTopOfPlatform <= 0.1f;
		boolean hitLeftEdge = diffBetweenRightSideOfPlayerAndLeftSideOfPlatform <= 0.3f;
		boolean hitRightEdge = diffBetweenLeftSideOfPlayerAndRightSideOfPlatform <= 0.3f;
		
		if(onTopOfRock)
		{
			player.currentVelocity.y = 0;
			player.position.y = platform.position.y + platform.bounds.height + .05f;
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
	}

}
