package com.lab.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Jogo extends ApplicationAdapter {
	private int count = 0;
	private SpriteBatch batch;
	private Texture bird;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		bird = new Texture("passaro1.png");
	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(bird, 300, 500);
		batch.end();
	}
	
	@Override
	public void dispose () {
	}
}
