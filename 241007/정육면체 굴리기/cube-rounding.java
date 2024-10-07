import java.io.*;
import java.util.*;

public class Main {

    static int n, m, x, y;
    static int[][] map;
    static int[] dice;
    static int[] dx = {0, 0, -1, 1}; // 동 서 북 남
    static int[] dy = {1, -1, 0, 0};


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        x = Integer.parseInt(st.nextToken());
        y = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        map = new int[n][m];
        dice = new int[6];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < k; i++) {
            int d = Integer.parseInt(st.nextToken());
            roll(d);

        }
        br.close();
    }

    public static void roll(int d) {

        int nX = x + dx[d - 1];
        int nY = y + dy[d - 1];

        if (nX < 0 || nY < 0 || nX >= n || nY >= m) {
            return;
        }
        int temp = dice[5];
        switch (d) {
            // 동
            case 1:
                dice[5] = dice[2];
                dice[2] = dice[0];
                dice[0] = dice[3];
                dice[3] = temp;
                break;
            // 서
            case 2:
                dice[5] = dice[3];
                dice[3] = dice[0];
                dice[0] = dice[2];
                dice[2] = temp;
                break;
            // 남
            case 3:
                dice[5] = dice[4];
                dice[4] = dice[0];
                dice[0] = dice[1];
                dice[1] = temp;
                break;

            // 북
            case 4:
                dice[5] = dice[1];
                dice[1] = dice[0];
                dice[0] = dice[4];
                dice[4] = temp;
        }

        System.out.println(dice[0]);

        // 좌표 업데이트
        x = nX;
        y = nY;

        // 0인경우 주사위 바닥 -> 맵
        if (map[x][y] == 0) {
            map[x][y] = dice[5];
        }

        // map이 0이 아닌 경우 맵 -> 주사위 복사, 맵은 0으로 된다.
        else {
            dice[5] = map[x][y];
            map[x][y] = 0;
        }

    }

}