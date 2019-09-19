package gov.nwcg.isuite.core.vo;

public class UnitOfMeasureVo {
	
	private String code;
	
	public UnitOfMeasureVo() {
		super();
	}
	
	public UnitOfMeasureVo(String code) {
		super();
		setCode(code);
	}
	
	/**
	    * Returns the Code.
	    * 
	    * @return
	    * 		the code to return
	    */
	   public String getCode() {
		   return code;
	   }

	   /**
	    * Sets the code.
	    * 
	    * @param code
	    * 			the code to set
	    */
	   public void setCode(String code) {
		   this.code=code;
	   }

}
