package com.douge.gdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.douge.gdx.game.objects.Platform;

public class Level
{
	public int back;
	public Platform platform;
	public String filepath;
	public MessageQueue messages;
	public ParticleEffect particleEffect;
	
	public Level(int backColor, Platform platform, String filepath, MessageQueue messages, ParticleEffect particleEffect)
	{
		this.back = backColor;
		this.platform = platform;
		this.filepath = filepath;
		this.messages = messages;
		this.particleEffect = particleEffect;
		if(particleEffect != null)
		this.particleEffect.setPosition(0f, 32f);
	}
}
