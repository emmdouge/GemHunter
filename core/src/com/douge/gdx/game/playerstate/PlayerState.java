package com.douge.gdx.game.playerstate;

import com.douge.gdx.game.objects.Survivor;
import com.douge.gdx.game.objects.Rock;

public abstract class PlayerState 
{
	public Survivor player;
	public PlayerStateContext context;
	public String tag;
	
	public PlayerState(Survivor astronaut, PlayerStateContext context)
	{
		player = astronaut;
		this.context = context;
	}

	public abstract void execute(float deltaTime);

	public abstract void onCollisionWith(Rock rock);
	public abstract void noRockCollision();

}
