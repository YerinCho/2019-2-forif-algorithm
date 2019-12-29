import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Food> foodList;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("성별을 입력해주세요: ");
        String gender = scan.next();
        System.out.println("가격, 탄수화물, 단백질, 지방의 우선순위를 입력해주세요.(형식: 1234)");
        String priority = scan.nextLine();

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
        int price;
        int score = 0;
        int sum = 0;

        if(personInfo.getCurrentStress() == 5) {
            //TODO : 경제사정, 영양소 정보 노출
            return true;
        }

        if(price > getAverage()) {
            return false;
        }

        if(c < 55 || c > (isMale ? 58 : 60)) {
            score += 10;
        }
        if(p < 15 || p > (isMale ? 21 : 18)) {
            score += 10;
        }
        if(f < (isMale ? 22 : 21) || f > (isMale ? 25 : 24)) {
            score += 10;
        }
        return false;
    }

    private getAverage(List<Food> foodList) {
        int sum = 0;
        for(Food food : foodList) {
            sum += food.getFoodPrice();
        }

        return sum / foodList.size();
    }
}

class PersonInfo {
    private String gender;
    private String priority;
    private String todayFood;
    private int currentStress;

    public PersonInfo(String gender, String priority, String todayFood, int currentStress) {
        this.gender = gender;
        this.priority = priority;
        this.todayFood = todayFood;
        this.currentStress = currentStress;
    }

    public String getGender() {
        return gender;
    }

    public String getPrioirty() {
        return priority;
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