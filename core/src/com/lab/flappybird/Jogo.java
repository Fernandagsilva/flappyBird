package com.lab.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Jogo extends ApplicationAdapter {
	
	@Override
	public void create () {
		Gdx.app.log("create", "Jogo iniciado");
	}

	@Override
	public void render () {
		Gdx.app.log("create", "Jogo renderizado");
	}
	
	@Override
	public void dispose () {
		Gdx.app.log("create", "Jogo finalizado");
	}
}
