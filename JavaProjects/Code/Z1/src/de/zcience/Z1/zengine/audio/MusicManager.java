package de.zcience.Z1.zengine.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicManager {
private boolean muted;
	
	Music music;
	
	public MusicManager() {
		this.initialize();
	}
	
	private void initialize() {
		this.muted = false;
		
	}
	
	/**
	 * Check if the MusicManager is muted
	 * 
	 * @return true when muted, false if not muted
	 */
	public boolean isMuted() {
		return this.muted;
	}
	
	/**
	 * Loads the music	
	 *
	 * @param music - String path to a .ogg file
	 */
	public void load(String musicPath) {
		music = Gdx.audio.newMusic(Gdx.files.internal(musicPath));
	}
	
	/**
	 * Plays the music
	 */
	public void play() {
		music.play();
	}
	
	/**
	 * Stops the music
	 */
	public void stop() {
		music.stop();
	}
	
	/**
	 * Pause the music
	 */
	public void pause() {
		music.pause();
	}
	
	/**
	 * Loops the music
	 * 
	 * @param isLooping - true is looping, false no looping
	 */
	public void isLooping(boolean isLooping) {
		music.setLooping(isLooping);
	}
}
