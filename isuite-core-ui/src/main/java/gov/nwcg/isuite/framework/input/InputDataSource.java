/**
 * 
 */
package gov.nwcg.isuite.framework.input;

import gov.nwcg.isuite.framework.types.InputDataSourceEnum;

import java.io.Serializable;

/**
 * Represents the source of the input data.
 * <p>
 * The source is identified by two pieces of data, a
 * <code>InputDataSourceEnum</code> that identifies the system from which the
 * data originated and a <code>originator</code> that identifies some entity
 * within that external system that orginated the data. This originator could be
 * the user of the external system that created the data or authorized its
 * release. It may also be null if the external system does not support such
 * fine-grained identification.
 * </p>
 * 
 * @author doug
 */
//@Embeddable
public interface InputDataSource extends Serializable {

   /**
    * Identifier of the originator of the data from within the external system.
    * 
    * @return Identifier of the originator of the data from within the external
    *         system. can be null if unknown
    */
   public String getOriginator();

   /**
    * Enumerated source of data.
    * 
    * @return enumerated source of data
    */
   public InputDataSourceEnum getInputSourceEnum();

}
