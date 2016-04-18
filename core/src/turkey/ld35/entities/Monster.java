package turkey.ld35.entities;

import java.util.Random;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import turkey.ld35.game.Game;
import turkey.ld35.game.Game.Shape;
import turkey.ld35.graphics.Draw2D;
import turkey.ld35.sounds.SoundManager;

public class Monster extends Entity
{
	private static Random random = new Random();
	private Shape ears;
	private Shape body;
	private Shape eyes;

	boolean bodyLeft = true;
	private int earsLeft = 2;
	private int eyesLeft = 2;

	public Monster(Game game, Vector2 pos)
	{
		super(game);
		this.body = getRandomShape(null);
		this.ears = getRandomShape(null);
		this.eyes = getRandomShape(this.body);
		this.position = pos;
	}

	public void update()
	{
		if(!super.canMove)
			return;

		super.update();
		Player player = game.getPlayer();
		int xToMove = 0;
		int yToMove = 0;
		if(player.position.x + 32 < this.position.x)
			xToMove -= Game.ENTITY_MOVE_SPEED;
		if(player.position.x + 32 > this.position.x)
			xToMove += Game.ENTITY_MOVE_SPEED;
		if(player.position.y + 24 < this.position.y)
			yToMove -= Game.ENTITY_MOVE_SPEED;
		if(player.position.y + 24 > this.position.y)
			yToMove += Game.ENTITY_MOVE_SPEED;

		if(xToMove != 0 && yToMove != 0)
		{
			xToMove /= 1.5;
			yToMove /= 1.5;
		}

		this.position.x += xToMove;
		this.position.y += yToMove;

		if(this.canAttack())
		{
			Rectangle monsterBox = new Rectangle(this.position.x, this.position.y, 16, 16);
			Rectangle playerBox = new Rectangle(player.position.x + 32, player.position.y + 24, 32, 32);
			if(monsterBox.overlaps(playerBox))
			{
				this.attack();
				player.damage();
			}
		}
	}

	public void render()
	{
		if(this.bodyLeft)
			Draw2D.drawTextured(this.position.x - 16, this.position.y - 16, 32, 32, this.body.getTexture());
		if(this.eyesLeft > 0)
			Draw2D.drawTextured(this.position.x - 12, this.position.y - 8, 8, 8, this.eyes.getTexture());
		if(this.eyesLeft > 1)
			Draw2D.drawTextured(this.position.x + 4, this.position.y - 8, 8, 8, this.eyes.getTexture());
		if(this.earsLeft > 0)
			Draw2D.drawTextured(this.position.x - 16, this.position.y + 8, 10, 10, this.ears.getTexture());
		if(this.earsLeft > 1)
			Draw2D.drawTextured(this.position.x + 6, this.position.y + 8, 10, 10, this.ears.getTexture());
	}

	public boolean damageByShape(Shape shape)
	{
		boolean damaged = false;
		if(this.ears.equals(shape) && this.earsLeft > 0)
		{
			this.earsLeft--;
			damaged = true;
		}
		else if(this.eyes.equals(shape) && this.eyesLeft > 0)
		{
			this.eyesLeft--;
			damaged = true;
		}
		else if(this.body.equals(shape))
		{
			this.bodyLeft = false;
			damaged = true;
		}

		if(damaged)
			SoundManager.playSound(SoundManager.monsterDamage, 1f);

		if(this.earsLeft == 0 && this.eyesLeft == 0 && !this.bodyLeft)
		{
			if(random.nextInt(10) == 4)
				this.game.addEntity(new Perk(super.game, this.position.cpy(), Perk.getRandomPerk()));
			this.kill();
			return true;
		}

		return damaged;

	}

	public static Shape getRandomShape(Shape reject)
	{
		Shape shapeToReturn = null;
		while(shapeToReturn == null || shapeToReturn == reject)
		{
			int choice = random.nextInt(3);
			if(choice == 0)
				shapeToReturn = Shape.Circle;
			else if(choice == 1)
				shapeToReturn = Shape.Square;
			else
				shapeToReturn = Shape.Triangle;
		}
		return shapeToReturn;
	}
}
