package com.timer.service;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class CounterService extends Service {

	private Timer timer;

	@Override
	public int onStartCommand(final Intent intent, final int flags,
			final int startId) {
		timer = new Timer();
		final Integer counter = intent.getIntExtra("seconds", 0);

		timer.scheduleAtFixedRate(new TimerTask() {
			Integer secondsCount = counter;

			@Override
			public void run() {
				Log.i("Timer counter: ", secondsCount.toString());
				final Intent countInternt = new Intent("com.timer.COUNTER");
				countInternt.putExtra("COUNT", secondsCount);
				sendBroadcast(countInternt);

				if (secondsCount < 0) {
					stopSelf();
				}

				secondsCount--;
			}
		}, 0, 1000);

		return START_NOT_STICKY;
	}

	@Override
	public IBinder onBind(final Intent arg0) {
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (timer != null) {
			timer.cancel();
		}
	}

}
