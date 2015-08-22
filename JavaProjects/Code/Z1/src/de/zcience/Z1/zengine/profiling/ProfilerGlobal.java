package de.zcience.Z1.zengine.profiling;

import com.badlogic.gdx.Gdx;

/**
 * Profiler with static methods and members, can be used globally
 * @author David
 *
 */
public class ProfilerGlobal {

	private static long startTime, endTime, maxTime;
	private static float timer;
	private static float timerMax = 1.0f;
	
	private ProfilerGlobal(){
		
	}
	
	public static void startTime(){
		startTime = System.currentTimeMillis();
	}
	
	public static void endTime(){
		endTime = System.currentTimeMillis();
		
		maxTime = maxTime < (endTime-startTime) ? (endTime-startTime) : maxTime;
	}
	
	public static void outMax(String message){
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
	public static void setTimerToEverySecond(float set){
		timerMax = set;
	}
}
