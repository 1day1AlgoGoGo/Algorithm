package main.jaeseo.day230912;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

/*
해당 문제에서 가장 중요한 부분은 파이어볼을 어떻게 관리할지 (어떤 자료구조를 사용해야할지) 라고 생각한다.
이 부분을 잘 처리하지못해서 이 풀이는 시간초과가 났다..

일단, 연결리스트를 사용해서 파이어볼을 관리하려했다.
파이어볼 객체에 r,c,m,s,d와 duplicatedCount(해당 위치에 겹쳐진 파이어볼 개수), duplicatedDirection(해당 위치에 겹쳐진 파이어볼의 방향정보)를 통해, 파이어볼이 겹쳐진 상황을 해결하려고 했다.
하지만, 연결리스트를 두개를 사용하고, 이 연결리스트들을 탐색하는 로직이 너무 많아서 시간초과가 났다.

그래서 구글링을 하였고, 연결리스트와 Queue[][]를 사용하여 관리하는 v2의 풀이로 다시 한번 풀어보았다.
 */

public class 마법사상어와파이어볼_v2 {
    static int N, M, K;
    static LinkedList<Fireball> fireballs1 = new LinkedList<>();
    static LinkedList<Fireball> fireballs2 = new LinkedList<>();
    static int[] dr;
    static int[] dc;

    public static void main(String[] args) throws IOException {
        init();

        for (int i = 0; i < K; i++) {
            moveFireballs();

            divideFireballs();
        }

        printM();
    }

    private static void printM() {
        int m = 0;
        if (fireballs1.size() > 0) {
            for (Fireball fireball : fireballs1) {
                m += fireball.m;
            }
        } else {
            for (Fireball fireball : fireballs2) {
                m += fireball.m;
            }
        }
        System.out.println(m);
    }

    private static void divideFireballs() {
        if (fireballs1.size() > 0) {
            for (int i = 0; i < fireballs1.size(); i++) {
                Fireball fireball = fireballs1.get(i);

                // 파이어볼이 겹쳐져있는 상태라면
                if (fireball.duplicatedCount > 1) {
                    int r = fireball.r;
                    int c = fireball.c;
                    int m = fireball.m / 5;
                    int s = fireball.s / fireball.duplicatedCount;
                    int[] d = generateDividedDirection(fireball);

                    fireballs1.remove(i);

                    // 질량이 0이 아니라면, (파이어볼 4개로 쪼개져야함)
                    if(m != 0){
                        for (int j = 0; j < 4; j++) {
                            fireballs1.addFirst(new Fireball(r, c, m, s, d[j]));
                        }
                    }else{
                        i--;
                    }
                }
            }
        } else {
            for (int i = 0; i < fireballs2.size(); i++) {
                Fireball fireball = fireballs2.get(i);

                if (fireball.duplicatedCount > 1) {
                    int r = fireball.r;
                    int c = fireball.c;
                    int m = fireball.m / 5;
                    int s = fireball.s / fireball.duplicatedCount;
                    int[] d = generateDividedDirection(fireball);

                    fireballs2.remove(i);
                    if(m != 0){
                        for (int j = 0; j < 4; j++) {
                            fireballs2.addFirst(new Fireball(r, c, m, s, d[j]));
                        }
                    }else{
                        i--;
                    }
                }
            }
        }
    }

    private static int[] generateDividedDirection(Fireball fireball) {
        boolean even = false;
        boolean odd = false;

        if (fireball.d % 2 == 0) {
            odd = true;
        } else {
            even = true;
        }

        for (int d : fireball.duplicatedDirection) {
            if (d % 2 == 0) {
                odd = true;
            } else {
                even = true;
            }
        }

        // 모두 짝수거나 모두 홀수면
        if ((even && !odd) || (!even && odd)) {
            return new int[]{0, 2, 4, 6};
        }

        return new int[]{1, 3, 5, 7};
    }

    private static void moveFireballs() {
        if (fireballs1.size() > 0) {
            for (int i = 0; i < fireballs1.size(); i++) {
                Fireball fireball = fireballs1.get(i);

                int nextR = (fireball.r + dr[fireball.d] * fireball.s) % N;
                int nextC = (fireball.c + dc[fireball.d] * fireball.s) % N;

                boolean isExisted = false;
                for (Fireball fireball2 : fireballs2) {
                    // 이동한 파이어볼이 존재하는 연결리스트에 이동할 위치의 파이어볼이 이미 존재한다면,
                    if (fireball2.r == nextR && fireball2.c == nextC) {
                        fireball2.m += fireball.m;
                        fireball2.s += fireball.s;
                        fireball2.duplicatedCount++;
                        fireball2.duplicatedDirection.add(fireball.d);
                        isExisted = true;
                    }
                }
                // 이동한 파이어볼이 존재하는 연결리스트에 이동할 위치의 파이어볼이 없다면,
                if (!isExisted) {
                    fireballs2.add(new Fireball(nextR, nextC, fireball.m, fireball.s, fireball.d));
                }
            }
            fireballs1.clear();
        } else {
            for (int i = 0; i < fireballs2.size(); i++) {
                Fireball fireball = fireballs2.get(i);

                int nextR = (fireball.r + dr[fireball.d] * fireball.s) % N;
                int nextC = (fireball.c + dc[fireball.d] * fireball.s) % N;

                boolean isExisted = false;
                for (Fireball fireball1 : fireballs1) {
                    // 이동한 파이어볼이 존재하는 연결리스트에 이동할 위치의 파이어볼이 이미 존재한다면,
                    if (fireball1.r == nextR && fireball1.c == nextC) {
                        fireball1.m += fireball.m;
                        fireball1.s += fireball.s;
                        fireball1.duplicatedCount++;
                        fireball1.duplicatedDirection.add(fireball.d);
                        isExisted = true;
                    }
                }
                // 이동한 파이어볼이 존재하는 연결리스트에 이동할 위치의 파이어볼이 없다면,
                if (!isExisted) {
                    fireballs1.add(new Fireball(nextR, nextC, fireball.m, fireball.s, fireball.d));
                }
            }
            fireballs2.clear();
        }
    }

    private static void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        dr = new int[]{N - 1, N - 1, 0, 1, 1, 1, 0, N - 1};
        dc = new int[]{0, 1, 1, 1, 0, N - 1, N - 1, N - 1};

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            int r = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken()) - 1;
            int m = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());

            fireballs1.add(new Fireball(r, c, m, s, d));
        }

    }

    static class Fireball {
        int r;
        int c;
        int m;
        int s;
        int d;
        // 같은 위치의 파이어볼 개수
        int duplicatedCount;
        LinkedList<Integer> duplicatedDirection;

        Fireball(int r, int c, int m, int s, int d) {
            this.r = r;
            this.c = c;
            this.m = m;
            this.s = s;
            this.d = d;
            this.duplicatedCount = 1;
            this.duplicatedDirection = new LinkedList<>();
        }
    }
}