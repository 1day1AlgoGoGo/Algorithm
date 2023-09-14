package main.jaeseo.day230905;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

/*
문제를 제대로 읽지 않아서 정말 힘들었던 문제..

문제를 잘 이해했다면 구현하는거 자체는 크게 어렵진 않다.
하지만 아래 조건들을 이해하고 고려하는게 약간 까다로웠음

1. 도도와 수연 각각 턴이 존재하고, 이 턴마다 게임진행횟수가 차감됌
2. 덱과 그라운드를 관리하기 위해 연결리스트를 사용했는데, addLast, addFirst가 추가할때 약간 헷갈렸음
3. 수연이 종치는 조건에서 [단, 어느 쪽의 그라운드도 비어있으면 안된다.]를 고려하지 않아서 계속 틀렸음
 */
public class 숫자할리갈리게임 {
    static int N, M;
    static LinkedList<Integer> dodoDeq = new LinkedList<>();
    static LinkedList<Integer> suyeonDeq = new LinkedList<>();
    static LinkedList<Integer> dodoGround = new LinkedList<>();
    static LinkedList<Integer> suyeonGround = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        init();

        boolean dodoTurn = true;
        int dodoCard = 0, suyeonCard = 0;
        while (M-- > 0) {

            if (dodoTurn) {
                // 도도 차례

                // 도도 덱에서 가장 위에 있는 카드 뒤집기
                dodoCard = dodoDeq.removeFirst();
                // 도도 그라운드에 올리기
                dodoGround.addFirst(dodoCard);

                if (dodoDeq.size() == 0) {
                    // 수연 승리
                    System.out.println("su");
                    return;
                }

                if (dodoCard == 5) {
                    // 도도 종 치기

                    // 수연의 그라운드를 뒤집어서 도도 덱 아래에 합치기
                    while (!suyeonGround.isEmpty()) {
                        dodoDeq.addLast(suyeonGround.removeLast());
                    }

                    //도도 그라운드에 있는 카드도 뒤집어서 도도 덱 아래에 합치기
                    while (!dodoGround.isEmpty()) {
                        dodoDeq.addLast(dodoGround.removeLast());
                    }
                } else if (dodoCard + suyeonCard == 5 && suyeonGround.size() != 0) {
                    // 수연 종 치기

                    // 도도의 그라운드를 뒤집어서 수연 덱 아래에 합치기
                    while (!dodoGround.isEmpty()) {
                        suyeonDeq.addLast(dodoGround.removeLast());
                    }

                    //수연 그라운드에 있는 카드도 뒤집어서 수연 덱 아래에 합치기
                    while (!suyeonGround.isEmpty()) {
                        suyeonDeq.addLast(suyeonGround.removeLast());
                    }
                }

                dodoTurn = false;
            } else {
                // 수연 차례

                // 수연 덱에서 가장 위에 있는 카드 뒤집기
                suyeonCard = suyeonDeq.removeFirst();
                // 수연 그라운드에 올리기
                suyeonGround.addFirst(suyeonCard);

                if (suyeonDeq.size() == 0) {
                    // 도도 승리
                    System.out.println("do");
                    return;
                }

                if (suyeonCard == 5) {
                    // 도도 종 치기

                    // 수연의 그라운드를 뒤집어서 도도 덱 아래에 합치기
                    while (!suyeonGround.isEmpty()) {
                        dodoDeq.addLast(suyeonGround.removeLast());
                    }

                    //도도 그라운드에 있는 카드도 뒤집어서 도도 덱 아래에 합치기
                    while (!dodoGround.isEmpty()) {
                        dodoDeq.addLast(dodoGround.removeLast());
                    }
                } else if (dodoCard + suyeonCard == 5 && dodoGround.size() != 0) {
                    // 수연 종 치기

                    // 도도의 그라운드를 뒤집어서 수연 덱 아래에 합치기
                    while (!dodoGround.isEmpty()) {
                        suyeonDeq.addLast(dodoGround.removeLast());
                    }

                    //수연 그라운드에 있는 카드도 뒤집어서 수연 덱 아래에 합치기
                    while (!suyeonGround.isEmpty()) {
                        suyeonDeq.addLast(suyeonGround.removeLast());
                    }
                }

                dodoTurn = true;
            }
        }

        if (dodoDeq.size() > suyeonDeq.size()) {
            System.out.println("do");
        } else if (dodoDeq.size() < suyeonDeq.size()) {
            System.out.println("su");
        } else {
            System.out.println("dosu");
        }
    }

    private static void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            int dodoCard = Integer.parseInt(st.nextToken());
            int suyeonCard = Integer.parseInt(st.nextToken());

            dodoDeq.addFirst(dodoCard);
            suyeonDeq.addFirst(suyeonCard);
        }
    }
}