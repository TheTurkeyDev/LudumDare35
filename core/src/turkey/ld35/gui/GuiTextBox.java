package turkey.ld35.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import turkey.ld35.graphics.Draw2D;

public class GuiTextBox extends GuiComponent
{
	private Texture texture;
	private long tick = 0;
	private int maxLength = 10;
	
	private boolean focused = false;

	public GuiTextBox(int id, float x, float y, float width, float height, Texture texture)
	{
		super(id, x, y, width, height);
		this.texture = texture;
		this.displayText = "";
	}

	public void render()
	{
		if(!visible)
			return;
		tick++;

		Draw2D.drawTextured(x, y, width, height, texture);
		if(tick % 120 <= 60 && this.focused)
			Draw2D.drawString3(x + 15, y + this.height - 15, this.displayText + "|", 1.75f, Color.WHITE);
		else
			Draw2D.drawString3(x + 15, y + this.height - 15, this.displayText, 1.75f, Color.WHITE);
	}

	public void keyTyped(char character)
	{
		if(character == 8)
		{
			this.displayText = this.displayText.substring(0, Math.max(this.displayText.length() - 1, 0));
		}
		else
		{
			if(this.displayText.length() < maxLength)
				this.displayText += character;
			this.displayText = this.displayText.trim();
		}
	}
	
	public void focused(boolean toggle)
	{
		this.focused = toggle;
	}
}