package BOJ_Gold.G5_23747;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class 와드 {
    private static int[] dr = {-1, 1, 0, 0};
    private static int[] dc = {0, 0, -1, 1};

    private static class Coordinate {
        private int r;
        private int c;

        public Coordinate(int r, int c) {
            this.r = r;
            this.c = c;
        }

        public int getR() {
            return r;
        }

        public int getC() {
            return c;
        }

        public void move(char dir) {
            switch (dir) {
                case 'U':
                    this.r -= 1;
                    break;
                case 'D':
                    this.r += 1;
                    break;
                case 'L':
                    this.c -= 1;
                    break;
                case 'R':
                    this.c += 1;
                    break;
                default:
                    break;
            }
        }
    }

    // 같은 영역에 속해 있는지 확인하기 위한 탐색
    private static void bfs(char[][] map, char[][] answer, boolean[][] visited, Coordinate start) {
        Queue<Coordinate> queue = new LinkedList<>();
        queue.add(start);
        visited[start.getR()][start.getC()] = true;
        answer[start.getR()][start.getC()] = '.';

        Coordinate cur;
        int curR, curC, nextR, nextC;
        while (!queue.isEmpty()) {
            cur = queue.poll();
            curR = cur.getR();
            curC = cur.getC();

            for (int d = 0; d < 4; d++) {
                nextR = curR + dr[d];
                nextC = curC + dc[d];

                // 배열 범위 바깥인 경우 or 이미 시야가 밝혀진 위치인 경우 or 방문했던 좌표인 경우 PASS
                if (!isAvailable(nextR, nextC, map) || answer[nextR][nextC] == '.' || visited[nextR][nextC]) {
                    continue;
                }

                // 같은 문자를 가진 영역인 경우 시야 체크
                if (map[curR][curC] == map[nextR][nextC]) {
                    answer[nextR][nextC] = '.';
                    visited[nextR][nextC] = true;
                    queue.add(new Coordinate(nextR, nextC));
                }
            }
        }
    }

    // 마지막 위치에서 사방 시야 밝히기
    private static void arrive(char[][] answer, Coordinate coordinate) {
        answer[coordinate.getR()][coordinate.getC()] = '.';
        int nextR, nextC;
        for (int d = 0; d < 4; d++) {
            nextR = coordinate.getR() + dr[d];
            nextC = coordinate.getC() + dc[d];

            // 배열 범위 이내인 곳에 한해서 시야 밝히기
            if (isAvailable(nextR, nextC, answer)) {
                answer[nextR][nextC] = '.';
            }
        }
    }

    // 배열 범위 체크 메소드
    private static boolean isAvailable(int r, int c, char[][] map) {
        return r >= 0 && c >= 0 && r < map.length && c < map[r].length;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());
        int R = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());
        char[][] map = new char[R][C];
        char[][] answer = new char[R][C];
        boolean[][] visited = new boolean[R][C];
        for (int i = 0; i < R; i++) {
            map[i] = in.readLine().toCharArray();
            Arrays.fill(answer[i], '#');
        }

        st = new StringTokenizer(in.readLine());
        int HR = Integer.parseInt(st.nextToken()) - 1;  // 현재 좌표행
        int HC = Integer.parseInt(st.nextToken()) - 1;  // 현재 좌표열
        Coordinate coordinate = new Coordinate(HR, HC); // 현재 좌표

        char[] histories = in.readLine().toCharArray(); // 여행 기록
        for (char history : histories) {
            // 여행 기록이 와드일경우 시야 밝히기 위해 탐색
            if (history == 'W') {
                bfs(map, answer, visited, coordinate);
            // 그 외 U, D, R, L인 경우 현재 좌표 이동
            } else {
                coordinate.move(history);
            }
        }

        // 최종 위치에서 시야 밝히기
        arrive(answer, coordinate);

        // 출력
        StringBuilder builder = new StringBuilder();
        for (char[] arr : answer) {
            for (char c : arr) {
                builder.append(c);
            }

            builder.append("\n");
        }

        System.out.print(builder);
    }
}
