package turkey.ld35.entities;

import com.badlogic.gdx.math.Vector2;

import turkey.ld35.game.Game;
import turkey.ld35.sounds.SoundManager;

public class Entity
{
	protected Vector2 position;
	protected int width, height;
	protected boolean isAlive = true;
	protected Game game;

	protected int health;

	protected int attackDelay = 60;
	protected int attackTick = 0;

	public Entity(Game game)
	{
		this.game = game;
	}

	public void update()
	{
		if(this.attackTick > 0)
			this.attackTick--;
	}

	public void attack()
	{
		SoundManager.playSound(SoundManager.playerShoot, 1f);
		this.attackTick = this.attackDelay;
	}
	
	public boolean canAttack()
	{
		return this.attackTick == 0;
	}

	public void render()
	{

	}

	public Vector2 getPosition()
	{
		return this.position.cpy();
	}
	
	public void addPosition(Vector2 offset)
	{
		this.position.add(offset);
	}

	public int getHealth()
	{
		return this.health;
	}

	public boolean isAlive()
	{
		return this.isAlive;
	}

	public void kill()
	{
		this.isAlive = false;
	}
}
