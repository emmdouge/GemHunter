package com.douge.gdx.game.playerstate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.douge.gdx.game.VIEW_DIRECTION;
import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.objects.Platform;

public class JumpAttackState extends PlayerState
{
	public JumpAttackState(PlayerStateContext context) 
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
		player.currentAnimation = Assets.instance.survivor.jumpAttackAnimation;
	
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
		
		player.currentVelocity.y += player.gravity * deltaTime;
		// Make sure the object's velocity does not exceed the
		// positive or negative terminal velocity
		player.currentVelocity.y = MathUtils.clamp(player.currentVelocity.y, -player.maxVelocity.y, player.maxVelocity.y);
		

		
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
		player.position.y += player.currentVelocity.y * deltaTime;
		
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
		//drawn starting from bottom left
		float diffBetweenBottomOfPlayerAndTopOfRock = platform.position.y + platform.bounds.height - player.position.y;
		float diffBetweenLeftSideOfPlayerAndRightSideOfRock = platform.position.x + platform.bounds.width - player.position.x;
		float diffBetweenTopOfPlayerAndBottomOfRock = player.position.y + player.bounds.height + .001f - platform.position.y;
		float diffBetweenRightSideOfPlayerAndLeftSideOfRock = player.position.x + player.bounds.width - platform.position.x;
		
		boolean hitTop =  diffBetweenTopOfPlayerAndBottomOfRock <= 0.07f;
		boolean landOnTop =  diffBetweenBottomOfPlayerAndTopOfRock <= 0.2f;
		boolean hitLeftEdge = diffBetweenRightSideOfPlayerAndLeftSideOfRock <= 0.07f;
		boolean hitRightEdge = diffBetweenLeftSideOfPlayerAndRightSideOfRock <= 0.07f;
		
		if(landOnTop)
		{
			player.currentGravity = 0;
			player.currentVelocity.y = 0;
			player.position.y = platform.position.y + platform.bounds.height - 0.001f;
			context.setPlayerState(context.getGroundState());
		}
		else if(hitTop)
		{
			//Gdx.app.log(tag, "player: " + player.position.y + " " + rock.position.y);
			player.currentGravity = 0;
			player.currentVelocity.y = 0;
			player.timeJumping = player.JUMP_TIME_MAX;
			player.position.y = platform.position.y - player.bounds.height - .001f;
			context.setPlayerState(context.getJumpFallingState());
		}
		else if(hitLeftEdge)
		{
			player.currentFriction = 0;
			player.currentVelocity.x = 0;
			player.position.x = platform.position.x - 1 - .001f;
			player.maxVelocity.x = 0f;
		}
		else if(hitRightEdge)
		{
			player.currentFriction = 0;
			player.currentVelocity.x = 0;
			player.position.x = platform.position.x + platform.bounds.width;			
			player.maxVelocity.x = 0f;
		}
	}

	@Override
	public void noPlatformCollision() 
	{
		context.noPlatformCollision();
		player.maxVelocity.x = 3f;
	}

}
