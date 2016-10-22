package com.douge.gdx.game;

import com.badlogic.gdx.Gdx;
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
import com.douge.gdx.game.assets.Assets;

public class Message 
{
	public String text;
	public String appendedText;
	public float maxHeight;
	public float currentHeight = 0f;
	public int textIndex = 0;
	public boolean playerSkipped = false;
	public float timeBetweenCharacters = 0;
	public Vector2 conditions;
	private Rectangle box;
	private boolean boxIsRendered = false;
	private ShapeRenderer shapeRenderer = new ShapeRenderer();
	private boolean iconIsRendered = false;
	private Sprite icon;
	private float iconX;
	
	public Message(String text, Vector2 conditions, TextureRegion reg)
	{
		this.text = text;
		text += " ";
		this.conditions = conditions;
		box = new Rectangle(0, Constants.VIEWPORT_GUI_HEIGHT - 70, Constants.VIEWPORT_GUI_WIDTH, 0);
		maxHeight = Constants.VIEWPORT_GUI_HEIGHT - box.y;
		icon = new Sprite(reg);
		icon.setRegion(reg, 0, 0, reg.getRegionWidth(), reg.getRegionWidth()/2);
		icon.setX(-20);
		icon.setY(box.y - 20f);
		icon.flip(false, true);
	}
	
	public void updateText(float deltaTime)
	{
		timeBetweenCharacters += deltaTime;
		if(timeBetweenCharacters >= .1f && boxIsRendered)
		{
			textIndex++;
			timeBetweenCharacters = 0;
		}
	}
	
	public void renderText(SpriteBatch batch)
	{
		Gdx.app.log("", "" + icon.getX() + " " + textIndex);
		if(playerSkipped == false)
		{
			renderBox(batch, currentHeight);
			renderIcon(batch);
			if(boxIsRendered && iconIsRendered)
			{
				if(textIndex >= text.length())
				{
					textIndex = text.length();
				}
				for(int i = 0; i <= textIndex; i++)
				{
					BitmapFont fontGameOver = Assets.instance.fonts.defaultBig;
					fontGameOver.setColor(Color.BLACK);
					fontGameOver.draw(batch, text.subSequence(0, i), box.x + 100, box.y + 25, 0, Align.left, true);		
				}
			}	
			else
			{
				currentHeight += 1.5f;
				currentHeight = MathUtils.clamp(currentHeight, 0, maxHeight);
				if(currentHeight == maxHeight)
				{
					boxIsRendered = true;
				}
				iconX += .05f;
				iconX = MathUtils.clamp(iconX, -20f, 0 + .2f);
				icon.setX(iconX);
				if(icon.getX() >= 0 + .2f)
				{
					iconIsRendered = true;
				}
			}
		}
		
	}

	private void renderIcon(SpriteBatch batch) 
	{
		batch.draw(icon, 
				-5f, icon.getY(), 
				0, 0,
				icon.getWidth(), icon.getHeight(), 
				4, 2,
				0f);
	}

	private void renderBox(SpriteBatch batch, float currentHeight) 
	{
		batch.end();
		shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.BLUE);
		box.height = currentHeight;
		shapeRenderer.rect(box.x, box.y, box.width, box.height);
		shapeRenderer.end();
		batch.begin();
	}
}
