import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        List<Food> foodList = new ArrayList<>();
        int n = scan.nextInt();
        for(int i=0; i<n; i++) {
            for(int j=0; j<4; j++) {
                foodList.add(new Food(scan.next(), scan.next(), scan.nextInt(), scan.nextInt()));
            }
        }
        String todayFood = scan.nextLine();
        System.out.println(mayEat(todayFood) ? "O" : "X");
    }

    private static boolean mayEat(String todayFood) {
        return false;
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

    public Food getFood() {
        return new Food(foodName, foodPrice, peopleCnt, stressLv);
    }
}