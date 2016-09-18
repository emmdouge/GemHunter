package com.douge.gdx.game;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class WorldController extends InputAdapter
{
	private static final String TAG = WorldController.class.getName();
	public Sprite[] testSprites;
	public int selectedSprite;
	public CameraHelper cameraHelper;
	
	public WorldController()
	{
		init();
	}
	
	private void init()
	{
		Gdx.input.setInputProcessor(this);
		cameraHelper = new CameraHelper();
		initTestObjects();
	}
	
	private void initTestObjects() 
	{
		testSprites = new Sprite[5];
		
		//create a list of texture regions
		Array<TextureRegion> regions = new Array<TextureRegion>();
		regions.add(Assets.instance.astronaut.astronaut);
		regions.add(Assets.instance.goldCoin.goldCoin);
		regions.add(Assets.instance.heart.heart);
		regions.add(Assets.instance.survivor.survivor);
		regions.add(Assets.instance.tiles.chest);
		regions.add(Assets.instance.tiles.tileTop);
		regions.add(Assets.instance.tiles.tileBot);
		
		//create new sprites using a random texture region
		for(int i = 0; i < testSprites.length; i++)
		{
			Sprite spr = new Sprite(regions.random());
			spr.setSize(1, 1);
			spr.setOrigin(spr.getWidth()/2.0f, spr.getHeight()/2.0f);
			
			float randomX = MathUtils.random(-2.0f, 2.0f);
			float randomY = MathUtils.random(-2.0f, 2.0f);
			
			spr.setPosition(randomX, randomY);
			
			testSprites[i] = spr;
		}
	}

	public void update(float deltaTime)
	{
		handleDebugInput(deltaTime);
		updateTestObjects(deltaTime);
		cameraHelper.update(deltaTime);
	}

	private void handleDebugInput(float deltaTime) {
		if (Gdx.app.getType() != ApplicationType.Desktop) return;
		// Selected Sprite Controls
		float sprMoveSpeed = 5 * deltaTime;
		if (Gdx.input.isKeyPressed(Keys.A)) 
		{
			moveSelectedSprite(-sprMoveSpeed, 0);
		}
		if (Gdx.input.isKeyPressed(Keys.D))
		{
			moveSelectedSprite(sprMoveSpeed, 0);
		}
		if (Gdx.input.isKeyPressed(Keys.W)) 
		{
			moveSelectedSprite(0,sprMoveSpeed);
		}
		if (Gdx.input.isKeyPressed(Keys.S)) moveSelectedSprite(0,-sprMoveSpeed);
		
		// Camera Controls (move)
		float camMoveSpeed = 5 * deltaTime;
		float camMoveSpeedAccelerationFactor = 5;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
		{
			camMoveSpeed *=camMoveSpeedAccelerationFactor;
		}
		if (Gdx.input.isKeyPressed(Keys.LEFT)) 
		{
			moveCamera(-camMoveSpeed,0);
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) 
		{
			moveCamera(camMoveSpeed,0);
		}
		if (Gdx.input.isKeyPressed(Keys.UP)) 
		{
			moveCamera(0, camMoveSpeed);
		}
		if (Gdx.input.isKeyPressed(Keys.DOWN)) 
		{
			moveCamera(0,-camMoveSpeed);
		}
		if (Gdx.input.isKeyPressed(Keys.BACKSPACE))
		{
			cameraHelper.setPosition(0, 0);
		}
		
		// Camera Controls (zoom)
		float camZoomSpeed = 1 * deltaTime;
		float camZoomSpeedAccelerationFactor = 5;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) 
		{
			camZoomSpeed *= camZoomSpeedAccelerationFactor;
		}
		if (Gdx.input.isKeyPressed(Keys.COMMA))
		{
			cameraHelper.addZoom(camZoomSpeed);
		}
		if (Gdx.input.isKeyPressed(Keys.PERIOD)) 
		{
			cameraHelper.addZoom(-camZoomSpeed);
		}	
		if (Gdx.input.isKeyPressed(Keys.SLASH)) 
		{
			cameraHelper.setZoom(1);
		}
	}
	
	private void moveCamera (float x, float y) {
		x += cameraHelper.getPosition().x;
		y += cameraHelper.getPosition().y;
		cameraHelper.setPosition(x, y);
		}

	@Override
	public boolean keyUp (int keycode) 
	{
		// Reset game world
		if (keycode == Keys.R) 
		{
			init();
			Gdx.app.debug(TAG, "Game world resetted");
		}
		
		// Select next sprite
		else if (keycode == Keys.SPACE) {
			selectedSprite = (selectedSprite + 1) % testSprites.length;
			if(cameraHelper.hasTarget())
			{
				cameraHelper.setTarget(testSprites[selectedSprite]);
			}
			Gdx.app.debug(TAG, "Sprite #" + selectedSprite + " selected");
		}
		
		else if (keycode == Keys.ENTER) {
			cameraHelper.setTarget(cameraHelper.hasTarget() ? null :
			testSprites[selectedSprite]);
			Gdx.app.debug(TAG, "Camera follow enabled: " +
			cameraHelper.hasTarget());
			}
	
		return false;
	}
	
	private void moveSelectedSprite(float x, float y) {
		testSprites[selectedSprite].translate(x, y);
	}

	private void updateTestObjects(float deltaTime) 
	{
		float rotation = testSprites[selectedSprite].getRotation();
		
		rotation = rotation + (90*deltaTime);
		rotation = rotation%360;
		
		testSprites[selectedSprite].setRotation(rotation);
	}
}
