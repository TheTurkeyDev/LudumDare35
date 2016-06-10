package turkey.ld35.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import turkey.ld35.game.Game;
import turkey.ld35.graphics.Draw2D;
import turkey.ld35.gui.GuiButton;
import turkey.ld35.gui.GuiComponent;
import turkey.ld35.gui.GuiTextBox;
import turkey.ld35.util.DataBaseConnect;

public class GameOverScreen extends Screen
{
	private Texture button = new Texture("textures/button.png");
	private Texture textBox = new Texture("textures/textBox.png");

	private boolean isValidUsername = false;
	private boolean wasScoreSent = false;

	private GuiButton submit;
	private GuiTextBox userName;

	public GameOverScreen()
	{
		super("Game Over Screen");
		this.addGuiComponent(new GuiButton(0, 10, 10, 338, 75, "Play Again", button));
		this.addGuiComponent(submit = new GuiButton(1, (Gdx.graphics.getWidth() / 2) - 169, 10, 338, 75, "Submit Score", button));
		this.addGuiComponent(new GuiButton(2, Gdx.graphics.getWidth() - 348, 10, 338, 75, "Main Menu", button));
		this.addGuiComponent(userName = new GuiTextBox(3, (Gdx.graphics.getWidth() / 2) - 225, 150, 450, 75, textBox));
	}

	public void onScreenLoad()
	{
		isValidUsername = false;
		wasScoreSent = false;
		submit.setVisible(true);
	}

	public void onComponentClicked(GuiComponent guic)
	{
		if(guic.getId() == 0)
		{
			ScreenManager.INSTANCE.setCurrentScreen("Game Screen");
		}
		else if(guic.getId() == 1)
		{
			if(userName.getDisplayText().length() > 0)
			{
				DataBaseConnect.sendData(userName.getDisplayText().trim(), Game.getScore(), Game.getWave());
				this.isValidUsername = false;
				this.wasScoreSent = true;
				guic.setVisible(false);
			}
			else
			{
				this.isValidUsername = true;
			}
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

		Draw2D.drawString3(500, Gdx.graphics.getHeight() - 475, "Username:", 2f, Color.ORANGE);
		if(this.isValidUsername)
			Draw2D.drawString3(415, Gdx.graphics.getHeight() - 625, "Invalid Username!", 2f, Color.RED);
		if(this.wasScoreSent)
			Draw2D.drawString3(500, Gdx.graphics.getHeight() - 625, "Score Sent!", 2f, Color.GREEN);
	}

}