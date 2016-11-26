package com.douge.gdx.game.playerstate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.objects.Player;
import com.douge.gdx.game.objects.VIEW_DIRECTION;
import com.douge.gdx.game.Constants;
import com.douge.gdx.game.objects.platform.Platform;

public class JumpRisingState extends PlayerState
{

	public JumpRisingState(PlayerStateContext context)
	{
		super(context);
		tag = this.getClass().getName();
	}
	
	@Override
	public void execute(float deltaTime) 
	{
		player.currentAnimation = Assets.instance.survivor.jumpingAnimation;
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
		}
	
		// Make sure the object's velocity does not exceed the
		// positive or negative terminal velocity
		player.currentVelocity.x = MathUtils.clamp(player.currentVelocity.x, -player.maxVelocity.x, player.maxVelocity.x);
		
		// Keep track of jump time
		player.timeJumping += deltaTime;

		// Jump time left?
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
		
		if (player.currentVelocity.x != 0) 
		{
			player.viewDirection = player.currentVelocity.x < 0 ? VIEW_DIRECTION.LEFT : VIEW_DIRECTION.RIGHT;
		}

		player.timeLeftJumpPowerup -= deltaTime;
		
		if (player.timeLeftJumpPowerup > 0 && player.timeJumping < player.JUMP_TIME_MAX*1.5) 
		{
			player.currentGravity = 0;
			player.afterImageJump.addNode(player, player.currentAnimation.getKeyFrame(player.stateTime));
		}
		if (player.timeLeftJumpPowerup < 0) 
		{
			// disable power-up
			player.timeLeftJumpPowerup = 0;
			player.setJumpPowerup(false);
		}
		
		// Move to new position
		//Gdx.app.log(tag, "player: " + player.position.y + " " + player.currentVelocity.y);
		player.position.x += player.currentVelocity.x * deltaTime;
		player.position.y += player.currentVelocity.y * deltaTime;
	}

	@Override
	public void onCollisionWith(Platform platform) 
	{
		//drawn starting from bottom left
		float diffBetweenTopOfPlayerAndBottomOfRock = Math.abs(player.position.y + player.bounds.height + .001f - platform.position.y);
		float diffBetweenLeftSideOfPlayerAndRightSideOfRock = Math.abs(platform.position.x + platform.bounds.width - player.position.x);
		float diffBetweenBottomOfPlayerAndTopOfRock = Math.abs(platform.position.y + platform.bounds.height - player.position.y);
		float diffBetweenRightSideOfPlayerAndLeftSideOfRock = Math.abs(player.position.x + player.bounds.width - platform.position.x);
		
		boolean hitTop =  diffBetweenTopOfPlayerAndBottomOfRock <= 0.1f;
		boolean hitLeftEdge = diffBetweenRightSideOfPlayerAndLeftSideOfRock <= 0.3f;
		boolean hitRightEdge = diffBetweenLeftSideOfPlayerAndRightSideOfRock <= 0.3f;
		boolean onTopOfRock =  diffBetweenBottomOfPlayerAndTopOfRock <= 0.2f;
		
		if(hitTop)
		{
			player.timeJumping = player.JUMP_TIME_MAX;
			if(platform.body.getLinearVelocity().y < 0)
			{
				context.setPlayerState(context.getHurtState());
			}
			else
			{
				player.currentVelocity.y = player.gravity;
				context.setPlayerState(context.getFallingState());
			}
		}
		else if(onTopOfRock)
		{
			player.position.y += .01f;
		}
		else if(hitLeftEdge)
		{
			//since the rocks are all linked together, rock's bound witdth is the entire platform
			player.position.x = platform.position.x - player.bounds.width - .01f;
			player.maxVelocity.x = 0f;
		}
		else if(hitRightEdge)
		{
			player.position.x = platform.position.x + platform.bounds.width + .01f;
			player.maxVelocity.x = 0f;
		}
	}

	@Override
	public void noPlatformCollision() 
	{
		//System.out.println("no rock collision in rising" + Math.random());
		context.noPlatformCollision();
		player.maxVelocity.x = 3f;
	}

}
