package testdata;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TestData0 {

	/**
	 * holds a reference to the texture data that is stored in memory at runtime
	 */
	public Texture texture;
	
	/**
	 * a graphical object that has a position in 2d space, width, and height and can be rotated and scaled
	 * holds a reference to a texture region object which has the ability to cut out a piece of a texture with
	 * the top-left of the texture being (0, 0) during initialization
	 * 
	 * the origin of a sprite starts at the bottom-left as (0, 0)
	 * 
	 * gets drawn in the bottom left of the screen (0,0) starting at the bottom left of the texture region
	 */
	public Sprite sprite;
	
	public TestData0()
	{
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
	
	public void update(float deltaTime)
	{
		//does not change any properties
	}
}
