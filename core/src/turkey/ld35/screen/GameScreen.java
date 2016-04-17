package turkey.ld35.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import turkey.ld35.game.Game;
import turkey.ld35.gui.GuiButton;
import turkey.ld35.gui.GuiComponent;

public class GameScreen extends Screen
{
	private Game game;

	private Texture button = new Texture("textures/button.png");
	public GuiButton mainMenu;
	public GuiButton restart;

	public GameScreen()
	{
		super("Game Screen");
		this.addGuiComponent(mainMenu = new GuiButton(0, (Gdx.graphics.getWidth() / 2) - 169, 200, 338, 75, "Main Menu", button));
		this.addGuiComponent(restart = new GuiButton(1, (Gdx.graphics.getWidth() / 2) - 169, 300, 338, 75, "Restart", button));
		mainMenu.setVisible(false);
		restart.setVisible(false);
	}
	
	public void onComponentClicked(GuiComponent guic)
	{
		if(guic.getId() == 0)
			ScreenManager.INSTANCE.setCurrentScreen("Main Screen");
		else if(guic.getId() == 1)
			ScreenManager.INSTANCE.setCurrentScreen("Game Screen");
	}

	public void update()
	{
		game.update();
		super.update();
	}

	public void render()
	{
		game.render();
		super.render();
	}

	public void onScreenLoad()
	{
		game = new Game(this);
		game.initgame();
	}

	public void onScreenUnload()
	{

	}

	@Override
	public boolean keyDown(int keycode)
	{
		return game.keyDown(keycode);
	}

	@Override
	public boolean keyUp(int keycode)
	{
		return game.keyUp(keycode);
	}
}
