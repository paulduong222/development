package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.core.filter.AdjustCategoryFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;

public class AdjustCategoryFilterImpl extends FilterImpl implements AdjustCategoryFilter {

   private static final long serialVersionUID = -3788939806355561004L;
   private String code;
   
   public void setCode(String code) {
      this.code = code;
   }

   public String getCode() {
	   return code;
   }
}
