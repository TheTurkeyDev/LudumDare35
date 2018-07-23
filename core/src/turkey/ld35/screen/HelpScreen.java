package turkey.ld35.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import turkey.ld35.GameCore;
import turkey.ld35.entities.Monster;
import turkey.ld35.game.Game.Shape;
import turkey.ld35.graphics.Renderer;

public class HelpScreen implements Screen
{
	private Stage stage;
	private Table table;

	private Texture button = new Texture("textures/button.png");
	private Texture keys = new Texture("textures/controls.png");
	private Texture bottomBar = new Texture("textures/bottomBar.png");
	private Texture healthBar = new Texture("textures/healthBar.png");
	private Texture playerTexture = new Texture("textures/player/playerTemp.png");
	private Texture perk = new Texture("textures/perk.png");
	private Texture randomShape = Shape.Square.getTexture();

	private Monster monster1 = new Monster(null, new Vector2(650, Gdx.graphics.getHeight() - 300));
	private Monster monster2 = new Monster(null, new Vector2(700, Gdx.graphics.getHeight() - 300));
	private Monster monster3 = new Monster(null, new Vector2(750, Gdx.graphics.getHeight() - 300));

	private TextButton back;
	private TextButton next;

	private int page = 1;
	private int maxPages = 4;

	private long tick = 0;

	public HelpScreen()
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

		back = new TextButton("Back Page", tbs);
		back.getLabel().setFontScale(1f);
		back.addListener(new ClickListener()
		{
			public void clicked(InputEvent e, float x, float y)
			{
				if(page > 1)
					page--;
				updateOnClick();
			}
		});
		table.add(back).pad(0, 15, 0, 15);

		TextButton mainmenu = new TextButton("Main Menu", tbs);
		mainmenu.getLabel().setFontScale(1f);
		mainmenu.addListener(new ClickListener()
		{
			public void clicked(InputEvent e, float x, float y)
			{
				GameCore.THE_GAME.setScreen(ScreenManager.getScreen("main_screen", false));
				updateOnClick();
			}
		});
		table.add(mainmenu).pad(0, 15, 0, 15);

		next = new TextButton("Next Page", tbs);
		next.getLabel().setFontScale(1f);
		next.addListener(new ClickListener()
		{
			public void clicked(InputEvent e, float x, float y)
			{
				if(page < maxPages)
					page++;
				updateOnClick();
			}
		});
		table.add(next).pad(0, 15, 0, 15);

		table.bottom();
		table.padBottom(25);

		back.setVisible(false);
	}

	public void updateOnClick()
	{
		back.setVisible(true);
		next.setVisible(true);
		if(this.page == 1)
		{
			back.setVisible(false);
		}
		else if(this.page == this.maxPages)
		{
			next.setVisible(false);
		}
	}

	@Override
	public void render(float delta)
	{
		tick++;
		if(this.page == 1)
			this.renderPageOne();
		else if(this.page == 2)
			this.renderPageTwo();
		else if(this.page == 3)
			this.renderPageThree();
		else if(this.page == 4)
			this.renderPageFour();

		Renderer.end();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		Renderer.begin();
	}

	public void renderPageOne()
	{
		Renderer.drawString(Renderer.sl, 450, Gdx.graphics.getHeight() - 10, "WELCOME", 3f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, 175, Gdx.graphics.getHeight() - 150, "Welcome to Geo Shifter! A game made in 48 hours for Ludum Dare 35!", 1f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, 175, Gdx.graphics.getHeight() - 175, "The objective of the game is to destroy the enimies by changing the shape", 1f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, 175, Gdx.graphics.getHeight() - 200, "you are shooting to correspond to their various body parts.", 1f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, 175, Gdx.graphics.getHeight() - 275, "The enimies look like this: ", 1f, Color.ORANGE);

		if(tick % 120 == 0)
		{
			monster1 = new Monster(null, monster1.getPosition());
			monster2 = new Monster(null, monster2.getPosition());
			monster3 = new Monster(null, monster3.getPosition());
		}

		float yoff = (float) (Math.cos(tick / 30) / 2);
		this.monster1.addPosition(new Vector2(0, yoff));
		this.monster2.addPosition(new Vector2(0, yoff));
		this.monster3.addPosition(new Vector2(0, yoff));

		monster1.render();
		monster2.render();
		monster3.render();

		Renderer.drawString(Renderer.sl, 175, Gdx.graphics.getHeight() - 325, "As you can see they each have a body, 2 eyes and 2 ears", 1f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, 175, Gdx.graphics.getHeight() - 350, "The game is an endless style game and it has waves that will progressively", 1f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, 175, Gdx.graphics.getHeight() - 375, "get harder and the game will end when you run out of health.", 1f, Color.ORANGE);
	}

	public void renderPageTwo()
	{
		Renderer.drawString(Renderer.sl, 450, Gdx.graphics.getHeight() - 10, "CONTROLS", 3f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, 50, Gdx.graphics.getHeight() - 150, "So the controls for Geo Shifter are similar to Binding of Isaac.", 1f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, 50, Gdx.graphics.getHeight() - 175, "Here's what I mean:", 1f, Color.ORANGE);
		Renderer.drawTextured(150, Gdx.graphics.getHeight() - 400, this.keys);
		Renderer.drawString(Renderer.sl, 170, Gdx.graphics.getHeight() - 285, "Move Up", 0.5f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, 75, Gdx.graphics.getHeight() - 350, "Move Left", 0.5f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, 160, Gdx.graphics.getHeight() - 385, "Move Down", 0.5f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, 255, Gdx.graphics.getHeight() - 350, "Move Right", 0.5f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, 425, Gdx.graphics.getHeight() - 285, "Shoot Up", 0.5f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, 340, Gdx.graphics.getHeight() - 350, "Shoot Left", 0.5f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, 420, Gdx.graphics.getHeight() - 385, "Shoot Down", 0.5f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, 515, Gdx.graphics.getHeight() - 350, "Shoot Right", 0.5f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, 255, Gdx.graphics.getHeight() - 400, "Change Ammo Shape", 0.5f, Color.ORANGE);

		Renderer.drawString(Renderer.sl, 50, Gdx.graphics.getHeight() - 450, "Pretty self explanitory, and the spaace bar, or change ammo shape control", 1f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, 50, Gdx.graphics.getHeight() - 475, "Changes the shape that you shoot.", 1f, Color.ORANGE);
	}

	public void renderPageThree()
	{
		Renderer.drawString(Renderer.sl, 550, Gdx.graphics.getHeight() - 10, "HUD", 3f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, 50, Gdx.graphics.getHeight() - 150, "So your game hud is this thing which will be at the bottom of the in-game screen", 1f, Color.ORANGE);

		// Box
		Renderer.drawTextured(0, 400, bottomBar);

		if(tick % 60 == 0)
			this.randomShape = Monster.getRandomShape(null).getTexture();
		// Selected Shape
		// Renderer.drawTextured(88, 0, 87, 85, border);
		Renderer.drawString(Renderer.ss, 73, 497, "Selected Shape", .5f, Color.WHITE);
		Renderer.drawTextured(100, 410, 64, 64, randomShape);

		Renderer.drawString(Renderer.sl, 10, Gdx.graphics.getHeight() - 380, "This slot of the", 1f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, 10, Gdx.graphics.getHeight() - 405, "Hud will show", 1f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, 10, Gdx.graphics.getHeight() - 430, "the current shape", 1f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, 10, Gdx.graphics.getHeight() - 455, "that you have", 1f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, 10, Gdx.graphics.getHeight() - 480, "selected to shoot.", 1f, Color.ORANGE);

		// Perks
		Renderer.drawString(Renderer.ss, 300, 497, "Active Perks:", .5f, Color.WHITE);
		Renderer.drawString(Renderer.sl, 275, Gdx.graphics.getHeight() - 380, "This displays", 1f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, 275, Gdx.graphics.getHeight() - 405, "the current", 1f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, 275, Gdx.graphics.getHeight() - 430, "perks that", 1f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, 275, Gdx.graphics.getHeight() - 455, "the player has", 1f, Color.ORANGE);

		// Health
		int xtemp = Gdx.graphics.getWidth() / 2;
		String health = "Health (" + 100 + " / 100)";
		Renderer.drawTextured(xtemp - 32, 440, playerTexture);
		Renderer.drawTextured(xtemp - 100, 405, healthBar);
		Renderer.drawString(Renderer.ss, xtemp - (health.length() * 4), 425, health, .6f, Color.BLACK);

		Renderer.drawString(Renderer.sl, xtemp - 120, Gdx.graphics.getHeight() - 380, "This slot of the Hud", 1f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, xtemp - 120, Gdx.graphics.getHeight() - 405, "shows you players", 1f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, xtemp - 120, Gdx.graphics.getHeight() - 430, "current health out of", 1f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, xtemp - 120, Gdx.graphics.getHeight() - 455, "100 hit points. This", 1f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, xtemp - 120, Gdx.graphics.getHeight() - 480, "also shows your player", 1f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, xtemp - 120, Gdx.graphics.getHeight() - 505, "model, because why not.", 1f, Color.ORANGE);

		// Score
		xtemp = Gdx.graphics.getWidth() - 350;
		String scoreTemp = "" + 35;
		Renderer.drawString(Renderer.ss, xtemp, 490, "Score", 1, Color.WHITE);
		Renderer.drawString(Renderer.ss, xtemp + 40 - (scoreTemp.length() * 5), 455, scoreTemp, 1.5f, Color.WHITE);

		Renderer.drawString(Renderer.sl, xtemp - 40, Gdx.graphics.getHeight() - 380, "Your current", 1f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, xtemp - 40, Gdx.graphics.getHeight() - 405, "game score", 1f, Color.ORANGE);

		// Wave
		xtemp = Gdx.graphics.getWidth() - 100;
		String waveTemp = "" + 4;

		Renderer.drawString(Renderer.ss, xtemp, 490, "Wave", 1f, Color.WHITE);
		Renderer.drawString(Renderer.ss, xtemp + 35 - (waveTemp.length() * 5), 455, waveTemp, 1.5f, Color.WHITE);
		Renderer.drawString(Renderer.sl, xtemp - 70, Gdx.graphics.getHeight() - 380, "The current", 1f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, xtemp - 70, Gdx.graphics.getHeight() - 405, "wave that", 1f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, xtemp - 70, Gdx.graphics.getHeight() - 430, "the game", 1f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, xtemp - 70, Gdx.graphics.getHeight() - 455, "is on", 1f, Color.ORANGE);
	}

	private void renderPageFour()
	{
		Renderer.drawString(Renderer.sl, 525, Gdx.graphics.getHeight() - 10, "PERKS", 3f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, 50, Gdx.graphics.getHeight() - 150, "Perks drop as little stars like this ->", 1f, Color.ORANGE);
		Renderer.drawTextured(550, Gdx.graphics.getHeight() - 175, perk);
		Renderer.drawString(Renderer.sl, 50, Gdx.graphics.getHeight() - 175, "By Collecting these perks your player gains a random buff or debuff.", 1f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, 50, Gdx.graphics.getHeight() - 200, "Here is a list of the perks!", 1f, Color.ORANGE);

		Renderer.drawString(Renderer.sl, 50, Gdx.graphics.getHeight() - 250, "FastShoot: Causes the player to shoot shapes faster.", 1f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, 50, Gdx.graphics.getHeight() - 275, "SpeedUp: Speeds up the player.", 1f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, 50, Gdx.graphics.getHeight() - 300, "SlowDown: Slows down the player.", 1f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, 50, Gdx.graphics.getHeight() - 325, "Freeze: Freezes all monsters currently on the screen.", 1f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, 50, Gdx.graphics.getHeight() - 350, "Teleport: Teleports the player to a random spot on the screen.", 1f, Color.ORANGE);
		Renderer.drawString(Renderer.sl, 50, Gdx.graphics.getHeight() - 375, "Health: Heals the player for 10 health.", 1f, Color.ORANGE);
	}

	@Override
	public void dispose()
	{
		button.dispose();
		keys.dispose();
		bottomBar.dispose();
		healthBar.dispose();
		playerTexture.dispose();
		perk.dispose();
		stage.dispose();
	}

	@Override
	public void show()
	{

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
