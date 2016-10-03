package com.douge.gdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.douge.gdx.game.objects.AbstractGameObject;
import com.douge.gdx.game.objects.Astronaut;

public class CameraHelper {
	private static final String TAG = CameraHelper.class.getName();
	
	private final float MAX_ZOOM_IN = 0.25f;
	private final float MAX_ZOOM_OUT = 10.0f;
	public boolean clampCam = true;
	private float zoom; 
	public OrthographicCamera camera;
	private Vector2 position;
	private AbstractGameObject target;
	
	public CameraHelper()
	{
		position = new Vector2();
		zoom = 1.0f;
	}
	
	public void update(float deltaTime)
	{
		/**
		 * adding the origin center(assuming the sprite's origin has been set to its center
		 * will center the sprite completely
		 * if not added it will be a bit off
		 */
		if(target != null)
		{
			position.x = target.position.x + target.origin.x;
			position.y = target.position.y + target.origin.y;
		}
		
	    // Prevent camera from moving down too far 
	    position.y = Math.max(-1f, position.y); 
	}
	
	public void setPosition(float x, float y)
	{
		position.set(x, y);
	}
	
	public Vector2 getPosition()
	{
		return position;
	}

	public float getZoom()
	{
		return zoom;
	}
	
	public void addZoom(float amountToAdd)
	{
		setZoom(zoom +  amountToAdd);
	}
	
	public void setZoom(float zoom)
	{
		this.zoom = MathUtils.clamp(zoom, MAX_ZOOM_IN, MAX_ZOOM_OUT);
	}
	
	public boolean targetIs(AbstractGameObject target) {
		return this.target == target;
	}
	
	public void applyTo(OrthographicCamera camera)
	{
		if(clampCam == true)
		{
			if(position.x < Constants.CAMERA_X_MIN)
			{
				position.x = Constants.CAMERA_X_MIN;
			}
			if(position.x > Constants.CAMERA_X_MAX)
			{
				position.x = Constants.CAMERA_X_MAX;
			}
			if(position.y < Constants.CAMERA_Y_MIN)
			{
				position.y = Constants.CAMERA_Y_MIN;
			}
			if(position.y > Constants.CAMERA_Y_MAX)
			{
				position.y = Constants.CAMERA_Y_MAX;
			}
		}
		
		camera.position.x = position.x;
		camera.position.y = position.y;
		camera.zoom = zoom;
		camera.update();
	}
	
	public void setTarget(AbstractGameObject object)
	{
		target = object;
	}
	
	public boolean hasTarget()
	{
		return target != null;
	}
	
	public boolean hasTarget(AbstractGameObject astronaut)
	{
		return hasTarget()&& this.target.equals(astronaut);
	}


}
