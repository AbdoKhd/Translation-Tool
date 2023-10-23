/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import java.sql.*;

/**
 *
 * @author Main
 */
public class ClassConnection {
    
    private static final String jdbcDriver  = "com.mysql.cj.jdbc.Driver";
    private static final String dbPath      = "jdbc:mysql://localhost:3306/";
    private static final String dbName      = "new Project";
    private static final String user        = "root";
    private static final String pwd         = "rootroot";
    
    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;
    
    public ClassConnection()
    {
        connect();
    }
    
    private void connect()
    {
        try{
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbPath+dbName+"?serverTimezone=UTC", user, pwd);
            statement = connection.createStatement();
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    public ResultSet executeQuery(String sql)
    {
        ResultSet resultSet = null;
        try{
            resultSet = statement.executeQuery(sql);
        }catch(Exception e)
        {
            
        }
        return resultSet;
        
    }
    
    public PreparedStatement loadPreparedStatement(String sql)
    {
        try{
            preparedStatement = connection.prepareStatement(sql);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }finally{
            return preparedStatement;
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }
    
    
}
