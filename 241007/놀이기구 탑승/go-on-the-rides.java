import java.io.*;
import java.util.*;

public class Main {

    // N, 만족도 총 합
    static int n, sum;
    // 학생 배열, 상 우 하 좌
    static int[] students;
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};
    // 학생들이 앉은 map
    static int[][] map;
    // 학생별 좋아하는 학생들
    static Map<Integer, Set<Integer>> preferences;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        map = new int[n][n];
        students = new int[n * n];
        preferences = new HashMap<>();
        sum = 0;

        for (int i = 0; i < n * n; i++) {
            st = new StringTokenizer(br.readLine());
            int student = Integer.parseInt(st.nextToken());
            // 학생 배열에 기록
            students[i] = student;
            // 학생별 좋아하는 학생들 기록
            preferences.put(student, new HashSet<>());
            for (int j = 0; j < 4; j++) {
                preferences.get(student).add(Integer.parseInt(st.nextToken()));
            }
        }

        solution();

        System.out.println(sum);

        br.close();
    }

    public static void solution() {
        // 1. 학생들 자리 배치
        for (int i = 0; i < students.length; i++) {
            Seat seat = findSeat(students[i]);
            map[seat.x][seat.y] = students[i];
        }

        // 2. 점수 합산
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // 주변 학생 수에 따라 점수 합산
                int count = getStudentSum(i, j, map[i][j]);
                if (count > 0) {
                    sum += (int) Math.pow(10, count - 1);
                }
            }
        }

    }

    // 앉을 좌석 찾기
    public static Seat findSeat(int student) {
        Seat seat = null;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // 이미 자리에 누구 앉아있으면 skip
                if (map[i][j] > 0) {
                    continue;
                }
                // 현재 자리의 정보 (x y 좌표, 인접한 좋아하는 학생 수, 빈칸 수)
                Seat cur = new Seat(i, j, getStudentSum(i, j, student), getEmptySum(i, j));
                // 비교할 seat이 null이라면 초기화 후 skip
                if (seat == null) {
                    seat = cur;
                    continue;
                }
                // 이전 좌석과 현재 좌석 비교
                if (seat.compareTo(cur) > 0) {
                    seat = cur;
                }
            }
        }
        return seat;
    }

    // 인접한 좋아하는 학생 수
    public static int getStudentSum(int x, int y, int student) {
        int count = 0;

        for (int i = 0; i < 4; i++) {
            int nX = x + dx[i];
            int nY = y + dy[i];
            if (nX < 0 || nY < 0 || nX >= n || nY >= n) {
                continue;
            }
            // 인접한 학생이 좋아하는 학생에 포함되면 count 증가
            if (preferences.get(student).contains(map[nX][nY])) {
                count++;
            }
        }
        return count;

    }

    // 빈칸 수
    public static int getEmptySum(int x, int y) {

        int count = 0;
        for (int i = 0; i < 4; i++) {
            int nX = x + dx[i];
            int nY = y + dy[i];
            if (nX < 0 || nY < 0 || nX >= n || nY >= n) {
                continue;
            }
            if (map[nX][nY] == 0) {
                count ++;
            }
        }
        return count;
    }


    public static class Seat implements Comparable<Seat> {

        int x, y;
        // 주변 좋아하는 학생 수, 주변 빈칸 수
        int studentSum, emptySum;


        public Seat(int x, int y, int studentSum, int emptySum) {

            this.x = x;
            this.y = y;
            this.studentSum = studentSum;
            this.emptySum = emptySum;
        }

        @Override
        public int compareTo(Seat other) {
            if (other.studentSum == this.studentSum) {
                if (other.emptySum == this.emptySum) {
                    if (other.x == this.x) {
                        return this.y - other.y;
                    }
                    return this.x - other.x;
                }
                return other.emptySum - this.emptySum;
            }
            return other.studentSum - this.studentSum;
        }
    }

}