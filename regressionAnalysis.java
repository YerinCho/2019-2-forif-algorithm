import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class regressionAnalysis {

    static ArrayList<ArrayList<String>> eaten_data = new ArrayList<>();

    public regressionAnalysis() {

	// 저장된 파일 이차원어레이리스트에 저장
	try {
	    // 파일 객체 생성
	    File file = new File("Eaten_Food.txt");
	    // 입력 스트림 생성
	    FileReader filereader = new FileReader(file);
	    // 입력 버퍼 생성
	    BufferedReader bufReader = new BufferedReader(filereader);
	    String line = "";
	    while ((line = bufReader.readLine()) != null) {
		String[] tmp = line.split(" ");
		ArrayList<String> tmpArrayList = new ArrayList<>(Arrays.asList(tmp));
		eaten_data.add(tmpArrayList);
	    }
	    // .readLine()은 끝에 개행문자를 읽지 않는다.
	    bufReader.close();
	} catch (FileNotFoundException e) {
	    // TODO: handle exception
	} catch (IOException e) {
	    System.out.println(e);
	}
    }

    public static double LinearAnalayis(int idx) {
	double sumx = 0, sumy = 0, sumx2 = 0;
	int n = eaten_data.size();
	for (int i = 0; i < n; i++) {
	    sumx += i + 1;
	    sumx2 += (i + 1) * (i + 1);
	    System.out.println(eaten_data.get(i).get(idx));
	    sumy += Double.parseDouble(eaten_data.get(i).get(idx));
	}
	double xbar = sumx / n;
	double ybar = sumy / n;

	double xxbar = 0.0, yybar = 0.0, xybar = 0.0;
	for (int i = 0; i < n; i++) {
	    xxbar += (i + 1 - xbar) * (i + 1 - xbar);
	    yybar += (Double.parseDouble(eaten_data.get(i).get(idx)) - ybar)
		    * (Double.parseDouble(eaten_data.get(i).get(idx)) - ybar);
	    xybar += (i + 1 - xbar) * (Double.parseDouble(eaten_data.get(i).get(idx)) - ybar);
	}
	double beta1 = xybar / xxbar;
	double beta0 = ybar - beta1 * xbar;
	
	return beta1 * (n + 1) + beta0;
    }

    public static double getFinalResult(double input,int idx) {
	double result=Math.abs(LinearAnalayis(idx)-input);
	return result;
    }
}
