package com.douge.gdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

public class DougeGdxGame implements ApplicationListener {
	private static final String TAG = DougeGdxGame.class.getName();

	
	WorldController worldController;
	WorldRenderer worldRenderer;

	/**
	 * acts like a constructor
	 */
	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		worldController = new WorldController();
		worldRenderer = new WorldRenderer(worldController);

	}

	/**
	 * overrides the original render methods in ApplicationAdapter
	 */
	@Override
	public void render () {
		worldController.update(Gdx.graphics.getDeltaTime());		
		worldRenderer.render();
	}

	@Override
	public void resize(int width, int height) {
		worldRenderer.resize(width, height);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		worldRenderer.dispose();
	}
}
