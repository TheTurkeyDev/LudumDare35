package turkey.ld35.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import turkey.ld35.game.GameSettings;
import turkey.ld35.graphics.Draw2D;
import turkey.ld35.gui.GuiButton;
import turkey.ld35.gui.GuiComponent;
import turkey.ld35.gui.GuiRadioButton;

public class SettingsScreen extends Screen
{
	private Texture button = new Texture("textures/button.png");
	private GuiRadioButton sounds;
	private GuiRadioButton colorblind;

	public SettingsScreen()
	{
		super("Settings Screen");
		this.addGuiComponent(new GuiButton(0, (Gdx.graphics.getWidth() / 2) - 169, 10, 338, 75, "Main Menu", button));
		super.addGuiComponent(sounds = new GuiRadioButton(1, 400, Gdx.graphics.getHeight() - 225, 16, 16, true));
		super.addGuiComponent(colorblind = new GuiRadioButton(2, 400, Gdx.graphics.getHeight() - 175, 16, 16, false));

	}

	public void onComponentClicked(GuiComponent guic)
	{
		if(guic.getId() == 0)
		{
			ScreenManager.INSTANCE.setCurrentScreen("Main Screen");
		}
	}

	public void render()
	{
		super.render();
		Draw2D.drawString3(350, Gdx.graphics.getHeight() - 10, "SETTINGS", 3f, Color.ORANGE);
		Draw2D.drawString3(100, Gdx.graphics.getHeight() - 200, "Sounds", 1f, Color.ORANGE);
		Draw2D.drawString3(100, Gdx.graphics.getHeight() - 150, "Colorblind Mode", 1f, Color.ORANGE);
	}

	public void update()
	{
		GameSettings.sounds = sounds.isSelected();
		GameSettings.colorBlindMode = colorblind.isSelected();
	}
}
