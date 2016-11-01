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
import com.douge.gdx.game.effects.AfterImage.Node;
import com.douge.gdx.game.objects.AbstractGameObject;
import com.douge.gdx.game.objects.Player;

public class Messages
{
	public Message head = null;
	public Message tail = null;
	
	public final int MAX_NUM_NODES = 6;
	public int currentNumNodes = 0;
	
	public void enqueue(Message newNode)
	{
		Message node = newNode;
		if(currentNumNodes != MAX_NUM_NODES)
		{
			if(head == null || tail == null)
			{
				head = node;
				tail = node;
			}
			else
			{
				tail.nextNode = node;
				tail = node;
			}
		}
		else
		{
			Message oldHead = head;
			head = node;
			head.nextNode = oldHead;
			setTail();
		}
		currentNumNodes++;
		if(currentNumNodes > MAX_NUM_NODES)
		{
			currentNumNodes = MAX_NUM_NODES;
		}
	}
	
	public void dequeue()
	{
		head = head.nextNode;
	}

	private void setTail() 
	{
		tail = head;
		for(int i = 0; tail != null && i < MAX_NUM_NODES; i++)
		{
			tail = tail.nextNode;
		}
	}
}
