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
	            		System.out.println("read 명령어로 파일을 읽은 후 실행하세요.");
	            	} else {
	            		System.out.println(wordCount);
	            	}
	            } else if(command.equals("find")) {
	            	if(wordCount == 0) {
	            		System.out.println("read 명령어로 파일을 읽은 후 실행하세요.");
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
	                
	                if(fileReader.length() == closeBracoverCountet + 1) { // 닫는 소괄호 뒤에 값이 없는 경우에 대한 처리
	                    voca[wordCount] = fileReader;
	                    part[wordCount] = "";
	                    mean[wordCount] = "";
	                } else { // 모든 값이 온전히 있는 경우
	                    voca[wordCount] = fileReader.substring(0,openBracoverCountet-1);
	                    part[wordCount] = fileReader.substring(openBracoverCountet,closeBracoverCountet+1);
	                    mean[wordCount] = fileReader.substring(closeBracoverCountet+2);
	                }
	                
	                if(filePath.hasNext()) { // 파일의 끝을 초과해 읽을 수도 있으므로 해당 코드 추가
	                    fileReader = filePath.nextLine(); // 공백 라인으로 단어가 구분되므로 하나의 단어를 읽은 후 다음 라인으로 변경
	                }
	                
	                wordCount++; // 찾은 단어의 갯수에 증감연산
	            }
	            
	            filePath.close();
	            
	        } catch (FileNotFoundException e) {
	            System.out.println("Can not find file");
	        }
	    }
	   
	    public static int find(int beginIndex, int endIndex, String []voca, String searchWord) {
	        int centerIndex = (beginIndex + endIndex)/2;
	        
	        // 만약 종료 인덱스가 시작 인덱스보다 작으면 해당 값을 리턴하고 재귀 호출 종료
	        if(endIndex < beginIndex) {
	                return endIndex;
	        }
	         
	        if(voca[centerIndex].equalsIgnoreCase(searchWord)) { // 대소문자를 구분하지 않고 비교
	            return centerIndex;
	        } else if(voca[centerIndex].compareToIgnoreCase(searchWord) < 0) {
	            beginIndex = centerIndex + 1;
	        } else {
	            endIndex = centerIndex - 1;
	        }
	        
	        // 재귀 함수로 단어 위치 검색
	        return find(beginIndex, endIndex, voca, searchWord);
	    }
	    
	    public static void printResult(int index, String word) {
	        if(voca[index].equalsIgnoreCase(word)) { // 만약 찾는 단어가 있는 경우
	            int vocaIndex = index;
	            int overCount = 0;
	            
	            // 인덱스를 증가시키면서 같은 단어가 몇 개인지 검사한다.
	            while(vocaIndex < wordCount && voca[vocaIndex].equalsIgnoreCase(word)) {
	                vocaIndex++;
	                overCount++;
	            }
	            
	            System.out.println("Found " + overCount + " items.");
	            
	            // 같은 단어의 갯수만큼 결과 출력
	            for(int i = 0; i < overCount; i++) {
	                System.out.println(voca[index+i] + " " + part[index+i] + " " + mean[index+i]);
	            }
	            
	            // 만약 찾는 단어가 없는 경우
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