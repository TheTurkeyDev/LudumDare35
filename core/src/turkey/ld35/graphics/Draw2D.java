package turkey.ld35.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Draw2D
{
	private static SpriteBatch batch = new SpriteBatch();
	private static BitmapFont font = new BitmapFont(Gdx.files.internal("font/shapeShifter.fnt"), Gdx.files.internal("font/shapeShifter.png"), false);
	private static BitmapFont font2 = new BitmapFont(Gdx.files.internal("font/gameFont.fnt"), Gdx.files.internal("font/gameFont.png"), false);
	private static BitmapFont font3 = new BitmapFont(Gdx.files.internal("font/slant.fnt"), Gdx.files.internal("font/slant.png"), false);
	private static ShapeRenderer shape = new ShapeRenderer();
	private static OrthographicCamera camera;

	public Draw2D()
	{
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);
	}

	public static void updateCamera()
	{
		batch.setProjectionMatrix(camera.combined);
	}

	public static void drawTextured(float x, float y, Texture texture)
	{
		batch.enableBlending();
		batch.begin();
		batch.draw(texture, x, y);
		batch.end();
	}

	public static void drawTextured(float x, float y, float scaleX, float scaleY, Texture texture)
	{
		batch.enableBlending();
		batch.begin();
		batch.draw(new TextureRegion(texture), x, y, 16, 16, scaleX, scaleY, 1, 1, 0);
		batch.end();
	}

	public static void drawTextured(float x, float y, Texture texture, float deg)
	{
		batch.enableBlending();
		batch.begin();
		batch.draw(new TextureRegion(texture), x, y, 16, 16, texture.getWidth(), texture.getHeight(), 1, 1, deg);
		batch.end();
	}

	public static void drawTextured(float x, float y, float ooffx, float ooffy, Texture texture, int deg)
	{
		batch.enableBlending();
		batch.begin();
		batch.draw(new TextureRegion(texture), x, y, ooffx, ooffy, texture.getWidth(), texture.getHeight(), 1, 1, deg);
		batch.end();
	}

	public static void drawString(float x, float y, String str, float scale, Color color)
	{

		batch.begin();
		font.setScale(scale);
		font.setColor(color);
		font.draw(batch, str, x, y);
		batch.end();
	}
	
	public static void drawString2(float x, float y, String str, float scale, Color color)
	{

		batch.begin();
		font2.setScale(scale);
		font2.setColor(color);
		font2.draw(batch, str, x, y);
		batch.end();
	}
	
	public static void drawString3(float x, float y, String str, float scale, Color color)
	{

		batch.begin();
		font3.setScale(scale);
		font3.setColor(color);
		font3.draw(batch, str, x, y);
		batch.end();
	}

	public static void drawRect(float x, float y, float width, float height, Color color, boolean filled)
	{
		if(filled)
			shape.begin(ShapeType.Filled);
		else
			shape.begin(ShapeType.Line);
		shape.setColor(color);
		shape.rect(x, y, width, height);
		shape.end();
	}

	public static void drawRect(float x, float y, float width, float height, int degrees, Color color, boolean filled)
	{
		if(filled)
			shape.begin(ShapeType.Filled);
		else
			shape.begin(ShapeType.Line);
		shape.setColor(color);
		shape.rect(x, y, 8, 8, width, height, 1, 1, degrees);
		shape.end();
	}

	public static void drawLine(float x, float y, float x2, float y2, Color color)
	{
		shape.begin(ShapeType.Filled);
		shape.setColor(color);
		shape.line(x, y, x2, y2);
		shape.end();
	}

	public static void dispose()
	{
		batch.dispose();
	}
}