package com.douge.gdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;

import testdata.TestData0;
import testdata.TestData1;

/**
 * The InputAdapter class is a default implementation of the InputProcessor interface
 * that provides various methods to handle input events.
 * 
 *
 */
public class WorldController extends InputAdapter
{
	private static final String TAG = WorldController.class.getName();

	public TestData0 testData0;
	public TestData1 testData1;
	public CameraHelper camHelper;
	
	public WorldController()
	{
		init();
	}
	
	/**
	 * so we can reinitialize world controller whenever we want
	 */
	public void init()
	{
		/**
		 * libgdx needs to be told where to send the received input events 
		 */
		Gdx.input.setInputProcessor(this);
		
		testData0 = new TestData0();
		testData1 = new TestData1();
	}
	
	public void update(float deltaTime)
	{
		handleInput(deltaTime);
		camHelper.update(deltaTime);
		
		testData0.update(deltaTime);
		testData1.update(deltaTime);
	}

	private void handleInput(float deltaTime) 
	{
		testData1.handleInput(deltaTime);
		
		float camMoveSpeed = 5 * deltaTime;
		float camMoveSpeedAccelerationFactor = 5;
		
		float camZoomSpeed = 1 * deltaTime;
		float camZoomSpeedAccelerationFactor = 5;
		
		//camera move
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
		{
			camMoveSpeed = camMoveSpeed * camMoveSpeedAccelerationFactor;
		}
		if (Gdx.input.isKeyPressed(Keys.LEFT)) 
		{
			camHelper.setPosition(-camMoveSpeed+camHelper.getPosition().x, camHelper.getPosition().y);
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) 
		{
			camHelper.setPosition(camMoveSpeed+camHelper.getPosition().x, camHelper.getPosition().y);
		}
		if (Gdx.input.isKeyPressed(Keys.UP))
		{
			camHelper.setPosition(camHelper.getPosition().x, camHelper.getPosition().y+camMoveSpeed);
		}
		if (Gdx.input.isKeyPressed(Keys.DOWN))
		{
			camHelper.setPosition(camHelper.getPosition().x, camHelper.getPosition().y-camMoveSpeed);
		}
		if (Gdx.input.isKeyPressed(Keys.BACKSPACE))
		{
			camHelper.setPosition(0, 0);
		}
		
		//camera zoom
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
		{
			camZoomSpeed *= camZoomSpeedAccelerationFactor;
		}
		if (Gdx.input.isKeyPressed(Keys.Q))
		{
			camHelper.addZoom(camZoomSpeed);
		}
		if (Gdx.input.isKeyPressed(Keys.E)) 
		{
			camHelper.addZoom(-camZoomSpeed);
		}
		if (Gdx.input.isKeyPressed(Keys.F))
		{
			camHelper.setZoom(1);
		}
	}
	
	/**
	 * keyUp is the method called when a key is released
	 */
	@Override
	public boolean keyUp(int keycode)
	{
		if(keycode == Keys.R)
		{
			init();
			Gdx.app.debug(TAG, "Game World reset");
		}
		if(keycode == Keys.SPACE)
		{
			testData1.selectedSpriteIndex = (testData1.selectedSpriteIndex+1)%testData1.NUM_SPRITES;
			camHelper.setTarget(testData1.testSprites[testData1.selectedSpriteIndex]);
			Gdx.app.debug(TAG, "Sprite " + testData1.selectedSpriteIndex + " selected");
		}
		if(keycode == Keys.ENTER)
		{
			if(camHelper.targetIsEmpty())
			{
				camHelper.setTarget(testData1.testSprites[testData1.selectedSpriteIndex]);
				Gdx.app.debug(TAG, "Currently following Sprite " + testData1.selectedSpriteIndex);
			}
			if(!camHelper.targetIsEmpty())
			{
				camHelper.setTarget(null);
			}
		}
		
		return false;
	}
	
}
