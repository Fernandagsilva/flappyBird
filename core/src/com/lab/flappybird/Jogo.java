package com.lab.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

public class Jogo extends ApplicationAdapter {
	/* Textures */
	private SpriteBatch batch;
	private Texture[] birds;
	private Texture background;
	private Texture pipeBottom;
	private Texture pipeTop;
	private Texture gameOver;

	/* Shapes */
	private Circle birdCircle;
	private Rectangle rectanglePipeTop;
	private Rectangle rectanglePipeBottom;

	/* Positions */
	private float width;
	private float height;
	private float variation = 0;
	private float gravity = 0;
	private float startPositionHeight = 0;
	private float pipeWidthPosition;
	private float pipeHeightPosition;
	private float pipesSpaceBetween;
	private Random random;
	private boolean touchPipe;

	/* Texts */
	BitmapFont textPoints;
	BitmapFont restartText;
	BitmapFont highestScoreText;
	private String gameState = "startGame";

	/* Sounds config  */
	Sound birdFlyingSound;
	Sound collisionSound;
	Sound pointSound;

	/* Preferences  */
	Preferences preferences;
	private int points = 0;
	private int highestScore = 0;

	/* Config camera */
	private OrthographicCamera camera;
	private Viewport viewport;
	private final float VIRTUAL_WIDTH = 720;
	private final float VIRTUAL_HEIGHT = 1280;

	@Override
	public void create () {
		initializeTextures();
		initializeObject();
	}

	@Override
	public void render () {

		/* clear lasts frames  */
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		verifyGameState();
		validatePoints();
		drawTextures();
		detectCollisions();
	}

	private void drawTextures(){
		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		/* Draw birds and pipes positions */
		batch.draw(background, 0, 0, width, height);
		batch.draw(birds[(int)  variation], 50, startPositionHeight);
		batch.draw(pipeBottom, pipeWidthPosition, height/2 - pipeBottom.getHeight() - pipesSpaceBetween/2 + pipeHeightPosition);
		batch.draw(pipeTop, pipeWidthPosition, height/2 + pipesSpaceBetween/2 + pipeHeightPosition);

		/* Draw score text position */
		textPoints.draw(batch, String.valueOf(points), width/2, height - 110);

		/* Draw game over texts positions */
		if(gameState == "collision"){
			batch.draw(gameOver, (width - gameOver.getWidth())/2, height/2);
			restartText.draw(batch, "Touch to Restart!", width/2 -140, height/2 - gameOver.getHeight()/2);
			highestScoreText.draw(batch, "Highest score: "+ highestScore + " points", width/2 -140, height/2- gameOver.getHeight());
		}

		batch.end();

	}

	private void verifyGameState(){
		boolean touched = Gdx.input.justTouched();

		/* Verify game state */
		switch (gameState){
			/* Execute case player is starting the game */
			case "startGame":
				if(touched){
					gravity = -15;
					gameState = "playingGame";
					birdFlyingSound.play();
				}
				break;
			/* Execute case player is playing the game */
			case "playingGame":
				/* Apply touch event while playing */
				if(touched){
					gravity = -15;
					birdFlyingSound.play();
				}

				/* Create a random position to pipes */
				pipeWidthPosition -= Gdx.graphics.getDeltaTime() * 200;
				if(pipeWidthPosition < -pipeTop.getWidth() ){
					pipeWidthPosition = width;
					pipeHeightPosition = random.nextInt(400) -200;
					touchPipe = false;
				}

				if(startPositionHeight > 0 || touched)
					startPositionHeight = startPositionHeight - gravity;

				gravity++;
				break;
			/* Execute case have a collision */
			case "collision":
				/* make bird fall */
				if(startPositionHeight > 0 || touched)
					startPositionHeight = startPositionHeight - gravity;
				gravity++;

				/* verify highest score */
				if(points > highestScore){
					highestScore = points;
					preferences.putInteger("highestScore", highestScore);
				}

				/* Apply touch event to restart game */
				if(touched){
					gameState = "startGame";
					points = 0;
					gravity = 0;
					startPositionHeight = height/2;
					pipeWidthPosition = width;
				}
				break;
			default:
				break;
		}
	}

	private void detectCollisions(){
		/* Apply shapes to detect collisions from bird to pipes */
		birdCircle.set(50 + birds[0].getWidth()/2, startPositionHeight + birds[0].getHeight()/2, birds[0].getWidth()/2);
		rectanglePipeBottom.set(pipeWidthPosition, height/2 - pipeBottom.getHeight() - pipesSpaceBetween/2 + pipeHeightPosition, pipeBottom.getWidth(), pipeBottom.getHeight());
		rectanglePipeTop.set(pipeWidthPosition, height/2 + pipesSpaceBetween/2 + pipeHeightPosition, pipeTop.getWidth(), pipeTop.getHeight());

		/* Detect a collision on pipe top or pipe bottom */
		boolean touchPipeTop = Intersector.overlaps(birdCircle, rectanglePipeTop);
		boolean touchPipeBottom = Intersector.overlaps(birdCircle, rectanglePipeBottom);

		if(touchPipeBottom || touchPipeTop){
			if(gameState == "playingGame"){
				collisionSound.play();
				gameState = "collision";
			}

		}
	}

	/* Update score to a player */
	private void validatePoints(){
		if(pipeWidthPosition < 50-birds[0].getWidth()){
			if(!touchPipe){
				points++;
				touchPipe = true;
				pointSound.play();
			}
		}

		/* creates variations numbers to change texture from the bird */
		variation += Gdx.graphics.getDeltaTime() * 20;
		if(variation > 3)
			variation = 0;
	}

	private void initializeTextures(){
		/* Creates bird texture */
		birds = new Texture[3];
		birds[0] = new Texture("passaro1.png");
		birds[1] = new Texture("passaro2.png");
		birds[2] = new Texture("passaro3.png");

		/* Creates background texture */
		background = new Texture("fundo.png");

		/* Creates pipes textures  */
		pipeTop = new Texture("cano_topo_maior.png");
		pipeBottom = new Texture("cano_baixo_maior.png");

		/* Creates game over textures */
		gameOver = new Texture("game_over.png");
	}

	private void initializeObject(){
		batch = new SpriteBatch();
		random = new Random();

		/* Initialize bird and pipes positions */
		width = VIRTUAL_WIDTH;
		height = VIRTUAL_HEIGHT;
		startPositionHeight = height/2;
		pipeWidthPosition = width;
		pipesSpaceBetween = 350;

		/* Config points text position */
		textPoints = new BitmapFont();
		textPoints.setColor(Color.WHITE);
		textPoints.getData().setScale(10);

		/* Config restart text position */
		restartText = new BitmapFont();
		restartText.setColor(Color.GREEN);
		restartText.getData().setScale(2);

		/* Config highest score text position */
		highestScoreText = new BitmapFont();
		highestScoreText.setColor(Color.RED);
		highestScoreText.getData().setScale(2);

		/* Initialize shapes to bird and pipes position */
		birdCircle = new Circle();
		rectanglePipeTop = new Rectangle();
		rectanglePipeBottom = new Rectangle();

		/* Initialize sounds */
		birdFlyingSound = Gdx.audio.newSound(Gdx.files.internal("som_asa.wav"));
		collisionSound = Gdx.audio.newSound(Gdx.files.internal("som_batida.wav"));
		pointSound = Gdx.audio.newSound(Gdx.files.internal("som_pontos.wav"));

		/* Config preferences */
		preferences = Gdx.app.getPreferences("flappyBird");
		highestScore = preferences.getInteger("highestScore", 0);

		/* Config camera */
		camera = new OrthographicCamera();
		camera.position.set(VIRTUAL_WIDTH/2, VIRTUAL_HEIGHT/2, 0);
		viewport = new StretchViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
	}

	@Override
	public void resize(int width, int height){
		viewport.update(width, height);
	}
	
	@Override
	public void dispose () {
	}
}
