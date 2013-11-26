package vlt.android.intervaltimerxp.model;

import java.sql.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "training_record")
public class TrainingRecord {

	@DatabaseField(generatedId = true, allowGeneratedIdInsert = true)
	private Integer id;

	@DatabaseField(canBeNull = false)
	private int repeatCount;

	@DatabaseField(canBeNull = true)
	private int lastIntervalRepeatCount;

	@DatabaseField(canBeNull = false)
	private Date trainingDate;

	@DatabaseField(canBeNull = false, foreign = true)
	private Training trainingProgram;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(int repeatCount) {
		this.repeatCount = repeatCount;
	}

	public int getLastIntervalRepeatCount() {
		return lastIntervalRepeatCount;
	}

	public void setLastIntervalRepeatCount(int lastIntervalRepeatCount) {
		this.lastIntervalRepeatCount = lastIntervalRepeatCount;
	}

	public Date getTrainingDate() {
		return trainingDate;
	}

	public void setTrainingDate(Date trainingDate) {
		this.trainingDate = trainingDate;
	}

	public Training getTrainingProgram() {
		return trainingProgram;
	}

	public void setTrainingProgram(Training trainingProgram) {
		this.trainingProgram = trainingProgram;
	}

}
