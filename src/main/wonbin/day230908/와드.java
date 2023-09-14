package main.wonbin.day230908;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
    [시뮬레이션]_백준_23747_와드

    시야가 보이는 곳을 탐색하는 문제로 BFS로 구현하였다.
    처음에 같은 알파벳이 인접해있지 않아도 같은 구역이라 생각하고 구현하였는데
    다른 구역이었다. 문제를 잘 읽자.

 */

public class 와드 {
    static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static int row, col;
    // 순서대로 상/하/좌/우/가운데
    static int dx[] = {-1, 1, 0, 0, 0};
    static int dy[] = {0, 0, -1, 1, 0};
    static char map[][];
    static boolean visiblePoint[][];
    static Queue<Point> q = new LinkedList<>();
    static Queue<Point> points = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());

        row = Integer.parseInt(st.nextToken());
        col = Integer.parseInt(st.nextToken());

        // 구역을 저장할 배열
        map = new char[row][col];
        // 시야가 보이는 곳을 저장할 배열
        visiblePoint = new boolean[row][col];

        for (int i = 0; i < row; i++) {
            String temp = br.readLine();

            for (int j = 0; j < col; j++) {
                map[i][j] = temp.charAt(j);
            }
        }

        st = new StringTokenizer(br.readLine());
        // 시작 인덱스
        int x = Integer.parseInt(st.nextToken()) - 1;
        int y = Integer.parseInt(st.nextToken()) - 1;

        String way = br.readLine();

        warding(x, y, way);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (visiblePoint[i][j]) {
                    sb.append('.');
                } else
                    sb.append('#');
            }
            sb.append("\n");
        }

        System.out.println(sb);
        br.close();
    }

    // 와드를 설치한 곳과 같은 구역을 돌아다니며 시야를 밝혀주는 메소드
    private static void bfs(int x, int y, char alpa) {

        points.add(new Point(x, y));
        visiblePoint[x][y] = true;

        // 사방 탐색을 하면서 와드가 설치된 곳과 같은 구역 찾기
        while (!points.isEmpty()) {
            Point now = points.poll();

            for (int i = 0; i < 4; i++) {
                int nx = now.x + dx[i];
                int ny = now.y + dy[i];

                if (nx < 0 || ny < 0 || nx >= row || ny >= col)
                    continue;
                if (visiblePoint[nx][ny])
                    continue;
                if (map[nx][ny] != alpa)
                    continue;

                visiblePoint[nx][ny] = true;
                points.add(new Point(nx, ny));
            }
        }
    }

    // 돌아다니며 와드를 박는 메소드
    private static void warding(int startX, int startY, String way) {
        q.add(new Point(startX, startY));

        int index = 0;
        while (index < way.length()) {

            Point now = q.poll();

            // 다음에 움직일 방향
            char dir = way.charAt(index);
            int intDir = 4;
            switch (dir) {
                case 'W':
                    // 방향이 와드면 같은 구역에 와드를 설치함
                    if (!visiblePoint[now.x][now.y])
                        bfs(now.x, now.y, map[now.x][now.y]);
                    break;
                case 'U':
                    intDir = 0;
                    break;
                case 'D':
                    intDir = 1;
                    break;
                case 'L':
                    intDir = 2;
                    break;
                case 'R':
                    intDir = 3;
                    break;

            }
            // 다음 좌표를 큐에 넣고 갱신해줌.
            q.add(new Point(now.x + dx[intDir], now.y + dy[intDir]));

            index++;

        }

        // 마지막 위치에서는 상/하/좌/우/가운데 방향을 볼 수 있게 처리
        Point lastPoint = q.poll();
        for (int i = 0; i < 5; i++) {
            int nx = lastPoint.x + dx[i];
            int ny = lastPoint.y + dy[i];

            if (nx < 0 || ny < 0 || nx >= row || ny >= col)
                continue;
            visiblePoint[nx][ny] = true;
        }
    }
}
