package com.douge.gdx.game.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.douge.gdx.game.Constants;
import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.playerstate.PlayerStateContext;
import com.douge.gdx.game.utils.GamePreferences;
import com.sun.xml.internal.bind.CycleRecoverable.Context;
import com.douge.gdx.game.screens.CharacterSkin;

import effects.AfterImage;
import effects.AfterImage.Node;


public class Survivor extends AbstractGameObject
{
	public static final String TAG = Survivor.class.getName();
	public final float JUMP_TIME_MAX = 0.3f;
	public final float JUMP_TIME_MIN = 0.1f;
	public final float JUMP_TIME_OFFSET_FLYING = JUMP_TIME_MAX - 0.018f;
	public float timeJumping;
	
	public final float DASH_TIME_MAX = 0.25f;
	public float timeDashing;
	
	public final float SLIDE_TIME_MAX = 0.25f;
	public float timeSliding;
	
	public PlayerStateContext context;
	
	public AfterImage afterImageDash;
	public AfterImage afterImageJump;
	public AfterImage afterImageNeutral;
	
	public enum VIEW_DIRECTION 
	{ 
		LEFT, 
		RIGHT;
		
		public static int getInt(VIEW_DIRECTION direction)
		{
			if(direction == LEFT)
			{
				return -1;
			}
			else
			{
				return 1;
			}
		}
	}
	
	private TextureRegion regSurvivor;
	public VIEW_DIRECTION viewDirection;

	public boolean hasGreenHeartPowerup;
	public float timeLeftGreenHeartPowerup;

	
	public Survivor() 
	{
		init();
	}
	public void init() 
	{
		dimension.set(1, 1);
		regSurvivor = Assets.instance.survivor.survivor;
		
		// Center image on game object
		origin.set(dimension.x / 2, dimension.y / 2);
		
		// Bounding box for collision detection
		bounds.set(0, 0, dimension.x, dimension.y);
		
		// Set physics values
		maxVelocity.set(3.0f, 4.0f);
		friction = 12.0f;
		gravity = -25.0f;
		currentGravity = gravity;
		currentFriction = friction;
		
		// View direction
		viewDirection = VIEW_DIRECTION.RIGHT;

		context = new PlayerStateContext(this);
		timeJumping = 0;
		
		afterImageDash = new AfterImage();
		afterImageJump = new AfterImage();
		afterImageNeutral = new AfterImage();
		
		// Power-ups
		hasGreenHeartPowerup = false;
		timeLeftGreenHeartPowerup = 0;		
	};
	
	public void update(float deltaTime)
	{
		context.getCurrentState().execute(deltaTime);
		afterImageNeutral.addNode(this, this.getRegion());
	}
	
	public void render(SpriteBatch batch)
	{
		TextureRegion reg = regSurvivor;
		
		// Apply Skin Color
		batch.setColor(CharacterSkin.values()[GamePreferences.instance.charSkin].getColor());
		
		// Draw image
		Node currentNode = afterImageNeutral.head;
		for(int i = 0; currentNode != null && context.getCurrentState() != context.getDashState() && i < afterImageNeutral.MAX_NUM_NODES-1; i++)
		{
			batch.setColor(Color.GRAY);
			batch.draw(currentNode.reg.getTexture(), 
					currentNode.position.x, currentNode.position.y, 
					currentNode.origin.x, currentNode.origin.y, 
					currentNode.dimension.x, currentNode.dimension.y, 
					currentNode.scale.x, currentNode.scale.y, 
					currentNode.rotation,
					currentNode.reg.getRegionX(), currentNode.reg.getRegionY(), 
					currentNode.reg.getRegionWidth(), currentNode.reg.getRegionHeight(), 
					viewDirection == VIEW_DIRECTION.LEFT, false);
			currentNode = currentNode.nextNode;
		}
		
		// Draw image
		currentNode = afterImageDash.head;
		for(int i = 0; currentNode != null && context.getCurrentState() == context.getDashState() && i < afterImageDash.MAX_NUM_NODES-1; i++)
		{
			batch.setColor(Color.BLUE);
			batch.draw(currentNode.reg.getTexture(), 
					currentNode.position.x, currentNode.position.y, 
					currentNode.origin.x, currentNode.origin.y, 
					currentNode.dimension.x, currentNode.dimension.y, 
					currentNode.scale.x, currentNode.scale.y, 
					currentNode.rotation,
					currentNode.reg.getRegionX(), currentNode.reg.getRegionY(), 
					currentNode.reg.getRegionWidth(), currentNode.reg.getRegionHeight(), 
					viewDirection == VIEW_DIRECTION.LEFT, false);
			currentNode = currentNode.nextNode;
		}
		
		// Set special color when game object has a feather power-up
		if (hasGreenHeartPowerup) 
		{
			batch.setColor(1.0f, 0.8f, 0.0f, 1.0f);
			currentNode = afterImageJump.head;
			for(int i = 0; currentNode != null && (context.getCurrentState() == context.getJumpRisingState() || context.getCurrentState() == context.getJumpFallingState()) && i < afterImageJump.MAX_NUM_NODES-1; i++)
			{
				batch.draw(currentNode.reg.getTexture(), 
						currentNode.position.x, currentNode.position.y, 
						currentNode.origin.x, currentNode.origin.y, 
						currentNode.dimension.x, currentNode.dimension.y, 
						currentNode.scale.x, currentNode.scale.y, 
						currentNode.rotation,
						currentNode.reg.getRegionX(), currentNode.reg.getRegionY(), 
						currentNode.reg.getRegionWidth(), currentNode.reg.getRegionHeight(), 
						viewDirection == VIEW_DIRECTION.LEFT, false);
				currentNode = currentNode.nextNode;
			}
		}
		// Reset color to white
		batch.setColor(1, 1, 1, 1);
		
		batch.draw(reg.getTexture(), 
				position.x, position.y, 
				origin.x, origin.y, 
				dimension.x, dimension.y, 
				scale.x, scale.y, 
				rotation,
				reg.getRegionX(), reg.getRegionY(), 
				reg.getRegionWidth(), reg.getRegionHeight(), 
				viewDirection == VIEW_DIRECTION.LEFT, false);
		
	}
	
	public void setGreenHeartPowerup (boolean pickedUp) 
	{
		hasGreenHeartPowerup = pickedUp;
		if (pickedUp) 
		{
			timeLeftGreenHeartPowerup = Constants.ITEM_GREENHEART_POWERUP_DURATION;
		}	
	};
	
	public boolean hasGreenHeartPowerup () 
	{
		return hasGreenHeartPowerup && timeLeftGreenHeartPowerup > 0;
	}
	public TextureRegion getRegion() 
	{
		return regSurvivor;
	};
}
