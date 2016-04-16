package turkey.ld35.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import turkey.ld35.entities.Entity;
import turkey.ld35.entities.Monster;
import turkey.ld35.entities.Player;
import turkey.ld35.entities.ShapeProjectile;
import turkey.ld35.graphics.Draw2D;

public class Game
{
	public static final int PLAYER_MOVE_SPEED = 3;
	public static final int ENTITY_MOVE_SPEED = 2;
	public static final int PROJECTILE_SPEED = 4;

	private boolean paused = false;

	private Random random = new Random();

	private List<Entity> entities = new ArrayList<Entity>();
	private Player player;

	private int score = 0;
	private int wave = 1;
	private int bewteenWaveDelay = 180;
	private int spawnDelay = 300;
	private int spawnTick = 200;
	private int spawnsLeft = 10;

	private Texture background = new Texture("textures/background.png");
	private Texture bottomBar = new Texture("textures/bottomBar.png");
	private Texture border = new Texture("textures/border.png");
	private Texture healthBar = new Texture("textures/healthBar.png");

	public void initgame()
	{
		entities.clear();
		player = new Player(this, new Vector2(0, 100));
		this.addEntity(player);
		score = 0;
	}

	public void render()
	{

		if(this.paused)
		{
			Draw2D.drawString((Gdx.graphics.getWidth() / 2) - 200, (Gdx.graphics.getHeight() / 2) + 200, "PAUSED", 4f, Color.RED);
		}

		// Draw2D.drawTextured(0, 100, background);
		for(Entity entity : entities)
		{
			entity.render();
		}

		// Wave info
		if(this.bewteenWaveDelay > 0)
		{
			Draw2D.drawString((Gdx.graphics.getWidth() / 2) - 150, (Gdx.graphics.getHeight() / 2) + 200, "WAVE", 4f, Color.RED);
			Draw2D.drawString((Gdx.graphics.getWidth() / 2) - 25, (Gdx.graphics.getHeight() / 2) + 75, "" + wave, 4f, Color.MAROON);
		}

		// Box
		Draw2D.drawTextured(0, 0, bottomBar);

		// Selected Shape
		//Draw2D.drawTextured(88, 0, 87, 85, border);
		Draw2D.drawString(73, 97, "Selected Shape", .5f, Color.WHITE);
		Draw2D.drawTextured(100, 10, 64, 64, player.getSelectedShape().getTexture());

		// Health
		int xtemp = Gdx.graphics.getWidth() / 2;
		String health = "Health (" + player.getHealth() + "/100)";
		Draw2D.drawTextured(xtemp - 32, 40, player.getTexture());
		Draw2D.drawTextured(xtemp - 100, 5f, healthBar);
		Draw2D.drawRect(xtemp - 100 + Math.max(player.getHealth() * 2, 0), 5f, 200 - Math.max(player.getHealth() * 2, 0), 30, Color.RED, true);
		Draw2D.drawString(xtemp - (health.length() * 4), 30, health, .6f, Color.BLACK);

		// Score
		xtemp = Gdx.graphics.getWidth() - 250;
		String scoreTemp = "" + score;
		Draw2D.drawString(xtemp, 90, "Score", 1, Color.WHITE);
		Draw2D.drawString(xtemp + 40 - (scoreTemp.length() * 5), 55, scoreTemp, 1.5f, Color.WHITE);

		// Wave
		xtemp = Gdx.graphics.getWidth() - 100;
		String waveTemp = "" + wave;
		Draw2D.drawString(xtemp, 90, "Wave", 1f, Color.WHITE);
		Draw2D.drawString(xtemp + 35 - (waveTemp.length() * 5), 55, waveTemp, 1.5f, Color.WHITE);
	}

	public void update()
	{
		if(paused)
			return;

		for(int i = entities.size() - 1; i >= 0; i--)
		{
			Entity ent = entities.get(i);
			ent.update();
			if(!ent.isAlive())
				entities.remove(i);
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
		this.wave++;
		this.spawnsLeft += this.wave * 5;
		int dec = 1 / (this.wave / 2);
		this.spawnDelay -= dec < .1 ? .1 : dec;
	}

	public void addScore(boolean kill)
	{
		this.score += 10 * (kill ? 2 : 1);
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

	public boolean keyDown(int keycode)
	{
		if(keycode == Keys.ESCAPE)
		{
			this.paused = !this.paused;
			return true;
		}

		if(this.paused)
			return false;

		if(keycode == Keys.UP)
		{
			player.setYVel(PLAYER_MOVE_SPEED);
			return true;
		}
		else if(keycode == Keys.LEFT)
		{
			player.setXVel(-PLAYER_MOVE_SPEED);
			return true;
		}
		else if(keycode == Keys.DOWN)
		{
			player.setYVel(-PLAYER_MOVE_SPEED);
			return true;
		}
		else if(keycode == Keys.RIGHT)
		{
			player.setXVel(PLAYER_MOVE_SPEED);
			return true;
		}

		if(player.canAttack())
		{
			if(keycode == Keys.W)
			{
				this.addEntity(new ShapeProjectile(this, player.getSelectedShape(), player.getPositon().add(16, 16), new Vector2(0, PROJECTILE_SPEED)));
				player.attack();
				return true;
			}
			else if(keycode == Keys.A)
			{
				this.addEntity(new ShapeProjectile(this, player.getSelectedShape(), player.getPositon().add(16, 16), new Vector2(-PROJECTILE_SPEED, 0)));
				player.attack();
				return true;
			}
			else if(keycode == Keys.S)
			{
				this.addEntity(new ShapeProjectile(this, player.getSelectedShape(), player.getPositon().add(16, 16), new Vector2(0, -PROJECTILE_SPEED)));
				player.attack();
				return true;
			}
			else if(keycode == Keys.D)
			{
				this.addEntity(new ShapeProjectile(this, player.getSelectedShape(), player.getPositon().add(16, 16), new Vector2(PROJECTILE_SPEED, 0)));
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
		if((keycode == Keys.UP && player.getYVel() > 0) || (keycode == Keys.DOWN && player.getYVel() < 0))
		{
			player.setYVel(0);
			return true;

		}
		else if((keycode == Keys.LEFT && player.getXVel() < 0) || (keycode == Keys.RIGHT && player.getXVel() > 0))
		{
			player.setXVel(0);
			return true;
		}
		return false;
	}

	public enum Shape
	{
		Circle(new Texture("textures/circle.png")), Triangle(new Texture("textures/triangle.png")), Square(new Texture("textures/square.png"));

		private Texture texture;

		Shape(Texture texture)
		{
			this.texture = texture;
		}

		public Texture getTexture()
		{
			return this.texture;
		}
	}
}
