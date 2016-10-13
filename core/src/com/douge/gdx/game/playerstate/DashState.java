package com.douge.gdx.game.playerstate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.douge.gdx.game.objects.Rock;
import com.douge.gdx.game.objects.Survivor;
import com.douge.gdx.game.objects.Survivor.VIEW_DIRECTION;

public class DashState extends PlayerState
{

	public DashState(Survivor astronaut, PlayerStateContext context) {
		super(astronaut, context);
	}

	@Override
	public void execute(float deltaTime) 
	{
		//Gdx.app.log(tag, "" + player.timeDashing);
		// Keep track of jump time
		player.timeDashing += deltaTime;
		
		if(player.timeDashing <= player.DASH_TIME_MAX)
		{
			player.afterImageDash.addNode(player, player.getRegion());
			
			int direction = VIEW_DIRECTION.getInt(player.viewDirection);
			
			player.position.x += (10*direction) * deltaTime;
			player.currentVelocity.y = 0;
			
			if (player.timeLeftGreenHeartPowerup > 0) 
			{
				player.timeLeftGreenHeartPowerup -= deltaTime;
			}
			if (player.timeLeftGreenHeartPowerup < 0) 
			{
				// disable power-up
				player.timeLeftGreenHeartPowerup = 0;
				player.setGreenHeartPowerup(false);
			}
		}
		else
		{
			player.timeJumping = player.JUMP_TIME_MAX;
			context.setPlayerState(context.getFallingState());
		}
	}

	@Override
	public void onCollisionWith(Rock rock) 
	{
		float diffBetweenLeftSideOfPlayerAndRightSideOfRock = rock.position.x + rock.bounds.x - player.position.x;
		
		float diffBetweenRightSideOfPlayerAndLeftSideOfRock = player.position.x + player.bounds.width + .001f - rock.position.x;
		boolean hitLeftEdge = diffBetweenRightSideOfPlayerAndLeftSideOfRock <= 0.07f;
		boolean hitRightEdge = diffBetweenLeftSideOfPlayerAndRightSideOfRock <= 0.07f;
		
		if(hitLeftEdge)
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
	public void noRockCollision() {
		// TODO Auto-generated method stub
		
	}

}
