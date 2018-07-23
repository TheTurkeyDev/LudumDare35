package turkey.ld35;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import turkey.ld35.graphics.Renderer;
import turkey.ld35.screen.GameOverScreen;
import turkey.ld35.screen.GameScreen;
import turkey.ld35.screen.HelpScreen;
import turkey.ld35.screen.LeaderBoardScreen;
import turkey.ld35.screen.MainScreen;
import turkey.ld35.screen.ScreenManager;
import turkey.ld35.screen.SettingsScreen;
import turkey.ld35.sounds.SoundManager;

public class GameCore extends Game
{
	public static GameCore THE_GAME;

	@Override
	public void create()
	{
		THE_GAME = this;
		Renderer.initRenderer();

		ScreenManager.addScreen("main_screen", MainScreen.class);
		ScreenManager.addScreen("help_screen", HelpScreen.class);
		ScreenManager.addScreen("leader_board_screen", LeaderBoardScreen.class);
		ScreenManager.addScreen("settings_screen", SettingsScreen.class);
		ScreenManager.addScreen("game_screen", GameScreen.class);
		ScreenManager.addScreen("game_over_screen", GameOverScreen.class);

		this.setScreen(ScreenManager.getScreen("main_screen", false));
	}

	@Override
	public void dispose()
	{
		Renderer.dispose();
		SoundManager.disposeSounds();
	}

	@Override
	public void render()
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Renderer.update();
		Renderer.begin();
		super.render();
		Renderer.end();
	}

	public void resize(int width, int height)
	{
		Renderer.resize(width, height);
	}

	public void pause()
	{
	}

	public void resume()
	{
	}
}
