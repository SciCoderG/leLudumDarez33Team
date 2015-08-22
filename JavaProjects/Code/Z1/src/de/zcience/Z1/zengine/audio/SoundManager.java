package de.zcience.Z1.zengine.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.audio.Ogg.Sound;

public class SoundManager {
	private boolean muted;
	
	Sound sound;
	
	public SoundManager() {
		this.initialize();
	}
	
	private void initialize() {
		this.muted = false;
		
	}
	
	/**
	 * Check if the AudioManager is muted
	 * 
	 * @return true when muted, false if not muted
	 */
	public boolean isMuted() {
		return this.muted;
	}
	
	public void play(String sound) {
		Gdx.audio.newSound(Gdx.files.internal(sound)).play();
	}
}
