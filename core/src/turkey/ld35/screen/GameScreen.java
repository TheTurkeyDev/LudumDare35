package turkey.ld35.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import turkey.ld35.game.Game;
import turkey.ld35.graphics.Renderer;

public class GameScreen implements Screen
{
	private Game theGame;

	private Stage stage;
	private Table table;

	private Texture button = new Texture("textures/button.png");
	public TextButton mainMenu;
	public TextButton restart;
	public TextButton resume;

	public GameScreen()
	{
		stage = new Stage()
		{
			@Override
			public boolean keyDown(int keycode)
			{
				if(!super.keyDown(keycode))
					return theGame.keyDown(keycode);
				return true;
			}

			@Override
			public boolean keyUp(int keycode)
			{
				if(!super.keyUp(keycode))
					return theGame.keyUp(keycode);
				return true;
			}
		};
		Gdx.input.setInputProcessor(stage);

		table = new Table();
		table.setFillParent(true);
		stage.addActor(table);

		TextButtonStyle tbs = new TextButtonStyle();
		tbs.up = new TextureRegionDrawable(new TextureRegion(button));
		tbs.down = new TextureRegionDrawable(new TextureRegion(button));
		tbs.font = Renderer.ss;

		resume = new TextButton("Resume", tbs);
		resume.getLabel().setFontScale(1f);
		resume.addListener(new ClickListener()
		{
			public void clicked(InputEvent e, float x, float y)
			{
				theGame.pauseGame();
			}
		});
		table.add(resume).pad(0, 10, 0, 10);

		mainMenu = new TextButton("Main Menu", tbs);
		mainMenu.getLabel().setFontScale(1f);
		mainMenu.addListener(new ClickListener()
		{
			public void clicked(InputEvent e, float x, float y)
			{
				GameCore.THE_GAME.setScreen(ScreenManager.getScreen("main_screen", false));
			}
		});
		table.add(mainMenu).pad(0, 10, 0, 10);

		restart = new TextButton("Restart", tbs);
		restart.getLabel().setFontScale(1f);
		restart.addListener(new ClickListener()
		{
			public void clicked(InputEvent e, float x, float y)
			{
				GameCore.THE_GAME.setScreen(ScreenManager.getScreen("game_screen", false));
			}
		});
		table.add(restart).pad(0, 10, 0, 10);

		mainMenu.setVisible(false);
		restart.setVisible(false);
		resume.setVisible(false);
	}

	public void update()
	{
		theGame.update();
	}

	@Override
	public void render(float delta)
	{
		update();
		theGame.render();
		Renderer.end();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		Renderer.begin();
	}

	@Override
	public void show()
	{
		theGame = new Game(this);
		theGame.initgame();
	}

	@Override
	public void hide()
	{

	}

	@Override
	public void dispose()
	{
		button.dispose();
		stage.dispose();
		theGame.dispose();
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
}
