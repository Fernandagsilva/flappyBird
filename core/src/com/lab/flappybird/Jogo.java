package com.lab.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Jogo extends ApplicationAdapter {
	private int moveX = 0;
	private int moveY = 0;
	private SpriteBatch batch;
	private Texture bird;
	private Texture background;

	private float width;
	private float height;

	@Override
	public void create () {
		batch = new SpriteBatch();
		bird = new Texture("passaro1.png");
		background = new Texture("fundo.png");

		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
	}

	@Override
	public void render () {
		batch.begin();

		batch.draw(background, 0, 0, width, height);
		batch.draw(bird, moveX, moveY);

		moveX++;
		moveY++;
		batch.end();
	}
	
	@Override
	public void dispose () {
	}
}
