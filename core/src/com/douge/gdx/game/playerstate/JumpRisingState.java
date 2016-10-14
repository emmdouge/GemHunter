package com.douge.gdx.game.playerstate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.douge.gdx.game.assets.Assets;
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
		player.currentVelocity.y += player.gravity * deltaTime;
		
		// Make sure the object's velocity does not exceed the
		// positive or negative terminal velocity
		player.currentVelocity.y = MathUtils.clamp(player.currentVelocity.y, -player.maxVelocity.y, player.maxVelocity.y);
		
		if (player.currentVelocity.x != 0) 
		{
			player.viewDirection = player.currentVelocity.x < 0 ? VIEW_DIRECTION.LEFT : VIEW_DIRECTION.RIGHT;
		}
		
		if (player.timeLeftGreenHeartPowerup > 0 && player.timeJumping < player.JUMP_TIME_MAX*1.5) 
		{
			player.timeLeftGreenHeartPowerup -= deltaTime;
			player.currentVelocity.y = player.maxVelocity.y;
			player.afterImageJump.addNode(player, player.currentAnimation.getKeyFrame(player.stateTime));
		}
		if (player.timeLeftGreenHeartPowerup < 0) 
		{
			// disable power-up
			player.timeLeftGreenHeartPowerup = 0;
			player.setGreenHeartPowerup(false);
		}
		
		// Move to new position
		//Gdx.app.log(tag, "player: " + player.position.y + " " + player.currentVelocity.y);
		player.position.x += player.currentVelocity.x * deltaTime;
		player.position.y += player.currentVelocity.y * deltaTime;
	}

	@Override
	public void onCollisionWith(Rock rock) 
	{
		//drawn starting from bottom left
		float diffBetweenTopOfPlayerAndBottomOfRock = player.position.y + player.bounds.height + .001f - rock.position.y;
		float diffBetweenLeftSideOfPlayerAndRightSideOfRock = rock.position.x + rock.bounds.x - player.position.x;
		float diffBetweenBottomOfPlayerAndTopOfRock = rock.position.y + rock.bounds.height - player.position.y;
		float diffBetweenRightSideOfPlayerAndLeftSideOfRock = player.position.x + player.bounds.width + .001f - rock.position.x;
		
		boolean hitTop =  diffBetweenTopOfPlayerAndBottomOfRock <= 0.07f;
		boolean hitLeftEdge = diffBetweenRightSideOfPlayerAndLeftSideOfRock <= 0.07f;
		boolean hitRightEdge = diffBetweenLeftSideOfPlayerAndRightSideOfRock <= 0.07f;
		boolean onTopOfRock =  diffBetweenBottomOfPlayerAndTopOfRock <= 0.07f;
		
		if(hitTop)
		{
			//Gdx.app.log(tag, "player: " + player.position.y + " " + rock.position.y);
			player.currentGravity = 0;
			player.currentVelocity.y = 0;
			player.timeJumping = player.JUMP_TIME_MAX;
			player.position.y = rock.position.y - player.bounds.height - .001f;
			context.setPlayerState(context.getJumpFallingState());
			//Gdx.app.log(tag, "player: " + player.position.y + " " + player.currentVelocity.y);
		}
		else if(onTopOfRock)
		{
			player.currentVelocity.y = player.maxVelocity.y;
		}
		else if(hitLeftEdge)
		{
			//Gdx.app.log(tag, "rock: " + rock.position.x + "+" + rock.bounds.height + "=" + (rock.position.y+rock.bounds.height) + ", player: " + player.position.y + " " +(4.5-player.position.y) );
			player.currentFriction = 0;
			player.currentVelocity.x = 0;
			
			//since the rocks are all linked together, rock's bound witdth is the entire platform
			player.position.x = rock.position.x - 1;
		}
		else if(hitRightEdge)
		{
			//Gdx.app.log(tag, "rock: " + rock.position.x + "+" + rock.bounds.width + "=" + (rock.position.x+rock.bounds.width) + ", player: " + player.position.y + " " +(4.5-player.position.y) );
			player.currentFriction = 0;
			player.currentVelocity.x = 0;
			player.position.x = rock.position.x + rock.bounds.width;
		}
	}

	@Override
	public void noRockCollision() 
	{
		context.noRockCollision();
	}

}
