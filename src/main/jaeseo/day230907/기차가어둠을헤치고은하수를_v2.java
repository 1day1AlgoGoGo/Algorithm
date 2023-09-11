package main.jaeseo.day230907;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

/*
v1의 디버깅을 완료하지못해서 구글링하여 풀이를 보고 푼 내용.
출처 - https://velog.io/@gandi0330/%EC%95%8C%EA%B3%A0%EB%A6%AC%EC%A6%98-Java-%EB%B0%B1%EC%A4%80-%EA%B8%B0%EC%B0%A8%EA%B0%80-%EC%96%B4%EB%91%A0%EC%9D%84-%ED%97%A4%EC%B9%98%EA%B3%A0-%EC%9D%80%ED%95%98%EC%88%98%EB%A5%BC-15787

기차는 배열로, 좌석은 비트마스킹으로 구현하였다.
또한, 은하수를 건넌 기차의 중복을 확인하기위해 set을 사용하였다.

진짜 이 풀이 보고 감탄함..
비트마스킹이 true/false 형태를 관리하는 최고의 자료구조 중 하나라는 점을 깨달았다.
그리고 중복 관리가 나오면 set을 꼭 떠올리자..
 */
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
