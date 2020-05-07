package com.application.weather;
import java.util.Timer;
import java.util.TimerTask;

import com.application.weather.*;
public class Main {
	public static long timerSeconds;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		timerSeconds = 180000; //5 minutes
		WeatherAlert alert = new WeatherAlert();
		
		
		Timer timer = new Timer();
	        timer.schedule(new TimerTask() {

	            @Override
	            public void run() {
	            	alert.checkTemperature();
	            }
	        }, 0, timerSeconds);
	    }



		
		
	}


