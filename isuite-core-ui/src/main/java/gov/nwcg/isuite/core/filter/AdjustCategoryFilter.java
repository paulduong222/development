package gov.nwcg.isuite.core.filter;

import gov.nwcg.isuite.framework.core.filter.Filter;

public interface AdjustCategoryFilter extends Filter {
   
   public void setCode(String code);

   public String getCode();
}
