import java.io.*;
import java.util.*;

public class Main {

    static int n, L, R;
    static int[][] map;
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};
    static List<Pair> list;
    static int count;
    static boolean[][] visited;
    static boolean isMove = false;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());
        map = new int[n][n];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        check();
        System.out.println(count);
        br.close();

    }

    public static void check() {
        while (true) {
            isMove = false;
            visited = new boolean[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (!visited[i][j]) {
                        bfs(i, j);
                    }
                }
            }

            if (!isMove) {
                break;
            } else {
                count++;
            }
        }

    }

    public static void bfs(int x, int y) {
        Queue<Pair> q = new LinkedList<>();
        list = new ArrayList<>();
        q.offer(new Pair(x, y));
        list.add(new Pair(x, y));
        visited[x][y] = true;

        while (!q.isEmpty()) {
            Pair p = q.poll();
            for (int i = 0; i < 4; i++) {
                int nX = p.x + dx[i];
                int nY = p.y + dy[i];

                if (nX >= 0 && nY >= 0 && nX < n && nY < n && !visited[nX][nY]) {
                    int temp = Math.abs(map[p.x][p.y] - map[nX][nY]);
                    if (temp >= L && temp <= R) {
                        isMove = true;
                        q.offer(new Pair(nX, nY));
                        list.add(new Pair(nX, nY));
                        visited[nX][nY] = true;
                    }
                }
            }
        }

        int sum = 0;
        for (int i = 0; i < list.size(); i++) {
            Pair p = list.get(i);
            sum += map[p.x][p.y];
        }
        for (int i = 0; i < list.size(); i++) {
            Pair p = list.get(i);
            map[p.x][p.y] = sum / list.size();
        }
        
    }

    public static void changePopulation(int sum) {

        int avg = sum / list.size();

        for (Pair p : list) {
            map[p.x][p.y] = avg;
        }
    }

    public static class Pair {

        int x, y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

}