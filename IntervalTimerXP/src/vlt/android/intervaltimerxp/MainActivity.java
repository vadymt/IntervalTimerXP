package vlt.android.intervaltimerxp;

import vlt.android.intervaltimerxp.model.DatabaseHelper;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends OrmLiteBaseActivity<DatabaseHelper> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	private void initializeButtonAddTraining(Bundle savedInstanceState) {
		final Button button = (Button) findViewById(R.id.buttonAddTraining);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                
            }
        });
	}

}
