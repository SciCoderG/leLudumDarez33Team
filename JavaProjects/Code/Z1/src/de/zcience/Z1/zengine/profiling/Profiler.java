package de.zcience.Z1.zengine.profiling;

import com.badlogic.gdx.Gdx;

/**
 * 
 * @author David
 *
 */
public class Profiler {
	private long startTime, endTime, maxTime;
	private float timer;
	private float timerMax;
	private String message = "";
	
	public Profiler(){
		timerMax = 1.0f;
	}
	
	/**
	 * 
	 * @param message will be appended to the output
	 */
	public Profiler(String message){
		this();
		this.message = message;
	}
	
	public void startTime(){
		startTime = System.currentTimeMillis();
	}
	
	public void endTime(){
		endTime = System.currentTimeMillis();
		
		maxTime = maxTime < (endTime-startTime) ? (endTime-startTime) : maxTime;
	}
	
	public void outMax(){
		this.outMax(this.message);
	}
	
	public void outMax(String message){
		if(timer <= 0){
			System.out.format(message + "max time: " + maxTime + " ms\n");
			timer = timerMax;
			maxTime = 0;
		}else{
			timer -= Gdx.graphics.getDeltaTime();
		}
	}
	
	/**
	 * Sets the frequency of the Profiler output
	 * @param set frequency in seconds
	 */
	public void setTimerToEverySecond(float set){
		timerMax = set;
	}
	
	public void setMessage(String message){
		this.message = message;
	}
}
