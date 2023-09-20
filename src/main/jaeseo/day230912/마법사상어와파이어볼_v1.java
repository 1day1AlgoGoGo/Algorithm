package main.jaeseo.day230912;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
참고(https://dlee0129.tistory.com/217)

파이어볼 정보들을 관리하는 연결리스트와 파이어볼이 이동했을때 겹치는 부분을 손쉽게 확인하고 관리하기 위해 큐 배열을 활용.
파이어볼을 이동할때 값 처리가 중요 -> (N + (fireball.r + dr[fireball.d] * (fireball.s % N))) % N;


 */
public class 마법사상어와파이어볼_v1 {
    static int N, M, K, sumM, sumS, countFireball, dividedD[];
    static int[] dr = new int[]{-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] dc = new int[]{0, 1, 1, 1, 0, -1, -1, -1};
    // 파이어볼 정보들을 관리하는 연결리스트
    static LinkedList<Fireball> fireballs = new LinkedList<>();
    // 파이어볼이 이동했을때 겹치는 부분을 손쉽게 확인하고 관리하기 위해 큐 배열을 활용
    static Queue<Fireball>[][] map;

    public static void main(String[] args) throws IOException {
        init();

        for (int i = 0; i < K; i++) {
            moveFireballs();

            combineAndDivideFireballs();
        }

        printM();
    }

    private static void moveFireballs() {
        for (Fireball fireball : fireballs) {
            // nextR / nextC로 굳이 새로운 변수 추가할 필요 없이 바로 이동시킨 위치 저장하기 (어차피 배열 범위를 벗어나는 경우가 없기 때문)
            // (fireball.s % N)를 해줘야 indexOutOfBound 에러가 안남...
            fireball.r = (N + (fireball.r + dr[fireball.d] * (fireball.s % N))) % N;  // (N을 더해주는 이유는, fireball.r = 0일때, 이동 후 -1로 되는 경우가 있는데, N을 더해줌으로써 자동으로 N-1 위치로 가게 할 수 있음
            fireball.c = (N + (fireball.c + dc[fireball.d] * (fireball.s % N))) % N;

            map[fireball.r][fireball.c].add(fireball);
        }
    }

    private static void combineAndDivideFireballs() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {

                // 특정 위치에 파이어볼이 2개 이상이라면, (겹쳐져있다면)
                if (map[i][j].size() >= 2) {
                    // 겹쳐진 파이어볼을 먼저 병합시키기
                    combine(i, j);

                    // 파이어볼 4개로 나누기
                    divide(i, j);
                } else {
                    // 파이어볼이 겹쳐져있지 않다면 (더이상 큐 배열에서 관리할 필요가 없음. 큐 배열은 겹쳐져있는 파이어볼을 쉽게 나누기 위해 사용하는거기때문)
                    map[i][j].clear();
                }
            }
        }

    }

    private static void combine(int r, int c) {
        sumM = 0;
        sumS = 0;
        countFireball = map[r][c].size();
        boolean isOdd = false, isEven = false;

        while (!map[r][c].isEmpty()) {
            Fireball fireball = map[r][c].poll();

            sumM += fireball.m;
            sumS += fireball.s;

            if (fireball.d % 2 == 0) {
                isEven = true;
            } else {
                isOdd = true;
            }

            // 객체 자체로 검색해서 삭제하기 !!!!
            fireballs.remove(fireball);
        }

        if ((isEven && !isOdd) || (!isEven && isOdd)) {
            dividedD = new int[]{0, 2, 4, 6};
        }else{
            dividedD = new int[]{1, 3, 5, 7};
        }

    }

    private static void divide(int r, int c) {
        // 질량이 0이면, 그대로 소멸
        if (sumM / 5 == 0) {
            return;
        }

        for (int i = 0; i < 4; i++) {
            fireballs.add(new Fireball(r, c, sumM / 5, sumS / countFireball, dividedD[i]));
        }
    }

    private static void printM() {
        int m = 0;
        for (Fireball fireball : fireballs) {
            m += fireball.m;
        }
        System.out.println(m);
    }


    private static void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        map = new Queue[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                map[i][j] = new LinkedList<>();
            }
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            int r = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken()) - 1;
            int m = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());

            // 파이어볼이 이동했을때 겹치는 부분을 손쉽게 확인하기 위해 큐 배열을 활용하고, 해당 위치에 파이어볼을 추가
            fireballs.add(new Fireball(r, c, m, s, d));
        }

    }

    static class Fireball {
        int r;
        int c;
        int m;
        int s;
        int d;

        Fireball(int r, int c, int m, int s, int d) {
            this.r = r;
            this.c = c;
            this.m = m;
            this.s = s;
            this.d = d;
        }
    }
}