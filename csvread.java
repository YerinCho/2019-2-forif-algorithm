import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class csvread {

    static String[][] Food_Info = new String[2221][10]; // CSV 파일을 읽고 저장할 배열 선언 , arraylist나 벡터 등의 다른 곳에 저장해도 상관없음
	String[] Food_Name=new String[2221];

	public static List<Food> getEatenFoodList() {
		List<Food> foodList = new ArrayList<>();

		try {
			// csv 데이터 파일
			File csv = new File("Eaten_Food.txt");
			BufferedReader br = new BufferedReader(new FileReader(csv));
			String line = "";
			int row = 0;
	
			while ((line = br.readLine()) != null) {
			// -1 옵션은 마지막 "," 이후 빈 공백도 읽기 위한 옵션
			String[] token = line.split(" ", -1);
				Food food = new Food(token[0], Integer.parseInt(token[1]),
				Integer.parseInt(token[2]), Integer.parseInt(token[3]),
				Integer.parseInt(token[4]));
				foodList.add(food);
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return foodList;
	}

	public csvread(String fileName) {
		try {
			// csv 데이터 파일
			File csv = new File(fileName);
			BufferedReader br = new BufferedReader(new FileReader(csv));
			String line = "";
			int row = 0, i;
	
			while ((line = br.readLine()) != null) {
			// -1 옵션은 마지막 "," 이후 빈 공백도 읽기 위한 옵션
			String[] token = line.split(",", -1);
			for (i = 0; i < 10; i++) {
				Food_Info[row][i] = token[i];
			}
			Food_Name[row]=Food_Info[row][0];
			row++;
			}
			br.close();	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}

	public void write(Food food) {
		try{
                         
            // BufferedWriter 와 FileWriter를 조합하여 사용 (속도 향상)
            BufferedWriter fw = new BufferedWriter(new FileWriter("Eaten_Food.txt", true));
             
            // 파일안에 문자열 쓰기
			fw.write("\n" + food.getFoodName() + " " + food.getFoodPrice() + " " + food.getPeopleCnt()
			+ " " + food.getBeforeStressLv() + " " + food.getAfterStressLv() + " " +
			getCarbohydrate(food.getFoodName()) + " " + getProtein(food.getFoodName()) + " " +
			getFat(food.getFoodName()) + " " + getSaturatedFat(food.getFoodName()) + " " +
			getTransFat(food.getFoodName()) + " " + getCholesterol(food.getFoodName()) 
			+ " " + (food.getResultScore() > 70 ? "0" : "1")+ " " + food.getResultScore());
            fw.flush();
 
            // 객체 닫기
            fw.close();
             
             
        }catch(Exception e){
			e.printStackTrace();
		}
	}

	public float getCarbohydrate(String Name) {
	return Float.parseFloat(Food_Info[searchName(Name)][2]);
    }

    public float getProtein(String Name) {
	return Float.parseFloat(Food_Info[searchName(Name)][3]);
    }

    public float getFat(String Name) {
	return Float.parseFloat(Food_Info[searchName(Name)][4]);
    }

    public float getSaturatedFat(String Name) {
	return Float.parseFloat(Food_Info[searchName(Name)][8]);
    }

    public float getTransFat(String Name) {
	return Float.parseFloat(Food_Info[searchName(Name)][9]);
    }

    public float getCholesterol(String Name) {
	return Float.parseFloat(Food_Info[searchName(Name)][7]);
	}
	
	public float getRefinedFat(String Name) {
		return getFat(Name) * 0.4f + getTransFat(Name) * 0.2f
		+ getCholesterol(Name) * 0.2f + getSaturatedFat(Name) * 0.2f;
	}
    
    public int searchName(String Name) {
	int idx = Arrays.binarySearch(Food_Name,Name);
	return idx;
	}
}
