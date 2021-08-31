package DB;


import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Map;

public class DBUtils {

    /**
     * this method call for SQL query without parameters.
     * @param sql SQL query that is used to change parameters on Data Base.
     * @return sql tables after execution
     */
    public static boolean runQuery(String sql){
        //drop table , delete , alter
        Connection connection = null;
        try{
            //taking a connection from connection pool
            connection = ConnectionPool.getInstance().getConnection();

            //run the sql command
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return false;
    }


    /**
     * this method take a a SQL query and a map of the parameter and injects the parameters from the map to the query
     * @param query  specific Sql query
     * @param params the name of the map
     * @throws SQLException
     */
    public static void runQuery(String query, Map<Integer,Object> params) throws SQLException {
        Connection connection = null;
        try{
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            params.forEach((key,value)->{
                try{
                    //int,string,date,boolean,double,float
                    if (value instanceof Integer){
                        statement.setInt(key,(int) value);
                    } else if (value instanceof String){
                        statement.setString(key, String.valueOf(value));
                    } else if (value instanceof java.util.Date ){
                        //this will convert date to local date
                        LocalDate myDate = LocalDate.ofInstant(((java.util.Date) value).toInstant(), ZoneId.systemDefault());
                        //this will convert local date to sql date.
                        java.sql.Date sqlDate = java.sql.Date.valueOf(myDate);
                        statement.setDate(key, sqlDate);
                    } else if (value instanceof Boolean){
                        statement.setBoolean(key,(boolean) value);
                    } else if (value instanceof Double){
                        statement.setDouble(key,(double)value);
                    } else if (value instanceof Float){
                        statement.setFloat(key,(float) value);
                    } else if (value instanceof Timestamp){
                        statement.setTimestamp(key, (Timestamp) value);
                    }
                } catch (SQLException err){
                    System.out.println(err.getMessage());
                }
            });
            statement.execute();
        } catch (Exception err){
            System.out.println("DBUtils.runQuery"+err.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }
}
