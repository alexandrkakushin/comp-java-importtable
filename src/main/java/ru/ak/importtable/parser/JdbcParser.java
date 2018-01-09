/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ak.importtable.parser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.ak.importtable.model.*;

/**
 *
 * @author ak
 */
public class JdbcParser {
    
    /**
     * Извлечение данных из базы данных и формирование модели
     * @param dbms - вид СУБД
     * @param connectionString - строка подключения
     * @param query - запрос
     * @return ModelTable - модель представления данных
     */
    public static ModelTable parse(String dbms, String connectionString, String query) {        
        ModelTable table = new ModelTable();
        
        try (Connection connection = getConnection(dbms, connectionString)) {            
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            
            // fields
            ResultSetMetaData metadata = rs.getMetaData();                        
            int numberOfFields = metadata.getColumnCount();                        
            String[] fields = new String[numberOfFields];
            for (int i = 1; i <= numberOfFields; i++) {                                
                table.addField(new ModelField(metadata.getColumnName(i), i, metadata.getColumnTypeName(i)));
                fields[i-1] = metadata.getColumnName(i);
            }

            // rows
            ArrayList<ModelCell> modelCells = new ArrayList<>();
            while (rs.next()) {
                modelCells.clear();                
                for (int i = 0; i < fields.length; i++) {
                    ModelCell modelCell = new ModelCell();
                    if (rs.getObject(i+1) != null) {
                        modelCell.setValue(rs.getObject(i+1).toString()); 
                    }
                    modelCells.add(modelCell); 
                }
                table.addRow(new ru.ak.importtable.model.ModelRow(modelCells));
            }
            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(JdbcParser.class.getName()).log(Level.SEVERE, null, ex);
            table.setError(ex.getLocalizedMessage());                    
        }
        
        return table;
    }
   
    /**
     * Проверка подключения к базе данных
     * @param dbms - вид СУБД
     * @param connectionString - строка подключения
     * @return Результат проверки
     */
    public static ModelPing ping(String dbms, String connectionString) {
        ModelPing result = new ModelPing();                
        try (Connection connection = getConnection(dbms, connectionString)) {
            result.setDescription("success");
        } catch (SQLException | ClassNotFoundException ex) {
            result.setError(true);
            result.setDescription(ex.getLocalizedMessage());
        }        
        return result;
    }
    
    /**
     * Получение структуры БД
     * @param dbms - вид СУБД
     * @param connectionString - строка подключения
     * @return ModelDb - модель базы данных
     */
    public static ModelDb getStructure(String dbms, String connectionString) {        
        
        ModelDb modelDb = new ModelDb();            
        try (Connection connection = getConnection(dbms, connectionString)) {
            Statement statement = connection.createStatement();        
            ResultSet rs = connection.getMetaData().getTables(null, null, null, null);                
            while (rs.next()) {
                ModelTable table = new ModelTable();
                table.setName(rs.getString("TABLE_NAME"));                
                modelDb.addTable(table);
            }            
        } catch (SQLException | ClassNotFoundException ex) {
            modelDb.setError(ex.getLocalizedMessage());
        }
        
        return modelDb;
    }
    
    /**
     * Подключение к базе данных
     * @param dbms - вид СУБД
     * @param connectionString - строка подключения
     * @return Connection
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    private static Connection getConnection(String dbms, String connectionString) throws SQLException, ClassNotFoundException {    
        Connection connection = null;                        
        String driver;
        driver = getJdbcDriver(dbms);
        if (!driver.isEmpty()) {
            connection = DriverManager.getConnection(connectionString);
            Class.forName(driver);                         
        }                
        return connection;        
    }
     
    /**
     * Получение строки подключения JDBC
     * @param dbms - вид СУБД
     * @return Имя класса для загрузки драйвера
     */
    private static String getJdbcDriver(String dbms) {        
        String driver = "";        
        if (dbms.equalsIgnoreCase("mysql")) {
            driver = "com.mysql.jdbc.Driver";             
        } else if (dbms.equalsIgnoreCase("postgresql")) {
            driver = "org.postgresql.Driver";
        } else if (dbms.equalsIgnoreCase("sqlserver")) {
            driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        } else if (dbms.equalsIgnoreCase("oracle")) {
            driver = "oracle.jdbc.OracleDriver";
        } else if (dbms.equalsIgnoreCase("sqlite")) {
            driver = "org.sqlite.JDBC";
        } else if (dbms.equalsIgnoreCase("msaccess")) {
            driver = "net.ucanaccess.jdbc.UcanaccessDriver";
        }                   
   
        return driver;
    }    
       
}
