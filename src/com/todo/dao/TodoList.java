package com.todo.dao;

import java.util.*;

import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByName;

public class TodoList {
	private List<TodoItem> list;
	private int exist;
	
	public TodoList() {
		this.list = new ArrayList<TodoItem>();
		exist=0;
	}

	public void addItem(TodoItem t) {
		list.add(t);
	}

	public void deleteItem(TodoItem t) {
		list.remove(t);
	}

	void editItem(TodoItem t, TodoItem updated) {
		int index = list.indexOf(t);
		list.remove(index);
		list.add(updated);
	}

	public ArrayList<TodoItem> getList() {
		return new ArrayList<TodoItem>(list);
	}

	public void sortByName() {
		Collections.sort(list, new TodoSortByName());

	}

	public void listAll() {
		System.out.println("\n"
				+ "inside list_All method\n");
		for (TodoItem myitem : list) {
			System.out.println(myitem.getTitle() + myitem.getDesc());
		}
	}
	
	public void reverseList() {
		Collections.reverse(list);
	}

	public void sortByDate() {
		Collections.sort(list, new TodoSortByDate());
	}

	public int indexOf(TodoItem t) {
		return list.indexOf(t);
	}

	public Boolean isDuplicate(String title, String category) {
		for (TodoItem item : list) {
			if (title.equals(item.getTitle())) {
				if(category.equals(item.getCategory()))
				return true;}
		}
		return false;
	}
	
	
	public void plusexist(){
		exist++;
	}
	public void minusexist() {
		exist--;
	}
	public int getExist() {
		return exist;
	}
	public TodoItem get(int i) {
		return list.get(i);
	}
	public void set(int i, TodoItem t) {
		list.set(i,t);
	}
}
