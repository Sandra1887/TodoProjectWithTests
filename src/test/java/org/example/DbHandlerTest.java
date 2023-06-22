package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class DbHandlerTest {
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
    private Statement statementMock;
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
        when(connectionMock.createStatement()).thenReturn(statementMock); //<--
        when(statementMock.execute(anyString())).thenReturn(true); //<--
        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(preparedStatementMock.executeUpdate()).thenReturn(success);
    }

    @Test
    public void testCreate() throws SQLException {
        when(helperMock.askForTableName()).thenReturn(tableNameMock);
        String sql = "CREATE TABLE IF NOT EXISTS " + tableNameMock + " (" +
                "todo_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "todo VARCHAR(50)," +
                "done VARCHAR(10)," +
                "assignedTo VARCHAR(50)" +
                ")";

        boolean result = dbHandlerMock.create();

        Mockito.verify(connectionMock.createStatement());
        Mockito.verify(statementMock.executeQuery(sql));

        assertTrue(result);
    }
}