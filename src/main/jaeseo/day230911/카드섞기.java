package main.jaeseo.day230911;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
아 이문제 너무 어려웠다.. 문제 자체가 이해가 잘 안됐다..
그래서 일단 블로그를 참고하면서 문제를 이해하고 풀이를 이해했다. (출처 - https://record-developer.tistory.com/162)

일단 헷갈렸던 부분이 아래 두가지이다.
1. "2번의 (2, K) - 섞기 후의 카드 더미 결과를 만드는 각각의 K는 유일함이 보장된다."라고 해서 K는 하나의 수를 말하는줄 알았음..
2. 근데 출력값에는 첫번째 K와 두번째 K를 구하라고 나와있음..
==> 블로그를 참고해보니, 2^k < N을 만족하는 모든 k를 활용해서 두번 섞어야하는거였다
==> 예를들어, 2^k < 5라면, (1,1), (1,2), (2,1), (2,2)의 경우의 수가 있는거였음 아놔..

문제가 이해가 됐지만 카드 섞는 로직이 도저히 떠오르지 않았다.
블로그에서는 Queue를 두개 활용했다. 똑똑하다..

 */
public class 카드섞기 {
    static int N, cards[], maxK;
    public static void main(String[] args) throws IOException {
        init();

        // K의 최대값을 먼저 구함
        findMaxKRange();

        for (int i = 1; i <= maxK; i++) {
            for (int j = 1; j <= maxK; j++) {
                // 섞은 카드를 저장하는 큐인 cardQ
                Queue<Integer> cardQ = new LinkedList<>();
                // cardQ 초기화
                for (int k = N; k > 0; k--) {
                    cardQ.add(k);
                }
                // 첫번째 카드 섞기
                CardMix(i, cardQ);
                //두번째 카드 섞기
                CardMix(j, cardQ);

                // 두번 섞은 카드가 결과카드랑 동일한지 확인
                if(isSameCard(cardQ)){
                    System.out.println(i + " " + j);
                    return;
                }
            }
        }

    }

    private static boolean isSameCard(Queue<Integer> cardQ) {
        boolean flag = true;
        for (int i = N-1; i >= 0; i--) {
            if(cards[i] != cardQ.poll() )   flag = false;
        }
        if(flag)    return true;
        return false;
    }

    private static void CardMix(int K, Queue<Integer> cardQ) {
        // 섞는 작업을 위해 여분의 큐를 하나 더 선언
        Queue<Integer> tempQ = new LinkedList<>();

        //첫번째 단계
        int valueOfEachStage = (int) Math.pow(2,K);
        for (int i = 0; i < valueOfEachStage; i++) {
            tempQ.add(cardQ.poll());
        }

        //두번째 이상 단계
        // 단계가 끝날때마다 섞은 카드를 card 큐에 한번에 다 모으는게 아니라, tempQ에 저장하고 단계가 지날때마다 완료된 카드들을 빼주는 로직
        int i = 2;
        while(i <= K + 1){
            valueOfEachStage = (int) Math.pow(2, K-i+1);

            for (int j = 0; j < valueOfEachStage; j++) {
                tempQ.add(tempQ.poll());
            }
            for (int j = 0; j < valueOfEachStage; j++) {
                cardQ.add(tempQ.poll());
            }
            i++;
        }
        cardQ.add(tempQ.poll());
    }


    private static void findMaxKRange() {
        int twoKSquare = 1;

        while(twoKSquare < N){
            twoKSquare *= 2;
            maxK++;
        }
        maxK--;
    }

    private static void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());

        cards = new int[N];

        int index = 0;
        while(st.hasMoreTokens()){
            cards[index++] = Integer.parseInt(st.nextToken());
        }
    }
}
