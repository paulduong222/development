package gov.nwcg.isuite.framework.core.xmltransferv2;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface XmlTransferTable {
	// name of table
	String name() default "";

	String table() default "";
	
	String orderby() default "";
	
	String filter() default "";
	
	String filterincident() default "";
	
	String filtergroup() default "";
	
	String alias() default "";
}
