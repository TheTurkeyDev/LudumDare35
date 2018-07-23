package turkey.ld35.entities;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import turkey.ld35.game.Game;
import turkey.ld35.graphics.Renderer;

public class Perk extends Entity
{
	private PerkType perk;
	private Texture texture = new Texture("textures/perk.png");

	public Perk(Game game, Vector2 position, PerkType perk)
	{
		super(game);
		super.position = position;
		this.perk = perk;
	}

	public void render()
	{
		Renderer.drawTextured(this.position.x, this.position.y, texture);
	}

	public void update()
	{
		Player player = super.game.getPlayer();
		Rectangle perkBox = new Rectangle(this.position.x, this.position.y, 16, 16);
		Rectangle playerBox = new Rectangle(player.position.x, player.position.y, 32, 32);
		if(perkBox.overlaps(playerBox))
		{
			game.addPerkEffectToGame(perk);
			this.kill();
		}
	}

	public static PerkType getRandomPerk()
	{
		return PerkType.values()[Game.random.nextInt(PerkType.values().length)];
	}

	public enum PerkType
	{

		FastShoot, SpeedUp, SlowDown, Freeze, Teleport, Health;

		private Random random = new Random();

		public void triggerPerk(Game game)
		{
			switch(this)
			{
				case FastShoot:
					game.getPlayer().attackDelay = 15;
					break;
				case Freeze:
					for(Entity ent : game.getEntities())
						if(ent instanceof Monster)
							ent.setMoving(false);
					break;
				case SlowDown:
					Game.PLAYER_MOVE_SPEED -= 1;
					break;
				case SpeedUp:
					Game.PLAYER_MOVE_SPEED += 1;
					break;
				case Teleport:
					game.getPlayer().setPosition(new Vector2(random.nextInt(Gdx.graphics.getWidth() - 100) + 50, random.nextInt(Gdx.graphics.getHeight() - 200) + 150));
					break;
				case Health:
					game.getPlayer().heal();
					break;
				default:
					break;
			}
		}

		public void endPerk(Game game)
		{
			switch(this)
			{
				case FastShoot:
					game.getPlayer().attackDelay = 30;
					break;
				case Freeze:
					for(Entity ent : game.getEntities())
						if(ent instanceof Monster)
							ent.setMoving(true);
					break;
				case Health:
					break;
				case SlowDown:
					Game.PLAYER_MOVE_SPEED += 1;
					break;
				case SpeedUp:
					Game.PLAYER_MOVE_SPEED -= 1;
					break;
				case Teleport:
					break;
				default:
					break;

			}
		}
	}

}
