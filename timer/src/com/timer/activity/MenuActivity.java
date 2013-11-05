package com.timer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.timer.R;

public class MenuActivity extends Activity {

	private Button btSimple;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);

		btSimple = (Button) findViewById(R.id.bt_menu_simple_workout);
		btSimple.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				final Intent intent = new Intent(MenuActivity.this,
						SimpleWorkoutSettingsActivity.class);
				startActivity(intent);
			}
		});
	}
}
