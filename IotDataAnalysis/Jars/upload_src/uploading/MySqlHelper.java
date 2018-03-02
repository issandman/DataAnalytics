package uploading;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringJoiner;

/**
 * Created by hepeng on 17-7-6.
 */
public class MySqlHelper {
    private Connection connect = null;

    private static ArrayList<String> recordValue = new ArrayList<>();

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://115.159.120.160/iotdata?"
                + "user=root&password=852456321.");
    }

    public MySqlHelper() {

        // Setup the connection with the DB
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Statement getStatement() {
        try {
            if(!connect.isValid(1000) || connect == null){
                connect = getConnection();
            }

            return connect.createStatement();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void Insertrecord(String ip) {

        String value = String.format("(\"%s\")", ip);
        recordValue.add(value);
        if(recordValue.size() >= 1000) {
            ExecuteNonQuery(getSql());
            recordValue.clear();
        }
    }

    private void ExecuteNonQuery(String sql) {
        Statement s = getStatement();
        try {
            s.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getSql() {
        String prefix = "INSERT INTO ipaddress (ip) VALUES ";
        String infix = ",";
        String postfix = "";
        StringJoiner joiner = new StringJoiner(infix, prefix, postfix);
        for (String i : recordValue)
            joiner.add(i);
        return joiner.toString();
    	
    }

    public void Close() {
        if(recordValue.size() != 0) {
            ExecuteNonQuery(getSql());
        }
        try {
            connect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) throws Exception{
    	MySqlHelper mh = new MySqlHelper();
    	mh.Insertrecord("11.11.11.11");
    	mh.Close();
    }


}
