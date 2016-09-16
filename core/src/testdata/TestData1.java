package testdata;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

public class TestData1 
{

	public Sprite[] testSprites;
	public Texture texture;
	public int selectedSpriteIndex = 0;
	public final int NUM_SPRITES = 5;
	
	public TestData1()
	{
		testSprites = new Sprite[NUM_SPRITES];
		int width = 32;
		int height = 32;
		
		/**
		 * holds actual pixel data that can be drawn as a texture
		 */
		Pixmap pixmap = new Pixmap(width, height, Format.RGB888);
		drawOnPixmap(pixmap, width, height);
		this.texture = new Texture(pixmap);
		
		for(int i = 0; i < testSprites.length; i++)
		{
			Sprite sprite = new Sprite(texture);
			
			//set width and height of sprite in pixels
			sprite.setSize(32, 32);
			
			//set origin to center of sprite for it to rotate at its center
			sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
			
			float randomX = MathUtils.random(-20.0f, 20.0f);
			float randomY = MathUtils.random(-20.0f, 20.0f);
			
			sprite.setPosition(randomX, randomY);
			
			testSprites[i] = sprite;
		}
	}
	
	public void update(float deltaTime) 
	{
		float currentRotation = testSprites[selectedSpriteIndex].getRotation();
		
		float nextRotation = currentRotation + (90 * deltaTime);
		
		//wrap around at 360
		nextRotation = nextRotation % 360;
		
		testSprites[selectedSpriteIndex].setRotation(nextRotation);
	}
	
	/**
	 * the isKeyPressed method checks to see if a key is still being pressed
	 * 
	 */
	public void handleInput(float deltaTime)
	{
		float spriteMoveSpeed = 5;
		
		if(Gdx.input.isKeyPressed(Keys.W))
		{
			testSprites[selectedSpriteIndex].translateY(spriteMoveSpeed);
		}
		if(Gdx.input.isKeyPressed(Keys.A))
		{
			testSprites[selectedSpriteIndex].translateX(-spriteMoveSpeed);
		}
		if(Gdx.input.isKeyPressed(Keys.S))
		{
			testSprites[selectedSpriteIndex].translateY(-spriteMoveSpeed);
		}
		if(Gdx.input.isKeyPressed(Keys.D))
		{
			testSprites[selectedSpriteIndex].translateX(spriteMoveSpeed);
		}
		float camMoveSpeed = 5 * deltaTime;
		float camMoveSpeedAccelerationFactor = 5;
	}
	
	public void drawOnPixmap(Pixmap pixmap, int width, int height)
	{
		pixmap.setColor(1, 0, 0, 0.5f);
		pixmap.fill();
		
		pixmap.setColor(1, 1, 0, 1);
	
		//(0, 0) is top left
		pixmap.drawLine(0, 0, 32, 32);
		
		pixmap.setColor(0, 1, 1, 1);
		pixmap.drawRectangle(0, 0, width, height);
	}

}
