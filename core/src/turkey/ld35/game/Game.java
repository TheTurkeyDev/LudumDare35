package turkey.ld35.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import turkey.ld35.entities.Entity;
import turkey.ld35.entities.Player;
import turkey.ld35.entities.ShapeProjectile;
import turkey.ld35.graphics.Draw2D;

public class Game
{
	public static final int PLAYER_MOVE_SPEED = 2;
	public static final int PROJECTILE_SPEED = 4;
	private List<Entity> entities = new ArrayList<Entity>();
	private Player player;
	private int score = 0;

	public void initgame()
	{
		entities.clear();
		player = new Player(new Vector2(0, 100));
		this.addEntity(player);
		score = 0;
	}

	public void render()
	{
		for(Entity entity : entities)
		{
			entity.render();
		}
		// Box
		Draw2D.drawRect(0, 0, Gdx.graphics.getWidth(), 100, Color.GRAY, true);

		// Selected Shape
		Draw2D.drawString(70, 90, "Selected Shape", 1.2f, Color.WHITE);
		Draw2D.drawTextured(100, 7, 64, 64, player.getSelectedShape().getTexture());

		// Health
		int xtemp = Gdx.graphics.getWidth() / 2;
		String health = "Health (" + player.getHealth() + "/100)";
		Draw2D.drawTextured(xtemp - 32, 40, player.getTexture());
		Draw2D.drawRect(xtemp - 100, 5f, 200, 25, Color.RED, true);
		Draw2D.drawRect(xtemp - 100, 5f, 200, 30, Color.GREEN, true);
		Draw2D.drawString(xtemp - (health.length() * 3), 27, health, 1f, Color.WHITE);

		// Score
		xtemp = Gdx.graphics.getWidth() - 150;
		String scoreTemp = "" + score;
		Draw2D.drawString(xtemp, 100, "Score", 1.5f, Color.WHITE);
		Draw2D.drawString(xtemp + 29 - (scoreTemp.length() * 5), 75, scoreTemp, 1.25f, Color.WHITE);

		// Wave
		String wave = "0";
		Draw2D.drawString(xtemp, 50, "Wave", 1.5f, Color.WHITE);
		Draw2D.drawString(xtemp + 29 - (wave.length() * 5), 25, wave, 1.25f, Color.WHITE);
	}

	public void update()
	{
		for(int i = entities.size() - 1; i >= 0; i--)
		{
			Entity ent = entities.get(i);
			ent.update();
			if(!ent.isAlive())
				entities.remove(i);
		}
	}

	public void addEntity(Entity entity)
	{
		this.entities.add(entity);
	}

	public boolean keyDown(int keycode)
	{
		if(keycode == Keys.UP)
		{
			player.setYVel(PLAYER_MOVE_SPEED);
			return true;
		}
		if(keycode == Keys.LEFT)
		{
			player.setXVel(-PLAYER_MOVE_SPEED);
			return true;
		}
		if(keycode == Keys.DOWN)
		{
			player.setYVel(-PLAYER_MOVE_SPEED);
			return true;
		}
		if(keycode == Keys.RIGHT)
		{
			player.setXVel(PLAYER_MOVE_SPEED);
			return true;
		}

		if(keycode == Keys.W)
		{
			this.addEntity(new ShapeProjectile(player.getSelectedShape(), player.getPositon(), new Vector2(0, PROJECTILE_SPEED)));
			return true;
		}
		if(keycode == Keys.A)
		{
			this.addEntity(new ShapeProjectile(player.getSelectedShape(), player.getPositon(), new Vector2(-PROJECTILE_SPEED, 0)));
			return true;
		}
		if(keycode == Keys.S)
		{
			this.addEntity(new ShapeProjectile(player.getSelectedShape(), player.getPositon(), new Vector2(0, -PROJECTILE_SPEED)));
			return true;
		}
		if(keycode == Keys.D)
		{
			this.addEntity(new ShapeProjectile(player.getSelectedShape(), player.getPositon(), new Vector2(PROJECTILE_SPEED, 0)));
			return true;
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
		if((keycode == Keys.LEFT && player.getXVel() < 0) || (keycode == Keys.RIGHT && player.getXVel() > 0))
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
