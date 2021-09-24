package com.todo.dao;

import java.util.Date;
import java.text.SimpleDateFormat;

public class TodoItem {
    private String title;
    private String desc;
    private String simpledate;

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
    
    public String toSaveString() {
    	return title+"##"+desc+"##"+simpledate+"\n";
    }
       
}
