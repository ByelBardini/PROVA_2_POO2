package prova_2;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class Conexao {
    public static Connection concectaMySql(){
        Connection conn = null;
        try{
            Properties props = new Properties();
            props.load(new FileInputStream("db.properties"));

            conn = DriverManager.getConnection(
                    props.getProperty("url"),
                    props);
        }catch (Exception e){
            e.printStackTrace();
        }
        return conn;
    }
}
