package turkey.ld35.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import turkey.ld35.game.Game;
import turkey.ld35.graphics.Draw2D;
import turkey.ld35.gui.GuiButton;
import turkey.ld35.gui.GuiComponent;

public class GameOverScreen extends Screen
{
	private Texture button = new Texture("textures/button.png");

	public GameOverScreen()
	{
		super("Game Over Screen");
		this.addGuiComponent(new GuiButton(0, 10, 10, 338, 75, "Play Again", button));
		this.addGuiComponent(new GuiButton(1, Gdx.graphics.getWidth() - 169, 10, 338, 75, "Submit Score", button));
		this.addGuiComponent(new GuiButton(2, (Gdx.graphics.getWidth() / 2) - 348, 10, 338, 75, "Main Menu", button));
	}

	public void onComponentClicked(GuiComponent guic)
	{
		if(guic.getId() == 0)
		{
			ScreenManager.INSTANCE.setCurrentScreen("Game Screen");
		}
		else if(guic.getId() == 1)
		{
			
		}
		else if(guic.getId() == 2)
		{
			ScreenManager.INSTANCE.setCurrentScreen("Main Screen");
		}
	}

	public void render()
	{
		super.render();
		Draw2D.drawString3(425, Gdx.graphics.getHeight() - 10, "GAME OVER", 3f, Color.ORANGE);
		Draw2D.drawString3(550, Gdx.graphics.getHeight() - 150, "Score:", 2f, Color.ORANGE);
		Draw2D.drawString3(550, Gdx.graphics.getHeight() - 200, "" + Game.getScore(), 2f, Color.ORANGE);
		Draw2D.drawString3(550, Gdx.graphics.getHeight() - 300, "Wave:", 2f, Color.ORANGE);
		Draw2D.drawString3(550, Gdx.graphics.getHeight() - 350, "" + Game.getWave(), 2f, Color.ORANGE);
	}

}