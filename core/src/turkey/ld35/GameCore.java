package turkey.ld35;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import turkey.ld35.screen.GameOverScreen;
import turkey.ld35.screen.GameScreen;
import turkey.ld35.screen.HelpScreen;
import turkey.ld35.screen.LeaderBoardScreen;
import turkey.ld35.screen.MainScreen;
import turkey.ld35.screen.ScreenManager;
import turkey.ld35.screen.SettingsScreen;
import turkey.ld35.sounds.SoundManager;

public class GameCore extends ApplicationAdapter
{

	@Override
	public void create()
	{
		ScreenManager.INSTANCE.addScreen(new MainScreen());
		ScreenManager.INSTANCE.addScreen(new HelpScreen());
		ScreenManager.INSTANCE.addScreen(new LeaderBoardScreen());
		ScreenManager.INSTANCE.addScreen(new SettingsScreen());
		ScreenManager.INSTANCE.addScreen(new GameScreen());
		ScreenManager.INSTANCE.addScreen(new GameOverScreen());

		ScreenManager.INSTANCE.setCurrentScreen("Main Screen");
	}

	@Override
	public void dispose()
	{
		SoundManager.disposeSounds();
	}

	@Override
	public void render()
	{
		ScreenManager.INSTANCE.updateScreen();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		ScreenManager.INSTANCE.renderScreen();
	}
}
