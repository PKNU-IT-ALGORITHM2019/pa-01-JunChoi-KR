package report_01;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
        static Scanner keyboard;
	    static int MaxIndex = 1024*1024;
	    static String []voca;
	    static String []part;
	    static String []mean;
	    static String command;
	    static String fileReader;
	    static int wordCount = 0;
	    
	    public static void main(String[] args) {
	        voca = new String [MaxIndex];
	        part = new String [MaxIndex];
	        mean = new String [MaxIndex];
	        
	        while(true){
	            System.out.print("$ ");
	            
	            keyboard = new Scanner(System.in);
	            command = keyboard.next();
	            
	            if(command.equals("read")) {
	                String path = keyboard.next();
	                read(path);
	            } else if(command.equals("size")) {
	            	if(wordCount == 0) {
	            		System.out.println("read ��ɾ�� ������ ���� �� �����ϼ���.");
	            	} else {
	            		System.out.println(wordCount);
	            	}
	            } else if(command.equals("find")) {
	            	if(wordCount == 0) {
	            		System.out.println("read ��ɾ�� ������ ���� �� �����ϼ���.");
	            	} else {
		            	String searchWord = keyboard.nextLine().trim();
		                int findIndex = find(0,wordCount-1,voca, searchWord);
		                printResult(findIndex, searchWord);
	            	}
	            } else if(command.equals("exit")) {
	                break;
	            }
	        }
	    }
	    
	    public static void read(String path) {
	        try {
	            Scanner filePath = new Scanner(new File(path));
	            
	            while(filePath.hasNext()) {
	                fileReader = filePath.nextLine();
	                int openBracoverCountet = fileReader.indexOf("(");
	                int closeBracoverCountet = fileReader.indexOf(")");
	                
	                if(fileReader.length() == closeBracoverCountet + 1) { // �ݴ� �Ұ�ȣ �ڿ� ���� ���� ��쿡 ���� ó��
	                    voca[wordCount] = fileReader;
	                    part[wordCount] = "";
	                    mean[wordCount] = "";
	                } else { // ��� ���� ������ �ִ� ���
	                    voca[wordCount] = fileReader.substring(0,openBracoverCountet-1);
	                    part[wordCount] = fileReader.substring(openBracoverCountet,closeBracoverCountet+1);
	                    mean[wordCount] = fileReader.substring(closeBracoverCountet+2);
	                }
	                
	                if(filePath.hasNext()) { // ������ ���� �ʰ��� ���� ���� �����Ƿ� �ش� �ڵ� �߰�
	                    fileReader = filePath.nextLine(); // ���� �������� �ܾ ���еǹǷ� �ϳ��� �ܾ ���� �� ���� �������� ����
	                }
	                
	                wordCount++; // ã�� �ܾ��� ������ ��������
	            }
	            
	            filePath.close();
	            
	        } catch (FileNotFoundException e) {
	            System.out.println("Can not find file");
	        }
	    }
	   
	    public static int find(int beginIndex, int endIndex, String []voca, String searchWord) {
	        int centerIndex = (beginIndex + endIndex)/2;
	        
	        // ���� ���� �ε����� ���� �ε������� ������ �ش� ���� �����ϰ� ��� ȣ�� ����
	        if(endIndex < beginIndex) {
	                return endIndex;
	        }
	         
	        if(voca[centerIndex].equalsIgnoreCase(searchWord)) { // ��ҹ��ڸ� �������� �ʰ� ��
	            return centerIndex;
	        } else if(voca[centerIndex].compareToIgnoreCase(searchWord) < 0) {
	            beginIndex = centerIndex + 1;
	        } else {
	            endIndex = centerIndex - 1;
	        }
	        
	        // ��� �Լ��� �ܾ� ��ġ �˻�
	        return find(beginIndex, endIndex, voca, searchWord);
	    }
	    
	    public static void printResult(int index, String word) {
	        if(voca[index].equalsIgnoreCase(word)) { // ���� ã�� �ܾ �ִ� ���
	            int vocaIndex = index;
	            int overCount = 0;
	            
	            // �ε����� ������Ű�鼭 ���� �ܾ �� ������ �˻��Ѵ�.
	            while(vocaIndex < wordCount && voca[vocaIndex].equalsIgnoreCase(word)) {
	                vocaIndex++;
	                overCount++;
	            }
	            
	            System.out.println("Found " + overCount + " items.");
	            
	            // ���� �ܾ��� ������ŭ ��� ���
	            for(int i = 0; i < overCount; i++) {
	                System.out.println(voca[index+i] + " " + part[index+i] + " " + mean[index+i]);
	            }
	            
	            // ���� ã�� �ܾ ���� ���
	        } else {
	        	System.out.println("Not found.");
		        System.out.println(voca[index] + " " + part[index]);
		        System.out.println("- - -");
		        if(index+1 < wordCount) {
		        	System.out.println(voca[index+1] + " " + part[index+1]);
		        }
	        }
	    }
}