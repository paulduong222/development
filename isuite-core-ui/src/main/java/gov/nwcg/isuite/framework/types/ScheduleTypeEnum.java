package gov.nwcg.isuite.framework.types;

/**
 * Indicates the type of schedule.
 * @author doug
 *
 */
public enum ScheduleTypeEnum {
	IMMEDIATE, // once a task is completed, start another immediately
	CONTINUOUS, // once a task is completed, start another after a delay
	DAILY, // once a task is completed, start another the next day at a given time
	NEVER; // once a task is completed, don't start another

}
