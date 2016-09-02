package com.douge.gdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DougeGdxGame extends ApplicationAdapter {
	/**
	 * where all drawing commands are sent
	 * optimizes draw commands under certain circumstances
	 */
	SpriteBatch batch;
	
	/**
	 * Displays the 2d scene of our game
	 * Orthographic - It does not matter how far away an object is from the camera, 
	 * it will always have the same size on the screen
	 * this is the player's view of the actual game which is defined by a certain width and height provided
	 * by the viewport
	 */
	OrthographicCamera camera;
	
	/**
	 * holds a reference to the texture data that is stored in memory at runtime
	 */
	Texture texture;
	
	/**
	 * a graphical object that has a position in 2d space, width, and height and can be rotated and scaled
	 * holds a reference to a texture region object which has the ability to cut out a piece of a texture with
	 * the top-left of the texture being (0, 0) during initialization
	 * 
	 * the origin of a sprite starts at the bottom-left as (0, 0)
	 * 
	 * gets drawn in the bottom left of the screen (0,0) starting at the bottom left of the texture region
	 */
	Sprite sprite;
	
	/**
	 * overrides the original create method in ApplicatonAdapter
	 */
	@Override
	public void create () {
		batch = new SpriteBatch();
		texture = new Texture("badlogic.jpg");
		
		sprite = new Sprite(new TextureRegion(texture, 0, 0, 256, 256));

		/**
		 * Sets origin of sprite to its center
		 */
		sprite.setOrigin(+sprite.getWidth()/2, +sprite.getHeight()/2);
		
		/**
		 * Draws sprite as center of screen
		 */
		sprite.setPosition(Gdx.graphics.getWidth()/2-(sprite.getWidth()/2), Gdx.graphics.getHeight()/2-(sprite.getHeight()/2));
	}

	/**
	 * overrides the original render methods in ApplicationAdapter
	 */
	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		/**
		 * projection and view matrix multiplied to draw on canvas
		 */
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		batch.draw(texture, 0, 0);
		sprite.draw(batch);
		batch.end();
	}
}
