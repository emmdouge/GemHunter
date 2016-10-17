package com.douge.gdx.game.playerstate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.objects.Rock;
import com.douge.gdx.game.objects.Survivor;
import com.douge.gdx.game.VIEW_DIRECTION;

public class HurtState extends PlayerState
{

	public HurtState(Survivor astronaut, PlayerStateContext context) 
	{
		super(astronaut, context);
	}

	@Override
	public void execute(float deltaTime) 
	{
		player.timeStunned += deltaTime;
		player.currentAnimation = Assets.instance.survivor.hurtAnimation;
		player.currentVelocity.y = 0;
		
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
						Gdx.app.log("", ""+player.currentVelocity.x);
						player.currentVelocity.x = Math.min(player.currentVelocity.x - 1 * deltaTime, 0);
					}
					player.currentVelocity.x = MathUtils.clamp(player.currentVelocity.x, -player.maxVelocity.x, player.maxVelocity.x);
					player.viewDirection = player.currentVelocity.x < 0 ? VIEW_DIRECTION.LEFT : VIEW_DIRECTION.RIGHT;
				}
				if (player.currentVelocity.y != 0) 
				{
					player.currentVelocity.y += player.gravity;
					player.currentVelocity.y = MathUtils.clamp(player.currentVelocity.y, -player.maxVelocity.y, player.maxVelocity.y);
				}
				// Move to new position
				player.position.x += player.currentVelocity.x * deltaTime;
				player.position.y += player.currentVelocity.y * deltaTime;
			}
			else
			{
				player.isStunned = false;
				player.timeJumping = player.JUMP_TIME_MAX;
				player.timeStunned = 0;
				player.context.setPlayerState(player.context.getFallingState());
			}
		}
		else
		{
			player.isStunned = true;
			player.currentVelocity.x = player.maxVelocity.x*VIEW_DIRECTION.getOppositeInt(player.viewDirection);
		}
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
			player.position.y = rock.position.y - player.bounds.height - .001f;
			//Gdx.app.log(tag, "player: " + player.position.y + " " + player.currentVelocity.y);
		}
		else if(onTopOfRock)
		{
			player.position.y = rock.position.y + rock.bounds.height + 0.001f;
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
		player.currentVelocity.y = player.gravity;
		context.noRockCollision();
	}

}
