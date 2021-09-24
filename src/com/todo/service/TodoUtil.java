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
				+ "������ �Է��ϼ���: ");
		
		title = sc.next();
		sc.nextLine();
		
		if (list.isDuplicate(title)) {
			System.out.println("Error: �׸� �߰��� �����Ͽ����ϴ�.");
			System.out.printf("�ߺ��� ������ �����մϴ�. �׸��� �ߺ��� �� �����ϴ�.\n");
			System.out.println("=========================================");
			return;
		}
		
		System.out.printf("������ �Է��ϼ���: ");
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
				+ "������ ������ �Է��ϼ���: "
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
		System.out.println("Error: �׸� ������ �����Ͽ����ϴ�.");
		System.out.println("�������� �ʴ� �����Դϴ�.");
		System.out.println("=========================================");}
		
		else {System.out.println(title+"��(��) �����Ǿ����ϴ�.");
		System.out.println("=========================================");
		l.minusexist();
		}
		
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.printf(
				 "=========== Edit Item Section ===========\n"
				+ "�����Ͻñ⸦ ���ϴ� ������ �Է����ֽʽÿ�: "
				);
		
		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("Error: �׸� ������ �����Ͽ����ϴ�.");
			System.out.println("�������� �ʴ� �����Դϴ�.");
			System.out.println("=========================================");
			return;
		}

		System.out.printf("���ο� ������ �Է����ֽʽÿ�: ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("Error: �׸� ������ �����Ͽ����ϴ�.");
			System.out.println("���� ��Ͽ� �ߺ��Ǵ� ������ �����մϴ�. ������ �ߺ��� �� �����ϴ�.");
			System.out.println("=========================================");
			return;
		}
		
		System.out.printf("���ο� ������ �Է����ֽʽÿ�: ");
		String new_description = sc.nextLine();
		sc.nextLine();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println(title+" �׸��� "+new_title+"�� �����Ǿ����ϴ�");
				System.out.println("=========================================");
				break;
			}
		}

	}

	public static void listAll(TodoList l) {
		if(l.getExist()==0) {
			System.out.println("����� ����Ʈ�� �������� �ʽ��ϴ�.");
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
		System.out.println("�Է��Ͻ� �׸���� ����Ǿ����ϴ�.");
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
