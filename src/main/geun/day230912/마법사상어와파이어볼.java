package BOJ_Gold.G4_20056;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class 마법사상어와파이어볼 {
    private static final int[] dr = {-1, -1, 0, 1, 1, 1, 0, -1};
    private static final int[] dc = {0, 1, 1, 1, 0, -1, -1, -1};

    private static class Fireball {
        private int r;
        private int c;
        private int m;
        private int s;
        private int d;

        public Fireball(int r, int c, int m, int s, int d) {
            this.r = r;
            this.c = c;
            this.m = m;
            this.s = s;
            this.d = d;
        }

        public int getR() {
            return r;
        }

        public int getC() {
            return c;
        }

        public int getM() {
            return m;
        }

        public int getS() {
            return s;
        }

        public int getD() {
            return d;
        }

        // 파이어볼 이동
        public void move(int[][] map) {
            int nextR = r + dr[d] * s;
            int nextC = c + dc[d] * s;
            r = ((nextR + map.length) % map.length + map.length) % map.length;
            c = ((nextC + map.length) % map.length + map.length) % map.length;
        }

        @Override
        public String toString() {
            return "Fireball{" +
                    "r=" + r +
                    ", c=" + c +
                    ", m=" + m +
                    ", s=" + s +
                    ", d=" + d +
                    '}';
        }
    }

    // 파이어볼 이동하는 메소드
    private static void moveFireball(List<Fireball> list, int[][] map) {
        for (Fireball fireball : list) {
            map[fireball.getR()][fireball.getC()]--; // 현재위치 개수 감소
            fireball.move(map); // 이동
            map[fireball.getR()][fireball.getC()]++; // 이동 후 위치 개수 증가
        }
    }

    // 파이어볼 나누는 메소드
    private static void divideFireball(int N, int[][] map, List<Fireball> list) {
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                if (map[r][c] >= 2) {
                    int m = 0;
                    int s = 0;
                    boolean evenCheck = false; // 짝수 체크
                    boolean oddCheck = false; // 홀수 체크

                    int idx = 0;
                    while (idx < list.size()) {
                        Fireball fireball = list.get(idx);

                        // 해당 위치의 파이어볼인 경우 질량, 속도 계산, 방향 홀/짝 체크
                        if (fireball.getR() == r && fireball.getC() == c) {
                            m += fireball.getM();
                            s += fireball.getS();
                            if (fireball.getD() % 2 == 0) {
                                evenCheck = true;
                            } else {
                                oddCheck = true;
                            }

                            list.remove(idx);
                        } else {
                            idx++;
                        }
                    }

                    m /= 5;
                    // 총 질량에서 5를 나눴을 때 0이 되면 소멸. 현재 위치 개수도 0으로 초기화
                    if (m == 0) {
                        map[r][c] = 0;
                        continue;
                    }

                    s /= map[r][c];
                    int d = !evenCheck || !oddCheck ? 0 : 1;
                    for (int i = 0; i < 4; i++, d += 2) {
                        list.add(new Fireball(r, c, m, s, d));
                    }

                    // 4개로 나눠졌으니 현재 위치의 파이어볼 개수값 증가
                    map[r][c] = 4;
                }
            }
        }
    }

    // 남아있는 파이어볼의 질량 구하는 메소드
    private static int getRemainM(List<Fireball> list) {
        int remainM = 0;
        for (Fireball fireball : list) {
            remainM += fireball.getM();
        }

        return remainM;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int[][] map = new int[N][N]; // 파이어볼 개수 관리용 배열
        List<Fireball> list = new ArrayList<>(); // 파이어볼 저장 리스트
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(in.readLine());
            int r = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken()) - 1;
            int m = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());

            list.add(new Fireball(r, c, m, s, d));
            map[r][c]++;
        }

        while (K-- > 0) {
            // 파이어볼 이동
            moveFireball(list, map);
            // 파이어볼 나누기 (2개 이상인 경우만)
            divideFireball(N, map, list);
        }

        System.out.println(getRemainM(list));
    }
}