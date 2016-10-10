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
		Gdx.app.log(tag, "" + player.timeDashing);
		// Keep track of jump time
		player.timeDashing += deltaTime;
		
		if(player.timeDashing <= player.DASH_TIME_MAX)
		{
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
			context.setPlayerState(context.getFallingState());
		}
	}

	@Override
	public void onCollisionWith(Rock rock) {
		// TODO Auto-generated method stub
		
	}

}
