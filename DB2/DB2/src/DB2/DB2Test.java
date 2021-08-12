package DB2;
import java.sql.*;


public class DB2Test {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;


    public void openConnection(){

// Step 1: Load IBM DB2 JDBC driver

        try {

            DriverManager.registerDriver(new com.ibm.db2.jcc.DB2Driver());

        }

        catch(Exception cnfex) {

            System.out.println("Problem in loading or registering IBM DB2 JDBC driver");

            cnfex.printStackTrace();
        }

// Step 2: Opening database connection


        try {

            connection = DriverManager.getConnection("jdbc:db2://62.44.108.24:50000/SAMPLE", "db2admin", "db2admin");

            statement = connection.createStatement();

        }

        catch(SQLException s){

            s.printStackTrace();

        }

    }

    public void closeConnection(){

        try {

            if(null != connection) {

                // cleanup resources, once after processing

                resultSet.close();

                statement.close();


                // and then finally close connection

                connection.close();

            }

        }

        catch (SQLException s) {

            s.printStackTrace();

        }

    }

    public String select(String stmnt) {

        try{
            resultSet = statement.executeQuery(stmnt);
            StringBuilder result = new StringBuilder();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int column = resultSetMetaData.getColumnCount();

            while(resultSet.next()) {

                for (int i = 1; i <= column; i++) {

                    result.append(resultSet.getString(i));

                    if (i == column)
                    {
                        result.append(" \n");
                    }

                    else
                    {
                        result.append( ", ");
                    }
                }
            }
            return result.toString();
        }
        catch (SQLException s)
        {
            s.printStackTrace();
            return "Error occurred";
        }
    }

    public boolean insert(String stmnt) {

        try{
            if(checkWord(stmnt, "INSERT"))
            {
                statement.executeUpdate(stmnt);
                return true;
            }
            else
            {
                return false;
            }

        }

        catch (SQLException s)
        {

            s.printStackTrace();
            return false;

        }

    }


    public boolean delete(String stmnt) {

        try{

            if(checkWord(stmnt, "DELETE"))
            {
                statement.executeUpdate(stmnt);
                return true;
            }
            else
            {
                return false;
            }

        }

        catch (SQLException s){

            s.printStackTrace();
            return false;

        }
    }
    private boolean checkWord(String stringToSearch, String wordToMatch)
    {
        int intIndex = stringToSearch.indexOf(wordToMatch);
        if (intIndex == -1)
        {
            return false;
        } else
        {
            return true;
        }
    }


          public static void main(String[] args)
          {
      DB2Test db2Obj = new DB2Test();
        String stmnt = "";
        String title = "ABC Love D";
        String star = "Jane Fonda";
        int  year = 1980;

        db2Obj.openConnection();

        stmnt = "SELECT STAFFID, NAME, BRANCH_NAME  FROM FN71986.STAFF";

              System.out.println(db2Obj.select(stmnt));

       // stmnt = " INSERT INTO DB2MOVIE.STARSIN(MOVIETITLE, MOVIEYEAR, STARNAME)"
              //  + " VALUES ('" + title + "','" + year + "','" + star + "')";

        //db2Obj.insert(stmnt);


       // stmnt = "DELETE FROM DB2MOVIE.STARSIN WHERE MOVIETITLE = " + "'" + title + "' ";

        //db2Obj.delete(stmnt);

       db2Obj.closeConnection();

    }




}