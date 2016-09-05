package com.douge.gdx.game;

import testdata.TestData1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class WorldRenderer implements Disposable {

	private static String TAG = WorldRenderer.class.getName();
	private static final Color BACKGROUND_COLOR = new Color(1, 1, 1, 1);
	
	//defines the size of the captured game world
	private static final int VIEWPORT_WIDTH = 1280;
	private static final int VIEWPORT_HEIGHT = 720;
	
	
	/**
	 * "you have something that can draw to the screen via gpu like the spritebatch. And you have a camera.
	 * you gotta tell the spritebatch where we are / where the camera is looking, because on screen shall be what the camera is looking at."
	 * 
	 * 
	 * 
	 * Orthographic - It does not matter how far away an object is from the camera, 
	 * it will always have the same size on the screen
	 * this is the player's view of the actual game which is defined by a certain width and height provided
	 * by the viewport
	 */
	OrthographicCamera camera;
	
	/**
	 * where all drawing commands are sent
	 * optimizes draw commands under certain circumstances
	 * draws our objects with respect to the camera's current settings(position, zoom, etc)
	 */
	SpriteBatch batch;
	
	/**
	 * contains game logic
	 */
	WorldController worldController;
	
	public TestData1 testData1 = new TestData1();
	
	public WorldRenderer(WorldController worldController)
	{
		this.worldController = worldController;
		
		camera = new OrthographicCamera();
		camera.viewportWidth = VIEWPORT_WIDTH;
		camera.viewportHeight = VIEWPORT_HEIGHT;
		
		//sets position of camera
		//z position does nothing since its orthographic(?)
		camera.position.set(0, 0, 0);
		
		//always update camera after changing its properties for changes to take effect
		camera.update();
		
		worldController.camHelper = new CameraHelper(camera);
		
		batch = new SpriteBatch();
		
		
	}
	
	@Override
	public void dispose() {
		batch.dispose();
	}

	public void render() 
	{	
		//sets the clear color
		Gdx.gl.glClearColor(BACKGROUND_COLOR.r, BACKGROUND_COLOR.g, BACKGROUND_COLOR.b, BACKGROUND_COLOR.a);
		
		//clears the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		/**
		 * Viewport width and height must also be initialized
		 * and the camera must be updated
		 * 
		 * Before setting the projection matrix and initializing the viewport,
		 * graphics were draw using the bottom left as the origin(0, 0)
		 * this is why test0's center picture is now offscreen 
		 */
		batch.setProjectionMatrix(camera.combined);		
		
		worldController.camHelper.applyChanges();
		
		batch.begin();
		
		test0();
		test1();
		
		batch.end();
	}

	public void resize(int width, int height)
	{
		//as the height of the window decreases, we see more of the world
		//but changing the width only stretches the view like before
		camera.viewportWidth = (VIEWPORT_HEIGHT/height)*width;
		camera.update();
	}
	
	public void test0()
	{	
		batch.draw(worldController.testData0.texture, 0.0f, 0.0f);
		batch.draw(worldController.testData0.texture, -Gdx.graphics.getWidth()/2, -Gdx.graphics.getHeight()/2);
		batch.draw(worldController.testData0.texture, (Gdx.graphics.getWidth()/2)-worldController.testData0.texture.getWidth()/2, -Gdx.graphics.getHeight()/2);
		worldController.testData0.sprite.draw(batch);
	}
	
	private void test1() 
	{	
		batch.draw(worldController.testData1.texture, 0.0f, 0.0f);
		for(Sprite sprite: worldController.testData1.testSprites)
		{
			sprite.draw(batch);
		}
		
	}
	

}
