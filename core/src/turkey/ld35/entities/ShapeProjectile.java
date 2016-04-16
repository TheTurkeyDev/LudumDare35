package turkey.ld35.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import turkey.ld35.game.Game.Shape;
import turkey.ld35.graphics.Draw2D;

public class ShapeProjectile extends Entity
{
	private Shape projectileShape;

	private Vector2 velocity;

	public ShapeProjectile(Shape type, Vector2 position, Vector2 velocity)
	{
		this.projectileShape = type;
		super.position = position;
		this.velocity = velocity;
	}

	public void update()
	{
		this.position.add(this.velocity);
		if(this.position.x < 0 || this.position.x > Gdx.graphics.getWidth() || this.position.y < 0 || this.position.y > Gdx.graphics.getHeight())
			this.kill();
	}

	public void render()
	{
		Draw2D.drawTextured(super.position.x, super.position.y, this.projectileShape.getTexture());
	}

}
