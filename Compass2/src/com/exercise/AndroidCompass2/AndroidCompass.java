package com.exercise.AndroidCompass2;
/*
 *  Original code from: http://android-er.blogspot.com/2010/08/simple-compass-sensormanager-and.html
 *  Minor modifications for second screen compass: Rob.Erwin@gmail.com
 *  
 */

import java.util.List;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class AndroidCompass {
	private static SensorManager mySensorManager;
	private Context context;
	private boolean sensorrunning;
	protected boolean calibrateMazeNorth;
	private float difference = (float) 1000.0;
	private float magneticNorth = (float) 0.0;
	private float mazeNorth = (float) 0.0;

	public AndroidCompass(Context pContext) {
		context = pContext;
		mySensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		List<Sensor> mySensors = mySensorManager.getSensorList(Sensor.TYPE_ORIENTATION);

		if (mySensors.size() > 0) {
			mySensorManager.registerListener(mySensorEventListener,
					mySensors.get(0), SensorManager.SENSOR_DELAY_NORMAL);
			sensorrunning = true;
		} else {
			sensorrunning = false;
		}
	}
	
	public SensorManager getSensorManager() {
		return mySensorManager;
	}
	
	public boolean isSensorRunning(){
		return sensorrunning;
	}
	
	public void calibrateMazeNorth(){
		calibrateMazeNorth = true;
	}
	
	public float getMagneticNorth(){
		return magneticNorth;
	}
	
	public float getMazeNorth(){
		return mazeNorth;
	}
	
	public void unregisterSensorEventListener() {
		if (sensorrunning) {
			mySensorManager.unregisterListener(mySensorEventListener);
			sensorrunning = false;
		}

	}
	
	private SensorEventListener mySensorEventListener = new SensorEventListener() {
		{
			Log.d("mySensorEventListener", "created");
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			Log.d("mySensorEventListener.onAccuracyChanged", "called");
		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			Log.d("mySensorEventListener.onSensorChanged", "called");
			// set the Difference only once when told by external program
			if (calibrateMazeNorth) {
				// since MazeNorth will be 0, difference will simply be event.values[0]
				difference = (float) event.values[0];
				calibrateMazeNorth = false;
			}
			
			// set magnetic North
			magneticNorth = event.values[0];
			
			// set maze North
			if (difference == 1000.0)
				mazeNorth = (float) 0.0;
			else {
				float calcMazeNorth = (float) (event.values[0] - difference);
				mazeNorth = (calcMazeNorth < 0.0) ? 360 + calcMazeNorth : calcMazeNorth;
			}
		}
	};

}
