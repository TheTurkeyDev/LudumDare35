package turkey.ld35.screen;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import turkey.ld35.GameCore;
import turkey.ld35.graphics.Renderer;
import turkey.ld35.util.CustomEntry;
import turkey.ld35.util.DataBaseConnect;

public class LeaderBoardScreen implements Screen
{
	private Stage stage;
	private Table table;

	private List<CustomEntry<String, String>> topPlayers = new ArrayList<CustomEntry<String, String>>();

	private Texture button = new Texture("textures/button.png");

	public LeaderBoardScreen()
	{
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		table = new Table();
		table.setFillParent(true);
		stage.addActor(table);

		// Add widgets to the table here.

		TextButtonStyle tbs = new TextButtonStyle();
		tbs.up = new TextureRegionDrawable(new TextureRegion(button));
		tbs.down = new TextureRegionDrawable(new TextureRegion(button));
		tbs.font = Renderer.ss;

		TextButton mainmenu = new TextButton("Main Menu", tbs);
		mainmenu.getLabel().setFontScale(1f);
		mainmenu.addListener(new ClickListener()
		{
			public void clicked(InputEvent e, float x, float y)
			{
				GameCore.THE_GAME.setScreen(ScreenManager.getScreen("main_screen", false));
			}
		});
		table.add(mainmenu);
		table.bottom();
	}

	@Override
	public void show()
	{
		topPlayers = DataBaseConnect.getLeaderBoard();
	}

	@Override
	public void render(float delta)
	{
		Renderer.drawString(Renderer.sl, 350, Gdx.graphics.getHeight() - 10, "LEADERBOARD", 3f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, 200, Gdx.graphics.getHeight() - 100, "Username", 2f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, 600, Gdx.graphics.getHeight() - 100, "Score", 2f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, 1000, Gdx.graphics.getHeight() - 100, "Wave", 2f, Color.ORANGE);

		int i = 0;
		for(CustomEntry<String, String> entry : topPlayers)
		{
			Renderer.drawString(Renderer.sl, 10, Gdx.graphics.getHeight() - (150 + (i * 50)), "" + (i + 1) + ")", 1.5f, Color.ORANGE);
			Renderer.drawString(Renderer.sl, 200, Gdx.graphics.getHeight() - (150 + (i * 50)), entry.getKey(), 1.5f, Color.ORANGE);
			Renderer.drawString(Renderer.sl, 600, Gdx.graphics.getHeight() - (150 + (i * 50)), entry.getValue().substring(0, entry.getValue().indexOf(",")), 1.5f, Color.ORANGE);
			Renderer.drawString(Renderer.sl, 1000, Gdx.graphics.getHeight() - (150 + (i * 50)), entry.getValue().substring(entry.getValue().indexOf(",") + 1), 1.5f, Color.ORANGE);
			i++;
		}
		Renderer.end();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		Renderer.begin();
	}

	@Override
	public void dispose()
	{
		button.dispose();
		stage.dispose();
	}

	@Override
	public void resize(int width, int height)
	{
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void pause()
	{

	}

	@Override
	public void resume()
	{

	}

	@Override
	public void hide()
	{

	}
}
