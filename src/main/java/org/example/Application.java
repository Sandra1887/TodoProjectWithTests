package org.example;

public class Application {
    ToDo todo;
    DbHandler dbHandler;
    ToDoHandler todoHandler;
    Helper helper;
    boolean exit = false;

    public Application() {
        helper = new Helper();
        String dbName = helper.askForDbName();
        todoHandler = new ToDoHandler(dbName);
        dbHandler = new DbHandler(dbName);
    }
    public void start() {
        while(!exit) {
            switch(helper.mainMenu()) {
                case 1 -> {
                    handleTable();
                    break;
                }
                case 2 -> {
                    handleTodos();
                    break;
                }
                case 3 -> exit = true;
            }
        }
    }

    public void handleTable() {
        int choice = helper.tableMenu();
        switch (choice) {
            case 1 -> dbHandler.create();
            case 2 -> dbHandler.read();
            case 3 -> dbHandler.update();
            case 4 -> dbHandler.delete();
            case 5 -> exit = false;
        }
    }

    public void handleTodos() {
        int choice = helper.todoMenu();
        switch (choice) {
            case 1 -> {
                todoHandler.create();
                break;
            }
            case 2 -> {
                todoHandler.readOne();
                break;
            }
            case 3 -> todoHandler.read();
            case 4 -> {
                todoHandler.updateText();
                break;
            }
            case 5 -> {
                todoHandler.updateDone();
                break;
            }
            case 6 -> {
                todoHandler.delete();
                break;
            }
            case 7 -> exit = true;
        }
    }
}