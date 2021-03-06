package com.timer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.timer.R;
import com.timer.util.TimeUtils;

public class SimpleWorkoutSettingsActivity extends Activity {

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simple_workout_setting);

		final Button startWorkoutButton = (Button) findViewById(R.id.ws_bt_start_workout);
		startWorkoutButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View arg0) {
				final Intent startCounterServiceIntent = new Intent(
						SimpleWorkoutSettingsActivity.this,
						WorkoutActivity.class);

				startCounterServiceIntent.putExtra("effortTime",
						TimeUtils.getSeconds(0, 0, 10));
				startCounterServiceIntent.putExtra("pauseTime",
						TimeUtils.getSeconds(0, 0, 5));
				startCounterServiceIntent.putExtra("rounds", 3);

				startActivity(startCounterServiceIntent);
			}
		});

	}
}
