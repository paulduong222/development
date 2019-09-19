package gov.nwcg.isuite.framework.core.xmltransfer;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface XmlTransferField {
	// name of data field, xml field
	String name() default "";

	String sqlname() default "";
	
	String sequence() default "";
	
	boolean primarykey() default false;
	
	Class target() default Object.class;
	
	// type of data field
	String type() default "";
	
	boolean derived() default false;
	
	String derivedfield() default "";
	
	String lookupname() default "";
	
	String sourcename() default "";

	boolean cascade() default false;
	
	boolean disjoined() default false;
	
	String disjoinedtable() default "";
	
	String disjoinedfield() default "";
	
	String disjoinedsource() default "";
	
	boolean nullwhenempty() default false;
	
	boolean joinkeyprimary() default false;
	
	boolean joinkeysecondary() default false;
	
	boolean updateable() default true;
	
	String alias() default "";
	
	String defaultvalue() default "";
	
	boolean ischardata() default false;
	
	String filter() default "";
}
