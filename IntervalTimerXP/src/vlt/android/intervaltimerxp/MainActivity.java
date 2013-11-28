package vlt.android.intervaltimerxp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vlt.android.intervaltimerxp.IntervalTimer.IntervalTimerTask;
import vlt.android.intervaltimerxp.model.DatabaseHelper;
import vlt.android.intervaltimerxp.model.Training;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends OrmLiteBaseActivity<DatabaseHelper> implements
		OnItemClickListener {
	private Context context = this;
	private TrainingAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initialize();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void initialize() {
		final ListView listView = (ListView) findViewById(R.id.listTraining);
		List<Training> list = new ArrayList<Training>();
		try {
			Dao<Training, Integer> dao = getHelper().getDao(Training.class);
			list = dao.queryForAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Collections.sort(list);
		adapter = new TrainingAdapter(context, list);
		listView.setAdapter(adapter);
		/*
		 * Set on click listener for listView
		 */
		listView.setOnItemClickListener(this);
		listView.setOnCreateContextMenuListener(this);
		initializeButtonAndDialogAddTraining();
	}

	@Override
	protected void onResume() {
		super.onResume();
		initialize();
	};

	private void initializeButtonAndDialogAddTraining() {
		final Button button = (Button) findViewById(R.id.buttonAddTraining);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showSaveEditDialog(null);
			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Training training = adapter.getItem(arg2);
		Bundle bundle = new Bundle();
		bundle.putSerializable("training", training);
		Intent intent = new Intent(MainActivity.this, TrainingActivity.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		if (v.getId() == R.id.listTraining) {
			getMenuInflater().inflate(R.menu.list_item_menu, menu);
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		int menuItemIndex = item.getItemId();
		Training tr = adapter.getItem(info.position);
		int count;
		if (menuItemIndex == R.id.itemRemove) {
			try {
				Dao<Training, Integer> dao = getHelper().getDao(Training.class);
				dao.deleteById(tr.getId());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				return false;
			}
		}
		if (menuItemIndex == R.id.itemEdit) {
			/*
			 * Show edit dialog
			 */
			showSaveEditDialog(tr);

		}
		initialize();
		return true;

	}

	private final void showSaveEditDialog(Training training) {
		final Dialog addTrainingDialog = new Dialog(context);
		addTrainingDialog.setTitle(R.string.add);
		addTrainingDialog.setContentView(R.layout.dialog_add_training);
		final EditText textExcersize = (EditText) addTrainingDialog
				.findViewById(R.id.editTextExcersize);
		final EditText textNumberOfIntervals = (EditText) addTrainingDialog
				.findViewById(R.id.editTextNumberOfIntervals);
		final EditText textWorkDuration = (EditText) addTrainingDialog
				.findViewById(R.id.editTextWorkDuration);
		final EditText textRestDuratione = (EditText) addTrainingDialog
				.findViewById(R.id.editTextRestDuration);
		final EditText textDescription = (EditText) addTrainingDialog
				.findViewById(R.id.editTextDescription);
		final TextView textViewError = (TextView) addTrainingDialog
				.findViewById(R.id.textViewError);
		Button buttonSave = (Button) addTrainingDialog
				.findViewById(R.id.buttonSaveTraining);
		if (training != null) {
			textExcersize.setText(training.getExercize());
			textNumberOfIntervals.setText(Integer.toString(training
					.getNumberOfIntervals()));
			textRestDuratione.setText(Integer.toString(training
					.getRestTimeForOneInterval()));
			textWorkDuration.setText(Integer.toString(training
					.getWorkTimeForOneInterval()));
			textDescription.setText(training.getDescription());

		}
		class OnClickListenerForSaveAndEdit implements View.OnClickListener {
			private Training training;

			@Override
			public void onClick(View v) {
				boolean save;
				if (training == null) {
					save = true;
					training = new Training();
				} else {
					save = false;
				}
				try {
					training.setExercize(textExcersize.getText().toString());
					training.setNumberOfIntervals(Integer
							.parseInt((textNumberOfIntervals.getText()
									.toString())));
					training.setWorkTimeForOneInterval(Integer
							.parseInt((textWorkDuration.getText().toString())));
					training.setRestTimeForOneInterval(Integer
							.parseInt((textRestDuratione.getText().toString())));
					training.setDescription(textDescription.getText()
							.toString());
					if (training.getExercize().trim().equals("")) {
						textViewError
								.setText("Error: please, complete all fields (description is not nessecary)!");
					} else {
						if (save) {
							getHelper().getDao(Training.class).create(training);
						} else {
							getHelper().getDao(Training.class).update(training);
							System.out.println("UPDATE COMLETE");
						}
						addTrainingDialog.dismiss();
						initialize();
					}
				} catch (SQLException e) {
					e.printStackTrace();
					textViewError
							.setText("Error: can't write to database, please, contact the developer");
				} catch (NumberFormatException e) {

					textViewError
							.setText("Error: please, complete all fields (description is not nessecary)!");
				}
			}

			public void setTraining(Training tr) {
				this.training = tr;
			}

		}

		OnClickListenerForSaveAndEdit listener = new OnClickListenerForSaveAndEdit();
		listener.setTraining(training);
		buttonSave.setOnClickListener(listener);
		addTrainingDialog.show();
	}
}
