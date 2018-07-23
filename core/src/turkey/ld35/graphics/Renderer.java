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

public class Renderer
{
	private static OrthographicCamera camera;
	private static SpriteBatch batch;
	public static ShapeRenderer shape;

	public static BitmapFont ss;
	public static BitmapFont gf;
	public static BitmapFont sl;

	public static void initRenderer()
	{
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		batch = new SpriteBatch();
		shape = new ShapeRenderer();

		ss = new BitmapFont(Gdx.files.internal("font/shapeShifter.fnt"), Gdx.files.internal("font/shapeShifter.png"), false);
		gf = new BitmapFont(Gdx.files.internal("font/gameFont.fnt"), Gdx.files.internal("font/gameFont.png"), false);
		sl = new BitmapFont(Gdx.files.internal("font/slant.fnt"), Gdx.files.internal("font/slant.png"), false);
	}

	public static void update()
	{
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		shape.setProjectionMatrix(camera.combined);
	}

	public static void resize(int width, int height)
	{
		camera.setToOrtho(false, width, height);
	}

	public static void begin()
	{
		batch.begin();
	}

	public static void end()
	{
		batch.end();
	}

	public static void dispose()
	{
		batch.dispose();
		shape.dispose();
		ss.dispose();
		gf.dispose();
		sl.dispose();
	}

	public static void drawTextured(float x, float y, Texture texture)
	{
		batch.draw(texture, x, y);
	}

	public static void drawTextured(float x, float y, Texture texture, float width, float height)
	{
		batch.draw(texture, x, y, width, height);
	}

	public static void drawTextured(float x, float y, float scaleX, float scaleY, Texture texture)
	{
		batch.draw(new TextureRegion(texture), x, y, 16, 16, scaleX, scaleY, 1, 1, 0);
	}

	public static void drawTextured(float x, float y, Texture texture, float deg)
	{
		batch.draw(new TextureRegion(texture), x, y, 16, 16, texture.getWidth(), texture.getHeight(), 1, 1, deg);
	}

	public static void drawTextured(float x, float y, float ooffx, float ooffy, Texture texture, int deg)
	{
		batch.draw(new TextureRegion(texture), x, y, ooffx, ooffy, texture.getWidth(), texture.getHeight(), 1, 1, deg);
	}

	public static void drawString(BitmapFont f, float x, float y, String str, float scale, Color color)
	{
		f.setColor(color);
		f.getData().setScale(scale);
		f.draw(batch, str, x, y);
	}

	public static void drawRect(float x, float y, float width, float height, Color color, boolean filled)
	{
		batch.end();
		if(filled)
			shape.begin(ShapeType.Filled);
		else
			shape.begin(ShapeType.Line);
		shape.setColor(color);
		shape.rect(x, y, width, height);
		shape.end();
		batch.begin();
	}

	public static void drawRect(float x, float y, float width, float height, int degrees, Color color, boolean filled)
	{
		batch.end();
		if(filled)
			shape.begin(ShapeType.Filled);
		else
			shape.begin(ShapeType.Line);
		shape.setColor(color);
		shape.rect(x, y, 8, 8, width, height, 1, 1, degrees);
		shape.end();
		batch.begin();
	}

	public static void drawLine(float x, float y, float x2, float y2, Color color)
	{
		batch.end();
		shape.begin(ShapeType.Filled);
		shape.setColor(color);
		shape.line(x, y, x2, y2);
		shape.end();
		batch.begin();
	}
}
