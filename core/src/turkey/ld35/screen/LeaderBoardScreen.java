package turkey.ld35.screen;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import turkey.ld35.graphics.Draw2D;
import turkey.ld35.gui.GuiButton;
import turkey.ld35.gui.GuiComponent;
import turkey.ld35.util.CustomEntry;
import turkey.ld35.util.DataBaseConnect;

public class LeaderBoardScreen extends Screen
{
	private List<CustomEntry<String, String>> topPlayers = new ArrayList<CustomEntry<String, String>>();

	private Texture button = new Texture("textures/button.png");

	public LeaderBoardScreen()
	{
		super("Leaderboard Screen");
		this.addGuiComponent(new GuiButton(0, (Gdx.graphics.getWidth() / 2) - 169, 10, 338, 75, "Main Menu", button));
	}

	public void onComponentClicked(GuiComponent guic)
	{
		if(guic.getId() == 0)
		{
			ScreenManager.INSTANCE.setCurrentScreen("Main Screen");
		}
	}

	public void onScreenLoad()
	{
		topPlayers = DataBaseConnect.getLeaderBoard();
	}

	public void render()
	{
		Draw2D.drawString3(350, Gdx.graphics.getHeight() - 10, "LEADERBOARD", 3f, Color.ORANGE);
		Draw2D.drawString3(200, Gdx.graphics.getHeight() - 100, "Username", 2f, Color.ORANGE);
		Draw2D.drawString3(600, Gdx.graphics.getHeight() - 100, "Score", 2f, Color.ORANGE);
		Draw2D.drawString3(1000, Gdx.graphics.getHeight() - 100, "Wave", 2f, Color.ORANGE);

		int i = 0;
		for(CustomEntry<String, String> entry : topPlayers)
		{
			Draw2D.drawString3(10, Gdx.graphics.getHeight() - (150 + (i * 50)), "" + (i + 1) + ")", 1.5f, Color.ORANGE);
			Draw2D.drawString3(200, Gdx.graphics.getHeight() - (150 + (i * 50)), entry.getKey(), 1.5f, Color.ORANGE);
			Draw2D.drawString3(600, Gdx.graphics.getHeight() - (150 + (i * 50)), entry.getValue().substring(0, entry.getValue().indexOf(",")), 1.5f, Color.ORANGE);
			Draw2D.drawString3(1000, Gdx.graphics.getHeight() - (150 + (i * 50)), entry.getValue().substring(entry.getValue().indexOf(",") + 1), 1.5f, Color.ORANGE);
			i++;
		}
		super.render();
	}
}
