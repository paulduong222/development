package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.types.ScheduleTypeEnum;

/**
 * Defines the schedule of when data is processed.
 * <p>
 * A schedule defines when the next invocation of a task should occur. There are
 * several types of schedules as defined by the ScheduleTypeEnum.
 * </p>
 * <p>
 * The time is interpreted based on the type.  
 * <ul>
 * <li>IMMEDIATE -- time is ignored because the next invocation occurs immediately after the task is completed. </li>
 * <li>CONTINUOUS -- the next task is scheduled to run after a delay of the time value. </li>
 * <li>DAILY -- the next tasks is schedule to run once a day so the time is after midnight. </li>
 * <li>NEVER -- time is ignored because the next invocation never occurs. </li>
 * </ul>
 * 
 * @author doug
 * 
 */
public interface Schedule {

	/**
	 * Return the type of schedule.
	 * 
	 * @return type of schedule
	 */
	public ScheduleTypeEnum getType();

	/**
	 * Get the time of next invocation.
	 * 
	 * @return time  of next invocation
	 */
	public long getTime();

}
