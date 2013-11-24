package vlt.android.intervaltimerxp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import android.view.Menu;
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
			list = getHelper().getDao(Training.class).queryForAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		adapter = new TrainingAdapter(context, list);
		listView.setAdapter(adapter);
		/*
		 * Set on click listener for listView
		 */
		listView.setOnItemClickListener(this);
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
				buttonSave.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Training training = new Training();
						try {
							training.setExercize(textExcersize.getText()
									.toString());
							training.setNumberOfIntervals(Integer
									.parseInt((textNumberOfIntervals.getText()
											.toString())));
							training.setWorkTimeForOneInterval(Integer
									.parseInt((textWorkDuration.getText()
											.toString())));
							training.setRestTimeForOneInterval(Integer
									.parseInt((textRestDuratione.getText()
											.toString())));
							training.setDescription(textDescription.getText()
									.toString());
							if (training.getExercize().trim().equals("")) {
								AlertDialog.Builder builder = new AlertDialog.Builder(
										context);
								builder.setMessage("Error: please, complete all fields (description is not nessecary)!");
							} else {
								getHelper().getDao(Training.class).create(
										training);
								addTrainingDialog.dismiss();
								initialize();
							}
						} catch (SQLException e) {
							AlertDialog.Builder builder = new AlertDialog.Builder(
									context);
							builder.setMessage("Error: can't write to database, please, contact the developer");
							textViewError
									.setText("Error: can't write to database, please, contact the developer");
						} catch (NumberFormatException e) {
							AlertDialog.Builder builder = new AlertDialog.Builder(
									context);
							builder.setMessage("Error: please, complete all fields (description is not nessecary)!");
							textViewError
									.setText("Error: please, complete all fields (description is not nessecary)!");
						}
					}
				});
				addTrainingDialog.show();
			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		final Dialog addTrainingDialog = new Dialog(context);
		addTrainingDialog.setTitle(R.string.edit);
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
		
		addTrainingDialog.show();
	}
	
	
}
