package org.example;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ToDoHandlerTest {
    @Mock
    DbHandler dbHandlerMock;
    @Mock
    private String dbNameMock;
    @Mock
    private String tableNameMock;
    @Mock
    private String todoTextMock;
    @Mock
    private String todoDoneMock;
    @Mock
    private Helper helperMock;
    @Mock
    private Connection connectionMock;
    @Mock
    private PreparedStatement preparedStatementMock;
    @Mock
    private ResultSet resultSetMock;
    @Mock
    private ToDoHandler toDoHandlerMock;
    @Mock
    private ToDo todoMock;
    @Mock
    private int success;
    @BeforeEach
    public void setUp() throws SQLException {
        helperMock = new Helper();
        dbHandlerMock = new DbHandler(dbNameMock);
        todoMock = new ToDo("Test Bicicleta", "Zlatan", "Yes");
        todoTextMock = "Test Volley";
        success = 1;
        todoDoneMock = "Yes";
        MockitoAnnotations.initMocks(this);
        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(preparedStatementMock.executeUpdate()).thenReturn(success);
    }
    @Test
    public void testCreate() throws SQLException {
        when(helperMock.askForTableNameToAddTodo()).thenReturn(tableNameMock);
        when(helperMock.askForNewTodo()).thenReturn(todoMock);
        String sql = "INSERT INTO " + tableNameMock + " (todo, assignedTo, done) VALUES (?,?,?)";
        //when(connectionMock.prepare..
        doNothing().when(preparedStatementMock).setString(1, todoMock.getTodo());
        doNothing().when(preparedStatementMock).setString(2, todoMock.getAssignedTo());
        doNothing().when(preparedStatementMock).setString(3, todoMock.getDone());
        preparedStatementMock.executeUpdate();
        boolean result = toDoHandlerMock.create();

        Mockito.verify(helperMock).askForTableNameToAddTodo();
        Mockito.verify(helperMock).askForNewTodo();
        Mockito.verify(preparedStatementMock).setString(1, "Test Bicicleta");
        Mockito.verify(preparedStatementMock).setString(2, "Zlatan");
        Mockito.verify(preparedStatementMock).setString(3, "Yes");
        Mockito.verify(preparedStatementMock).executeUpdate();
        Mockito.verify(System.out).println("Todo Successfully added");

        assertTrue(result);
    }

    @Test
    public void testRead() throws SQLException {
        when(helperMock.askForTableName()).thenReturn(tableNameMock);
        String sql = "SELECT * FROM " + tableNameMock;
        when(resultSetMock.next()).thenReturn(true).thenReturn(false);
        when(resultSetMock.getString("todo")).thenReturn("Test Bicicleta");
        when(resultSetMock.getString("assignedTo")).thenReturn("Zlatan");
        when(resultSetMock.getString("done")).thenReturn("Yes");
        String result = toDoHandlerMock.read();

        Mockito.verify(helperMock.askForTableName());
        Mockito.verify(connectionMock).prepareStatement(sql);
        Mockito.verify(preparedStatementMock).executeQuery();
        Mockito.verify(resultSetMock).next();
        Mockito.verify(resultSetMock).getString("todo");
        Mockito.verify(resultSetMock).getString("assignedTo");
        Mockito.verify(resultSetMock).getString("done");

        String expected = "Todo: Test Bicicleta. Assigned to: Zlatan. Done: Yes";
        assertEquals(expected, result);
    }
    @Test
    public void testUpdateTodoText() throws SQLException {
        when(helperMock.askForTableName()).thenReturn(tableNameMock);
        when(helperMock.askForId()).thenReturn(success);
        when(helperMock.askForTodo()).thenReturn(todoTextMock);
        String sql = "UPDATE " + tableNameMock + " SET todo = ? WHERE todo_id = ?";
        doNothing().when(preparedStatementMock).setString(1, todoTextMock);
        doNothing().when(preparedStatementMock).setInt(2, 1);
        boolean result = toDoHandlerMock.updateText();

        Mockito.verify(helperMock.askForTableName());
        Mockito.verify(helperMock.askForId());
        Mockito.verify(helperMock.askForTodo());
        Mockito.verify(connectionMock).prepareStatement(sql);
        Mockito.verify(preparedStatementMock).executeUpdate();
        Mockito.verify(preparedStatementMock).setString(1, todoTextMock);
        Mockito.verify(preparedStatementMock).setInt(2, 1);

        assertTrue(result);
    }

    @Test
    public void testUpdateTodoDone() throws SQLException {
        when(helperMock.askForTableName()).thenReturn(tableNameMock);
        when(helperMock.askForId()).thenReturn(success);
        when(helperMock.askForDone()).thenReturn(todoDoneMock);
        String sql = "UPDATE " + tableNameMock + " SET done = ? WHERE todo_id = ?";
        //when(connectionMock.prepareStatement(sql));
        doNothing().when(preparedStatementMock).setString(1, todoDoneMock);
        doNothing().when(preparedStatementMock).setInt(2, 1);
        boolean result = toDoHandlerMock.updateDone();

        Mockito.verify(helperMock.askForTableName());
        Mockito.verify(helperMock.askForId());
        Mockito.verify(helperMock.askForDone());
        Mockito.verify(connectionMock).prepareStatement(sql);
        Mockito.verify(preparedStatementMock).setString(1, todoDoneMock);
        Mockito.verify(preparedStatementMock).setInt(2, 1);
        Mockito.verify(preparedStatementMock).executeUpdate();

        assertTrue(result);
    }

    @Test
    public void testDelete() throws SQLException {
        when(helperMock.askForTableName()).thenReturn(tableNameMock);
        when(helperMock.askForId()).thenReturn(success);
        String sql = "DELETE FROM " + tableNameMock + " WHERE todo_id = ?";
        doNothing().when(preparedStatementMock).setInt(1, 1); //x: success ?
        boolean result = toDoHandlerMock.delete();

        Mockito.verify(helperMock.askForTableName());
        Mockito.verify(helperMock.askForId());
        Mockito.verify(connectionMock).prepareStatement(sql);
        Mockito.verify(preparedStatementMock).setInt(1, 1);
        Mockito.verify(preparedStatementMock).executeUpdate();

        assertTrue(result);
    }
}