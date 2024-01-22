import java.util.*;

public class Main {
    public static void main(String[] args) {
        // 여기에 코드를 작성해주세요.

        Scanner sc = new Scanner(System.in);

        String str1 = sc.next();
        String str2 = sc.next();
        String str3 = sc.next();

        int length1 = str1.length();
        int length2 = str2.length();
        int length3 = str3.length();
        List<Integer> list = new ArrayList<>();
        list.add(length1);
        list.add(length2);
        list.add(length3);
        int max = Collections.max(list);
        int min = Collections.min(list);

        System.out.println(max - min);


    }
}