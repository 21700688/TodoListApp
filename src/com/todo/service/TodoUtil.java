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
				);
		System.out.printf("카테고리를 입력하세요: ");
		String category = sc.nextLine();
		System.out.printf("제목을 압력하세요: ");
		title = sc.next();
		sc.nextLine();
		
		if (list.isDuplicate(title,category)) {
			System.out.println("Error: 항목 추가에 실패하였습니다.");
			System.out.printf("중복된 제목이 존재합니다. 항목은 중복될 수 없습니다.\n");
			System.out.println("=========================================");
			return;
		}
		
		System.out.printf("내용을 입력하세요: ");
		desc = sc.nextLine();
		
		System.out.printf("마감일을 입력하세요(yyyy/MM/dd): ");
		String due_date = sc.nextLine();
		
		System.out.println("=========================================");
		TodoItem t = new TodoItem(title, desc);
		t.setCategory(category);
		t.setDuedate(due_date);
		list.addItem(t);
		list.plusexist();
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		for(;;) {
			System.out.printf("항목의 일련번호로 삭제하시겠습니까?(y/n): ");
			String yn = sc.nextLine();
		if(yn.equals("y")) {
			System.out.printf("삭제할 항목의 번호을 입력하여 주십시오: ");
			int n=sc.nextInt();
			sc.nextLine();
			if(n<=l.getExist()) {
				
			TodoItem item= l.get(n-1);
			
			for(;;){
			System.out.printf("항목을 삭제 하시겠습니까?(y/n): ");
			String yn2 = sc.nextLine();
			if(yn2.equals("y")) {
			l.deleteItem(item);
			l.minusexist();
			System.out.println("항목 "+n+"번을 삭제하였습니다.");
			return;}
			else if(yn2.equals("n")){
			System.out.println("항목을 삭제하지 않았습니다.");
			return;
			}
			else {
				System.out.println("잘못된 입력입니다.");
					}
				}
			}
			else {
				System.out.println("목록의 총 개수를 초과합니다. 다시 입력해주세요.");
			}
		}
	
		
		
		else if(yn.equals("n")) {
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
		return;
		}
		
		else {
			System.out.println("y또는 n을 입력하여주십시오.");
		}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		String yn,yn2;
		
		for(;;) {
		System.out.printf("항목의 번호로 수정을 하시겠습니까?(y/n): ");
		yn = sc.nextLine();
		if(yn.equals("y")) {
			System.out.printf("수정할 항목의 번호를 입력해주십시오: ");
			int num = sc.nextInt();
			sc.nextLine();
			if(num<=l.getExist()) {
				TodoItem item = l.get(num-1);
				for(;;) {
				System.out.printf("["+item.getTitle() + "]"+"\t" + item.getDesc()+"\t"+item.getCurrent_date()+"를 수정하시겠습니까?(y/n): ");
				yn2= sc.nextLine();
				if(yn2.equals("y")) {
					String title;
					String category;
					System.out.printf("새로운 카테고리를 입력해주십시오: ");
					category = sc.nextLine();
					for(;;) {
					
					System.out.printf("새로운 제목을 입력해주십시오: ");
					title = sc.next();
					sc.nextLine();
					if (l.isDuplicate(title,category)) {
						System.out.println("Error: 항목 수정에 실패하였습니다.");
						System.out.println("기존 목록에 중복되는 제목이 존재합니다. 제목은 중복될 수 없습니다.");
						continue;
					}
					else {break;}
					
					}
					System.out.printf("새로운 내용을 입력해주십시오: ");
					String desc = sc.nextLine();
					System.out.printf("새로운 마감일을 입력해주십시오(yyyy/MM/dd): ");
					String due_date = sc.nextLine();
					TodoItem ni = new TodoItem(title,desc);
					ni.setCategory(category);
					ni.setDuedate(due_date);
					
					l.set(num-1,ni);
					System.out.println("항목이 수정되었습니다.");
					return;
					
				}
				else if(yn2.equals("n")) {
					return;
				}
				else {System.out.println("잘못된 입력입니다.");}
				}
				
				
			}
			else if(num<l.getExist()) {
				System.out.println("입력하신 번호가 항목의 개수를 초과합니다. 다시 입력해주세요.");
			}
			else {System.out.println("잘못된 입력입니다.");
			return;}
			
		}
		else if(yn.equals("n")) {
		System.out.printf(
				 "=========== Edit Item Section ===========\n"
				+ "수정하시기를 원하는 제목을 입력해주십시오: "
				);
		
		String title = sc.next().trim();
		System.out.printf("새 카테고리를 입력해주세요.");
		String category = sc.nextLine();
		if (!l.isDuplicate(title,category)) {
			System.out.println("Error: 항목 수정에 실패하였습니다.");
			System.out.println("존재하지 않는 제목입니다.");
			System.out.println("=========================================");
			return;
		}

		System.out.printf("새로운 제목을 입력해주십시오: ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title,category)) {
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
				return;
			}
		}
		}
		else System.out.println("잘못된 입력입니다.");
		}
	}

	public static void listAll(TodoList l) {
		int i=0;
		
		if(l.getExist()==0) {
			System.out.println("저장된 리스트가 존재하지 않습니다.");
		}
		else {
			System.out.println("[전체목록, 총 "+l.getExist()+"개]");
		System.out.println("=============== Item List ===============");
		for (TodoItem item : l.getList()) {
			if(i<=8) {
			System.out.println((i+1)+".\s\s"+"[" + item.getCategory() + "]"+"\t" +item.getTitle()+"<"+ item.getDesc()+">"+"-"+item.getCurrent_date()+"-마감일: "+item.getDuedate());
			}
			else if(i>8&&i<=98) {
				System.out.println((i+1)+".\s"+"[" + item.getCategory() + "]"+"\t" +item.getTitle()+"<"+item.getDesc()+">"+"-"+item.getCurrent_date()+"-마감일: "+item.getDuedate());
			}
			i++;
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
				String category = st.nextToken();
				String title= st.nextToken();
				String desc = st.nextToken();
				TodoItem it = new TodoItem(title,desc);
				String date = st.nextToken();
				String duedate = st.nextToken();
				it.setCurrent_date(date);
				it.setCategory(category);
				it.setDuedate(duedate);
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
	
	public static void findOut(String keyword, TodoList l) {
		int i = 0;
		for(TodoItem item : l.getList()){
			
			String title = item.getTitle();
			String desc = item.getDesc();
			int wordlength = keyword.length();
			
			if(wordlength==0) {
				System.out.println("다시 입력해주세요.");
				return;}
			
			int index1 = title.indexOf(String.valueOf(keyword.charAt(0)));
			if(index1!=-1&&title.length()>=index1+wordlength){
			String input = title.substring(index1,index1+wordlength);
			if(input.equals(keyword)) {
				i++;
				System.out.println("[" + item.getCategory() + "]"+"\t" +item.getTitle()+"<"+item.getDesc()+">"+"-"+item.getCurrent_date()+"-마감일: "+item.getDuedate());
				continue;
			}
			}
			
			int index2 = desc.indexOf(keyword.substring(0,1));
			if(index2 !=-1&&desc.length()>index2+wordlength) {
			String input2 = desc.substring(index2,index2+wordlength);
			if(input2.equals(keyword)) {
				i++;
				System.out.println("[" + item.getCategory() + "]"+item.getTitle()+"<" + item.getDesc()+">-"+item.getCurrent_date()+"-마감일: "+item.getDuedate());
			}
		}
		}
		System.out.println("총 "+i+"개의 항목을 찾았습니다.");
	}
	
	public static void findcate(String keycate,TodoList l) {
		int i = 0;
		
		if(keycate.length()==0) {
			System.out.println("잘못된 입력입니다.");
			return;
		}
		String key=String.valueOf(keycate.charAt(0));
		for(TodoItem item: l.getList()){
			String category = item.getCategory();
			int index = category.indexOf(key);
			if(index!=-1&&category.length()>=index+keycate.length()) {
				String input = category.substring(index,keycate.length());
				if(input.equals(keycate)){
					i++;
					System.out.println("[" + item.getCategory() + "]"+"\t" +item.getTitle()+"<"+item.getDesc()+">"+"-"+item.getCurrent_date()+"-마감일: "+item.getDuedate());
				}
				}
			}
		System.out.println("총 "+i+"개의 항목을 찾았습니다.");
			
		}
}

