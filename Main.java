import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.Math;

public class Main {
    private static List<Food> foodList;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("성별을 입력해주세요: ");
        String gender = scan.next();
        System.out.println("가격, 탄수화물, 단백질, 지방의 우선순위를 입력해주세요.(형식: 1234)");
        int priority = scan.nextInt();

        foodList = new ArrayList<>();
        int n = scan.nextInt();
        for(int i=0; i<n; i++) {
            for(int j=0; j<4; j++) {
                foodList.add(new Food(scan.next(), scan.nextInt(), scan.nextInt(), scan.nextInt()));
            }
        }
        String todayFood = scan.next();
        int currentStress = scan.nextInt();
        PersonInfo personInfo = new PersonInfo(gender, priority, todayFood, currentStress);

        System.out.println(mayEat(foodList, personInfo) ? "O" : "X");
    }

    private static boolean mayEat(List<Food> foodList, PersonInfo personInfo) {
        boolean isMale = personInfo.getGender() == "M";
        double c=0, p=0, f=0;
        int price = 0;
        int score = 0;
        int sum = 0;
        double cRate = c / (c+p+f) * 100;
        double pRate = p / (c+p+f) * 100;
        double fRate = f / (c+p+f) * 100;


        if(personInfo.getCurrentStress() == 5) {
            //TODO : 경제사정, 영양소 정보 노출
            return true;
        }

        if(price > getAverage(foodList)) {
            //TODO : 지금 먹은 음식의 가격은 어떻게 구할까
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
    private int stressLv;

    public Food(String foodName, int foodPrice, int peopleCnt, int stressLv) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.peopleCnt = peopleCnt;
        this.stressLv = stressLv;
    }

    public int getStressLv() {
        return stressLv;
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
}