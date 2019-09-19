package gov.nwcg.isuite.framework.types;

/**
 * @author doug
 *
 */
public enum ExternalSystemInputStateEnum {

   STALLED, // initial jobs, no processing jobs
   STOPPED, // processing job longer than x minutes
   PROCESSING, // processing job less than x minutes
   WAITING, // no initial or processing jobs
   UNKNOWN;
}
