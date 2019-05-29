package com.todolist.reminder;

public class TaskModel {

    private String taskName;
    private boolean isTaskComplete;
    private String day;
    private String month;
    private String year;


    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }



    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }


    public boolean isTaskComplete() {
        return isTaskComplete;
    }

    public void setTaskComplete(boolean taskComplete) {
        isTaskComplete = taskComplete;
    }
}
