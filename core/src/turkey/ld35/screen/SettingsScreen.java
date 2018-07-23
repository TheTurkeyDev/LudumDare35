package turkey.ld35.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import turkey.ld35.GameCore;
import turkey.ld35.game.GameSettings;
import turkey.ld35.graphics.Renderer;

public class SettingsScreen implements Screen
{
	private Stage stage;
	private Table table;
	private Table settingsTable;

	private Texture button = new Texture("textures/button.png");

	private CheckBox sounds;
	private CheckBox colorblind;

	public SettingsScreen()
	{
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		table = new Table();
		table.setFillParent(true);
		stage.addActor(table);

		settingsTable = new Table();
		settingsTable.setFillParent(true);
		stage.addActor(settingsTable);

		// Add widgets to the table here.

		CheckBoxStyle cbs = new CheckBoxStyle();
		cbs.fontColor = Color.WHITE;
		cbs.checkedFontColor = Color.ORANGE;
		cbs.font = Renderer.ss;

		LabelStyle ls = new LabelStyle();
		ls.font = Renderer.ss;

		Label label = new Label("Sounds:", ls);
		label.setFontScale(1f);
		settingsTable.add(label).pad(150, 75, 0, 0);

		sounds = new CheckBox("Sounds", cbs);
		sounds.setChecked(GameSettings.sounds);
		sounds.getLabel().setFontScale(1f);
		settingsTable.add(sounds).pad(150, 10, 0, 0);

		settingsTable.row();

		label = new Label("Color Blind:", ls);
		label.setFontScale(1f);
		settingsTable.add(label).pad(10, 50, 0, 0);

		colorblind = new CheckBox("Color Blind", cbs);
		colorblind.setChecked(GameSettings.colorBlindMode);
		colorblind.getLabel().setFontScale(1f);
		settingsTable.add(colorblind).pad(10, 10, 0, 0);

		settingsTable.left();
		settingsTable.top();

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
	public void render(float delta)
	{
		update();
		Renderer.drawString(Renderer.sl, 450, Gdx.graphics.getHeight() - 10, "SETTINGS", 3f, Color.ORANGE);
		Renderer.end();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		Renderer.begin();
	}

	public void update()
	{
		GameSettings.sounds = sounds.isChecked();
		GameSettings.colorBlindMode = colorblind.isChecked();
	}

	@Override
	public void dispose()
	{
		button.dispose();
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
