package turkey.ld35.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import turkey.ld35.entities.Monster;
import turkey.ld35.game.Game.Shape;
import turkey.ld35.graphics.Draw2D;
import turkey.ld35.gui.GuiButton;
import turkey.ld35.gui.GuiComponent;

public class HelpScreen extends Screen
{
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
	
	private GuiButton back;
	private GuiButton next;

	private int page = 1;
	private int maxPages = 4;

	private long tick = 0;

	public HelpScreen()
	{
		super("Help Screen");
		this.addGuiComponent(back = new GuiButton(0, 10, 10, 338, 75, "Back Page", button));
		this.addGuiComponent(next = new GuiButton(1, Gdx.graphics.getWidth() - 348, 10, 338, 75, "Next Page", button));
		this.addGuiComponent(new GuiButton(2, (Gdx.graphics.getWidth() / 2) - 169, 10, 338, 75, "Main Menu", button));
		back.setVisible(false);
	}

	public void onComponentClicked(GuiComponent guic)
	{
		if(guic.getId() == 0)
		{
			if(this.page > 1)
				this.page--;
		}
		else if(guic.getId() == 1)
		{
			if(this.page < maxPages)
				this.page++;
		}
		else if(guic.getId() == 2)
		{
			ScreenManager.INSTANCE.setCurrentScreen("Main Screen");
		}
		
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

	public void render()
	{
		tick++;
		super.render();
		if(this.page == 1)
			this.renderPageOne();
		else if(this.page == 2)
			this.renderPageTwo();
		else if(this.page == 3)
			this.renderPageThree();
		else if(this.page == 4)
			this.renderPageFour();
	}

	public void renderPageOne()
	{
		Draw2D.drawString3(450, Gdx.graphics.getHeight() - 10, "WELCOME", 3f, Color.ORANGE);
		Draw2D.drawString3(175, Gdx.graphics.getHeight() - 150, "Welcome to Geo Shifter! A game made in 48 hours for Ludum Dare 35!", 1f, Color.ORANGE);
		Draw2D.drawString3(175, Gdx.graphics.getHeight() - 175, "The objective of the game is to destroy the enimies by changing the shape", 1f, Color.ORANGE);
		Draw2D.drawString3(175, Gdx.graphics.getHeight() - 200, "you are shooting to correspond to their various body parts.", 1f, Color.ORANGE);
		Draw2D.drawString3(175, Gdx.graphics.getHeight() - 275, "The enimies look like this: ", 1f, Color.ORANGE);

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

		Draw2D.drawString3(175, Gdx.graphics.getHeight() - 325, "As you can see they each have a body, 2 eyes and 2 ears", 1f, Color.ORANGE);
		Draw2D.drawString3(175, Gdx.graphics.getHeight() - 350, "The game is an endless style game and it has waves that will progressively", 1f, Color.ORANGE);
		Draw2D.drawString3(175, Gdx.graphics.getHeight() - 375, "get harder and the game will end when you run out of health.", 1f, Color.ORANGE);
	}

	public void renderPageTwo()
	{
		Draw2D.drawString3(450, Gdx.graphics.getHeight() - 10, "CONTROLS", 3f, Color.ORANGE);
		Draw2D.drawString3(50, Gdx.graphics.getHeight() - 150, "So the controls for Geo Shifter are similar to Binding of Isaac.", 1f, Color.ORANGE);
		Draw2D.drawString3(50, Gdx.graphics.getHeight() - 175, "Here's what I mean:", 1f, Color.ORANGE);
		Draw2D.drawTextured(150, Gdx.graphics.getHeight() - 400, this.keys);
		Draw2D.drawString3(170, Gdx.graphics.getHeight() - 285, "Move Up", 0.5f, Color.ORANGE);
		Draw2D.drawString3(75, Gdx.graphics.getHeight() - 350, "Move Left", 0.5f, Color.ORANGE);
		Draw2D.drawString3(160, Gdx.graphics.getHeight() - 385, "Move Down", 0.5f, Color.ORANGE);
		Draw2D.drawString3(255, Gdx.graphics.getHeight() - 350, "Move Right", 0.5f, Color.ORANGE);
		Draw2D.drawString3(425, Gdx.graphics.getHeight() - 285, "Shoot Up", 0.5f, Color.ORANGE);
		Draw2D.drawString3(340, Gdx.graphics.getHeight() - 350, "Shoot Left", 0.5f, Color.ORANGE);
		Draw2D.drawString3(420, Gdx.graphics.getHeight() - 385, "Shoot Down", 0.5f, Color.ORANGE);
		Draw2D.drawString3(515, Gdx.graphics.getHeight() - 350, "Shoot Right", 0.5f, Color.ORANGE);
		Draw2D.drawString3(255, Gdx.graphics.getHeight() - 400, "Change Ammo Shape", 0.5f, Color.ORANGE);

		Draw2D.drawString3(50, Gdx.graphics.getHeight() - 450, "Pretty self explanitory, and the spaace bar, or change ammo shape control", 1f, Color.ORANGE);
		Draw2D.drawString3(50, Gdx.graphics.getHeight() - 475, "Changes the shape that you shoot.", 1f, Color.ORANGE);
	}

	public void renderPageThree()
	{
		Draw2D.drawString3(550, Gdx.graphics.getHeight() - 10, "HUD", 3f, Color.ORANGE);
		Draw2D.drawString3(50, Gdx.graphics.getHeight() - 150, "So your game hud is this thing which will be at the bottom of the in-game screen", 1f, Color.ORANGE);

		// Box
		Draw2D.drawTextured(0, 400, bottomBar);

		if(tick % 60 == 0)
			this.randomShape = Monster.getRandomShape(null).getTexture();
		// Selected Shape
		// Draw2D.drawTextured(88, 0, 87, 85, border);
		Draw2D.drawString(73, 497, "Selected Shape", .5f, Color.WHITE);
		Draw2D.drawTextured(100, 410, 64, 64, randomShape);

		Draw2D.drawString3(10, Gdx.graphics.getHeight() - 380, "This slot of the", 1f, Color.ORANGE);
		Draw2D.drawString3(10, Gdx.graphics.getHeight() - 405, "Hud will show", 1f, Color.ORANGE);
		Draw2D.drawString3(10, Gdx.graphics.getHeight() - 430, "the current shape", 1f, Color.ORANGE);
		Draw2D.drawString3(10, Gdx.graphics.getHeight() - 455, "that you have", 1f, Color.ORANGE);
		Draw2D.drawString3(10, Gdx.graphics.getHeight() - 480, "selected to shoot.", 1f, Color.ORANGE);
		
		//Perks
		Draw2D.drawString(300, 497, "Active Perks:", .5f, Color.WHITE);
		Draw2D.drawString3(275, Gdx.graphics.getHeight() - 380, "This displays", 1f, Color.ORANGE);
		Draw2D.drawString3(275, Gdx.graphics.getHeight() - 405, "the current", 1f, Color.ORANGE);
		Draw2D.drawString3(275, Gdx.graphics.getHeight() - 430, "perks that", 1f, Color.ORANGE);
		Draw2D.drawString3(275, Gdx.graphics.getHeight() - 455, "the player has", 1f, Color.ORANGE);
		
		// Health
		int xtemp = Gdx.graphics.getWidth() / 2;
		String health = "Health (" + 100 + " / 100)";
		Draw2D.drawTextured(xtemp - 32, 440, playerTexture);
		Draw2D.drawTextured(xtemp - 100, 405, healthBar);
		Draw2D.drawString(xtemp - (health.length() * 4), 425, health, .6f, Color.BLACK);

		Draw2D.drawString3(500, Gdx.graphics.getHeight() - 380, "This slot of the Hud", 1f, Color.ORANGE);
		Draw2D.drawString3(500, Gdx.graphics.getHeight() - 405, "shows you players", 1f, Color.ORANGE);
		Draw2D.drawString3(500, Gdx.graphics.getHeight() - 430, "current health out of", 1f, Color.ORANGE);
		Draw2D.drawString3(500, Gdx.graphics.getHeight() - 455, "100 hit points. This", 1f, Color.ORANGE);
		Draw2D.drawString3(500, Gdx.graphics.getHeight() - 480, "also shows your player", 1f, Color.ORANGE);
		Draw2D.drawString3(500, Gdx.graphics.getHeight() - 505, "model, because why not.", 1f, Color.ORANGE);

		// Score
		xtemp = Gdx.graphics.getWidth() - 250;
		String scoreTemp = "" + 35;
		Draw2D.drawString(xtemp, 490, "Score", 1, Color.WHITE);
		Draw2D.drawString(xtemp + 40 - (scoreTemp.length() * 5), 455, scoreTemp, 1.5f, Color.WHITE);

		Draw2D.drawString3(900, Gdx.graphics.getHeight() - 380, "Your current ^", 1f, Color.ORANGE);
		Draw2D.drawString3(900, Gdx.graphics.getHeight() - 405, "game score     |", 1f, Color.ORANGE);

		// Wave
		xtemp = Gdx.graphics.getWidth() - 100;
		String waveTemp = "" + 4;
		Draw2D.drawString(xtemp, 490, "Wave", 1f, Color.WHITE);
		Draw2D.drawString(xtemp + 35 - (waveTemp.length() * 5), 455, waveTemp, 1.5f, Color.WHITE);
		Draw2D.drawString3(1125, Gdx.graphics.getHeight() - 380, "The current", 1f, Color.ORANGE);
		Draw2D.drawString3(1125, Gdx.graphics.getHeight() - 405, "wave that", 1f, Color.ORANGE);
		Draw2D.drawString3(1125, Gdx.graphics.getHeight() - 430, "the game", 1f, Color.ORANGE);
		Draw2D.drawString3(1125, Gdx.graphics.getHeight() - 455, "is on", 1f, Color.ORANGE);
	}
	
	private void renderPageFour()
	{
		Draw2D.drawString3(525, Gdx.graphics.getHeight() - 10, "PERKS", 3f, Color.ORANGE);
		Draw2D.drawString3(50, Gdx.graphics.getHeight() - 150, "Perks drop as little stars like this ->", 1f, Color.ORANGE);
		Draw2D.drawTextured(550, Gdx.graphics.getHeight() - 175, perk);
		Draw2D.drawString3(50, Gdx.graphics.getHeight() - 175, "By Collecting these perks your player gains a random buff or debuff.", 1f, Color.ORANGE);
		Draw2D.drawString3(50, Gdx.graphics.getHeight() - 200, "Here is a list of the perks!", 1f, Color.ORANGE);
		
		Draw2D.drawString3(50, Gdx.graphics.getHeight() - 250, "FastShoot: Causes the player to shoot shapes faster.", 1f, Color.ORANGE);
		Draw2D.drawString3(50, Gdx.graphics.getHeight() - 275, "SpeedUp: Speeds up the player.", 1f, Color.ORANGE);
		Draw2D.drawString3(50, Gdx.graphics.getHeight() - 300, "SlowDown: Slows down the player.", 1f, Color.ORANGE);
		Draw2D.drawString3(50, Gdx.graphics.getHeight() - 325, "Freeze: Freezes all monsters currently on the screen.", 1f, Color.ORANGE);
		Draw2D.drawString3(50, Gdx.graphics.getHeight() - 350, "Teleport: Teleports the player to a random spot on the screen.", 1f, Color.ORANGE);
		Draw2D.drawString3(50, Gdx.graphics.getHeight() - 375, "Health: Heals the player for 10 health.", 1f, Color.ORANGE);
	}

}
