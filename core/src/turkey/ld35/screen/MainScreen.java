package turkey.ld35.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import turkey.ld35.GameCore;
import turkey.ld35.entities.Monster;
import turkey.ld35.graphics.Particle;
import turkey.ld35.graphics.Renderer;

public class MainScreen implements Screen
{
	private Stage stage;
	private Table table;

	private Random random = new Random();
	private Texture background = new Texture("backgrounds/mainScreen.png");
	private Texture button = new Texture("textures/button.png");

	private List<Particle> particles = new ArrayList<Particle>();
	private Monster leftMonster;
	private Monster rightMonster;

	private int spawnDelay = 20;
	private int spawnTick = 0;

	private long tick = 0;

	public MainScreen()
	{
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		table = new Table();
		table.setFillParent(true);
		stage.addActor(table);

		// Add widgets to the table here.

		TextButtonStyle tbs = new TextButtonStyle();
		tbs.up = new TextureRegionDrawable(new TextureRegion(button));
		tbs.down = new TextureRegionDrawable(new TextureRegion(button));
		tbs.font = Renderer.ss;

		TextButton start = new TextButton("START", tbs);
		start.getLabel().setFontScale(1f);
		start.addListener(new ClickListener()
		{
			public void clicked(InputEvent e, float x, float y)
			{
				GameCore.THE_GAME.setScreen(ScreenManager.getScreen("game_screen", false));
			}
		});
		table.add(start).pad(100).padBottom(50);

		TextButton help = new TextButton("HELP", tbs);
		help.getLabel().setFontScale(1f);
		help.addListener(new ClickListener()
		{
			public void clicked(InputEvent e, float x, float y)
			{
				GameCore.THE_GAME.setScreen(ScreenManager.getScreen("help_screen", false));
			}
		});
		table.add(help).pad(100).padBottom(50);

		table.row();

		TextButton scores = new TextButton("TOP SCORES", tbs);
		scores.getLabel().setFontScale(1f);
		scores.addListener(new ClickListener()
		{
			public void clicked(InputEvent e, float x, float y)
			{
				GameCore.THE_GAME.setScreen(ScreenManager.getScreen("leader_board_screen", false));
			}
		});
		table.add(scores).pad(100).padBottom(50);

		TextButton settings = new TextButton("SETTINGS", tbs);
		settings.getLabel().setFontScale(1f);
		settings.addListener(new ClickListener()
		{
			public void clicked(InputEvent e, float x, float y)
			{
				GameCore.THE_GAME.setScreen(ScreenManager.getScreen("settings_screen", false));
			}
		});
		table.add(settings).pad(100).padBottom(50);

		table.bottom();
	}

	@Override
	public void show()
	{
		this.tick = 0;
		leftMonster = new Monster(null, new Vector2(425, 595));
		rightMonster = new Monster(null, new Vector2(Gdx.graphics.getWidth() - 425, 595));
	}

	public void update()
	{
		for(int i = particles.size() - 1; i >= 0; i--)
		{
			Particle particle = particles.get(i);
			particle.update();
			if(!particle.isAlive())
				particles.remove(i);
		}

		this.spawnTick++;
		if(this.spawnTick >= this.spawnDelay)
		{
			this.spawnTick = 0;
			Vector2 pos;
			Vector2 vel;
			// X random
			if(random.nextBoolean())
			{
				int xRandom = random.nextInt(Gdx.graphics.getWidth());
				// Top
				if(random.nextBoolean())
				{
					pos = new Vector2(xRandom, Gdx.graphics.getHeight() + 50);
					vel = new Vector2(random.nextInt(7) - 3, -random.nextInt(5) + 3);
				}
				else
				{
					pos = new Vector2(xRandom, -50);
					vel = new Vector2(random.nextInt(7) - 3, random.nextInt(5) + 3);
				}
			}
			else
			{
				int yRandom = random.nextInt(Gdx.graphics.getHeight());
				// Left
				if(random.nextBoolean())
				{
					pos = new Vector2(-50, yRandom);
					vel = new Vector2(random.nextInt(5) + 3, random.nextInt(7) - 3);
				}
				else
				{
					pos = new Vector2(Gdx.graphics.getWidth() + 50, yRandom);
					vel = new Vector2(-random.nextInt(5) + 3, random.nextInt(7) - 3);
				}
			}
			Particle particle = new Particle(pos, vel, Monster.getRandomShape(null).getTexture(), 32, 32);
			particle.setOffMap();
			this.particles.add(particle);
		}

		tick++;

		if(tick % 120 == 0)
		{
			leftMonster = new Monster(null, leftMonster.getPosition());
			rightMonster = new Monster(null, rightMonster.getPosition());
		}

		float yoff = (float) (Math.cos(tick / 15) * 2);
		this.leftMonster.addPosition(new Vector2(0, yoff));
		this.rightMonster.addPosition(new Vector2(0, yoff));
	}

	@Override
	public void render(float delta)
	{
		update();
		Renderer.drawTextured(0, 0, background, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		for(Particle particle : particles)
			particle.render();
		this.leftMonster.render();
		this.rightMonster.render();
		Renderer.end();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		Renderer.begin();
	}

	@Override
	public void dispose()
	{
		background.dispose();
		button.dispose();
		stage.dispose();
	}

	@Override
	public void resize(int width, int height)
	{
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void pause()
	{

	}

	@Override
	public void resume()
	{

	}

	@Override
	public void hide()
	{

	}
}
