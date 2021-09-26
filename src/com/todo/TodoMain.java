package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		boolean isList = false;
		boolean quit = false;
		boolean menu = true;
		int d = 0;
		TodoUtil.loadList(l, "TodoListApp.txt");
		do {
			if(menu==false) {
				Menu.prompt();
				d=0;
			}
			else {
			Menu.displaymenu();
			menu = false;
			d=1;}
			
			isList = false;
			String choice = sc.next();
			
			if(d==1) {System.out.println("");}
			switch (choice) {

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;

			case "ls_name_asc":
				l.sortByName();
				isList = true;
				break;

			case "ls_name_desc":
				l.sortByName();
				l.reverseList();
				isList = true;
				break;
				
			case "ls_date":
				l.sortByDate();
				isList = true;
				break;
			
			case "ls_date_desc":
				l.sortByDate();
				l.reverseList();
				isList = true;
				break;

			case "exit":
				quit = true;
				TodoUtil.saveList(l,"TodoListApp.txt");
				System.out.println("프로그램을 종료합니다.");
				break;
			
			case "help":
				menu = true;
				break;
			
			case "find":
				String keyword = sc.nextLine().trim();
				TodoUtil.findOut(keyword,l);
				break;
			
			case "find_cate":
				String keycate = sc.nextLine().trim();
				TodoUtil.findcate(keycate,l);
				break;

			default:
				System.out.println("\n존재하지 않는 명령입니다. 도움말 - help\n");
				break;
			}
			
			if(isList) l.listAll();
		} while (!quit);
	}
}
