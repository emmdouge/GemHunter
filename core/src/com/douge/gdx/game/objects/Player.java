package com.douge.gdx.game.objects;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.douge.gdx.game.Constants;
import com.douge.gdx.game.VIEW_DIRECTION;
import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.fx.AfterImage;
import com.douge.gdx.game.fx.AfterImage.Node;
import com.douge.gdx.game.playerstate.PlayerStateContext;
import com.douge.gdx.game.utils.GamePreferences;
import com.douge.gdx.game.screen.CharacterSkin;


public class Player extends AbstractGameObject
{
	public static final String TAG = Player.class.getName();

	public final float INVINCIBLE_TIME_MAX = 1f;
	
	public final float JUMP_TIME_MAX = 0.4f;
	public final float JUMP_TIME_MIN = 0.1f;
	public float timeJumping;
	public boolean hasJumpPowerup;
	public float timeLeftJumpPowerup;
	
	public final float DASH_TIME_MAX = 0.25f;
	public float timeDashing;
	
	public final float STUN_TIME_MAX = 0.50f;
	public float timeStunned;
	public boolean isStunned = false;
	

	public final float TIME_BETWEEN_ATTACKS = .25f;
	public float timeAttacking;
	
	public PlayerStateContext context;
	
	public AfterImage afterImageDash;
	public AfterImage afterImageJump;
	public AfterImage afterImageNeutral;
	
	public Animation currentAnimation;
	
	public VIEW_DIRECTION viewDirection;

	public boolean activeMovement = false;
	
	public int numLives;
	public int numFireballs;
	public Array<Fireball> fireballs;
	public ParticleEffect currentParticleEffect;

	public boolean isInvincible = false;
	public boolean drawRed = false;
	public int invincibleSwitch = 0;
	
	public float timeInvincibleLeft = 0;
	
	public Player() 
	{
		init();
	}
	public void init() 
	{
		dimension.set(1, 1);
		
		numLives = Constants.MAX_LIVES;
		numFireballs = Constants.FIREBALLS_START;
		fireballs = new Array<Fireball>();
		
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
		hasJumpPowerup = false;
		timeLeftJumpPowerup = 0;		
		
		currentAnimation = Assets.instance.survivor.fallingAnimation;
		currentParticleEffect = Assets.instance.survivor.dustParticles;
	};
	
	public void update(float deltaTime)
	{
		context.getCurrentState().execute(deltaTime);
		context.getCurrentState().stateTime += deltaTime;
		afterImageNeutral.addNode(this, currentAnimation.getKeyFrame(context.getCurrentState().stateTime));
		currentParticleEffect.update(deltaTime);
	}
	
	public void render(SpriteBatch batch)
	{	
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
		if (hasJumpPowerup) 
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

		
		currentParticleEffect.draw(batch);
		
		if(isInvincible)
		{
			boolean switchColor = invincibleSwitch%4 == 0;
			if(switchColor)
			drawRed = !drawRed;
			if(drawRed)
			{
				batch.setColor(Color.RED);
			}
			else
			{
				batch.setColor(Color.WHITE);
			}
			invincibleSwitch++;
		}
		else
		{
			// Reset color to white
			batch.setColor(1, 1, 1, 1);		
		}
		
		TextureRegion reg = currentAnimation.getKeyFrame(context.getCurrentState().stateTime, true);
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
	
	public void setJumpPowerup(boolean pickedUp) 
	{
		hasJumpPowerup = pickedUp;
		if(hasJumpPowerup) 
		{
			timeLeftJumpPowerup = Constants.ITEM_GREENHEART_POWERUP_DURATION;
		}	
	};
	
	public boolean hasJumpPowerup() 
	{
		return hasJumpPowerup && timeLeftJumpPowerup > 0;
	}
	public void decreaseInvincibleTimeLeft(float deltaTime) 
	{
		timeInvincibleLeft -= deltaTime;
		if(timeInvincibleLeft <= 0)
		{
			timeInvincibleLeft = 0;
			isInvincible = false;
		}
		else
		{
			isInvincible = true;
		}
	}

}
