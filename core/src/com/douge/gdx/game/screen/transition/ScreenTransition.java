package com.douge.gdx.game.screen.transition;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface ScreenTransition 
{
	public float getDuration ();
	public void render (SpriteBatch batch, Texture currentScreen, Texture nextScreen, float alpha);
}
