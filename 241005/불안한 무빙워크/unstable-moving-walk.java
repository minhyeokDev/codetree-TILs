import java.util.*;
import java.io.*;

public class Main {

    static int n, k;
    static int[] belt;
    static boolean[] people;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        int size = 2 * n;
        belt = new int[size];
        people = new boolean[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < size; i++) {
            belt[i] = Integer.parseInt(st.nextToken());
        }

        int step = 0;

        while (true) {
            step++;
            // 무빙 워크 한칸 이동
            int tmp = belt[size - 1];
            for (int i = size - 1; i > 0; i--) {
                belt[i] = belt[i - 1];
            }
            belt[0] = tmp;

            for (int i = n - 1; i > 0; i--) {
                people[i] = people[i - 1];
            }

            // n-1에 위치에서는 항상 사람이 내리므로 사람 내림처리 해준다.
            people[n - 1] = false;
            // 새로운 벨트가 왔으므로 여긴 무조건 사람이 없다.
            people[0] = false;

            int cnt = 0;

            for (int i = n - 1; i > 0; i--) {
                // 현재 칸에 사람이 있고 앞선 칸에 사람이 없다면
                // 내구도 체크 후 이동
                if (people[i - 1] && !people[i] && belt[i] > 0) {
                    people[i - 1] = false;
                    people[i] = true;
                    belt[i]--;
                    if (belt[i] == 0) {
                        cnt++;
                    }
                    people[n - 1] = false;
                }
            }

            // 1번 칸에 사람이 없고 안정성이 0이 아니라면 사람을 올림
            if (belt[0] > 0) {
                people[0] = true;
                belt[0]--;
                if (belt[0] == 0) {
                    cnt++;
                }
            }

            if (cnt >= k) {
                break;
            }


        }
        System.out.println(step);
        br.close();
    }

}