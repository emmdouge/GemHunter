package com.douge.gdx.game.playerstate;

import com.badlogic.gdx.Gdx;
import com.douge.gdx.game.objects.Rock;
import com.douge.gdx.game.objects.Survivor;
import com.douge.gdx.game.objects.Survivor.VIEW_DIRECTION;

public class SlideState extends PlayerState
{

	public SlideState(Survivor astronaut, PlayerStateContext context) 
	{
		super(astronaut, context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(float deltaTime) 
	{
		Gdx.app.log(tag, "" + player.timeSliding);
		
		// Keep track of jump time
		player.timeSliding += deltaTime;
		
		if(player.timeSliding <= player.SLIDE_TIME_MAX)
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
