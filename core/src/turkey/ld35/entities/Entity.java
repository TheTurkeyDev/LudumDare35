package turkey.ld35.entities;

import com.badlogic.gdx.math.Vector2;

public class Entity
{
	protected Vector2 position;
	protected int width, height;
	protected boolean isAlive = true;
	
	protected int health;

	public Entity()
	{

	}

	public void update()
	{

	}

	public void render()
	{

	}

	public Vector2 getPositon()
	{
		return this.position.cpy();
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
