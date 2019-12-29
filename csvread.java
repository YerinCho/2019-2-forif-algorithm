import java.io.*;
import java.util.Arrays;

public class csvread {

    static String[][] Food_Info = new String[2221][10]; // CSV 파일을 읽고 저장할 배열 선언 , arraylist나 벡터 등의 다른 곳에 저장해도 상관없음

    public static void main(String[] args) {
	try {
	    // csv 데이터 파일
	    File csv = new File("Food_Data.csv");
	    BufferedReader br = new BufferedReader(new FileReader(csv));
	    String line = "";
	    int row = 0, i;

	    while ((line = br.readLine()) != null) {
		// -1 옵션은 마지막 "," 이후 빈 공백도 읽기 위한 옵션
		String[] token = line.split(",", -1);
		for (i = 0; i < 10; i++) {
		    Food_Info[row][i] = token[i];
		}

		// CSV에서 읽어 배열에 옮긴 자료 확인하기 위한 출력
		for (i = 0; i < 10; i++) {
		    System.out.print(Food_Info[row][i] + " ");
		}
		System.out.println("");

		row++;
	    }
	    br.close();

	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }


    public static float getCarbohydrate(String Name) {
	return Float.parseFloat(Food_Info[searchName(Name)][2]);
    }

    public static float getProtein(String Name) {
	return Float.parseFloat(Food_Info[searchName(Name)][3]);
    }

    public static float getFat(String Name) {
	return Float.parseFloat(Food_Info[searchName(Name)][4]);
    }

    public static float getSaturatedFat(String Name) {
	return Float.parseFloat(Food_Info[searchName(Name)][8]);
    }

    public static float getTransFat(String Name) {
	return Float.parseFloat(Food_Info[searchName(Name)][9]);
    }

    public static float getCholesterol(String Name) {
	return Float.parseFloat(Food_Info[searchName(Name)][7]);
    }
    
    public static int searchName(String Name) {
	String[] Food_Name=new String[2221];
	for(int i=1;i<2221;i++) {
	    Food_Name[i]=Food_Info[i][0];
	}
	
	int idx = Arrays.binarySearch(Food_Name,Name);
	return idx;
    }
}
