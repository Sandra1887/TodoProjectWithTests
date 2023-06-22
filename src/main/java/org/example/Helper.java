package org.example;

import java.sql.SQLOutput;
import java.util.Scanner;

public class Helper {

    Scanner sc;

    public Helper() {
        sc = new Scanner(System.in);
    }
    public String askForDbName() {
        System.out.println("Welcome! Please enter the name for your database");
        return sc.nextLine();
    }
    public int mainMenu() {
        System.out.println("""
                What would you like to do?
                1. Handle table
                2. Handle todos
                3. Exit""");
        return sc.nextInt();
    }

    public int tableMenu() {
        System.out.println("""
                1. Create table
                2. Read table
                3. Update table
                4. Delete table"
                5. Exit""");
        return sc.nextInt();
    }

    public String askForTableName() {
        System.out.println("Enter table name and press enter");
        return sc.nextLine();
    }

    public int askForId() {
        System.out.println("Enter the ID of the todo and press enter");
        return sc.nextInt();
    }

    public String askForTextOrDone() {
        System.out.println("Would you like to update the \"todo-text\" or the \"done\"?");
        return sc.nextLine();
    }

    public String askForTodo() {
        System.out.println("Enter the new \"To Do\" and press enter");
        return sc.nextLine();
    }
    public String askForAssignedTo() {
        System.out.println("Enter the name of assignee and press enter");
        return sc.nextLine();
    }
    public String askForDone() {
        System.out.println("Is the task done or not? Enter \"yes\" or \"no\" and press enter");
        return sc.nextLine();
    }
    public int todoMenu() {
        System.out.println("""
                Make your choice and press enter.
                1. Create to-do
                2. Read one to-do
                3. Read all to-dos
                4. Update to-do text
                5. Update to-do done
                6. Delete to-do
                7. Exit""");
        return sc.nextInt();
    }
    public String askForTableNameToAddTodo() {
        System.out.println("Which table would you like to add it to?");
        return sc.nextLine();
    }
    public ToDo askForNewTodo() {
        String task = askForTodo();
        String assignedTo = askForAssignedTo();
        String done = askForDone();
        return new ToDo(task, assignedTo, done);
    }
}
