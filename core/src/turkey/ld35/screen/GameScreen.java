package turkey.ld35.screen;

import turkey.ld35.game.Game;

public class GameScreen extends Screen
{
	private Game game;

	public GameScreen()
	{
		super("Game Screen");
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
		game = new Game();
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
