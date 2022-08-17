package com.lab.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class Jogo extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture[] birds;
	private Texture background;
	private Texture pipeBottom;
	private Texture pipeTop;

	private float width;
	private float height;
	private float variation = 0;
	private  float gravity = 0;
	private float startPositionY = 0;
	private float pipeWidthPosition;
	private float pipeHeightPosition;
	private float pipesSpaceBetween;
	private Random random;

	@Override
	public void create () {
		initializeTextures();
		initializeObject();
	}

	@Override
	public void render () {
		verifyState();
		drawTextures();
	}

	private void drawTextures(){
		batch.begin();

		batch.draw(background, 0, 0, width, height);
		batch.draw(birds[(int)  variation], 30, startPositionY);
		batch.draw(pipeBottom, pipeWidthPosition, height/2 - pipeBottom.getHeight() - pipesSpaceBetween/2 + pipeHeightPosition);
		batch.draw(pipeTop, pipeWidthPosition, height/2 + pipesSpaceBetween/2 + pipeHeightPosition);

		batch.end();

	}

	private void verifyState(){
		pipeWidthPosition -= Gdx.graphics.getDeltaTime() * 200;
		if(pipeWidthPosition < -pipeTop.getWidth() ){
			pipeWidthPosition = width;
			pipeHeightPosition = random.nextInt(800) -400;

		}

		boolean touched = Gdx.input.justTouched();
		if(touched){
			gravity = -15;
		}

		if(startPositionY > 0 || gravity < 0)
			startPositionY = startPositionY - gravity;

		variation += Gdx.graphics.getDeltaTime() * 10;
		if(variation > 3)
			variation = 0;

		gravity++;
	}

	private void initializeTextures(){
		birds = new Texture[3];
		birds[0] = new Texture("passaro1.png");
		birds[1] = new Texture("passaro2.png");
		birds[2] = new Texture("passaro3.png");

		background = new Texture("fundo.png");
		pipeTop = new Texture("cano_topo_maior.png");
		pipeBottom = new Texture("cano_baixo_maior.png");
	}

	private void initializeObject(){
		batch = new SpriteBatch();
		random = new Random();

		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		startPositionY = height/2;
		pipeWidthPosition = width;
		pipesSpaceBetween = 220;
	}
	
	@Override
	public void dispose () {
	}
}
