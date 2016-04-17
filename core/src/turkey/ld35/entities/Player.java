package turkey.ld35.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import turkey.ld35.game.Game;
import turkey.ld35.game.Game.Shape;
import turkey.ld35.graphics.Animation;
import turkey.ld35.graphics.Draw2D;
import turkey.ld35.sounds.SoundManager;

public class Player extends Entity
{
	private Texture texture;
	private int xVel = 0, yVel = 0;

	private Shape selectedShape = Shape.Circle;

	private Animation currentAnimation;
	private Animation leftAnimation;
	private Animation rightAnimation;
	private Animation frontAnimation;
	private Animation backAnimation;

	public Player(Game game, Vector2 pos)
	{
		super(game);
		super.attackDelay = 30;
		super.position = pos;
		texture = new Texture("textures/player/playerTemp.png");
		super.health = 100;

		Texture[] leftTextures = new Texture[5];
		Texture[] rightTextures = new Texture[5];
		Texture[] frontTextures = new Texture[5];
		Texture[] backTextures = new Texture[5];
		for(int i = 1; i < 6; i++)
		{
			leftTextures[i - 1] = new Texture("textures/player/playerLeft" + i + ".png");
			rightTextures[i - 1] = new Texture("textures/player/playerRight" + i + ".png");
			frontTextures[i - 1] = new Texture("textures/player/playerFront" + i + ".png");
			backTextures[i - 1] = new Texture("textures/player/playerBack" + i + ".png");
		}

		leftAnimation = new Animation(10, true, leftTextures);
		rightAnimation = new Animation(10, true, rightTextures);
		frontAnimation = new Animation(10, true, frontTextures);
		backAnimation = new Animation(10, true, backTextures);

		currentAnimation = leftAnimation;
	}

	public void update()
	{
		super.update();
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
		
		if(xVel != 0 && yVel != 0)
		{
			this.position.y += yVel / 1.5;
			this.position.x += xVel / 1.5;
		}
		else
		{
			this.position.y += yVel;
			this.position.x += xVel;
		}
		
	}

	public void render()
	{
		currentAnimation.update();
		currentAnimation.setMoving(false);
		if(this.xVel < 0)
		{
			if(currentAnimation != leftAnimation)
				currentAnimation = leftAnimation;
			currentAnimation.setMoving(true);
		}
		else if(this.xVel > 0)
		{
			if(currentAnimation != rightAnimation)
				currentAnimation = rightAnimation;
			currentAnimation.setMoving(true);
		}
		
		if(this.yVel < 0)
		{
			if(currentAnimation != frontAnimation)
				currentAnimation = frontAnimation;
			currentAnimation.setMoving(true);
		}
		else if(this.yVel > 0)
		{
			if(currentAnimation != backAnimation)
				currentAnimation = backAnimation;
			currentAnimation.setMoving(true);
		}
		
		Draw2D.drawTextured(super.position.x, super.position.y, this.currentAnimation.getCurrentTexture());
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

	public void damage()
	{
		SoundManager.playSound(SoundManager.playerDamage, 1f);
		this.health -= 5;
	}

}