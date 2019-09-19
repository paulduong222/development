package gov.nwcg.isuite.framework.core.xmltransfer;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface XmlTransferTable {
	// name of table
	String name() default "";

	String table() default "";
	
	boolean jointable() default false;
	
	boolean finaltable() default false;
	
	String orderby() default "";
	
	String filter() default "";
}
