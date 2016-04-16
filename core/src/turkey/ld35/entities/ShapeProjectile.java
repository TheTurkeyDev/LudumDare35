package turkey.ld35.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import turkey.ld35.game.Game;
import turkey.ld35.game.Game.Shape;
import turkey.ld35.graphics.Draw2D;

public class ShapeProjectile extends Entity
{
	private Shape projectileShape;

	private Vector2 velocity;

	public ShapeProjectile(Game game, Shape type, Vector2 position, Vector2 velocity)
	{
		super(game);
		this.projectileShape = type;
		super.position = position;
		this.velocity = velocity;
	}

	public void update()
	{
		this.position.add(this.velocity);
		if(this.position.x < 0 || this.position.x > Gdx.graphics.getWidth() || this.position.y < 0 || this.position.y > Gdx.graphics.getHeight())
			this.kill();

		for(Entity ent : game.getEntities())
		{
			if(ent instanceof Monster)
			{
				Monster monster = (Monster) ent;
				Rectangle projectile = new Rectangle(this.position.x, this.position.y, 16, 16);
				Rectangle monsterBox = new Rectangle(monster.position.x - 16, monster.position.y - 16, 32, 32);
				if(monsterBox.overlaps(projectile))
				{
					boolean damaged = monster.damageByShape(this.projectileShape);
					if(damaged)
						game.addScore(!monster.isAlive);
					this.kill();
					return;
				}
			}
		}
	}

	public void render()
	{
		Draw2D.drawTextured(super.position.x, super.position.y, this.projectileShape.getTexture());
	}

}
