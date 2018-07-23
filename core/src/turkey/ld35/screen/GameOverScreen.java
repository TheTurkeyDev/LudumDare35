package turkey.ld35.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import turkey.ld35.GameCore;
import turkey.ld35.game.Game;
import turkey.ld35.graphics.Renderer;
import turkey.ld35.util.DataBaseConnect;

public class GameOverScreen implements Screen
{
	private Stage stage;
	private Table table;
	private Table inputTable;

	private Texture button = new Texture("textures/button.png");
	private Texture textBox = new Texture("textures/textBox.png");

	private TextButton submit;
	private TextField username;

	private boolean isValidUsername = false;
	private boolean wasScoreSent = false;

	public GameOverScreen()
	{
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		table = new Table();
		table.setFillParent(true);
		stage.addActor(table);

		inputTable = new Table();
		inputTable.setFillParent(true);
		stage.addActor(inputTable);

		// Add widgets to the table here.

		LabelStyle ls = new LabelStyle();
		ls.font = Renderer.gf;
		ls.fontColor = Color.ORANGE;

		TextFieldStyle tfs = new TextFieldStyle();
		tfs.background = new TextureRegionDrawable(new TextureRegion(textBox));
		tfs.font = new BitmapFont();
		tfs.font.getData().scale(1.25f);
		tfs.fontColor = Color.WHITE;

		Label label = new Label("Score:", ls);
		label.setFontScale(1f);
		inputTable.add(label).pad(30, 10, 10, 10);
		label = new Label("" + Game.getScore(), ls);
		label.setFontScale(1f);
		inputTable.add(label).pad(30, 10, 10, 10).left();

		inputTable.row();

		label = new Label("Wave:", ls);
		label.setFontScale(1f);
		inputTable.add(label).pad(30, 10, 10, 10);
		label = new Label("" + Game.getWave(), ls);
		label.setFontScale(1f);
		inputTable.add(label).pad(30, 10, 10, 10).left();

		inputTable.row();

		label = new Label("Username:", ls);
		label.setFontScale(1f);
		inputTable.add(label).pad(30, 10, 10, 10);

		username = new TextField("", tfs);
		inputTable.add(username).width(300).pad(30, 10, 10, 10);

		TextButtonStyle tbs = new TextButtonStyle();
		tbs.up = new TextureRegionDrawable(new TextureRegion(button));
		tbs.down = new TextureRegionDrawable(new TextureRegion(button));
		tbs.font = Renderer.ss;

		TextButton play = new TextButton("Play Again", tbs);
		play.getLabel().setFontScale(1f);
		play.addListener(new ClickListener()
		{
			public void clicked(InputEvent e, float x, float y)
			{
				GameCore.THE_GAME.setScreen(ScreenManager.getScreen("game_screen", false));
			}
		});
		table.add(play).pad(10, 10, 0, 10);

		submit = new TextButton("Submit", tbs);
		submit.getLabel().setFontScale(1f);
		submit.addListener(new ClickListener()
		{
			public void clicked(InputEvent e, float x, float y)
			{
				if(username.getText().length() > 0)
				{
					DataBaseConnect.sendData(username.getText().trim(), Game.getScore(), Game.getWave());
					isValidUsername = false;
					wasScoreSent = true;
					submit.setVisible(false);
				}
				else
				{
					isValidUsername = true;
				}
			}
		});
		table.add(submit).pad(10, 10, 0, 10);

		TextButton mainMenu = new TextButton("Main Menu", tbs);
		mainMenu.getLabel().setFontScale(1f);
		mainMenu.addListener(new ClickListener()
		{
			public void clicked(InputEvent e, float x, float y)
			{
				GameCore.THE_GAME.setScreen(ScreenManager.getScreen("main_screen", false));
			}
		});
		table.add(mainMenu).pad(10, 10, 0, 10);

		table.bottom();

		// this.addGuiComponent(new GuiButton(0, 10, 10, 338, 75, "Play Again", button));
		// this.addGuiComponent(submit = new GuiButton(1, (Gdx.graphics.getWidth() / 2) - 169, 10,
		// 338, 75, "Submit Score", button));
		// this.addGuiComponent(new GuiButton(2, Gdx.graphics.getWidth() - 348, 10, 338, 75, "Main
		// Menu", button));
		// this.addGuiComponent(userName = new GuiTextBox(3, (Gdx.graphics.getWidth() / 2) - 225,
		// 150, 450, 75, textBox));
	}

	@Override
	public void show()
	{
		isValidUsername = false;
		wasScoreSent = false;
		submit.setVisible(true);
	}

	@Override
	public void hide()
	{

	}

	@Override
	public void render(float delta)
	{
		Renderer.end();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		Renderer.begin();
		Renderer.drawString(Renderer.sl, 425, Gdx.graphics.getHeight() - 10, "GAME OVER", 3f, Color.ORANGE);

		if(this.isValidUsername)
			Renderer.drawString(Renderer.sl, 415, 60, "Invalid Username!", 2f, Color.RED);
		if(this.wasScoreSent)
			Renderer.drawString(Renderer.sl, 500, 60, "Score Sent!", 2f, Color.GREEN);
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
	public void dispose()
	{
		username.getStyle().font.dispose();
		button.dispose();
		textBox.dispose();
		stage.dispose();
	}
}