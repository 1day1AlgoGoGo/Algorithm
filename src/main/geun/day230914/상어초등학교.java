package BOJ_Gold.G5_21608;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class 상어초등학교 {
    private static int N;
    private static int[] students;
    private static int[][] grid;
    private static Seat[] list;
    private static Map<Integer, Set<Integer>> map;

    private static final int[] dr = {-1, 0, 1, 0};
    private static final int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        init();
        solve();
        print();
    }

    private static void init() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(in.readLine());
        students = new int[N * N];
        grid = new int[N][N];
        list = new Seat[N * N + 1];
        map = new HashMap<>();

        StringTokenizer st;
        int studentNumber;
        for (int i = 0; i < N * N; i++) {
            st = new StringTokenizer(in.readLine());
            studentNumber = Integer.parseInt(st.nextToken());
            students[i] = studentNumber;

            map.put(studentNumber, new HashSet<>());
            for (int j = 0; j < 4; j++) {
                map.get(studentNumber).add(Integer.parseInt(st.nextToken()));
            }
        }
    }

    private static void solve() {
        for (int student : students) {
            findBestSeat(student);
        }
    }


    // 조건에 가장 적합한 자리를 배정하는 메소드
    private static void findBestSeat(int studentNumber) {
        Seat seat = null;
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                if (grid[r][c] != 0) {
                    continue;
                }

                Seat temp = setSeat(r, c, studentNumber);
                if (seat == null) {
                    seat = temp;
                    continue;
                }

                if (seat.compareTo(temp) > 0) {
                    seat = temp;
                }
            }
        }

        // 해당 자리에 현재 학생 번호 저장
        grid[seat.getR()][seat.getC()] = studentNumber;
        list[studentNumber] = seat;
    }

    // 인접한 빈 칸의 개수와 좋아하는 학생 수를 저장한 자리 객체 생성하는 메소드
    private static Seat setSeat(int r, int c, int studentNumber) {
        int likeCount = 0;
        int emptyCount = 0;

        int nextR, nextC;
        for (int d = 0; d < 4; d++) {
            nextR = r + dr[d];
            nextC = c + dc[d];

            // 범위 체크
            if (isNotAvailable(nextR, nextC)) {
                continue;
            }

            // 인접한 칸에 좋아하는 학생 번호가 있으면 카운트
            if (map.get(studentNumber).contains(grid[nextR][nextC])) {
                likeCount++;
            }

            // 인접한 칸이 빈 칸이면 개수 카운트
            if (grid[nextR][nextC] == 0) {
                emptyCount++;
            }
        }

        return new Seat(r, c, likeCount, emptyCount);
    }

    // 범위 바깥인 경우 체크
    private static boolean isNotAvailable(int r, int c) {
        return r < 0 || c < 0 || r >= N || c >= N;
    }

    private static void print() {
        int answer = 0;

        for (int student : students) {
            Seat seat = list[student];

            int count = 0;
            for (int likeStudent : map.get(student)) {
                Seat likeStudentSeat = list[likeStudent];
                if (isAdjacent(seat.getR(), seat.getC(), likeStudentSeat.getR(), likeStudentSeat.getC())) {
                    count++;
                }
            }

            if (count > 0) {
                answer += (int) Math.pow(10, --count);
            }
        }

        System.out.println(answer);
    }

    // 인접한지 체크하는 메소드
    private static boolean isAdjacent(int r1, int c1, int r2, int c2) {
        return Math.abs(r1 - r2) + Math.abs(c1 - c2) == 1;
    }

    // 현재 위치(r, c)와 인접한 칸의 좋아하는 학생 수와 빈 칸의 수를 저장하는 자리 클래스
    private static class Seat implements Comparable<Seat> {
        private final int r;
        private final int c;
        private final int likeCount;
        private final int emptyCount;

        public Seat(int r, int c, int likeCount, int emptyCount) {
            this.r = r;
            this.c = c;
            this.likeCount = likeCount;
            this.emptyCount = emptyCount;
        }

        public int getR() {
            return r;
        }

        public int getC() {
            return c;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public int getEmptyCount() {
            return emptyCount;
        }

        @Override
        public int compareTo(Seat o) {
            if (likeCount != o.getLikeCount()) {
                return o.getLikeCount() - likeCount;
            }

            if (emptyCount != o.getEmptyCount()) {
                return o.getEmptyCount() - emptyCount;
            }

            if (r != o.getR()) {
                return r - o.getR();
            }

            return c - o.getC();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Seat seat = (Seat) o;
            return r == seat.r && c == seat.c && likeCount == seat.likeCount && emptyCount == seat.emptyCount;
        }

        @Override
        public int hashCode() {
            return Objects.hash(r, c, likeCount, emptyCount);
        }
    }
}
