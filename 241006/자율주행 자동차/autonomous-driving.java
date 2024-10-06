import java.io.*;
import java.util.*;

public class Main {

    static int[][] map;

    static int n, m;
    static int[] dx = {0, 1, 0, -1}; // 북 동 남 서
    static int[] dy = {-1, 0, 1, 0};
    static int count = 1;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        map = new int[n][m];

        int x, y, d;
        st = new StringTokenizer(br.readLine());
        x = Integer.parseInt(st.nextToken());
        y = Integer.parseInt(st.nextToken());
        d = Integer.parseInt(st.nextToken()); // 0 1 2 3 북 동 남 서

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dfs(x, y, d);

        System.out.println(count);

        br.close();

    }

    public static void dfs(int x, int y, int d) {

        // 시작 위치는 방문한 도로
        map[x][y] = -1;

        for (int i = 0; i < 4; i++) {

            d = (d + 3) % 4; // 왼쪽 방향으로 변환 (북 -> 서 -> 남 -> 동){
            int nX = x + dx[d];
            int nY = y + dy[d];
            if (nX >= 0 && nY >= 0 && nX < n && nY < m) {
                // 만약 도로라면
                if (map[nX][nY] == 0) {
                    count++;
                    dfs(nX, nY, d);
                    return;
                }
            }

        }

        int dir = (d + 2) % 4; // 반대 방향으로 후진
        int bX = x + dx[dir];
        int bY = y + dy[dir];
        if (bX >= 0 && bY >= 0 && bX < n && bY < m && map[bX][bY] != 1) {
            dfs(bX, bY, d);
        }


    }

}