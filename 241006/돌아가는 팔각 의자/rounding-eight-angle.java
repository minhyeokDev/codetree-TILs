import java.io.*;
import java.util.*;

public class Main {

    static int[][] chair;
    static int k;
    static int sum;

    static int[] dir;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        chair = new int[4][8];

        for (int i = 0; i < 4; i++) {
            String str = br.readLine();
            for (int j = 0; j < 8; j++) {
                chair[i][j] = str.charAt(j) - '0';
            }
        }

        k = Integer.parseInt(br.readLine());

        int n = 0;
        int d = 0;
        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken()) - 1;
            d = Integer.parseInt(st.nextToken());
            dir = new int[4];
            dir[n] = d;
            checkDirection(n);

            rotation();

        }

        sum = chair[0][0] + 2 * chair[1][0] + 4 * chair[2][0] + 8 * chair[3][0];

        System.out.println(sum);
        br.close();
    }

    public static void checkDirection(int n) {

        // 좌측 회전 방향 검사
        for (int i = n - 1; i >= 0; i--) {
            if (chair[i][2] != chair[i + 1][6]) {
                dir[i] = -dir[i + 1];
            } else {
                //회전을 하지않으면 다음 톱니도 회전을 하지 않게 된다.
                break;
            }
        }

        // 우측 회전 방향 검사
        for (int i = n + 1; i < 4; i++) {
            if (chair[i][6] != chair[i - 1][2]) {
                dir[i] = -dir[i - 1];
            } else {
                //회전을 하지않으면 다음 톱니도 회전을 하지 않게 된다.
                break;
            }
        }

    }

    public static void rotation() {
        int temp = 0;

        // 모든 톱니바퀴에 대해서
        for (int i = 0; i < 4; i++) {
            if (dir[i] == 1) { // 시계 방향 회전
                temp = chair[i][7];

                for (int j = 7; j > 0; j--) {
                    chair[i][j] = chair[i][j - 1];
                }
                chair[i][0] = temp;
            } else if (dir[i] == -1) { // 반시계 방향 회전
                temp = chair[i][0];

                for (int j = 0; j < 7; j++) {
                    chair[i][j] = chair[i][j + 1];
                }
                chair[i][7] = temp;
            }
        }

    }

}