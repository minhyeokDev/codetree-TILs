import java.io.*;
import java.util.*;

public class Main {

    static int n, m, h, k, answer;
    static int[] dx = {-1, 0, 1, 0}; // 상 우 하 좌
    static int[] dy = {0, 1, 0, -1};
    static Police police;
    static boolean[][] visited;
    static int[][] treeMap;
    static ArrayList<Integer>[][] runnerMap;
    static int cnt, line;
    static boolean inToOut;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        h = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        visited = new boolean[n][n];
        police = new Police(n / 2, n / 2, 0);
        treeMap = new int[n][n];
        runnerMap = new ArrayList[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                runnerMap[i][j] = new ArrayList<>();
            }
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;
            int dir = Integer.parseInt(st.nextToken());
            runnerMap[x][y].add(dir);
        }

        for (int i = 0; i < h; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;
            treeMap[x][y] = 1; // 나무 위치 1
        }
        inToOut = true;
        cnt = 1;
        line = 1;

        for (int i = 1; i <= k; i++) {
            moveRunner();
            movePolice();
            catchRunner(i);
        }

        System.out.println(answer);

        br.close();
    }

    public static void moveRunner() {
        ArrayList<Integer>[][] tmp = new ArrayList[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tmp[i][j] = new ArrayList<>();
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (runnerMap[i][j].size() > 0 && checkDistance(new Pair(i, j))) {
                    for (int curDir : runnerMap[i][j]) {
                        int nX = i + dx[curDir];
                        int nY = j + dy[curDir];

                        if (nX < 0 || nY < 0 || nX >= n || nY >= n) {
                            curDir = (curDir + 2) % 4;
                            nX = i + dx[curDir];
                            nY = j + dy[curDir];
                        }

                        if (nY == police.y && nX == police.x) {
                            tmp[i][j].add(curDir);
                        } else {
                            tmp[nX][nY].add(curDir);
                        }
                    }
                } else {
                    for (int curDir : runnerMap[i][j]) {
                        tmp[i][j].add(curDir);
                    }
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                runnerMap[i][j].clear();
                for (int dir : tmp[i][j]) {
                    runnerMap[i][j].add(dir);
                }
            }
        }

    }

    public static boolean checkDistance(Pair p) {
        int dx = Math.abs(p.x - police.x);
        int dy = Math.abs(p.y - police.y);
        if (dx + dy > 3) {
            return false;
        } else {
            return true;
        }
    }

    public static void movePolice() {
        if (inToOut) {
            moveInToOut();
        } else {
            moveOutToIn();
        }
    }

    public static void moveInToOut() {
        int nX = police.x + dx[police.dir];
        int nY = police.y + dy[police.dir];

        police.x = nX;
        police.y = nY;
        cnt--;

        if (police.y == 0 && police.x == 0) {
            visited = new boolean[n][n];
            visited[0][0] = true;
            inToOut = false;
            police.dir = (police.dir + 2) % 4;
            return;
        }
        if (cnt == 0) {
            line++;
            police.dir = (police.dir + 1) % 4;
            cnt = (line / 2) + (line % 2);
        }

    }

    public static void moveOutToIn() {
        int nX = police.x + dx[police.dir];
        int nY = police.y + dy[police.dir];

        police.y = nY;
        police.x = nX;
        visited[police.x][police.y] = true;

        if (police.x == n / 2 && police.y == n / 2) {  // 중앙 도달 시 이동 방향 전환
            inToOut = true;
            cnt = 1;
            line = 1;
            police.dir = (police.dir + 2) % 4;
            return;
        }

        nX += dx[police.dir];
        nY += dy[police.dir];

        if (nX < 0 || nY < 0 || nX >= n || nY >= n || visited[nX][nY]) {
            police.dir = (police.dir + 3) % 4;  // 방향만 전환
        }

    }

    public static void catchRunner(int t) {

        for (int i = 0; i < 3; i++) {
            int targetX = police.x + i * dx[police.dir];
            int targetY = police.y + i * dy[police.dir];

            if (targetX < 0 || targetY < 0 || targetX >= n || targetY >= n) {
                return;
            }
            if (treeMap[targetX][targetY] == 0 && runnerMap[targetX][targetY].size() > 0) {
                answer += t * runnerMap[targetX][targetY].size();
                runnerMap[targetX][targetY].clear();
            }
        }
    }

    public static class Pair {

        int x, y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static class Police {

        int x, y;
        int dir;

        public Police(int x, int y, int dir) {
            this.x = x;
            this.y = y;
            this.dir = dir;
        }
    }

}