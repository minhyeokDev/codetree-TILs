import java.io.*;
import java.util.*;

public class Main {

    static int n, m;
    static int[][] map;
    public static int[][][] mode = {{{0}},
        {{0}, {1}, {2}, {3}}, // CASE 1 북(0) / 남(1) / 서(2) / 동(3)
        {{2, 3}, {0, 1}}, // CASE 2 서(2), 동(3) / 북(0), 남(1
        {{0, 3}, {1, 3}, {1, 2}, {0, 2}},
        // CASE 3 북(0), 동(3) / 남(1), 3(동) / 남(1), 서(2) / 북(0), 서(2)
        {{0, 2, 3}, {0, 1, 3}, {1, 2, 3}, {0, 1, 2}},
        // CASE 4 북(0), 서(2), 동(3) / 북(0), 남(1), 동(3) / 남(1), 서(2), 동(3) / 북(0), 남(1), 서(2)
        {{0, 1, 2, 3}}}; // CASE 5 북(0), 남(1), 서(2), 동(3)

    static int[] dx = {-1, 1, 0, 0}; // 북 남 서 동
    static int[] dy = {0, 0, -1, 1};
    static List<CCTV> cctvList;
    static int min = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        map = new int[n][m];
        cctvList = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] != 0 && map[i][j] != 6) {
                    cctvList.add(new CCTV(i, j, map[i][j]));
                }
            }
        }

        solve(0, map);

        System.out.println(min);

        br.close();
    }

    public static void solve(int depth, int[][] map) {
        // 체스에 넣은 값들을 뽑아내면서, 번호에 맞는 방향으로 백트래킹으로 그때마다의 방향을 바꿔줌.
        // 방향을 정하고, bfs 로 뻗어 나가면서 # 로 바꿈
        // 벽에 부딪히면 그만 뻗어나감.
        // 그때마다 copy 한 map 에 결과를 저장하고 , 그 때의 최소값을 구함.

        if (depth == cctvList.size()) {
            int count = check(map);
            min = Math.min(count, min);

            return;
        }

        int cctvNum = cctvList.get(depth).num;
        int x = cctvList.get(depth).x;
        int y = cctvList.get(depth).y;

        for (int i = 0; i < mode[cctvNum].length; i++) { // CASE 별로
            int[][] copyMap = new int[n][m];

            for (int k = 0; k < n; k++) {
                copyMap[k] = map[k].clone();
            }

            for (int j = 0; j < mode[cctvNum][i].length; j++) { // 각각의 방향 별로
                int dir = mode[cctvNum][i][j];

                int nX = x + dx[dir];
                int nY = y + dy[dir];

                while (true) {
                    if (nX < 0 || nY < 0 || nX >= n || nY >= m) {
                        break;
                    }
                    if (map[nX][nY] == 6) {
                        break;
                    }
                    copyMap[nX][nY] = -1;
                    nX += dx[dir];
                    nY += dy[dir];
                }
            }

            solve(depth + 1, copyMap);

        }

    }

    public static int check(int[][] map) {
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (map[i][j] == 0) {
                    cnt++;
                }
            }
        }
        return cnt;
    }


    public static class CCTV {

        int x, y;
        int num;

        public CCTV(int x, int y, int num) {
            this.x = x;
            this.y = y;
            this.num = num;
        }
    }

}