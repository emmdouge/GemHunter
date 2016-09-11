package com.douge.gdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class CameraHelper {
	private static final String TAG = CameraHelper.class.getName();
	
	private final float MAX_ZOOM_IN = 0.25f;
	private final float MAX_ZOOM_OUT = 10.0f;
	private float zoom; //= 1.0f;
	public OrthographicCamera camera;
	private Vector2 position;
	private Sprite target;
	
	public CameraHelper()
	{
		position = new Vector2();
		zoom = 1.0f;
		this.camera = camera;
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
			position.x = target.getX() + target.getOriginX();
			position.y = target.getY() + target.getOriginY();
		}
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
	
	public boolean targetIs(Sprite target) {
		return this.target == target;
	}
	
	public void applyTo(OrthographicCamera camera)
	{
		camera.position.x = position.x;
		camera.position.y = position.y;
		camera.zoom = zoom;
		camera.update();
	}
	
	public void setTarget(Sprite sprite)
	{
		target = sprite;
	}
	
	public boolean hasTarget()
	{
		return target != null;
	}
	
	public boolean hasTarget(Sprite target)
	{
		return hasTarget()&& this.target.equals(target);
	}
}
