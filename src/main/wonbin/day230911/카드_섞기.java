package main.wonbin.day230911;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

/*
    [시뮬레이션]_백준_21315_카드 섞기

    섞을 카드의 수가 최대 1000장이고 2^10 미만이므로 완전 탐색이 가능하다 생각했다.
    N장의 카드 수보다 작은 2^(1~K) 범위를 구했고, 두 번에 걸쳐 섞어주는 코드를 구현했다.
    K가 큰 수 부터 작은 수로 내려오는 방식의 메소드와 작은 수 부터 큰 수로 올라가는 메소드를 각각 구현했다.
    하지만 메모리나 시간 상 큰 차이는 없었다.

    이 문제의 핵심은 카드 덱 맨 아래에 있는 카드는 반드시 맨 위로 올라오게 된다는 것이다.
 */

public class 카드_섞기 {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());

        // 최종 결과값 비교 배열
        int answer[] = new int[N];

        for (int i = 0; i < N; i++) {
            answer[i] = Integer.parseInt(st.nextToken());
        }

        // 1부터 N까지 차례로 저장할 리스트
        ArrayList<Integer> nonMixedCard = new ArrayList<>();
        int num = 1;
        while (num <= N) {
            nonMixedCard.add(num++);
        }

        // 각각 카드 섞기에서 나온 결과값 저장 리스트
        ArrayList<Integer> firstMixedCard;
        ArrayList<Integer> lastMixedCard;

        // 최대로 가질 수 있는 2의 지수값 찾기
        int maxK = findPossibleMixingK(N);

        // 1~maxK 까지 완전 탐색
        for (int i = 1; i <= maxK; i++) {
            firstMixedCard = mixed2_K_ver2(i, N, (ArrayList<Integer>) nonMixedCard.clone());

            for (int j = 1; j <= maxK; j++) {
                lastMixedCard = mixed2_K_ver2(j, N, (ArrayList<Integer>) firstMixedCard.clone());

                // 최종 값과 비교하여 모두 같으면 출력
                boolean isEquals = true;
                for (int z = 0; z < N; z++) {
                    if (answer[z] != lastMixedCard.get(z)) {
                        isEquals = false;
                    }
                }
                if (isEquals) {
                    System.out.println(i + " " + j);
                    return;
                }
            }
        }


        br.close();
    }

    // 최대로 가질 수 있는 2의 지수값 찾기 메소드
    private static int findPossibleMixingK(int N) {
        int cnt = 9;
        while (cnt >= 1) {
            if (Math.pow(2, cnt) <= N)
                return cnt;
            cnt--;
        }
        return cnt;
    }

    // 2의 지수가 작은 수부터 큰 수로 갈 때, 카드 섞기 메소드
    private static ArrayList<Integer> mixed2_K_ver2(int K, int N, ArrayList<Integer> card) {

        ArrayList<Integer> result = new ArrayList<>();
        int cnt = 1;
        int index = N - 1;
        result.add(card.get(N - 1));
        while (cnt <= K) {
            int mixedCardListSize = (int) Math.pow(2, cnt);
            for (int i = N - mixedCardListSize; i < index; i++) {
                result.add(card.get(i));
            }
            index = N - mixedCardListSize;
            cnt++;
        }
        for (int i = 0; i < index; i++) {
            result.add(card.get(i));
        }
        return result;
    }

    // 2의 지수가 큰 수부터 작은 수로 갈 때, 카드 섞기 메소드
    private static ArrayList<Integer> mixed2_K_Ver1(int K, int N, ArrayList<Integer> card) {

        ArrayList<Integer> result = new ArrayList<>();
        int mixedCardListSize = (int) Math.pow(2, K);
        for (int i = N - mixedCardListSize - 1; i >= 0; i--) {
            result.add(card.get(i));
        }
        int index = N - mixedCardListSize;

        while (K >= 0) {

            mixedCardListSize = (int) Math.pow(2, K);


            for (int i = index + mixedCardListSize / 2 - 1; i >= index; i--) {
                result.add(card.get(i));
            }
            index += mixedCardListSize / 2;
            K--;
        }
        result.add(card.get(index));
        Collections.reverse(result);
        return result;
    }
}
