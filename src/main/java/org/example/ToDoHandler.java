package org.example;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ToDoHandler implements ICrud, IToDo {
    DbHandler dbHandler;
    Helper helper;

    public ToDoHandler(String dbName) {
        helper = new Helper();
        dbHandler = new DbHandler(dbName);
    }

    @Override
    public boolean create() {
        String tableName = helper.askForTableNameToAddTodo();
        ToDo todo = helper.askForNewTodo();
        String sql = "INSERT INTO " + tableName + " (todo, assignedTo, done) VALUES (?,?,?)";
        try (PreparedStatement pstmt = dbHandler.connection.prepareStatement(sql)) {
            pstmt.setString(1, todo.getTodo());
            pstmt.setString(2, todo.getAssignedTo());
            pstmt.setString(3, todo.getDone());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error creating todo: " + e.getMessage());
            return false;
        }
    }

    @Override
    public String readOne() {
        String tableName = helper.askForTableName();
        int id = helper.askForId();
        String sql = "SELECT todo FROM " + tableName + " WHERE todo_id = " + id;
        try {
            Statement stmt = dbHandler.connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                do {
                    String todo = rs.getString("todo");
                    String assignedTo = rs.getString("assignedTo");
                    String done = rs.getString("done");
                    return "Todo: " + todo + " assigned to " + assignedTo + "is done: " + done;
                } while (rs.next());
            } else {
                System.out.println("Could not find that todo");
            }
        } catch (SQLException e) {
            System.out.println("Error finding todo: " + e.getMessage());
            return null;
        }
        return null;
    }

    @Override
    public String read() {
        String found = dbHandler.read();
        if(found == null) {
            return null;
        } else {
            return found;
        }
    }

    public boolean update() {
        int id = helper.askForId();
        String tableName = helper.askForTableName();
        String answer = helper.askForTextOrDone();

        if (answer.equalsIgnoreCase("todo-text")) {
            String todo = helper.askForTodo();
            String sql = "UPDATE " + tableName + " SET todo = ? WHERE todo_id = ?";
            try (PreparedStatement pstmt = dbHandler.connection.prepareStatement(sql)) {
                pstmt.setString(1, todo);
                pstmt.setInt(2, id);
                pstmt.executeUpdate();
                System.out.println("Table updated successfully");
            } catch (SQLException e) {
                System.out.println("Error updating table: " + e.getMessage());
                return false;
            }
        }

        if (answer.equalsIgnoreCase("done")) {
            String done = helper.askForDone();
            String sql = "UPDATE " + tableName + " SET done = ? WHERE todo_id = ?";
            try (PreparedStatement pstmt = dbHandler.connection.prepareStatement(sql)) {
                pstmt.setString(1, done);
                pstmt.setInt(2, id);
                pstmt.executeUpdate();
                System.out.println("Table updated successfully");
            } catch (SQLException e) {
                System.out.println("Error updating table: " + e.getMessage());
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean updateText() {
        String tableName = helper.askForTableName();
        String todo = helper.askForTodo();
        int id = helper.askForId();
        String sql = "UPDATE " + tableName + " SET todo = ? WHERE todo_id = ?";
        try (PreparedStatement pstmt = dbHandler.connection.prepareStatement(sql)) {
            pstmt.setString(1, todo); //parametern i String sql
            pstmt.setInt(2, id); //parametern i String sql
            pstmt.executeUpdate();
            System.out.println("Table updated successfully");
            return true;
        } catch (SQLException e) {
            System.out.println("Error updating table: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateDone() {
        String tableName = helper.askForTableName();
        String done = helper.askForDone();
        int id = helper.askForId();
        String sql = "UPDATE " + tableName + " SET done = ? WHERE todo_id = ?";
        try (PreparedStatement pstmt = dbHandler.connection.prepareStatement(sql)) {
            pstmt.setString(1, done);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            System.out.println("Table updated successfully");
            return true;
        } catch (SQLException e) {
            System.out.println("Error updating table: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete() {
        String tableName = helper.askForTableName();
        int id = helper.askForId();
        String sql = "DELETE FROM " + tableName + " WHERE todo_id = ?";
        try (PreparedStatement pstmt = dbHandler.connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Table deleted successfully");
            return true;
        } catch (SQLException e) {
            System.out.println("Error deleting todo: " + e.getMessage());
            return false;
        }
    }
}
