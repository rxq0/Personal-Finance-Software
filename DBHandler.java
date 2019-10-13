package Database;

import Login.Controller.SignupController;

import javax.swing.plaf.nimbus.State;
import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class DBHandler {

    // MYSQL credentials
    static final String mysql_databaseName = "currency_converter"; // Database that the program will use
    static final String mysql_username = "root"; // Set to your MYSQL username
    static final String mysql_password = "1234"; // Set to your MYSQL password
    static final String mysql_url = "jdbc:mysql://localhost:3306/mysql?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; // Targeting local server

    public static Connection connection; // Initialize DB connection object
    public FileInputStream fis;

    public static void initializeDatabase() {
        // Create the database and it's tables on our local MYSQL Server if its not created already
        try {
            connection = DriverManager.getConnection(mysql_url, mysql_username, mysql_password);
            String createDatabaseQuery = "CREATE DATABASE IF NOT EXISTS " + mysql_databaseName;
            Statement statement = connection.createStatement();
            statement.executeUpdate(createDatabaseQuery);
            statement.close();

            connection.setCatalog(mysql_databaseName); // Set the context. Same as "USE DBNAME" query.

            // Create users table
            String createUsersTableQuery = "CREATE TABLE IF NOT EXISTS users" +
                    "(id INTEGER not NULL AUTO_INCREMENT," +
                    "username VARCHAR(255) not NULL," +
                    "password VARCHAR(255) not NULL," +
                    "age INTEGER," +
                    "gender VARCHAR(255) not NULL," +
                    "image BLOB," +
                    "TRY FLOAT, USD FLOAT, AUD FLOAT, DKK FLOAT, EUR FLOAT, GBP FLOAT, CHF FLOAT, SEK FLOAT, CAD FLOAT, KWD FLOAT, NOK FLOAT, SAR FLOAT, JPY FLOAT, BGN FLOAT, RON FLOAT, RUB FLOAT, IRR FLOAT, CNY FLOAT, PKR FLOAT, QAR FLOAT," +
                    "PRIMARY KEY (id))";
            Statement createUsersTableStatement = connection.createStatement();
            createUsersTableStatement.executeUpdate(createUsersTableQuery);
            createUsersTableStatement.close();

            // Create userTransactions Table
            String createUserTransactionsQuery = "CREATE TABLE IF NOT EXISTS usertransactions" +
                    "(id INTEGER not NULL AUTO_INCREMENT," +
                    "username VARCHAR(255) not NULL," +
                    "TRY FLOAT, USD FLOAT, EUR FLOAT, GBP FLOAT, SEK FLOAT, CAD FLOAT," +
                    "create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "PRIMARY KEY (id))";

            Statement createUserTransactionsStatement = connection.createStatement();
            createUserTransactionsStatement.executeUpdate(createUserTransactionsQuery);
            createUserTransactionsStatement.close();


            // Create currencies table
            String createCurrenciesTableQuery = "CREATE TABLE IF NOT EXISTS currencies" +
                    "(id INTEGER not NULL AUTO_INCREMENT," +
                    "code VARCHAR(255) not NULL," +
                    "buying FLOAT not NULL," +
                    "selling FLOAT not NULL," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "PRIMARY KEY (id))";
            Statement createCurrenciesTableStatement = connection.createStatement();
            createCurrenciesTableStatement.executeUpdate(createCurrenciesTableQuery);
            createCurrenciesTableStatement.close();

            // Create CrossrateCurrencies table
            String createCrossrateCurrenciesTableQuery = "CREATE TABLE IF NOT EXISTS currencies_crossrate" +
                    "(id INTEGER not NULL AUTO_INCREMENT," +
                    "code VARCHAR(255) not NULL," +
                    "crossrate FLOAT not NULL," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "PRIMARY KEY (id))";
            Statement createCrossrateCurrenciesTableStatement = connection.createStatement();
            createCrossrateCurrenciesTableStatement.executeUpdate(createCrossrateCurrenciesTableQuery);
            createCrossrateCurrenciesTableStatement.close();




        } catch (SQLException e) {
            e.printStackTrace();
        }





        //-------

        //-------
    }
    //
    public static void createUser(String username, String password, Integer age, String gender, String lira, String usd, String aud , String dkk, String eur, String gbp, String chf, String sek, String cad, String kwd, String nok, String sar, String jpy, String bgn, String ron, String rub, String irr, String cny, String pkr, String qar) {
        try {
            String hashedPassword = Login.Util.MD5.getHash(password);
            // The MYSQL insert statement
            String query = "INSERT INTO users (username, password, age,gender, TRY, USD, AUD, DKK, EUR, GBP, CHF, SEK, CAD, KWD, NOK, SAR, JPY, BGN, RON, RUB, IRR, CNY, PKR, QAR)"
                    + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            // Create the MYSQL insert PreparedStatement
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, username);
            preparedStmt.setString(2, hashedPassword);
            preparedStmt.setInt(3, age);
            preparedStmt.setString(4, gender);
            preparedStmt.setString(5, lira);
            preparedStmt.setString(6, usd);
            preparedStmt.setString(7, aud);
            preparedStmt.setString(8, dkk);
            preparedStmt.setString(9, eur);
            preparedStmt.setString(10, gbp);
            preparedStmt.setString(11, chf);
            preparedStmt.setString(12, sek);
            preparedStmt.setString(13, cad);
            preparedStmt.setString(14, kwd);
            preparedStmt.setString(15, nok);
            preparedStmt.setString(16, sar);
            preparedStmt.setString(17, jpy);
            preparedStmt.setString(18, bgn);
            preparedStmt.setString(19, ron);
            preparedStmt.setString(20, rub);
            preparedStmt.setString(21, irr);
            preparedStmt.setString(22, cny);
            preparedStmt.setString(23, pkr);
            preparedStmt.setString(24, qar);


            // Execute the PreparedStatement
            preparedStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkLogin(String username, String password) {
        String hashedPassword = Login.Util.MD5.getHash(password);
        try {
            if (username != null && password != null) {
                String checkLoginQuery = "SELECT * FROM users WHERE username='" + username + "' AND password='" + hashedPassword + "'";
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(checkLoginQuery); // Get query result
                if (rs.next()) {
                    // Enters here if a user is found with given credentials
                    System.out.println("Logging in...");
                    return true;
                } else {
                    // Enters here when the combination is invalid
                    System.out.println("User not found");
                    return false;
                }
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        return false; // placeholder
    }

    // Method to add a new currency and it's exchange rate to the database, usage: addCurrency("usd", "5.753", "5.713")
    public static void addCurrency(String code, String buying, String selling) {
        // The MYSQL insert statement
        String query = "INSERT INTO currencies (code, buying, selling)"
                + " VALUES (?, ?, ?)";

        try {
            // Create the MYSQL insert PreparedStatement
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, code);
            preparedStmt.setString(2, buying);
            preparedStmt.setString(3, selling);

            // Execute the PreparedStatement
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //***
    public static void addCrossrateCurrency(String code, String crossrate) {
        // The MYSQL insert statement
        String query = "INSERT INTO currencies_crossrate (code, crossrate)"
                + " VALUES (?, ?)";

        try {
            // Create the MYSQL insert PreparedStatement
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, code);
            preparedStmt.setString(2, crossrate);

            // Execute the PreparedStatement
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    //***

    // Method to fetch the balance of a user, returns a dictionary with a currency name and its associated amount
    public static Dictionary getBalanceOfUser(String username) {
        // Initializing a dictionary to store the currency name and the amount the user has on that currency, ex.: usd:530 gbp:150
        Dictionary balance = new Hashtable();

        // Prepare SQL query to fetch different currencies that belongs to the user
        String getBalanceQuery = "SELECT TRY,USD,AUD,DKK,EUR,GBP,CHF,SEK,CAD,KWD,NOK,SAR,JPY,BGN,RON,RUB,IRR,CNY,PKR,QAR FROM users WHERE username = '" + username + "'";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(getBalanceQuery); // Get query result, only rows that contain currency amount
            ResultSetMetaData rsmd = rs.getMetaData(); // Get metadata, column names like usd-eur-gbp etc.
            int columnCount = rsmd.getColumnCount();
            rs.next(); // Move the cursor to the row
            // The column count starts from 1, since index 0 is always id
            for (int i = 1; i <= columnCount; i++) {
                balance.put(rsmd.getColumnName(i), rs.getString(i)); // Put column name and the value of the index from the database, i.e. USD:530
                //System.out.println(rsmd.getColumnName(i) + rs.getString(i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return balance;
    }

    public static  Integer getUserAge(Integer userAge){ //TEST CODE
        String getuserAgeQuery = "SELECT age FROM user WHERE code = '"+ userAge+"'ORDER BY id DESC LIMIT 1";
        Statement statement = null;
        Integer userages = Integer.valueOf(0);
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(getuserAgeQuery); // Get query result, contains the last entry to the corresponding currency
            rs.next();
            userages = rs.getInt("age");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userages;

    }

    public static Float getCurrencyBuying(String currencyCode) {
        String getBuyingQuery = "SELECT buying FROM currencies WHERE code = '" + currencyCode + "' ORDER BY id DESC LIMIT 1";
        Statement statement = null;
        Float buyingValue = Float.valueOf(0);
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(getBuyingQuery); // Get query result, contains the last entry to the corresponding currency
            rs.next();
            buyingValue = rs.getFloat("buying");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return buyingValue;
    }

    public static Float getCurrencySelling(String currencyCode) {
        String getSellingQuery = "SELECT selling FROM currencies WHERE code = '" + currencyCode + "' ORDER BY id DESC LIMIT 1";
        Statement statement = null;
        Float sellingValue = Float.valueOf(0);
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(getSellingQuery); // Get query result, contains the last entry to the corresponding currency
            rs.next();
            sellingValue = rs.getFloat("selling");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sellingValue;
    }
    //--
    public static Float getCrossrateSelling(String currencyCode) {
        String getCrossrateQuery = "SELECT selling FROM currencies_crossrate WHERE code = '" + currencyCode + "' ORDER BY id DESC LIMIT 1";
        Statement statement = null;
        Float crossRateValue = Float.valueOf(0);
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(getCrossrateQuery); // Get query result, contains the last entry to the corresponding currency
            rs.next();
            crossRateValue = rs.getFloat("crossrate");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return crossRateValue;
    }
    //--

    // Fetches the last five historic buying values of a currency from the database and returns a ResultSet containing those values
    public static ResultSet getCurrencyHistoryBuying (String currencyCode){

        //String getHistoryQuery = "SELECT buying,DATE_FORMAT(created_at, '%H:%i:%s') FROM currencies WHERE code = '" + currencyCode + "' ORDER BY id DESC LIMIT 5";
        String getHistoryQuery = "SELECT DISTINCT buying,DATE(created_at) FROM currencies WHERE code = '" + currencyCode + "' ORDER BY created_at ASC LIMIT 30";
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(getHistoryQuery); // Get query result, contains the last entry to the corresponding currency

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;

    }
    //--
    public static ResultSet getCurrencyHistorySelling (String currencyCode){

        //String getHistoryQuery = "SELECT buying,DATE_FORMAT(created_at, '%H:%i:%s') FROM currencies WHERE code = '" + currencyCode + "' ORDER BY id DESC LIMIT 5";
        String getHistoryQuery = "SELECT DISTINCT selling,DATE(created_at) FROM currencies WHERE code = '" + currencyCode + "' ORDER BY created_at ASC LIMIT 30";
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(getHistoryQuery); // Get query result, contains the last entry to the corresponding currency

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;

    }
    //--

    //--
    public static ResultSet getCurrencyHistoryUSD (String currencyCode){

        //String getHistoryQuery = "SELECT buying,DATE_FORMAT(created_at, '%H:%i:%s') FROM currencies WHERE code = '" + currencyCode + "' ORDER BY id DESC LIMIT 5";
        String getHistoryQuery = "SELECT DISTINCT crossrate,DATE(created_at) FROM currencies_crossrate WHERE code = '" + currencyCode + "' ORDER BY created_at ASC LIMIT 30";
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(getHistoryQuery); // Get query result, contains the last entry to the corresponding currency

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;

    }
    //--

    // Methods to increase/decrease balance of a specific currency of a user. Used after converting money in DashboardController
    public static void increaseBalanceOfUser(String username, String currencyCode, Float amount) {
        String increaseBalanceQuery = "UPDATE users SET " + currencyCode + "=" + currencyCode + "+" + amount + "WHERE username='" + username + "'";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(increaseBalanceQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void decreaseBalanceOfUser(String username, String currencyCode, Float amount) {
        String decreaseBalanceQuery = "UPDATE users SET " + currencyCode + "=" + currencyCode + "-" + amount + "WHERE username='" + username + "'";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(decreaseBalanceQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setBalanceOfUser(String username, String currencyCode, Float amount) {
        String setBalanceQuery = "UPDATE users SET " + currencyCode + "=" + amount + "WHERE username='" + username + "'";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(setBalanceQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void decreaseUserBalance(String username, String currencyCode, Float amount) {
        String setBalanceQuery1 = "UPDATE users SET " + currencyCode + "=" + currencyCode + "-" + amount + "WHERE username='" + username + "'";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(setBalanceQuery1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void increaseUserBalance(String username, String currencyCode, Float amount) {
        String setBalanceQuery1 = "UPDATE users SET " + currencyCode + "=" + currencyCode + "+" + amount + "WHERE username='" + username + "'";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(setBalanceQuery1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
