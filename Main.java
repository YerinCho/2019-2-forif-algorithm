import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.Math;

public class Main {
    private static List<Food> foodList;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        foodList = new ArrayList<>();
        csvread eatenFood = new csvread("Eaten_Food.csv");

        System.out.println("성별을 입력해주세요.(M/F)");
        String gender = scan.nextLine();
        System.out.println("가격, 탄수화물, 단백질, 지방의 우선순위를 입력해주세요.(형식: 1234)");
        int priority = scan.nextInt();

        System.out.println("몇 가지의 음식을 드셨습니까?");
        int n = scan.nextInt();
        System.out.println("음식명, 음식값, 인원수, 스트레스 지수를 순서대로 입력해주세요.");
        for(int i=0; i<n; i++) {
            for(int j=0; j<4; j++) {
                eatenFood.writeData();
            }
        }

        System.out.println("오늘의 음식은?");
        String todayFood = scan.next();
        System.out.println("지금 스트레스의 상태는?(1~5)");
        int currentStress = scan.nextInt();
        PersonInfo personInfo = new PersonInfo(gender, priority, todayFood, currentStress);

        System.out.println(mayEat(foodList, personInfo) ? "O" : "X");
    }

    private static boolean mayEat(List<Food> foodList, PersonInfo personInfo) {
        boolean isMale = personInfo.getGender() == "M";
        csvread foodData = new csvread("Food_Data.csv");
        String todayFood = personInfo.getTodayFood();

        double c=foodData.getCarbohydrate(todayFood);
        double p=foodData.getProtein(todayFood);
        double f=foodData.getRefinedFat(todayFood);
        int price = 0;
        int score = 0;
        double cRate = c / (c+p+f) * 100;
        double pRate = p / (c+p+f) * 100;
        double fRate = f / (c+p+f) * 100;


        if(personInfo.getCurrentStress() == 5) {
            //TODO : 경제사정, 영양소 정보 노출
            return true;
        }

        if(price > getAverage(foodList)) {
            //TODO : 지금 먹은 음식의 가격은 어떻게 구하는지
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
    private String todayFood;
    private int currentStress;

    public PersonInfo(String gender, int priority, String todayFood, int currentStress) {
        this.gender = gender;
        this.priority = priority;
        this.todayFood = todayFood;
        this.currentStress = currentStress;
    }

    public String getGender() {
        return gender;
    }

    public int getPriority() {
        return priority;
    }

    public int getPriority(int order) {
        int priorPerOrder = priority % (int)Math.pow(10, 5 - order) 
            - priority / (int)Math.pow(10, 4 - order);
        return (5 - priorPerOrder) * 10;
    }

    public String getTodayFood() {
        return todayFood;
    }

    public int getCurrentStress() {
        return currentStress;
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