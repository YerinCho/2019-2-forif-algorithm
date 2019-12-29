import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.Math;

public class Main {
    private static List<Food> foodList;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Food food;
        foodList = csvread.getEatenFoodList();
        csvread foodData = new csvread("Food_Data.csv");

        System.out.println("성별을 입력해주세요.(M/F)");
        String gender = scan.nextLine();
        System.out.println("가격, 탄수화물, 단백질, 지방의 우선순위를 입력해주세요.(형식: 1234)");
        int priority = scan.nextInt();
        PersonInfo personInfo = new PersonInfo(gender, priority);
        while(true) {
            food = initFood(scan);
            if(foodData.searchName(food.getFoodName()) >= 0) {
                break;
            } else {
                System.out.println("데이터에 해당 메뉴가 없습니다.");
                continue;
            }
        }
        
        System.out.println(mayEat(food, foodData, foodList, personInfo) ? "O" : "X");
    }

    private static Food initFood(Scanner scan) {
        scan.nextLine();
        System.out.println("오늘의 음식은?");
        String todayFood = scan.nextLine();
        System.out.println("음식의 가격은?");
        int foodPrice = scan.nextInt();
        System.out.println("인원 수는?");
        int peopleCnt = scan.nextInt();
        System.out.println("지금 스트레스의 상태는?(1~5)");
        int currentStress = scan.nextInt();
        System.out.println("먹고 난 후의 스트레스는?(1~5)");
        int afterStress = scan.nextInt();

        Food food = new Food(todayFood, foodPrice, peopleCnt, currentStress, afterStress);
        return food;
    }

    private static boolean mayEat(Food todayFood, csvread foodData,
     List<Food> foodList, PersonInfo personInfo) {
        boolean isMale = personInfo.getGender() == "M";
        String todayFoodName = todayFood.getFoodName();

        double c=foodData.getCarbohydrate(todayFoodName);
        double p=foodData.getProtein(todayFoodName);
        double f=foodData.getRefinedFat(todayFoodName);
        int price = todayFood.getFoodPrice();
        int score = 0;
        double cRate = c / (c+p+f) * 100;
        double pRate = p / (c+p+f) * 100;
        double fRate = f / (c+p+f) * 100;


        if(todayFood.getBeforeStressLv() == 5) {
            System.out.println(String.format("스트레스가 최고 상태입니다.\n영양소는 탄수화물이 %3.2f, 단백질이 %3.2f, 지방이 %3.2f입니다.",
            c, p, f));
            //TODO : 경제사정, 영양소 정보 노출
            foodData.write(todayFood);
            return true;
        }

        if(price > getAverage(foodList)) {
            score += personInfo.getPriority(1);
        }

        if(cRate < 55 || cRate > (isMale ? 58 : 60)) {
            score += personInfo.getPriority(2);
        }
        if(pRate < 15 || pRate > (isMale ? 21 : 18)) {
            score += personInfo.getPriority(3);
        }
        if(fRate < (isMale ? 22 : 21) || fRate > (isMale ? 25 : 24)) {
            score += personInfo.getPriority(4);
        }

        todayFood.setResultStore(score);
        foodData.write(todayFood);

        return score < 70;  //기준 스코어는 임의로 정함, 수정 예정
    }

    private static int getAverage(List<Food> foodList) {
        int sum = 0;
        for(Food food : foodList) {
            sum += food.getFoodPrice();
        }

        return sum / foodList.size();
    }
}

class PersonInfo {
    private String gender;
    private int priority;

    public PersonInfo(String gender, int priority) {
        this.gender = gender;
        this.priority = priority;
    }

    public String getGender() {
        return gender;
    }

    public int getPriority() {
        return priority;
    }

    public int getPriority(int order) {
        int one = priority % (int)Math.pow(10, 5 - order);
        int two = one / (int)Math.pow(10, 4 - order);
        return (5 - two) * 10;
    }
}

class Food {
    private String foodName;
    private int foodPrice;
    private int peopleCnt;
    private int beforeStressLv;
    private int afterStressLv;
    private int resultScore;

    public Food(String foodName, int foodPrice, int peopleCnt, int beforeStressLv, int afterStressLv) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.peopleCnt = peopleCnt;
        this.beforeStressLv = beforeStressLv;
        this.afterStressLv = afterStressLv;
    }

    public int getBeforeStressLv() {
        return beforeStressLv;
    }

    public int getAfterStressLv() {
        return afterStressLv;
    }

    public int getPeopleCnt() {
        return peopleCnt;
    } 

    public int getFoodPrice() {
        return foodPrice;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setResultStore(int resultScore) {
        this.resultScore = resultScore;
    }

    public int getResultScore() {
        return resultScore;
    }
}