package BOJ_Gold.G5_21315;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class 카드섞기 {
    private static void mix(int[] cards, int N, int preCount, int nowCount) {
        int[] card = new int[N]; // 임시 배열
        int idx = 0;

        for (int i = preCount - nowCount; i < preCount; i++) {
            card[idx++] = cards[i]; // 임시 배열에 카드 저장
            cards[i] = 0; // 저장한 카드는 0으로 초기화
        }

        for (int i = 0; i < N; i++) {
            // 더미 위에 올린 카드 제외하고 나머지 카드 순서대로 저장
            if (cards[i] != 0) {
                card[idx++] = cards[i];
            }

            // 기존 카드더미도 섞인 카드로 저장
            cards[i] = card[i];
        }
    }

    private static void findRange(int[] cards, int k, int N) {
        int preCount = N; // 전에 올린 카드 개수

        // 1 <= i <= K + 1,   1 <= 2^K < N
        for (int i = 1; i <= k + 1; i++) {
            int nowCount = (int) Math.pow(2, k - i + 1.0); // 지금 카드 더미 위로 올릴 카드 개수
            mix(cards, N, preCount, nowCount); // 카드 섞기
            preCount = nowCount; // 전에 올린 카드 개수를 지금 올렸던 카드 개수로 초기화
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(in.readLine());
        int[] result = new int[N]; // 결과값 배열
        int[] cards = new int[N]; // 1 ~ N 카드더미
        String[] input = in.readLine().split(" ");
        for (int i = 0; i < N; i++) {
            result[i] = Integer.parseInt(input[i]);
            cards[i] = i + 1;
        }

        for (int i = 1; Math.pow(2, i) <= N; i++) {
            for (int j = 1; Math.pow(2, j) <= N; j++) {
                int[] temp = Arrays.copyOf(cards, cards.length); // 1 ~ N 까지의 카드더미 복사해서 사용

                // 완전탐색
                findRange(temp, i, N);
                findRange(temp, j, N);

                // 결과값과 섞은값이 일치하는 경우에 두 K 조합 출력
                boolean check = true;
                for (int k = 0; k < N; k++) {
                    if (result[k] != temp[k]) {
                        check = false;
                        break;
                    }
                }

                // 모두 일치하는 경우 출력 후 종료
                if (check) {
                    System.out.println(i + " " + j);
                    return;
                }
            }
        }
    }
}
