import java.util.*;
import java.io.*;

public class Main {

    static int n, m;
    static int[][] map;
    static int answer;
    static List<Pair> people = new ArrayList<>();
    static List<Pair> hospital = new ArrayList<>();
    static boolean[] open;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        map = new int[n][n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == 1) {
                    people.add(new Pair(i, j));
                } else if (map[i][j] == 2) {
                    hospital.add(new Pair(i, j));
                }
            }
        }

        answer = Integer.MAX_VALUE;
        open = new boolean[hospital.size()];

        dfs(0, 0);
        System.out.println(answer);

        br.close();
    }

    public static void dfs(int start, int depth) {
        if (depth == m) {
            int total = 0;

            for (int i = 0; i < people.size(); i++) {
                int temp = Integer.MAX_VALUE;

                for (int j = 0; j < hospital.size(); j++) {
                    if (open[j]) {
                        int distance = Math.abs(hospital.get(j).x - people.get(i).x) + Math.abs(
                            hospital.get(j).y - people.get(i).y);
                        temp = Math.min(temp, distance);
                    }
                }
                total += temp;
            }

            answer = Math.min(total, answer);
            return;
        }

        for (int i = start; i < hospital.size(); i++) {
            open[i] = true;
            dfs(i + 1, depth + 1);
            open[i] = false;
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