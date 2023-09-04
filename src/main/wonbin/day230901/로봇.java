package main.wonbin.day230901;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/*
    [시뮬레이션] 백준_13901_로봇

    이 문제의 관건은 로봇이 움직이지 못하는 시점을 어떻게 찾느냐 인거 같다.
    주어진 이동 방향을 모두 소진하면 초기 방향으로 돌아가야 해서 무한 반복문으로 설계하였다.
    한 방향으로 이동이 끝나면 사방 탐색으로 로봇의 상하좌우 좌표를 검사한다.
    장애물이 있는 곳이 총 4군데라면 해당 좌표를 반환하고 반복문을 끝낸다.

 */

public class 로봇 {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());

        int row = Integer.parseInt(st.nextToken());
        int col = Integer.parseInt(st.nextToken());

        int numOfObstacle = Integer.parseInt(br.readLine());

        int map[][] = new int[row][col];

        for (int i = 0; i < numOfObstacle; i++) {
            st = new StringTokenizer(br.readLine());

            map[Integer.parseInt(st.nextToken())][Integer.parseInt(st.nextToken())] = -1;
        }

        st = new StringTokenizer(br.readLine());
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());

        map[x][y] = 1;

        st = new StringTokenizer(br.readLine());

        ArrayList<Integer> list = new ArrayList<>();
        while (st.hasMoreTokens()) {
            list.add(Integer.parseInt(st.nextToken()) - 1);
        }

        int dx[] = {-1, 1, 0, 0};
        int dy[] = {0, 0, -1, 1};

        int sx = x;
        int sy = y;

        int cnt = 0;
        while (true) {
            int now = list.get(cnt);
            cnt = (cnt + 1) % list.size();

            while (true) {
                int nx = sx + dx[now];
                int ny = sy + dy[now];

                if (nx < 0 || ny < 0 || nx > row - 1 || ny > col - 1)
                    break;
                if (map[nx][ny] != 0)
                    break;

                map[nx][ny] = map[sx][sy] + 1;
                sx = nx;
                sy = ny;

            }

            int limit = 0;
            for (int i = 0; i < 4; i++) {
                int nx = sx + dx[i];
                int ny = sy + dy[i];

                if (nx < 0 || ny < 0 || nx > row - 1 || ny > col - 1) {
                    limit++;
                    continue;
                }
                if (map[nx][ny] != 0) {
                    limit++;
                }
            }
            if (limit == 4)
                break;

        }

        System.out.println(sx + " " + sy);

        br.close();
    }
}
