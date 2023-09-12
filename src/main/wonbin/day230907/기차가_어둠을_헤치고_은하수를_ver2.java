package main.wonbin.day230907;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

/*
    [시뮬레이션]_백준_15787_기차가 어둠을 헤치고 은하수를

    기차를 배열에 이진수값으로 저장하였다. 리스트로 만든 것보다
    메모리는 3배 적게 사용했고, 시간도 반으로 줄었다.

 */
public class 기차가_어둠을_헤치고_은하수를_ver2 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        // 기차를 이진수 값으로 저장할 배열
        int trains[] = new int[N];

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            // 명령어
            int order = Integer.parseInt(st.nextToken());
            // 기차 넘버
            int numberOfTrain = Integer.parseInt(st.nextToken()) - 1;

            // 각 명령어 이행
            if (order == 1) {
                int target = Integer.parseInt(st.nextToken()) - 1;
                trains[numberOfTrain] |= (1 << target);
            } else if (order == 2) {
                int target = Integer.parseInt(st.nextToken()) - 1;
                trains[numberOfTrain] &= ~(1 << target);
            } else if (order == 3) {
                // 마지막 좌석이 차있는 경우 삭제
                if ((trains[numberOfTrain] & (1 << 19)) != 0)
                    trains[numberOfTrain] &= ~(1 << 19);
                trains[numberOfTrain] <<= 1;
            } else {
                // 첫 좌석이 차있는 경우 삭제
                if ((trains[numberOfTrain] & 1) != 0)
                    trains[numberOfTrain] &= ~1;
                trains[numberOfTrain] >>= 1;
            }
        }

        // 중복 체크
        Set<Integer> set = new HashSet<>();
        int result = 0;

        for (int i = 0; i < N; i++) {
            if (!set.contains(trains[i])) {
                result++;
            }
            set.add(trains[i]);
        }

        System.out.println(result);
        br.close();
    }
}