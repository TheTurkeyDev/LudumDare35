package turkey.ld35.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import turkey.ld35.game.Game.Shape;
import turkey.ld35.graphics.Draw2D;

public class Player extends Entity
{
	private Texture texture;
	private int xVel = 0, yVel = 0;

	private Shape selectedShape = Shape.Circle;

	public Player(Vector2 pos)
	{
		super.position = pos;
		texture = new Texture("textures/playerTemp.png");
		super.health = 100;
	}

	public void update()
	{
		
		if(this.position.x + xVel > Gdx.graphics.getWidth() - 64)
		{
			this.position.x = Gdx.graphics.getWidth() - 64;
			this.xVel = 0;
		}
		else if(this.position.x + xVel < 0)
		{
			this.position.x = 0;
			this.xVel = 0;
		}
		else
		{
			this.position.x += xVel;
		}
		
		
		if(this.position.y + yVel > Gdx.graphics.getHeight() - 64)
		{
			this.position.y = Gdx.graphics.getHeight() - 64;
			this.yVel = 0;
		}
		else if(this.position.y + yVel < 100)
		{
			this.position.y = 100;
			this.yVel = 0;
		}
		else
		{
			this.position.y += yVel;
		}
	}

	public void render()
	{
		Draw2D.drawTextured(super.position.x, super.position.y, texture);
	}

	public void setXVel(int vel)
	{
		this.xVel = vel;
	}

	public int getXVel()
	{
		return this.xVel;
	}

	public void setYVel(int vel)
	{
		this.yVel = vel;
	}

	public int getYVel()
	{
		return this.yVel;
	}

	public void nextShape()
	{
		switch(this.selectedShape)
		{
			case Circle:
				this.selectedShape = Shape.Square;
				break;
			case Square:
				this.selectedShape = Shape.Triangle;
				break;
			case Triangle:
				this.selectedShape = Shape.Circle;
				break;
			default:
				this.selectedShape = Shape.Circle;
				break;
		}
	}

	public Shape getSelectedShape()
	{
		return this.selectedShape;
	}
	
	public Texture getTexture()
	{
		return this.texture;
	}

}