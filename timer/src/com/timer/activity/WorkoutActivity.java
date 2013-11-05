package com.timer.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.timer.R;
import com.timer.service.CounterService;

public class WorkoutActivity extends Activity {

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simple_workout);

		final Integer effortTime = getIntent().getIntExtra("effortTime", 0);
		final Integer pauseTime = getIntent().getIntExtra("pauseTime", 0);
		final Integer rounds = getIntent().getIntExtra("rounds", 0);

		if (effortTime != null && effortTime > 0) {
			final Intent startCounterServiceIntent = new Intent(
					WorkoutActivity.this, CounterService.class);
			startCounterServiceIntent.putExtra("seconds", effortTime);
			startService(startCounterServiceIntent);
		}

		final Button btStopService = (Button) findViewById(R.id.w_bt_stop);
		btStopService.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				final Intent stopServiceIntent = new Intent(
						WorkoutActivity.this, CounterService.class);
				stopService(stopServiceIntent);
			}
		});

	}

	private final BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(final Context context, final Intent intent) {
			final Integer counter = intent.getIntExtra("COUNT", -1);
			Log.i("counter", "counter:" + counter);
			final TextView tvEffortTimer = (TextView) findViewById(R.id.w_tv_effort_time);
			tvEffortTimer.setText(counter.toString());
		}
	};

	private final IntentFilter filter = new IntentFilter("com.timer.COUNTER");

	@Override
	protected void onResume() {
		super.onResume();
		registerReceiver(receiver, filter);
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(receiver);
	}
}
