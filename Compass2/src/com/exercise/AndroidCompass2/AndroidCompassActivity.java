package com.exercise.AndroidCompass2;
/*
 *  Original code from: http://android-er.blogspot.com/2010/08/simple-compass-sensormanager-and.html
 *  Minor modifications for second screen compass: Rob.Erwin@gmail.com
 *  
 */

import java.util.List;

import com.exercise.AndroidCompass2.R;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

public class AndroidCompassActivity extends Activity {

	private static SensorManager mySensorManager;
	
	private MyCompassView myCompassView;
	private MyCompassView mazeNorthView;
	private Button setMazeNorthButton;
	private AndroidCompass myCompass;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		myCompassView = (MyCompassView) findViewById(R.id.mycompassview);
		mazeNorthView = (MyCompassView) findViewById(R.id.mymazenorthview);
		setMazeNorthButton = (Button) findViewById(R.id.mazenorthbutton);

		setMazeNorthButton.setOnClickListener(setMazeNorthListener);

		myCompass = new AndroidCompass(this.getBaseContext());  //Note: assuming getBaseContext is correct
		mySensorManager = myCompass.getSensorManager();
		List<Sensor> mySensors = mySensorManager.getSensorList(Sensor.TYPE_ORIENTATION);

		if (myCompass.isSensorRunning()) {
			Toast.makeText(this, "Start ORIENTATION Sensor", Toast.LENGTH_SHORT)
					.show();
			mySensorManager.registerListener(mySensorEventListener,
					mySensors.get(0), SensorManager.SENSOR_DELAY_NORMAL);
		} else {
			Toast.makeText(this, "No ORIENTATION Sensor", Toast.LENGTH_LONG)
					.show();
			finish();

		}
	}

	private OnClickListener setMazeNorthListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			myCompass.calibrateMazeNorth();
		}
	};

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		myCompass.unregisterSensorEventListener();
	}
	
	private void androidMessagePopup(String text){
		
		Toast.makeText(this, text, Toast.LENGTH_LONG)
		.show();
	}
	
	private SensorEventListener mySensorEventListener = new SensorEventListener() {

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub
			// androidMessagePopup("onAccuracyChanged called in AndroidCompassActivity.");

		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			// androidMessagePopup("onSensorChanged called in AndroidCompassActivity.");
			myCompassView.updateDirection(myCompass.getMagneticNorth());
			mazeNorthView.updateDirection(myCompass.getMazeNorth());
		}
	};


}