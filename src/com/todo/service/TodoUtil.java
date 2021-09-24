package com.todo.service;

import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.printf(
				 "========== Create item Section ==========\n"
				+ "제목을 입력하세요: ");
		
		title = sc.next();
		sc.nextLine();
		
		if (list.isDuplicate(title)) {
			System.out.println("Error: 항목 추가에 실패하였습니다.");
			System.out.printf("중복된 제목이 존재합니다. 항목은 중복될 수 없습니다.\n");
			System.out.println("=========================================");
			return;
		}
		
		System.out.printf("내용을 입력하세요: ");
		desc = sc.nextLine();
		
		System.out.println("=========================================");
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
		list.plusexist();
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		boolean check = false;
		System.out.printf(
				 "========== Delete item Section ==========\n"
				+ "삭제할 제목을 입력하세요: "
				);
		String title = sc.next();
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				check = true;
				break;
			}
		}
		if(!check) {
		System.out.println("Error: 항목 삭제에 실패하였습니다.");
		System.out.println("존재하지 않는 제목입니다.");
		System.out.println("=========================================");}
		
		else {System.out.println(title+"이(가) 삭제되었습니다.");
		System.out.println("=========================================");
		l.minusexist();
		}
		
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.printf(
				 "=========== Edit Item Section ===========\n"
				+ "수정하시기를 원하는 제목을 입력해주십시오: "
				);
		
		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("Error: 항목 수정에 실패하였습니다.");
			System.out.println("존재하지 않는 제목입니다.");
			System.out.println("=========================================");
			return;
		}

		System.out.printf("새로운 제목을 입력해주십시오: ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("Error: 항목 수정에 실패하였습니다.");
			System.out.println("기존 목록에 중복되는 제목이 존재합니다. 제목은 중복될 수 없습니다.");
			System.out.println("=========================================");
			return;
		}
		
		System.out.printf("새로운 내용을 입력해주십시오: ");
		String new_description = sc.nextLine();
		sc.nextLine();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println(title+" 항목이 "+new_title+"로 수정되었습니다");
				System.out.println("=========================================");
				break;
			}
		}

	}

	public static void listAll(TodoList l) {
		if(l.getExist()==0) {
			System.out.println("저장된 리스트가 존재하지 않습니다.");
		}
		else {
		System.out.println("=============== Item List ===============");
		for (TodoItem item : l.getList()) {
			
			System.out.println("[" + item.getTitle() + "]"+"\t" + item.getDesc()+"\t"+item.getCurrent_date());
		}
		System.out.println("=========================================");}
	}
	
	public static void saveList(TodoList l, String Filename) {
		try{FileWriter fw = new FileWriter(Filename);
		for(TodoItem item: l.getList()) {fw.write(item.toSaveString());}
		fw.close();
		System.out.println("입력하신 항목들이 저장되었습니다.");
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadList(TodoList l, String Filename) {
		try {
			FileReader fread= new FileReader(Filename);
			BufferedReader br = new BufferedReader(fread);
			String s;
			String token;
			while((s=br.readLine())!=null) {
				StringTokenizer st = new StringTokenizer(s,"##");

				String title= st.nextToken();
				String desc = st.nextToken();
				TodoItem it = new TodoItem(title,desc);
				String date = st.nextToken();
				it.setCurrent_date(date);
				l.addItem(it);
				l.plusexist();
			}
			fread.close();
			br.close();
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
}
