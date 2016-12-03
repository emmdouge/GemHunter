package com.douge.gdx.game.message;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.douge.gdx.game.Constants;
import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.objects.Player;
import com.douge.gdx.game.utils.AudioManager;

public class Message 
{
	public boolean playerSkipped = false;
	public Vector2 conditions;
	public boolean dialogueIsRendered = false;
	public boolean shouldBeRendered = false;

	public boolean isRendering = false;
	private Music dialogue;
	public Message nextNode;
	public boolean completed = false;
	public Message(Music dialogue, Vector2 conditions) 
	{
		this.dialogue = dialogue;
		this.conditions = conditions;
	}

	public void updateText(float deltaTime, Player player) 
	{
		if (player.position.x >= conditions.x && player.position.y >= conditions.y) 
		{
			shouldBeRendered = true;
		}
	}

	public void renderText(SpriteBatch batch) 
	{
		// Gdx.app.log("", "" + icon.getX() + " " + textIndex);
		if (playerSkipped == false && shouldBeRendered && !completed) 
		{
			completed = AudioManager.instance.playUntilDone(dialogue);
		}
		AudioManager.instance.completed = false;
	}
}
