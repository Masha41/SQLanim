package tables;

import db.IDBConnector;
import db.MySQLConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbsTable {

    protected String tableName;

    protected Map<String, String> columns = new HashMap<>();

    IDBConnector db;


    public  AbsTable(String tableName){

        this.tableName = tableName;
    }


    public void create() {

        String sqlRequest = String.format("CREATE TABLE IF NOT EXISTS %s (%s)", this.tableName, convertMapColumsToString());

        db = new MySQLConnector();
        db.executeRequest(sqlRequest);
        db.close();
    }

    private String convertMapColumsToString(){
       String result = "";
      for(Map.Entry<String, String> el : columns.entrySet()) {
           result +=el.getKey() + "" + el.getValue() + ",";
       }
System.out.println(result);
        result = result.substring(0, result.length()-1);
        System.out.println(result);
      return result;

    }
    public void writeAll () {
        db = new MySQLConnector();
        final String sqlRequest = String.format("SELECT * FROM %s", tableName);
        ResultSet rs = db.executeRequestWithAnswer(sqlRequest);
        try {
            int colums = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= colums; i++) {
                    System.out.print(rs.getString(i) + "\t");

                }
                System.out.println();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }
    }
}
