package test_marco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import com.mysql.cj.jdbc.MysqlDataSource;

public class JdbcTest {
    
    private Connection con;
    
    public static void main(String[] args) {
        JdbcTest esempio = new JdbcTest();
        try {
            String nomeDB = "test"; 
            String nomeTab = "utente"; 

        
            Connection systemConn = esempio.startConnection("mysql");


            esempio.creaDatabase(systemConn, nomeDB);


            systemConn.close();

   
            esempio.con = esempio.startConnection(nomeDB);

       
            esempio.creaTabellaUtente(nomeTab);

            if (esempio.con.isValid(100)) {
                System.out.println("Connessione avvenuta con successo.");
            } else {
                System.out.println("Connessione fallita.");
            }
      
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private Connection startConnection(String nomeDB) throws SQLException {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName("127.0.0.1");
        dataSource.setPortNumber(3306);
        dataSource.setUser("root");
        dataSource.setPassword("password");
        dataSource.setDatabaseName(nomeDB);
        return dataSource.getConnection();
    }
    
    public void creaDatabase(Connection conn, String nomeDB) throws SQLException {
        String sql = "CREATE DATABASE IF NOT EXISTS " + nomeDB;
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    public void creaTabellaUtente(String nomeTab) throws SQLException {
    	
    	String useDB = "USE test;";
    	
        String creaTab = "CREATE TABLE IF NOT EXISTS " + nomeTab + " (" +
                     "id INT PRIMARY KEY, " +
                     "nome VARCHAR(255) NOT NULL, " +
                     "cognome VARCHAR(255) NOT NULL)";
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(creaTab);
            System.out.println("Tabella " + nomeTab + " creata correttamente.");
        } catch (SQLException e) {
            System.err.println("Errore durante la creazione della tabella:");
            e.printStackTrace();
            throw e; 
        }
    }

    }

