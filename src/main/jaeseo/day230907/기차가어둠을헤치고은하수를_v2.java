package main.jaeseo.day230907;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

public class 기차가어둠을헤치고은하수를_v2 {
    static BufferedReader br;
    static StringTokenizer st;
    static int N, M;
    static int[] train;

    public static void main(String[] args) throws IOException {
        init();

        commandToTrain();

        crossTheMilkyWay();
    }

    private static void init() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        train = new int[N];
    }

    private static void commandToTrain() throws IOException {
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            int command = Integer.parseInt(st.nextToken());
            int trainNumber = Integer.parseInt(st.nextToken()) - 1;

            if (command == 1) {
                int seatNumber = Integer.parseInt(st.nextToken()) - 1;

                // 기차 특정 좌석에 사람 태우기
                train[trainNumber] |= 1 << seatNumber;
            } else if (command == 2) {
                int seatNumber = Integer.parseInt(st.nextToken()) - 1;

                // 기차 특정 좌석에 사람 내리기
                train[trainNumber] &= ~(1 << seatNumber);
            } else if (command == 3) {
                //한칸씩 뒤로가기

                train[trainNumber] = (train[trainNumber] & ~(1 << 19)) << 1;
            } else if (command == 4) {
                //한칸씩 앞으로 가기

                train[trainNumber] = (train[trainNumber] & ~1) >> 1;
            }
        }
    }

    private static void crossTheMilkyWay() {
        HashSet<Integer> set = new HashSet<>();

        for (int i = 0; i < N; i++) {
            set.add(train[i]);
        }
        System.out.println(set.size());
    }

}
