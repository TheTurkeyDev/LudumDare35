package turkey.ld35.sounds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import turkey.ld35.game.GameSettings;

public class SoundManager
{
	public static Sound buttonPress = Gdx.audio.newSound(Gdx.files.internal("sounds/buttonPress.wav"));
	public static Sound monsterDamage = Gdx.audio.newSound(Gdx.files.internal("sounds/monsterDamage.wav"));
	public static Sound playerDamage = Gdx.audio.newSound(Gdx.files.internal("sounds/playerDamage.wav"));
	public static Sound playerShoot = Gdx.audio.newSound(Gdx.files.internal("sounds/playerShoot.wav"));

	public static void playSound(Sound sound, float volume)
	{
		if(GameSettings.sounds)
			sound.play(volume);
	}

	public static void disposeSounds()
	{
		buttonPress.dispose();
		monsterDamage.dispose();
		playerDamage.dispose();
		playerShoot.dispose();
	}
}
