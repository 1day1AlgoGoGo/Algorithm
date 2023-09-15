package BOJ_Silver.S1_3987;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class 보이저1호 {
    private static int time = 0;
    private static int dir = 0;
    private static boolean infinity = false;
    private static int[] dr = {-1, 0, 1, 0};
    private static int[] dc = {0, 1, 0, -1};
    private static final char[] DIRS = {'U', 'R', 'D', 'L'};
    private static final String VOYAGER = "Voyager";

    private static class Voyager {
        private int r;
        private int c;
        private int d;
        private int count;

        public Voyager(int r, int c, int d, int count) {
            this.r = r;
            this.c = c;
            this.d = d;
            this.count = count;
        }

        public int getR() {
            return r;
        }

        public int getC() {
            return c;
        }

        public int getD() {
            return d;
        }

        public int getCount() {
            return count;
        }

        // 현재 탐사선 이동 메소드
        public void move(int nextR, int nextC) {
            r = nextR;
            c = nextC;
            count += 1;
        }

        // 현재 탐사선의 방향 바꾸는 메소드
        public void changeDir(Voyager voyager, char[][] grid, int nextR, int nextC) {
            int curD = voyager.getD(); // 현재 방향값

            // '/' 만났을 때 탐사선의 방향값 수정
            if (grid[nextR][nextC] == '/') {
                d = curD == 1 || curD == 3 ? (curD + 3) % 4 : curD + 1;
            // '\' 만났을 때 탐사선의 방향값 수정
            } else if (grid[nextR][nextC] == '\\') {
                d = curD == 1 || curD == 3 ? (curD + 1) % 4 : (curD + 3) % 4;
            }
        }
    }

    private static void sendSignal(char[][] grid, Voyager voyager) {
        // 탐사를 시작한 좌표와 방향값
        int r = voyager.getR();
        int c = voyager.getC();
        int d = voyager.getD();

        int nextR, nextC;
        while (true) {
            // 현재 탐사선의 방향값에 따라 다음 좌표값 구하기
            nextR = voyager.getR() + dr[voyager.getD()];
            nextC = voyager.getC() + dc[voyager.getD()];

            // 같은 위치에 같은 방향으로 또 도착하면 무한 전파
            if (nextR == r && nextC == c && voyager.getD() == d) {
                dir = d;
                infinity = true;
                return;
            }

            // 범위를 벗어나거나 블랙홀에 빠지면
            if (!isAvailable(grid, nextR, nextC) || grid[nextR][nextC] == 'C') {
                // 최대 시간인 경우 시간과 방향값 저장
                if (time < voyager.getCount()) {
                    time = voyager.getCount();
                    dir = d;
                }

                return;
            }

            // 방향 체크
            voyager.changeDir(voyager, grid, nextR, nextC);
            // 탐사선 이동
            voyager.move(nextR, nextC);
        }
    }

    // 범위 체크 메소드
    private static boolean isAvailable(char[][] grid, int r, int c) {
        return r >= 0 && c >= 0 && r < grid.length && c < grid[r].length;
    }

    private static String print() {
        StringBuilder builder = new StringBuilder();
        builder.append(DIRS[dir]).append("\n");
        if (infinity) {
            builder.append(VOYAGER);
        } else {
            builder.append(time);
        }

        return builder.toString();
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        char[][] grid = new char[N][M];
        for (int i = 0; i < N; i++) {
            grid[i] = in.readLine().toCharArray();
        }

        st = new StringTokenizer(in.readLine());
        int PR = Integer.parseInt(st.nextToken()) - 1;
        int PC = Integer.parseInt(st.nextToken()) - 1;
        for (int d = 0; d < 4; d++) {
            // 무한 전파가 있으면 신호 보내지 않음
            if (!infinity) {
                sendSignal(grid, new Voyager(PR, PC, d, 1));
            }
        }

        System.out.println(print());
    }
}
