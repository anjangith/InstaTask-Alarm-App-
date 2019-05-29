package com.todolist.reminder;

import android.graphics.Bitmap;

/**
 * Created by HP on 25-12-2017.
 */

public class Model {
    private String tasktext;
    private String hr;
    private String min;
    private int ran1;
    private String year;
    private String month;
    private String day;

    public void setTasktext(String tasktext) {
        this.tasktext = tasktext;
    }

    public void setHr(String hr) {
        this.hr = hr;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public void setRan1(int ran1) {
        this.ran1 = ran1;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setDay(String day) {
        this.day = day;
    }



    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }




    public int getRan1() {
        return ran1;
    }






    public String getTasktext() {
        return tasktext;
    }



    public String getHr() {
        return hr;
    }

    public String getMin() {
        return min;
    }






    Model(String b,String hr,String min,int rand1,String day,String mon,String ye){
        this.tasktext=b;
        this.hr=hr;
        this.min=min;
        this.ran1=rand1;
        this.day=day;
        this.month=mon;
        this.year=ye;

    }
}
