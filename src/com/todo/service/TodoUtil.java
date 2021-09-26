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
		System.out.printf("ī�װ��� �Է��ϼ���: ");
		String category = sc.nextLine();
		System.out.printf("������ �з��ϼ���: ");
		title = sc.next();
		sc.nextLine();
		
		if (list.isDuplicate(title,category)) {
			System.out.println("Error: �׸� �߰��� �����Ͽ����ϴ�.");
			System.out.printf("�ߺ��� ������ �����մϴ�. �׸��� �ߺ��� �� �����ϴ�.\n");
			System.out.println("=========================================");
			return;
		}
		
		System.out.printf("������ �Է��ϼ���: ");
		desc = sc.nextLine();
		
		System.out.printf("�������� �Է��ϼ���(yyyy/MM/dd): ");
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
			System.out.printf("�׸��� �Ϸù�ȣ�� �����Ͻðڽ��ϱ�?(y/n): ");
			String yn = sc.nextLine();
		if(yn.equals("y")) {
			System.out.printf("������ �׸��� ��ȣ�� �Է��Ͽ� �ֽʽÿ�: ");
			int n=sc.nextInt();
			sc.nextLine();
			if(n<=l.getExist()) {
				
			TodoItem item= l.get(n-1);
			
			for(;;){
			System.out.printf("�׸��� ���� �Ͻðڽ��ϱ�?(y/n): ");
			String yn2 = sc.nextLine();
			if(yn2.equals("y")) {
			l.deleteItem(item);
			l.minusexist();
			System.out.println("�׸� "+n+"���� �����Ͽ����ϴ�.");
			return;}
			else if(yn2.equals("n")){
			System.out.println("�׸��� �������� �ʾҽ��ϴ�.");
			return;
			}
			else {
				System.out.println("�߸��� �Է��Դϴ�.");
					}
				}
			}
			else {
				System.out.println("����� �� ������ �ʰ��մϴ�. �ٽ� �Է����ּ���.");
			}
		}
	
		
		
		else if(yn.equals("n")) {
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
		return;
		}
		
		else {
			System.out.println("y�Ǵ� n�� �Է��Ͽ��ֽʽÿ�.");
		}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		String yn,yn2;
		
		for(;;) {
		System.out.printf("�׸��� ��ȣ�� ������ �Ͻðڽ��ϱ�?(y/n): ");
		yn = sc.nextLine();
		if(yn.equals("y")) {
			System.out.printf("������ �׸��� ��ȣ�� �Է����ֽʽÿ�: ");
			int num = sc.nextInt();
			sc.nextLine();
			if(num<=l.getExist()) {
				TodoItem item = l.get(num-1);
				for(;;) {
				System.out.printf("["+item.getTitle() + "]"+"\t" + item.getDesc()+"\t"+item.getCurrent_date()+"�� �����Ͻðڽ��ϱ�?(y/n): ");
				yn2= sc.nextLine();
				if(yn2.equals("y")) {
					String title;
					String category;
					System.out.printf("���ο� ī�װ��� �Է����ֽʽÿ�: ");
					category = sc.nextLine();
					for(;;) {
					
					System.out.printf("���ο� ������ �Է����ֽʽÿ�: ");
					title = sc.next();
					sc.nextLine();
					if (l.isDuplicate(title,category)) {
						System.out.println("Error: �׸� ������ �����Ͽ����ϴ�.");
						System.out.println("���� ��Ͽ� �ߺ��Ǵ� ������ �����մϴ�. ������ �ߺ��� �� �����ϴ�.");
						continue;
					}
					else {break;}
					
					}
					System.out.printf("���ο� ������ �Է����ֽʽÿ�: ");
					String desc = sc.nextLine();
					System.out.printf("���ο� �������� �Է����ֽʽÿ�(yyyy/MM/dd): ");
					String due_date = sc.nextLine();
					TodoItem ni = new TodoItem(title,desc);
					ni.setCategory(category);
					ni.setDuedate(due_date);
					
					l.set(num-1,ni);
					System.out.println("�׸��� �����Ǿ����ϴ�.");
					return;
					
				}
				else if(yn2.equals("n")) {
					return;
				}
				else {System.out.println("�߸��� �Է��Դϴ�.");}
				}
				
				
			}
			else if(num<l.getExist()) {
				System.out.println("�Է��Ͻ� ��ȣ�� �׸��� ������ �ʰ��մϴ�. �ٽ� �Է����ּ���.");
			}
			else {System.out.println("�߸��� �Է��Դϴ�.");
			return;}
			
		}
		else if(yn.equals("n")) {
		System.out.printf(
				 "=========== Edit Item Section ===========\n"
				+ "�����Ͻñ⸦ ���ϴ� ������ �Է����ֽʽÿ�: "
				);
		
		String title = sc.next().trim();
		System.out.printf("�� ī�װ��� �Է����ּ���.");
		String category = sc.nextLine();
		if (!l.isDuplicate(title,category)) {
			System.out.println("Error: �׸� ������ �����Ͽ����ϴ�.");
			System.out.println("�������� �ʴ� �����Դϴ�.");
			System.out.println("=========================================");
			return;
		}

		System.out.printf("���ο� ������ �Է����ֽʽÿ�: ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title,category)) {
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
				return;
			}
		}
		}
		else System.out.println("�߸��� �Է��Դϴ�.");
		}
	}

	public static void listAll(TodoList l) {
		int i=0;
		
		if(l.getExist()==0) {
			System.out.println("����� ����Ʈ�� �������� �ʽ��ϴ�.");
		}
		else {
			System.out.println("[��ü���, �� "+l.getExist()+"��]");
		System.out.println("=============== Item List ===============");
		for (TodoItem item : l.getList()) {
			if(i<=8) {
			System.out.println((i+1)+".\s\s"+"[" + item.getCategory() + "]"+"\t" +item.getTitle()+"<"+ item.getDesc()+">"+"-"+item.getCurrent_date()+"-������: "+item.getDuedate());
			}
			else if(i>8&&i<=98) {
				System.out.println((i+1)+".\s"+"[" + item.getCategory() + "]"+"\t" +item.getTitle()+"<"+item.getDesc()+">"+"-"+item.getCurrent_date()+"-������: "+item.getDuedate());
			}
			i++;
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
				System.out.println("�ٽ� �Է����ּ���.");
				return;}
			
			int index1 = title.indexOf(String.valueOf(keyword.charAt(0)));
			if(index1!=-1&&title.length()>=index1+wordlength){
			String input = title.substring(index1,index1+wordlength);
			if(input.equals(keyword)) {
				i++;
				System.out.println("[" + item.getCategory() + "]"+"\t" +item.getTitle()+"<"+item.getDesc()+">"+"-"+item.getCurrent_date()+"-������: "+item.getDuedate());
				continue;
			}
			}
			
			int index2 = desc.indexOf(keyword.substring(0,1));
			if(index2 !=-1&&desc.length()>index2+wordlength) {
			String input2 = desc.substring(index2,index2+wordlength);
			if(input2.equals(keyword)) {
				i++;
				System.out.println("[" + item.getCategory() + "]"+item.getTitle()+"<" + item.getDesc()+">-"+item.getCurrent_date()+"-������: "+item.getDuedate());
			}
		}
		}
		System.out.println("�� "+i+"���� �׸��� ã�ҽ��ϴ�.");
	}
	
	public static void findcate(String keycate,TodoList l) {
		int i = 0;
		
		if(keycate.length()==0) {
			System.out.println("�߸��� �Է��Դϴ�.");
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
					System.out.println("[" + item.getCategory() + "]"+"\t" +item.getTitle()+"<"+item.getDesc()+">"+"-"+item.getCurrent_date()+"-������: "+item.getDuedate());
				}
				}
			}
		System.out.println("�� "+i+"���� �׸��� ã�ҽ��ϴ�.");
			
		}
}

