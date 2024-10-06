import java.io.*;
import java.util.*;

public class Main {

    static int[][] map;
    static int n, m;
    static int[] dx = {-1, 0, 1, 0}; // 북 동 남 서
    static int[] dy = {0, 1, 0, -1};
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

        //현재 위치에서 현재 방향을 기준으로 왼쪽방향부터 차례대로 탐색을 진행한다.
        for (int i = 0; i < 4; i++) {

            // 90도 회전
            d = (d + 3) % 4; // 왼쪽 방향으로 변환 (북 -> 서 -> 남 -> 동){
            int nX = x + dx[d];
            int nY = y + dy[d];
            if (nX >= 0 && nY >= 0 && nX < n && nY < m) {
                // 만약 도로라면
                if (map[nX][nY] == 0) {
                    count++;
                    dfs(nX, nY, d);
                    //여기서 return을 안하면 복귀하는 도중 뒤로가서 다른 곳을 갈수가 있음.
                    return;
                }
            }

        }
        // 네 방향 모두 방문했거나 인도인 경우에는
        // 뒤쪽 칸이 인도가 아니라는 전제 하에, 바라보는 방향을 유지한 채로 한 칸 후진.
        int dir = (d + 2) % 4; // 반대 방향으로 후진
        int bX = x + dx[dir];
        int bY = y + dy[dir];
        if (bX >= 0 && bY >= 0 && bX < n && bY < m && map[bX][bY] != 1) {
            dfs(bX, bY, d); // 후진이니까 바라보는 방향은 유지
        }


    }

}