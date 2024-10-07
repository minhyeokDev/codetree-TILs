import java.io.*;
import java.util.*;

public class Main {

    static int[][] map;
    static int n, m;

//    static int[] dx = {0, -1, -1, -1, 0, 1, 1, 1};
//    static int[] dy = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] dx = {0, -1, -1, -1, 0, 1, 1, 1}; //1번부터 8번까지 → ↗ ↑ ↖ ← ↙ ↓ ↘
    static int[] dy = {1, 1, 0, -1, -1, -1, 0, 1}; // ←, ↖, ↑, ↗, →, ↘, ↓, ↙
    static Queue<Pair> clouds = new LinkedList<>();
    static boolean[][] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        map = new int[n][n];
        visited = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        clouds.add(new Pair(n - 1, 0));
        clouds.add(new Pair(n - 1, 1));
        clouds.add(new Pair(n - 2, 0));
        clouds.add(new Pair(n - 2, 1));

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            // 이동 방향
            int d = Integer.parseInt(st.nextToken())-1;
            // 이동 칸수 1 ~ 8
            int s = Integer.parseInt(st.nextToken());

            moveCloud(d, s);
            removeCloudAndWaterCopy();
            makeCloud();
        }

        int sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sum += map[i][j];
            }
        }

        System.out.println(sum);

        br.close();
    }

    public static void moveCloud(int d, int s) {
        // (현재좌표 + ( 이동방향 값 + 배열크기) * 이동 회수 ) % 배열크기
        for (Pair p : clouds) {
//            p.x = (p.x + (dx[d] + n) * s) % n;
//            p.y = (p.y + (dy[d] + n) * s) % n;
            p.x = (n + p.x + dx[d] * (s % n)) % n;
            p.y = (n + p.y + dy[d] * (s % n)) % n;
            map[p.x][p.y]++;
        }
    }

    public static void removeCloudAndWaterCopy() {
        // 구름제거, 물복사
        while (!clouds.isEmpty()) {
            Pair p = clouds.poll();

            int count = 0;
            visited[p.x][p.y] = true;
            // 대각선 물 복사
            for (int i = 1; i <= 7; i += 2) {
                int nX = p.x + dx[i];
                int nY = p.y + dy[i];
                if (nX >= 0 && nY >= 0 && nX < n && nY < n) {
                    if (map[nX][nY] >= 1) {
                        count++;
                    }
                }
            }
            map[p.x][p.y] += count;
        }

    }

    public static void makeCloud() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // 구름이 사라진 칸이 아닐 경우 생성
                if (!visited[i][j]) {
                    if (map[i][j] >= 2) {
                        clouds.add(new Pair(i, j));
                        map[i][j] -= 2;
                    }
                }
            }
        }
        visited = new boolean[n][n];

    }

    public static class Pair {

        int x, y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }

}