package vlt.android.intervaltimerxp.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "training")
public class Training {
	@DatabaseField(id = true, generatedId = true)
	private Integer id;

	@DatabaseField(canBeNull = false, uniqueCombo = true)
	private String exercize;

	@DatabaseField(canBeNull = true)
	private String description;

	@DatabaseField(canBeNull = false, uniqueCombo = true)
	private int numberOfIntervals;

	@DatabaseField(canBeNull = false, uniqueCombo = true)
	private int workTimeForOneInterval;

	@DatabaseField(canBeNull = true, uniqueCombo = true)
	private int restTimeForOneInterval;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getExercize() {
		return exercize;
	}

	public void setExercize(String exercize) {
		this.exercize = exercize;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getNumberOfIntervals() {
		return numberOfIntervals;
	}

	public void setNumberOfIntervals(int numberOfIntervals) {
		this.numberOfIntervals = numberOfIntervals;
	}

	public int getWorkTimeForOneInterval() {
		return workTimeForOneInterval;
	}

	public void setWorkTimeForOneInterval(int workTimeForOneInterval) {
		this.workTimeForOneInterval = workTimeForOneInterval;
	}

	public int getRestTimeForOneInterval() {
		return restTimeForOneInterval;
	}

	public void setRestTimeForOneInterval(int restTimeForOneInterval) {
		this.restTimeForOneInterval = restTimeForOneInterval;
	}

}
