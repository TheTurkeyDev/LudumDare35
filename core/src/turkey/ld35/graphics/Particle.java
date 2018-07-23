package turkey.ld35.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Particle
{
	private Vector2 position;
	private Vector2 velocity;
	private boolean isAlive = true;
	private Texture texture;
	private int width = 16, height = 16;
	private boolean isonMap = true;

	public Particle(Vector2 position, Vector2 velocity, Texture texture, int width, int height)
	{
		this.position = position;
		this.velocity = velocity;
		this.texture = texture;
		this.width = width;
		this.height = height;
	}

	public void update()
	{
		this.position.add(this.velocity);
		if(this.position.x < 0 || this.position.x > Gdx.graphics.getWidth() || this.position.y < 0 || this.position.y > Gdx.graphics.getHeight())
		{
			if(this.isonMap)
				this.kill();

		}
		else
		{
			this.isonMap = true;
		}
	}

	public void render()
	{
		Renderer.drawTextured(this.position.x, this.position.y, width, height, texture);
	}

	public Vector2 getPositon()
	{
		return this.position.cpy();
	}

	public boolean isAlive()
	{
		return this.isAlive;
	}

	public void kill()
	{
		this.isAlive = false;
	}

	public void setOffMap()
	{
		this.isonMap = false;
	}
}
