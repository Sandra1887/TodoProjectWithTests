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

class ChatGPTTest {
    @Mock
    DbHandler dbHandlerMock;
    @Mock
    private Helper helperMock;
    @Mock
    private Connection connectionMock;
    @Mock
    private PreparedStatement preparedStatementMock;
    @Mock
    private ResultSet resultSetMock;
    private ToDoHandler toDoHandler;
    private String tableNameMock;
    private String todoTextMock;
    private String todoDoneMock;
    private int success;

    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this); // Inicializar los mocks

        dbHandlerMock = mock(DbHandler.class); // Create a mock instance of DbHandler

        toDoHandler = new ToDoHandler(dbHandlerMock, helperMock); // Pasa la instancia de dbHandlerMock al constructor.
        toDoHandler.helper = helperMock;        // Asignar el Helper mockeado a la instancia de ToDoHandler

        tableNameMock = "testTableName";
        todoTextMock = "Test Volley";
        todoDoneMock = "Yes";
        success = 1;

        // Mockear el comportamiento de los métodos de DbHandler
        when(dbHandlerMock.getConnection()).thenReturn(connectionMock);

        // Mockear el comportamiento de los métodos de Connection
        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);

        // Mockear el comportamiento de los métodos de PreparedStatement
        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(preparedStatementMock.executeUpdate()).thenReturn(success);
    }

    @Test
    public void testCreate() throws SQLException {
        ToDo todoMock = new ToDo("Test Bicicleta", "Zlatan", "Yes");

        // Mockear el comportamiento de los métodos de helperMock
        when(helperMock.askForTableNameToAddTodo()).thenReturn(tableNameMock);
        when(helperMock.askForNewTodo()).thenReturn(todoMock);

        String sql = "INSERT INTO " + tableNameMock + " (todo, assignedTo, done) VALUES (?,?,?)";
        doNothing().when(preparedStatementMock).setString(1, todoMock.getTodo());
        doNothing().when(preparedStatementMock).setString(2, todoMock.getAssignedTo());
        doNothing().when(preparedStatementMock).setString(3, todoMock.getDone());

        boolean result = toDoHandler.create();

        // Verificar las invocaciones de métodos y el comportamiento esperado
        Mockito.verify(helperMock).askForTableNameToAddTodo();
        Mockito.verify(helperMock).askForNewTodo();
        Mockito.verify(connectionMock).prepareStatement(sql);
        Mockito.verify(preparedStatementMock).setString(1, "Test Bicicleta");
        Mockito.verify(preparedStatementMock).setString(2, "Zlatan");
        Mockito.verify(preparedStatementMock).setString(3, "Yes");
        Mockito.verify(preparedStatementMock).executeUpdate(); // Verificar que se ejecuta la sentencia SQL
        Mockito.verify(System.out).println("Todo Successfully added"); // Verificar la salida esperada

        assertTrue(result);
    }

    @Test
    public void testRead() throws SQLException {
        tableNameMock = "testTableName";

        when(helperMock.askForTableName()).thenReturn(tableNameMock);
        String sql = "SELECT * FROM " + tableNameMock;
        when(resultSetMock.next()).thenReturn(true).thenReturn(false); //<_-- kollade och jämförde med ditt ex
        when(resultSetMock.getString("todo")).thenReturn("Test Bicicleta");
        when(resultSetMock.getString("assignedTo")).thenReturn("Zlatan");
        when(resultSetMock.getString("done")).thenReturn("Yes");

        String result = toDoHandler.read();

        // Verificar las invocaciones de métodos y el comportamiento esperado
        Mockito.verify(helperMock).askForTableName();
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
        tableNameMock = "testTableName";
        int todoId = 1;
        String updatedTodoText = "Updated Todo Text";

        when(helperMock.askForTableName()).thenReturn(tableNameMock);
        when(helperMock.askForId()).thenReturn(todoId);
        when(helperMock.askForTodo()).thenReturn(updatedTodoText);

        String sql = "UPDATE " + tableNameMock + " SET todo = ? WHERE todo_id = ?";
        doNothing().when(preparedStatementMock).setString(1, updatedTodoText);
        doNothing().when(preparedStatementMock).setInt(2, todoId);

        boolean result = toDoHandler.updateText();

        // Verificar las invocaciones de métodos y el comportamiento esperado
        Mockito.verify(helperMock).askForTableName();
        Mockito.verify(helperMock).askForId();
        Mockito.verify(helperMock).askForTodo();
        Mockito.verify(connectionMock).prepareStatement(sql);
        Mockito.verify(preparedStatementMock).executeUpdate();
        Mockito.verify(preparedStatementMock).setString(1, updatedTodoText);
        Mockito.verify(preparedStatementMock).setInt(2, todoId);

        assertTrue(result);
    }

    @Test
    public void testUpdateTodoDone() throws SQLException {
        tableNameMock = "testTableName";
        int todoId = 1;
        String updatedTodoDone = "No";

        when(helperMock.askForTableName()).thenReturn(tableNameMock);
        when(helperMock.askForId()).thenReturn(todoId);
        when(helperMock.askForDone()).thenReturn(updatedTodoDone);

        String sql = "UPDATE " + tableNameMock + " SET done = ? WHERE todo_id = ?";
        doNothing().when(preparedStatementMock).setString(1, updatedTodoDone);
        doNothing().when(preparedStatementMock).setInt(2, todoId);

        boolean result = toDoHandler.updateDone();

        // Verificar las invocaciones de métodos y el comportamiento esperado
        Mockito.verify(helperMock).askForTableName();
        Mockito.verify(helperMock).askForId();
        Mockito.verify(helperMock).askForDone();
        Mockito.verify(connectionMock).prepareStatement(sql);
        Mockito.verify(preparedStatementMock).executeUpdate();
        Mockito.verify(preparedStatementMock).setString(1, updatedTodoDone);
        Mockito.verify(preparedStatementMock).setInt(2, todoId);
        assertTrue(result);
    }

    @Test
    public void testDelete() throws SQLException {
        tableNameMock = "testTableName";
        int todoId = 1;

        when(helperMock.askForTableName()).thenReturn(tableNameMock);
        when(helperMock.askForId()).thenReturn(todoId);

        String sql = "DELETE FROM " + tableNameMock + " WHERE todo_id = ?";
        doNothing().when(preparedStatementMock).setInt(1, todoId);

        boolean result = toDoHandler.delete();

        // Verificar las invocaciones de métodos y el comportamiento esperado
        Mockito.verify(helperMock).askForTableName();
        Mockito.verify(helperMock).askForId();
        Mockito.verify(connectionMock).prepareStatement(sql);
        Mockito.verify(preparedStatementMock).executeUpdate();
        Mockito.verify(preparedStatementMock).setInt(1, todoId);
        assertTrue(result);
    }
}
