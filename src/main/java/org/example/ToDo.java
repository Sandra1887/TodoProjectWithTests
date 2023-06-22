package org.example;

public class ToDo {
    private String todo;
    private String assignedTo;
    private String done; //Yes/No

    public ToDo(String todo, String assignedTo, String done) {
        setTodo(todo);
        setAssignedTo(assignedTo);
        setDone(done);
    }
    public void setTodo(String todo) {
        this.todo = todo;
    }
    public String getTodo() {
        return todo;
    }
    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }
    public String getAssignedTo() {
        return assignedTo;
    }
    public void setDone(String done) {
        this.done = done;
    }
    public String getDone() {
        return done;
    }
    public String toString() {
        return "Todo " + todo + " is done: " + done + " by " + assignedTo;
    }
}