package vlt.android.intervaltimerxp.model;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper{
	private static final String DATABASE_NAME = "interval.db";
	private static final int DATABASE_VERSION = 2;
	private final String LOG_NAME = getClass().getName();
	
	private Dao<Training, Integer> trainingDao;
	private Dao<TrainingRecord, Integer> trainingRecordDao;
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, Training.class);
			TableUtils.createTable(connectionSource, TrainingRecord.class);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Unable to create datbases", e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {
		try {
			TableUtils.dropTable(connectionSource, Training.class, true);
			TableUtils.dropTable(connectionSource, TrainingRecord.class, true);
			onCreate(sqliteDatabase, connectionSource);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Unable to upgrade database from version " + oldVer + " to new "
					+ newVer, e);
		}
	}

	public Dao<Training, Integer> getTrainingDao() throws SQLException {
		if (trainingDao == null) {
			trainingDao = getDao(Training.class);
		}
		return trainingDao;
	}

	public Dao<TrainingRecord, Integer> getTrainingRecordDao() throws SQLException {
		if (trainingRecordDao == null) {
			trainingRecordDao = getDao(TrainingRecord.class);
		}
		return trainingRecordDao;
	}

}
