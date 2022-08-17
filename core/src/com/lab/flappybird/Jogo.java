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
	private Texture[] birds;
	private Texture background;

	private float width;
	private float height;
	private float variation = 0;
	private  float gravity = 0;
	private float startPositionY = 0;

	@Override
	public void create () {
		batch = new SpriteBatch();
		birds = new Texture[3];
		birds[0] = new Texture("passaro1.png");
		birds[1] = new Texture("passaro2.png");
		birds[2] = new Texture("passaro3.png");

		background = new Texture("fundo.png");

		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		startPositionY = height/2;
	}

	@Override
	public void render () {
		batch.begin();

		if(variation > 3)
			variation = 0;

		if(Gdx.input.justTouched()){
			gravity = -15;
		}

		if(startPositionY > 0 || gravity < 0)
			startPositionY = startPositionY - gravity;

		batch.draw(background, 0, 0, width, height);
		batch.draw(birds[(int)  variation], 30, startPositionY);

		variation += Gdx.graphics.getDeltaTime() * 10;

		gravity++;
		moveX++;
		moveY++;

		batch.end();
	}
	
	@Override
	public void dispose () {
	}
}
