package BOJ_Silver.S1_20923;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class 숫자할리갈리게임 {
    private static final String DODO_WIN = "do";
    private static final String SUYEON_WIN = "su";
    private static final String DRAW = "dosu";
    private static final int WIN_NUMBER = 5;

    private static void winCheck(Deque<Integer> doG,
                                 Deque<Integer> suG,
                                 Deque<Integer> suDeck,
                                 Deque<Integer> doDeck) {
        // 수연이 종을 칠 수 있는 경우
        if (!doG.isEmpty() && !suG.isEmpty() && (doG.peek() + suG.peek() == WIN_NUMBER)) {
            takeCard(doG, suDeck, suG);
        }

        // 도도가 종을 칠 수 있는 경우
        if ((!doG.isEmpty() && doG.peek() == WIN_NUMBER) || (!suG.isEmpty() && suG.peek() == WIN_NUMBER)) {
            takeCard(suG, doDeck, doG);
        }
    }

    private static void takeCard(Deque<Integer> losersG,
                                 Deque<Integer> winnersDeck,
                                 Deque<Integer> winnersG) {
        // 종을 친 사람 덱에 상대방 그라운드 카드를 뒤집어서 저장하기
        while (!losersG.isEmpty()) {
            winnersDeck.add(losersG.pollLast());
        }

        // 그 다음 자기 그라운드 카드 뒤집어서 저장하기
        while (!winnersG.isEmpty()) {
            winnersDeck.add(winnersG.pollLast());
        }
    }

    private static boolean isEmptyDeck(Deque<Integer> doDeck, Deque<Integer> suDeck) {
        if (doDeck.isEmpty()) {
            System.out.println(SUYEON_WIN);
            return true;
        } else if (suDeck.isEmpty()) {
            System.out.println(DODO_WIN);
            return true;
        }

        return false;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        String answer = "";

        Deque<Integer> doDeck = new ArrayDeque<>();
        Deque<Integer> suDeck = new ArrayDeque<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(in.readLine());
            doDeck.push(Integer.parseInt(st.nextToken()));
            suDeck.push(Integer.parseInt(st.nextToken()));
        }

        Deque<Integer> doG = new ArrayDeque<>(); // 도도의 그라운드
        Deque<Integer> suG = new ArrayDeque<>(); // 수연의 그라운드
        boolean doTurn = true; // true : 도도의 턴, false : 수연의 턴
        while (M-- > 0) {
            // 도도의 턴
            if (doTurn) {
                doG.push(doDeck.pop());
                // 카드 내고 덱이 비어있는지 확인
                if (isEmptyDeck(doDeck, suDeck)) {
                    return;
                }

                // 종을 칠 수 있는지 확인하고 2-4 진행
                winCheck(doG, suG, suDeck, doDeck);
                doTurn = false; // 턴 변경
            // 수연의 턴
            } else {
                suG.push(suDeck.pop());
                // 카드 내고 덱이 비어있는지 확인
                if (isEmptyDeck(doDeck, suDeck)) {
                    return;
                }

                // 종을 칠 수 있는지 확인하고 2-4 진행
                winCheck(doG, suG, suDeck, doDeck);
                doTurn = true; // 턴 변경
            }
        }

        if (doDeck.size() > suDeck.size()) {
            answer = DODO_WIN;
        } else if (doDeck.size() < suDeck.size()) {
            answer = SUYEON_WIN;
        } else {
            answer = DRAW;
        }

        System.out.println(answer);
    }
}
