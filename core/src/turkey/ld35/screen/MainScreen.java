package turkey.ld35.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import turkey.ld35.entities.Monster;
import turkey.ld35.graphics.Draw2D;
import turkey.ld35.graphics.Particle;
import turkey.ld35.gui.GuiButton;
import turkey.ld35.gui.GuiComponent;

public class MainScreen extends Screen
{
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
		super("Main Screen");
		this.addGuiComponent(new GuiButton(0, 100, 300, 338, 75, "START", button));
		this.addGuiComponent(new GuiButton(1, 100, 150, 338, 75, "TOP SCORES", button));
		this.addGuiComponent(new GuiButton(2, Gdx.graphics.getWidth() - 438, 300, 338, 75, "HELP", button));
		this.addGuiComponent(new GuiButton(3, Gdx.graphics.getWidth() - 438, 150, 338, 75, "SETTINGS", button));
	}

	public void onScreenLoad()
	{
		leftMonster = new Monster(null, new Vector2(425, 595));
		rightMonster = new Monster(null, new Vector2(Gdx.graphics.getWidth() - 475, 595));
	}
	
	public void onComponentClicked(GuiComponent guic)
	{
		if(guic.getId() == 0)
			ScreenManager.INSTANCE.setCurrentScreen("Game Screen");
		else if(guic.getId() == 1)
			ScreenManager.INSTANCE.setCurrentScreen("Leaderboard Screen");
		else if(guic.getId() == 2)
			ScreenManager.INSTANCE.setCurrentScreen("Help Screen");
		else if(guic.getId() == 3)
			ScreenManager.INSTANCE.setCurrentScreen("Settings Screen");
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
		super.update();
	}

	public void render()
	{
		Draw2D.drawTextured(0, 0, background);
		for(Particle particle : particles)
			particle.render();
		this.leftMonster.render();
		this.rightMonster.render();
		super.render();
	}

}
