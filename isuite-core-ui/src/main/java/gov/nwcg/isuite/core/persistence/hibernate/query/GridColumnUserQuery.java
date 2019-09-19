package gov.nwcg.isuite.core.persistence.hibernate.query;

public class GridColumnUserQuery {

   public static String restoreDefaultsSQLUpdate(String gridName, Boolean isOracle) {
      StringBuffer sql = new StringBuffer();
      //TODO:  As far as I understand this data is static.
      //       Upon further clarification, these queries will be removed from the app
      //       and the needed data will come from a static data store. -dbudge
      if(gridName.equals("RESOURCE")) {
          sql
         .append("UPDATE ISW_GRID_COLUMN ");
         if(isOracle) {
            sql.append("SET IS_DEFAULT = 1 ");
         } else {
            sql.append("SET IS_DEFAULT = TRUE ");//PostgreSQL
         }
         sql
         .append("WHERE ID IN ( ")
         .append("SELECT ID FROM ISW_GRID_COLUMN ")
         .append("WHERE GRID_NAME='RESOURCE' ")
         .append("AND COLUMN_NAME IN ( ")
         .append("'RequestNumber','ResourceName','ItemName', ")
         .append("'Status','Agency','UnitID','CheckInDate')) ");
      }
      
      if(gridName.equals("RESOURCEDEMOB")) {
          sql
         .append("UPDATE ISW_GRID_COLUMN ");
          if(isOracle) {
             sql.append("SET IS_DEFAULT = 1 ");
          } else {
             sql.append("SET IS_DEFAULT = TRUE ");//PostgreSQL
          }
         sql
         .append("WHERE ID IN ( ")
         .append("SELECT ID FROM ISW_GRID_COLUMN ")
         .append("WHERE GRID_NAME='RESOURCEDEMOB' ")
         .append("AND COLUMN_NAME IN ( ")
         .append("'RequestNumber','ResourceName','ItemName', ")
         .append("'Status','Agency','UnitID','DemobDate', ")
         .append("'MobTravelMethod', 'MobJetPort','Supplies')) ");
      }
      
      if(gridName.equals("RESOURCETIME")) {
          sql
         .append("UPDATE ISW_GRID_COLUMN ");
         if(isOracle) {
            sql.append("SET IS_DEFAULT = 1 ");
         } else {
            sql.append("SET IS_DEFAULT = TRUE ");//PostgreSQL
         }
         sql
         .append("WHERE ID IN ( ")
         .append("SELECT ID FROM ISW_GRID_COLUMN ")
         .append("WHERE GRID_NAME='RESOURCETIME' ")
         .append("AND COLUMN_NAME IN ( ")
         .append("'RequestNumber','ResourceName','Status', ")
         .append("'ItemCode','ActualReleaseDate','Agency','Deletable')) ");
      }
      
      return sql.toString();
   }
}
