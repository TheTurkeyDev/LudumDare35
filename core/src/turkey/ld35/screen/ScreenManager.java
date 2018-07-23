package turkey.ld35.screen;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Screen;

public class ScreenManager
{
	private static Map<String, Class<? extends Screen>> screens = new HashMap<>();
	private static Map<String, Screen> screenCache = new HashMap<>();

	public static void addScreen(String name, Class<? extends Screen> screen)
	{
		screens.put(name, screen);
	}

	public static Screen getScreen(String name, boolean cache)
	{
		if(cache && screenCache.containsKey(name))
			return screenCache.get(name);

		if(screens.containsKey(name))
		{
			try
			{
				Screen s = screens.get(name).newInstance();
				if(screenCache.containsKey(name))
					screenCache.get(name).dispose();
				screenCache.put(name, s);
				return s;
			} catch(InstantiationException | IllegalAccessException e)
			{
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	public static void dispose()
	{
		for(String s : screenCache.keySet())
			screenCache.get(s).dispose();
	}
}
