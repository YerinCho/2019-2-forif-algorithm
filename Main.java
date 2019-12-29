import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("성별을 입력해주세요: ");
        String gender = scan.next();
        System.out.println("가격, 탄수화물, 단백질, 지방의 우선순위를 입력해주세요.(형식: 1234)");
        String priority = scan.nextLine();

        PersonInfo personInfo = new PersonInfo(gender, priority);

        List<Food> foodList = new ArrayList<>();
        int n = scan.nextInt();
        for(int i=0; i<n; i++) {
            for(int j=0; j<4; j++) {
                foodList.add(new Food(scan.next(), scan.next(), scan.nextInt(), scan.nextInt()));
            }
        }
        String todayFood = scan.nextLine();
        System.out.println(mayEat(todayFood, personInfo) ? "O" : "X");
    }

    private static boolean mayEat(String todayFood, PersonInfo personInfo) {
        boolean isMale = personInfo.getGender() == "M";
        double c=0, p=0, f=0;
        int price;
        int score = 0;

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
}

class PersonInfo {
    private String gender;
    private String priority;

    public PersonInfo(String gender, String priority) {
        this.gender = gender;
        this.priority = priority;
    }

    public String getGender() {
        return gender;
    }

    public String getPrioirty() {
        return priority;
    }
}

class Food {
    private String foodName;
    private String foodPrice;
    private int peopleCnt;
    private int stressLv;

    public Food(String foodName, String foodPrice, int peopleCnt, int stressLv) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.peopleCnt = peopleCnt;
        this.stressLv = stressLv;
    }
}