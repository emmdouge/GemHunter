package com.douge.gdx.game.playerstate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.objects.Player;
import com.douge.gdx.game.objects.VIEW_DIRECTION;
import com.douge.gdx.game.objects.platform.Platform;

public class DashState extends PlayerState
{

	public DashState(PlayerStateContext context) {
		super(context);
	}

	@Override
	public void execute(float deltaTime) 
	{
		// Keep track of jump time
		player.timeDashing += deltaTime;
		player.currentAnimation = Assets.instance.survivor.dashingAnimation;
		if(player.timeDashing <= player.DASH_TIME_MAX)
		{
			player.afterImageDash.addNode(player, player.currentAnimation.getKeyFrame(player.stateTime));
			int direction = VIEW_DIRECTION.getInt(player.viewDirection);
			
			player.position.x += (10*direction) * deltaTime;
			player.currentVelocity.y = 0;
			player.currentVelocity.x = 0;
			
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
		else
		{
			player.timeJumping = player.JUMP_TIME_MAX;
			context.setPlayerState(context.getFallingState());
		}
	}

	@Override
	public void onCollisionWith(Platform platform) 
	{
		float diffBetweenTopOfPlayerAndBottomOfPlatform = Math.abs(player.position.y + player.bounds.height + .001f - platform.position.y);
		float diffBetweenLeftSideOfPlayerAndRightSideOfPlatform = Math.abs(platform.position.x + platform.bounds.width - player.position.x);
		float diffBetweenBottomOfPlayerAndTopOfPlatform = Math.abs(platform.position.y + platform.bounds.height - player.position.y);
		float diffBetweenRightSideOfPlayerAndLeftSideOfPlatform = Math.abs(player.position.x + player.bounds.width - platform.position.x);
		
		boolean hitLeftEdge = diffBetweenRightSideOfPlayerAndLeftSideOfPlatform <= 0.3f;
		boolean hitRightEdge = diffBetweenLeftSideOfPlayerAndRightSideOfPlatform <= 0.3f;
		boolean onTopOfRock =  diffBetweenBottomOfPlayerAndTopOfPlatform <= 0.2f;

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
				player.timeJumping = player.JUMP_TIME_MAX;
				context.setPlayerState(context.getFallingState());
			}
			else
			{
				//since the rocks are all linked together, rock's bound witdth is the entire platform
				player.position.x = platform.position.x - player.bounds.width - .01f;
			}
		}
		else if(hitRightEdge)
		{
			player.currentFriction = 0;
			player.currentVelocity.x = 0;
			if(platform.currentVelocity.x != 0)
			{
				player.timeJumping = player.JUMP_TIME_MAX;
				context.setPlayerState(context.getFallingState());
			}
			else
			{
				player.position.x = platform.position.x + platform.bounds.width + .01f;
			}
		}
	}

	@Override
	public void noPlatformCollision() 
	{
		context.noPlatformCollision();
	}

}
