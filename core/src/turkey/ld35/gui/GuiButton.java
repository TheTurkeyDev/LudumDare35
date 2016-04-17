package turkey.ld35.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import turkey.ld35.graphics.Draw2D;

public class GuiButton extends GuiComponent
{
	private Texture texture;

	public GuiButton(int id, float x, float y, float width, float height, String text, Texture texture)
	{
		super(id, x, y, width, height);
		this.texture = texture;
		this.displayText = text;
	}

	public void render()
	{
		if(!visible)
			return;
		Draw2D.drawTextured(x, y, width, height, texture);
		Draw2D.drawString3(x + 30, y + (this.height - 18), this.displayText, 1.75f, Color.WHITE);
	}

	public Texture getTexture()
	{
		return this.texture;
	}
}
