package org.example;

import java.sql.*;

public class DbHandler implements ICrud {
    Connection connection = null;
    Helper helper;
    public DbHandler(String dbName) {
        helper = new Helper();
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbName + ".db");
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }
    public DbHandler() {
        helper = new Helper();
        //String dbName = helper.askForDbName();
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:dbName.db");
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }

    @Override
    public boolean create() {
        String tableName = helper.askForTableName();
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                "todo_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "todo VARCHAR(50)," +
                "done VARCHAR(10)," +
                "assignedTo VARCHAR(50)" +
                ")";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table created successfully");
            return true;
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
            return false;
        }
    }

    @Override
    public String read() {
        String tableName = helper.askForTableName();
        String sql = "SELECT * FROM " + tableName;
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String todo = rs.getString("todo");
                String done = rs.getString("done");
                String assignedTo = rs.getString("assignedTo");
                return "Todo: " + todo + ". Assigned to: " + assignedTo + ". Done: " + done;
            }
        } catch (SQLException e) {
            System.out.println("Error reading table: " + e.getMessage());
            return null;
        }
        return null;
    }

    @Override
    public boolean update() {
        String tableName = helper.askForTableName();
        String sql = "UPDATE " + tableName + " SET todo = ?, done = ?, assignedTo = ? WHERE todo_id = ?";
        int todoId = helper.askForId();
        String todo = helper.askForTodo();
        String assignedTo = helper.askForAssignedTo();
        String done = helper.askForDone();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, todo);
            pstmt.setString(2, done);
            pstmt.setString(3, assignedTo);
            pstmt.setInt(4, todoId);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error updating table: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete() {
        String tableName = helper.askForTableName();
        String sql = "DROP TABLE " + tableName;
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            return true;
            //closeConnectionToDb();
        } catch (SQLException e) {
            System.out.println("Error dropping table: " + e.getMessage());
            return false;
        }
    }
}
