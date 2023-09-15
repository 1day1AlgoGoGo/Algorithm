package main.wonbin.day230912;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
/*
    [시뮬레이션]_백준_20056_마법사 상어와 파이어볼

    각 파이어볼의 정보를 저장하는 것이 어려웠던 문제이다.
    Fireball class와 ArrayList를 사용하여 각 파이어볼의 정보를 저장했다.
    1번 인덱스와 N번 인덱스가 이어져 있어서 이동을 구현하는데 애를 먹었다.

 */
public class 마법사_상어와_파이어볼 {

    static class Fireball {
        int x, y, m, dir, speed;

        Fireball(int x, int y, int m, int speed, int dir) {
            this.x = x;
            this.y = y;
            this.m = m;
            this.speed = speed;
            this.dir = dir;
        }
    }

    static int N, M, K;
    static int dx[], dy[];
    static ArrayList<Fireball> fireballs = new ArrayList<>();
    static Queue<Fireball> dividedFourFireballs = new LinkedList<>();

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        // -1 방향이 N-1인 이유는 %N 계산을 하기 위함.
        dx = new int[]{N - 1, N - 1, 0, 1, 1, 1, 0, N - 1};
        dy = new int[]{0, 1, 1, 1, 0, N - 1, N - 1, N - 1};

        // 파이어볼의 위치 저장
        for (int z = 0; z < M; z++) {
            st = new StringTokenizer(br.readLine());
            fireballs.add(new Fireball(Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
        }

        moveFireball();

        System.out.println(sumOfM());

        br.close();
    }

    private static int sumOfM() {
        int result = 0;
        for (Fireball temp : fireballs) {
            result += temp.m;
        }
        return result;
    }

    private static void sumAndDivideFireball(int visited[][]) {

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {

                // 같은 위치의 파이어볼을 합치고 4개로 나누기
                if (visited[i][j] >= 2) {
                    int m = 0;
                    int s = 0;
                    int dir = -1;
                    boolean flag = true;

                    // 같은 위치의 파이어볼의 질량과 속력이 합쳐지고 방향을 계산
                    for (int h = 0; h < fireballs.size(); h++) {
                        Fireball temp = fireballs.get(h);
                        if (temp.x == i && temp.y == j) {
                            m += temp.m;
                            s += temp.speed;

                            // 방향 계산
                            if (dir == -1) {
                                dir = temp.dir % 2;
                            } else if (dir != temp.dir % 2) {
                                flag = false;
                            }
                            //계산한 현재 파이어볼을 삭제
                            fireballs.remove(h--);
                        }
                    }
                    // 파이어볼 4개로 나누기
                    m /= 5;
                    if (m == 0)
                        continue;
                    s /= visited[i][j];
                    int start = 0;
                    if (!flag) {
                        start = 1;
                    }
                    // 4개의 파이어볼을 큐에 넣기
                    for (int h = start; h < 8; h += 2) {
                        dividedFourFireballs.add(new Fireball(i, j, m, s, h));
                    }
                }
            }
        }
    }

    private static void moveFireball() {

        // K번만큼 이동
        while (K-- > 0) {

            // 이동한 파이어볼의 겹침을 파악하는 2차원 배열
            int visited[][] = new int[N][N];
            for (int i = 0; i < fireballs.size(); i++) {

                Fireball temp = fireballs.get(i);

                // 방향만큼 이동
                int nx = (temp.x + (dx[temp.dir] * temp.speed)) % N;
                int ny = (temp.y + (dy[temp.dir] * temp.speed)) % N;

                visited[nx][ny]++;
                fireballs.get(i).x = nx;
                fireballs.get(i).y = ny;
            }

            sumAndDivideFireball(visited);
            removeAndSaveFireball(visited);
        }
    }

    private static void removeAndSaveFireball(int visited[][]) {

        // 4개로 나누어진 파이어볼 제거
        for (int i = 0; i < fireballs.size(); i++) {
            if (visited[fireballs.get(i).x][fireballs.get(i).y] >= 2) {
                fireballs.remove(i);
                i--;
            }
        }
        // 4개로 나누어진 파이어볼 모두 저장
        while (!dividedFourFireballs.isEmpty()) {
            fireballs.add(dividedFourFireballs.poll());
        }

    }
}