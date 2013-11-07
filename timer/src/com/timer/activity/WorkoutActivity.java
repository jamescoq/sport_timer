package com.timer.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;

import com.timer.R;
import com.timer.service.CounterService;
import com.timer.util.TimeUtils;

public class WorkoutActivity extends Activity {

	private Integer effortTime;
	private Integer pauseTime;
	private Integer rounds;
	private Integer actualCount;
	private Boolean isPausePhase = false;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simple_workout);

		effortTime = getIntent().getIntExtra("effortTime", 0);
		pauseTime = getIntent().getIntExtra("pauseTime", 0);
		rounds = getIntent().getIntExtra("rounds", 0);
		Log.i("Effort timer ", effortTime.toString());
		Log.i("Pause timer ", pauseTime.toString());
		Log.i("Rounds ", rounds.toString());

		final TextView roundLabel = (TextView) findViewById(R.id.w_tv_round);
		roundLabel.setText(rounds.toString());

		if (effortTime != null && effortTime > 0) {
			startService(effortTime);
			actualCount = effortTime;
		}

		final Button btStopService = (Button) findViewById(R.id.w_bt_stop);
		btStopService.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				stopService();
			}
		});

		final Switch swPause = (Switch) findViewById(R.id.w_sw_pause_workout);
		swPause.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(final CompoundButton buttonView,
					final boolean isChecked) {
				if (isChecked) {
					stopService();
				} else {
					startService(actualCount);
				}
			}
		});
	}

	private final BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(final Context context, final Intent intent) {
			final Integer counter = intent.getIntExtra("COUNT", -1);

			if (counter < 0 && rounds > 0) {

				rounds--;
				isPausePhase = !isPausePhase;
				startService();

				final TextView roundLabel = (TextView) findViewById(R.id.w_tv_round);
				roundLabel.setText(rounds.toString());
			} else {

				actualCount = counter;
			}

			final Time time = TimeUtils.getTime(counter);
			final TextView tvTimer = (TextView) findViewById(R.id.w_tv_time);
			tvTimer.setText(time.format("%T"));
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

	private void startService() {
		if (isPausePhase) {
			startService(pauseTime);
		} else {
			startService(effortTime);
		}
	}

	private void startService(final Integer counter) {
		final Intent startCounterServiceIntent = new Intent(
				WorkoutActivity.this, CounterService.class);
		startCounterServiceIntent.putExtra("seconds", counter);
		startService(startCounterServiceIntent);
	}

	private void stopService() {
		final Intent stopServiceIntent = new Intent(WorkoutActivity.this,
				CounterService.class);
		stopService(stopServiceIntent);
	}
}
