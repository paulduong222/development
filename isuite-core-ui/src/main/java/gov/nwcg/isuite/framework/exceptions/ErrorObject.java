package gov.nwcg.isuite.framework.exceptions;

import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.ErrorTypeEnum;

public class ErrorObject {
	private String errorProperty;
	private String[] parameters;
	private ErrorTypeEnum errorType=null;
	
	public ErrorObject() {
		
	}
	
	public ErrorObject(ErrorEnum errorProperty,String... params){
		if(null != errorProperty){
			this.errorProperty=errorProperty.getErrorName();
		}
		this.parameters=params;
	}

	public ErrorObject(ErrorTypeEnum type, ErrorEnum errorProperty,String... params){
		if(null != errorProperty){
			this.errorProperty=errorProperty.getErrorName();
		}
		this.parameters=params;
		this.errorType=type;
	}
	
	public ErrorObject(String errorProperty, String... params){
		if(null != errorProperty){
			this.errorProperty=errorProperty;
		}
		this.parameters=params;
	}
	
	/**
	 * Returns the parameters.
	 *
	 * @return 
	 *		the parameters to return
	 */
	public String[] getParameters() {
		return parameters;
	}

	/**
	 * Sets the parameters.
	 *
	 * @param parameters 
	 *			the parameters to set
	 */
	public void setParameters(String[] parameters) {
		this.parameters = parameters;
	}

	/**
	 * Returns the errorProperty.
	 *
	 * @return 
	 *		the errorProperty to return
	 */
	public String getErrorProperty() {
		return errorProperty;
	}

	/**
	 * Sets the errorProperty.
	 *
	 * @param errorProperty 
	 *			the errorProperty to set
	 */
	public void setErrorProperty(String errorProperty) {
		this.errorProperty = errorProperty;
	}

	public ErrorTypeEnum getErrorType() {
		return errorType;
	}

	public void setErrorType(ErrorTypeEnum errorType) {
		this.errorType = errorType;
	}
}
