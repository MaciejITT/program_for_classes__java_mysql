import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.DriverManager;

/**
 *
 * @author Lenovo
 */
public class Sql_Ruler10 {
    static Connection connection;
    static Scanner scan;
    static Statement statement;
    static ResultSet resultSet;
    static ResultSetMetaData  resultSetMetaData;
    
    public static void main(String args[]) throws SQLException{
       scan = new Scanner(System.in);
       
       try{
           
           connection = GetConnection.getConnection();
           System.out.println("Connection established");
           System.out.println("Select - for printing all data from table: select <table name> egz. select user");
           System.out.println("Insert - for adding data to table user egz. Insert 84321 Karol Nowak");
           System.out.println("Show tables - shows tables from database");
           System.out.println("You can use also SQL to insert,select data");
           statement = connection.createStatement();
           System.out.println("--------------------------------------------------------------------");
           statement.executeUpdate("CREATE DATABASE IF NOT EXISTS fspwcho_baza;");
           System.out.println("--------------------------------------------------------------------");
           statement.executeUpdate("use fspwcho_baza");
           System.out.println("--------------------------------------------------------------------");
           statement.executeUpdate("create table if not exists user(nr_albumu varchar(50), f_name varchar(50), l_name varchar(50))");
           System.out.println("--------------------------------------------------------------------");
       }
       catch( SQLException e ){
            System.out.println("Check your connection again.");
        }
       while(true){
            executeQuery();
       }
    }

    private static void executeQuery() {
        System.out.print("$> ");
        try{
            String query = scan.nextLine();
            String[] queryBifercation = query.split(" ");
            
            if(queryBifercation[0].equalsIgnoreCase("SELECT") || queryBifercation[0].equalsIgnoreCase("SHOW")){
                if(queryBifercation[0].equalsIgnoreCase("SELECT") && queryBifercation.length==2){
                    query="select * from "+queryBifercation[1]+";";
                }
                resultSet = statement.executeQuery(query);
                printResultSet();
            }
            else if (queryBifercation[0].equalsIgnoreCase("INSERT") && queryBifercation.length==4){
                String nr = queryBifercation[1];
                String name_f = queryBifercation[2];
                String name_l = queryBifercation[3];
                String add ="INSERT INTO user values ('"+nr+"','"+name_f+"','"+name_l+"');";
                statement.executeUpdate(add);
                System.out.println("Dodano do tabeli: "+nr+","+name_f+", "+name_l);
            }
            else{
                statement.executeUpdate(query);
            }
                
        }catch(SQLException e){
            System.out.println("Statement Cannot be executed");
        }
    }

    private static void printResultSet() throws SQLException {
        resultSetMetaData = resultSet.getMetaData();
        
        for(int i = 1; i<= resultSetMetaData.getColumnCount(); i++){
            
                System.out.print("\t");
                String columnName = resultSetMetaData.getColumnName(i);
                System.out.print(columnName);
            
        }
        System.out.println();
        while (resultSet.next()){
            for (int i =1; i<= resultSetMetaData.getColumnCount(); i++){
                
                    System.out.print("\t");
                    String columnValue = resultSet.getString(i);
                    System.out.print(columnValue);
                
            }
            System.out.println("");
        }
    }
    
    
}

class GetConnection {
    static Connection conn;
    static String USERNAME;
    static String PASSWORD;
    static String CONNECTION;
    
    public static Connection getConnection(){
        USERNAME = "mwadas";
        PASSWORD = "abc123";
        CONNECTION = "jdbc:mysql://10.0.0.3/";
    
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
            return conn;
        }catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

}

