package com.douge.gdx.game.playerstate;

import com.douge.gdx.game.objects.Astronaut;
import com.douge.gdx.game.objects.Rock;

public abstract class PlayerState 
{
	public Astronaut player;
	public PlayerStateContext context;
	public String tag;
	
	public PlayerState(Astronaut astronaut, PlayerStateContext context)
	{
		player = astronaut;
		this.context = context;
	}

	public abstract void execute(float deltaTime);
	
	public abstract void setStateBasedOnInput(boolean jumpKeyPressed);

	public abstract void onCollisionWith(Rock rock);

}
