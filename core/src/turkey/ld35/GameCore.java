package turkey.ld35;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import turkey.ld35.screen.GameScreen;
import turkey.ld35.screen.ScreenManager;

public class GameCore extends ApplicationAdapter
{

	@Override
	public void create()
	{
		ScreenManager.INSTANCE.addScreen(new GameScreen());

		ScreenManager.INSTANCE.setCurrentScreen("Game Screen");
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
