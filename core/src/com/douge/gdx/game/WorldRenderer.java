package com.douge.gdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.utils.GamePreferences;

public class WorldRenderer implements Disposable
{
	public static OrthographicCamera camera;
	public static OrthographicCamera cameraGUI;
	
	private SpriteBatch batch;
	private WorldController worldController;
	private static final boolean DEBUG_DRAW_BOX2D_WORLD = false; 
	public Box2DDebugRenderer box2DdebugRenderer;
	
	public WorldRenderer(WorldController worldController)
	{
		this.worldController = worldController;
		init();
	}
	
	private void init()
	{
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		camera.position.set(0, 0, 0);
		camera.update();
		
		cameraGUI = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
		cameraGUI.position.set(0, 0, 0);
		cameraGUI.setToOrtho(true); // flip y-axis
		cameraGUI.update();
		
		box2DdebugRenderer = new Box2DDebugRenderer();
	}
	
	/**
	 * resize window
	 * @param width width of resized window in pixels
	 * @param height height of resized window in pixels
	 */
	public void resize(int width, int height)
	{
		float metersPerPixel = Constants.VIEWPORT_HEIGHT/height;
		camera.viewportWidth = metersPerPixel*width;
		camera.update();
		
		cameraGUI.viewportHeight = Constants.VIEWPORT_GUI_HEIGHT;
		cameraGUI.viewportWidth = (Constants.VIEWPORT_GUI_HEIGHT/(float)height) * (float)width;
		cameraGUI.position.set(cameraGUI.viewportWidth / 2, cameraGUI.viewportHeight / 2, 0);
		cameraGUI.update();
	}

	/**
	 * free memory
	 */
	@Override
	public void dispose() 
	{
		batch.dispose();
	}

	/**
	 * Render world and gui
	 */
	public void render() 
	{
		batch.begin();
		
		renderWorld();
		renderGui();
	    
		batch.end();
	}	
	
	/**
	 * Renders the world
	 */
	private void renderWorld()
	{
		worldController.cameraHelper.applyTo(camera);
		batch.setProjectionMatrix(camera.combined);
		worldController.levelLoader.render(batch);	
		if (DEBUG_DRAW_BOX2D_WORLD) 
		{
			box2DdebugRenderer.render(worldController.box2DWorld, camera.combined);
		}
	}
	
	/**
	 * Draws the GUI
	 */
	private void renderGui () 
	{
		batch.setProjectionMatrix(cameraGUI.combined);
		
		// draw collected gold coins icon + text
		// (anchored to top left edge)
		renderGuiScore();
		
	    // draw collected feather icon (anchored to top left edge) 
	    renderGuiJumpPowerup(batch); 
		
	    //render the 3 connected left bars
	    renderLeftBar();
	    
		// draw extra lives icon + text (anchored to top right edge)
		renderGuiHealth();
		
		// draw FPS text (anchored to bottom right edge)
		if (GamePreferences.instance.showFpsCounter)
		{
			renderGuiFpsCounter();
		}
		
		renderText();
		
	    // draw game over text 
	    renderGuiGameOverMessage(batch); 
	}
	
	private void renderLeftBar()
	{
		TextureRegion reg = Assets.instance.ui.leftEdgeOfBars;
		batch.draw(reg.getTexture(), 
				-10, 0, 
				0, 0, 
				70f, 70f,
				1f, 1f, 
				0, 
				reg.getRegionX(), reg.getRegionY(),
				reg.getRegionWidth(), reg.getRegionHeight(), 
				false, true);
	}
	
	private void renderText()
	{
		worldController.message.renderText(batch);
	}

	
	/**
	 * draw collected gold coins icon + text
	* anchored to top left edge
	 */
	private void renderGuiScore () 
	{
		float x = 500;
		float y = -15;
		float offsetX = 50;
		float offsetY = 50;
		if (worldController.scoreVisual<worldController.game.score) 
		{
			long shakeAlpha = System.currentTimeMillis() % 360;
			float shakeDist = 1.5f;
			offsetX += MathUtils.sinDeg(shakeAlpha * 2.2f) * shakeDist;
			offsetY += MathUtils.sinDeg(shakeAlpha * 2.9f) * shakeDist;
		}
		batch.draw(Assets.instance.goldCoin.goldCoin, 
				x, y, 
				offsetX, offsetY, 
				100, 100, 
				0.35f, -0.35f, 
				0);
		Assets.instance.fonts.defaultBig.draw(batch, "" + (int)worldController.scoreVisual, x + 75, y + 37);
	}
	
	/**
	 * draw extra lives icon + text (anchored to top right edge)
	 */
	private void renderGuiHealth () 
	{
		float x = 20;
		float y = -37;
		
		int maxLives = Constants.MAX_LIVES;
		if(worldController.levelLoader.player.numLives > maxLives)
		{
			maxLives = worldController.levelLoader.player.numLives;
		}
		for (int i = 0; i < maxLives; i++) 
		{
			boolean playerHasALife = i < worldController.levelLoader.player.numLives;
			boolean playerIsMissingALife = !playerHasALife;
			if(playerHasALife)
			{
				//draw in full color
				batch.setColor(Color.RED);
			}
			else if(playerIsMissingALife)
			{
				batch.setColor(0.5f, 0.5f, 0.5f, 0.5f);
			}
			batch.draw(Assets.instance.ui.block,
					x + i * 30, y, 
					50, 50, 
					120, 100, 
					0.2f, -0.2f,
					0);
		}
		
		batch.setColor(1, 1, 1, 1);

		y = -15;
		for (int currentFireball = 0; currentFireball < Constants.FIREBALLS_START; currentFireball++) 
		{
			boolean playerHasAFireball = currentFireball < worldController.levelLoader.player.numFireballs;
			boolean playerIsMissingAFireBall = !playerHasAFireball;
			if(playerHasAFireball)
			{
				//draw in full color
				batch.setColor(Color.PURPLE);
			}
			else if(playerIsMissingAFireBall)
			{
				batch.setColor(0.5f, 0.5f, 0.5f, 0.5f);
			}
			batch.draw(Assets.instance.ui.block,
					x + currentFireball * 30, y, 
					50, 50, 
					120, 100, 
					0.2f, -0.2f,
					0);
		}
		
		batch.setColor(1, 1, 1, 1);
	}
	
	/**
	 * draw FPS text (anchored to bottom right edge)
	 */
	private void renderGuiFpsCounter () 
	{
		float x = cameraGUI.viewportWidth - 55;
		float y = cameraGUI.viewportHeight - 15;
		int fps = Gdx.graphics.getFramesPerSecond();
		
		BitmapFont fpsFont = Assets.instance.fonts.defaultNormal;
		if (fps >= 45) 
		{
			// 45 or more FPS show up in green
			fpsFont.setColor(0, 1, 0, 1);
		} 
		else if (fps >= 30) 
		{
			// 30 or more FPS show up in yellow
			fpsFont.setColor(1, 1, 0, 1);
		} 
		else 
		{
			// less than 30 FPS show up in red
			fpsFont.setColor(1, 0, 0, 1);
		}
		
		fpsFont.draw(batch, "FPS: " + fps, x, y);
		fpsFont.setColor(1, 1, 1, 1); // white
	}

	
	private void renderGuiGameOverMessage (SpriteBatch batch) 
	{
		float x = cameraGUI.viewportWidth / 2;
		float y = cameraGUI.viewportHeight / 2;
		
		if (worldController.isGameOver()) 
		{
			BitmapFont fontGameOver = Assets.instance.fonts.defaultBig;
			fontGameOver.setColor(1, 0.75f, 0.25f, 1);
			fontGameOver.draw(batch, "GAME OVER", x, y, 0, Align.center, false);
			fontGameOver.setColor(1, 1, 1, 1);
		}
	}
	
	/**
	 * renders 
	 * @param batch
	 */
	private void renderGuiJumpPowerup (SpriteBatch batch) 
	{
		float x = -15;
		float y = 30;
		float timeLeftJumpPowerup = worldController.levelLoader.player.timeLeftJumpPowerup;
		if (timeLeftJumpPowerup > 0) 
		{
			// Start icon fade in/out if the left power-up time
			// is less than 4 seconds. The fade interval is set
			// to 5 changes per second.
			if (timeLeftJumpPowerup < 4) 
			{
				if (((int)(timeLeftJumpPowerup * 5) % 2) != 0) 
				{
					batch.setColor(1, 1, 1, 0.5f);
				}
			}
			batch.setColor(Color.YELLOW);
			batch.draw(Assets.instance.gems.jumpGem.getKeyFrame(0f).getTexture(),
					x, y, 
					50f, 50f, 
					100f, 100f, 
					0.35f, -0.35f);
		
			batch.setColor(1, 1, 1, 1);
			Assets.instance.fonts.defaultSmall.draw(batch, "" + (int)timeLeftJumpPowerup, x + 60, y + 57);

		}
	}	
}
