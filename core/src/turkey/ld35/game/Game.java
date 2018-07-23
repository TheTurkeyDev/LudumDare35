package turkey.ld35.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import turkey.ld35.GameCore;
import turkey.ld35.entities.Entity;
import turkey.ld35.entities.Monster;
import turkey.ld35.entities.Perk.PerkType;
import turkey.ld35.entities.Player;
import turkey.ld35.entities.ShapeProjectile;
import turkey.ld35.graphics.Renderer;
import turkey.ld35.screen.GameScreen;
import turkey.ld35.screen.ScreenManager;
import turkey.ld35.util.CustomEntry;

public class Game
{
	public static int PLAYER_MOVE_SPEED = 3;
	public static int ENTITY_MOVE_SPEED = 3;
	public static int PROJECTILE_SPEED = 4;

	private boolean paused = false;
	private GameScreen gameScreen;

	public static Random random = new Random();

	private List<Entity> entities = new ArrayList<Entity>();
	private Player player;

	private List<CustomEntry<PerkType, Integer>> activePerks = new ArrayList<CustomEntry<PerkType, Integer>>();

	private static int score = 0;
	private static int wave = 1;
	private int bewteenWaveDelay = 180;
	private int spawnDelay = 300;
	private int spawnTick = 200;
	private int spawnsLeft = 10;

	// private Texture background = new Texture("textures/background.png");
	private Texture bottomBar;
	// private Texture border = new Texture("textures/border.png");
	private Texture healthBar;

	public Game(GameScreen gameScreen)
	{
		this.gameScreen = gameScreen;
		
		bottomBar = new Texture("textures/bottomBar.png");
		healthBar = new Texture("textures/healthBar.png");
	}

	public void initgame()
	{
		entities.clear();
		player = new Player(this, new Vector2((Gdx.graphics.getWidth() / 2), (Gdx.graphics.getHeight() / 2)));
		this.addEntity(player);
		score = 0;
		wave = 1;
		spawnDelay = 300;
		spawnTick = 200;
		spawnsLeft = 10;
		gameScreen.mainMenu.setVisible(false);
		gameScreen.restart.setVisible(false);
		gameScreen.resume.setVisible(false);
	}

	public void render()
	{
		if(this.paused)
		{
			Renderer.drawString(Renderer.gf, (Gdx.graphics.getWidth() / 2) - 200, (Gdx.graphics.getHeight() / 2) + 200, "PAUSED", 4f, Color.RED);
		}

		// Renderer.drawTextured(0, 100, background);
		for(Entity entity : entities)
		{
			entity.render();
		}

		// Wave info
		if(this.bewteenWaveDelay > 0)
		{
			Renderer.drawString(Renderer.gf, (Gdx.graphics.getWidth() / 2) - 150, (Gdx.graphics.getHeight() / 2) + 200, "WAVE", 4f, Color.RED);
			Renderer.drawString(Renderer.gf, (Gdx.graphics.getWidth() / 2) - 25, (Gdx.graphics.getHeight() / 2) + 75, "" + wave, 4f, Color.MAROON);
		}

		// Box
		Renderer.drawTextured(0, 0, bottomBar);

		// Selected Shape
		// Renderer.drawTextured(88, 0, 87, 85, border);
		Renderer.drawString(Renderer.ss, 73, 97, "Selected Shape", 0.5f, Color.WHITE);
		Renderer.drawTextured(100, 10, 64, 64, player.getSelectedShape().getTexture());

		// Perks
		Renderer.drawString(Renderer.ss, 250, 97, "Active Perks:", 0.5f, Color.WHITE);
		int i = 0;
		for(CustomEntry<PerkType, Integer> perk : this.activePerks)
		{
			Renderer.drawString(Renderer.ss, 250, 80 - (17 * i), perk.getKey().name() + " (" + ((Integer) perk.getValue() / 60) + ")", 0.5f, Color.WHITE);
			i++;
		}

		// Health
		int xtemp = Gdx.graphics.getWidth() / 2;
		String health = "Health (" + player.getHealth() + " / 100)";
		Renderer.drawTextured(xtemp - 32, 40, player.getTexture());
		Renderer.drawTextured(xtemp - 100, 5, healthBar);
		Renderer.drawRect(xtemp - 100 + Math.max(player.getHealth() * 2, 0), 5, 200 - Math.max(player.getHealth() * 2, 0), healthBar.getHeight(), Color.RED, true);
		Renderer.drawString(Renderer.ss, xtemp - (health.length() * 4), 25, health, 0.5f, Color.BLACK);

		// Score
		xtemp = Gdx.graphics.getWidth() - 250;
		String scoreTemp = "" + score;
		Renderer.drawString(Renderer.ss, xtemp, 90, "Score", 1, Color.WHITE);
		Renderer.drawString(Renderer.ss, xtemp + 40 - (scoreTemp.length() * 5), 55, scoreTemp, 1.5f, Color.WHITE);

		// Wave
		xtemp = Gdx.graphics.getWidth() - 100;
		String waveTemp = "" + wave;
		Renderer.drawString(Renderer.ss, xtemp, 90, "Wave", 1f, Color.WHITE);
		Renderer.drawString(Renderer.ss, xtemp + 35 - (waveTemp.length() * 5), 55, waveTemp, 1.5f, Color.WHITE);
	}

	public void update()
	{
		if(paused)
			return;
		if(player.getHealth() <= 0)
			GameCore.THE_GAME.setScreen(ScreenManager.getScreen("game_over_screen", false));

		for(int i = entities.size() - 1; i >= 0; i--)
		{
			Entity ent = entities.get(i);
			ent.update();
			if(!ent.isAlive())
				entities.remove(i);
		}

		for(int i = this.activePerks.size() - 1; i >= 0; i--)
		{
			CustomEntry<PerkType, Integer> perk = activePerks.get(i);
			perk.setValue(perk.getValue() - 1);
			if(perk.getValue() <= 0)
			{
				activePerks.remove(i);
				perk.getKey().endPerk(this);
			}
		}

		if(this.bewteenWaveDelay > 0)
		{
			this.bewteenWaveDelay--;
			return;
		}

		boolean monstersLeft = false;
		for(Entity ent : this.entities)
			if(ent instanceof Monster)
				monstersLeft = true;
		if(this.spawnsLeft <= 0 && !monstersLeft)
			this.nextWave();

		this.spawnTick++;
		if(this.spawnTick >= this.spawnDelay)
		{
			this.spawnTick = 0;
			if(this.spawnsLeft <= 0)
				return;
			Vector2 pos;
			// X random
			if(random.nextBoolean())
			{
				int xRandom = random.nextInt(Gdx.graphics.getWidth());
				// Top
				if(random.nextBoolean())
					pos = new Vector2(xRandom, Gdx.graphics.getHeight() + 50);
				else
					pos = new Vector2(xRandom, -50);
			}
			else
			{
				int yRandom = random.nextInt(Gdx.graphics.getHeight());
				// Left
				if(random.nextBoolean())
					pos = new Vector2(-50, yRandom);
				else
					pos = new Vector2(Gdx.graphics.getWidth(), yRandom);
			}
			this.addEntity(new Monster(this, pos));
			spawnsLeft--;
		}
	}

	public void nextWave()
	{
		this.bewteenWaveDelay = 180;
		wave++;
		this.spawnsLeft += (wave * 2);
		if(this.spawnDelay < 30)
			this.spawnDelay -= 1;
		else if(this.spawnDelay < 90)
			this.spawnDelay -= 10;
		else
			this.spawnDelay -= 30;

	}

	public void addScore(boolean kill)
	{
		score += 10 * (kill ? 2 : 1);
	}

	public void addEntity(Entity entity)
	{
		this.entities.add(entity);
	}

	public List<Entity> getEntities()
	{
		return this.entities;
	}

	public Player getPlayer()
	{
		return this.player;
	}

	public void addPerkEffectToGame(PerkType p)
	{
		p.triggerPerk(this);
		activePerks.add(new CustomEntry<PerkType, Integer>(p, 600));
	}

	public void pauseGame()
	{
		this.paused = !this.paused;
		gameScreen.mainMenu.setVisible(this.paused);
		gameScreen.restart.setVisible(this.paused);
		gameScreen.resume.setVisible(this.paused);
	}

	public boolean keyDown(int keycode)
	{
		if(keycode == Keys.ESCAPE)
		{
			this.pauseGame();
			return true;
		}

		if(this.paused)
			return false;

		if(keycode == Keys.W)
		{
			player.setYVel(PLAYER_MOVE_SPEED);
			return true;
		}
		else if(keycode == Keys.A)
		{
			player.setXVel(-PLAYER_MOVE_SPEED);
			return true;
		}
		else if(keycode == Keys.S)
		{
			player.setYVel(-PLAYER_MOVE_SPEED);
			return true;
		}
		else if(keycode == Keys.D)
		{
			player.setXVel(PLAYER_MOVE_SPEED);
			return true;
		}

		if(player.canAttack())
		{
			if(keycode == Keys.UP)
			{
				player.lastShootDir = 1;
				this.addEntity(new ShapeProjectile(this, player.getSelectedShape(), player.getPosition().add(16, 16), new Vector2(0, PROJECTILE_SPEED)));
				player.attack();
				return true;
			}
			else if(keycode == Keys.LEFT)
			{
				player.lastShootDir = 2;
				this.addEntity(new ShapeProjectile(this, player.getSelectedShape(), player.getPosition().add(16, 16), new Vector2(-PROJECTILE_SPEED, 0)));
				player.attack();
				return true;
			}
			else if(keycode == Keys.DOWN)
			{
				player.lastShootDir = 3;
				this.addEntity(new ShapeProjectile(this, player.getSelectedShape(), player.getPosition().add(16, 16), new Vector2(0, -PROJECTILE_SPEED)));
				player.attack();
				return true;
			}
			else if(keycode == Keys.RIGHT)
			{
				player.lastShootDir = 4;
				this.addEntity(new ShapeProjectile(this, player.getSelectedShape(), player.getPosition().add(16, 16), new Vector2(PROJECTILE_SPEED, 0)));
				player.attack();
				return true;
			}
		}

		if(keycode == Keys.SPACE)
		{
			player.nextShape();
		}

		return false;
	}

	public boolean keyUp(int keycode)
	{
		if((keycode == Keys.W && player.getYVel() > 0) || (keycode == Keys.S && player.getYVel() < 0))
		{
			player.setYVel(0);
			return true;

		}
		else if((keycode == Keys.A && player.getXVel() < 0) || (keycode == Keys.D && player.getXVel() > 0))
		{
			player.setXVel(0);
			return true;
		}
		return false;
	}

	public void dispose()
	{
		bottomBar.dispose();
		healthBar.dispose();
	}

	public enum Shape
	{
		Circle(new Texture("textures/circle.png")), Triangle(new Texture("textures/triangle.png")), Square(new Texture("textures/square.png"));

		private Texture texture;
		private static final Texture circleCBM = new Texture("textures/circleCBM.png");

		Shape(Texture texture)
		{
			this.texture = texture;
		}

		public Texture getTexture()
		{
			if(this.equals(Shape.Circle) && GameSettings.colorBlindMode)
				return circleCBM;
			return this.texture;
		}
	}

	public static int getScore()
	{
		return score;
	}

	public static int getWave()
	{
		return wave;
	}
}
