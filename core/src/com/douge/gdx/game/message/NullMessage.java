package com.douge.gdx.game.message;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.objects.Player;

/**
 * Uses null object pattern to remove null dependent code
 * @author Emmanuel
 *
 */
public class NullMessage extends Message
{
	public NullMessage()
	{
		super(null, null, Assets.instance.survivor.survivor);
		playerSkipped = true;
	}
	
	public void updateText(float deltaTime, Player player) 
	{
		//do nothing
	}

	public void renderText(SpriteBatch batch) 
	{
		//do nothing
	}
}
