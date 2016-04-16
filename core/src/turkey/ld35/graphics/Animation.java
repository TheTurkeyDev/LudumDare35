package turkey.ld35.graphics;

import com.badlogic.gdx.graphics.Texture;

public class Animation
{
	private int stage = 0;
	private int totalStages;
	private int stageDelay;
	private int stageDelayTick;
	private int stageDirection = 1;

	private int idelTexture = 2;
	private boolean moving = false;

	private boolean loops;

	private Texture[] textures;

	private boolean running = true;

	public Animation(int delay, boolean loops, Texture... textures)
	{
		this.totalStages = textures.length - 1;
		this.stageDelay = delay;

		this.loops = loops;

		this.textures = textures;
	}

	public void update()
	{
		if(this.moving || this.stage != this.idelTexture)
		{
			this.stageDelayTick++;
			if(this.stageDelayTick > this.stageDelay)
			{
				this.stage += stageDirection;
				if(this.stage >= this.totalStages || this.stage <= 0)
				{
					if(this.loops)
					{
						this.stageDirection *= -1;
						this.stageDelayTick = 0;
					}
					else
					{
						this.stage--;
						this.running = false;
					}
				}
			}
		}
	}

	public Texture getCurrentTexture()
	{
		return this.textures[this.stage];
	}

	public boolean isRunning()
	{
		return this.running;
	}

	public void setMoving(boolean moving)
	{
		this.moving = moving;
	}
}
