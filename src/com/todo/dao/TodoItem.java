package com.todo.dao;

import java.util.Date;
import java.text.SimpleDateFormat;

public class TodoItem {
    private String title;
    private String desc;
    private String simpledate;
    private String category;
    private String due_date;

    public TodoItem(String title, String desc){
        this.title=title;
        this.desc=desc;
        Date current_date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        this.simpledate = format.format(current_date);
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCurrent_date() {
        return simpledate
        		;
    }

    public void setCurrent_date(String simpledate) {
    	this.simpledate = simpledate;
    }
    
    public String getCategory() {
    	return category;
    }
    
    public void setCategory(String category) {
    	this.category = category;
    }
    
    public 	String getDuedate() {
    	return due_date;
    }
    
    public void setDuedate(String due_date) {
    	this.due_date = due_date;
    }
    
    public String toSaveString() {
    	return category+"##"+title+"##"+desc+"##"+simpledate+"##"+due_date+"\n";
    }
       
}
