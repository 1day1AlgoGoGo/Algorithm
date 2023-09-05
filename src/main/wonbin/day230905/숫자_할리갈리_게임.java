package main.wonbin.day230905;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
    [시뮬레이션]_백준_20923_숫자 할리갈리 게임

    큐 2개를 사용하여 각자의 덱에서 오픈하는 카드를 순서대로 제출하였다.
    리스트를 활용하여 종을 친 경우 상대의 그라운드카드/나의 그라운드카드 더미를 순서대로 큐에 넣었다.
    카드를 제출하였을 때, 큐가 비었다면 상대 승리가 된다.
    M번의 횟수를 모두 소진하면 큐 사이즈를 비교하여 답을 출력하였다.

 */
public class 숫자_할리갈리_게임 {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        // 카드를 순서대로 오픈하기 위한 큐
        Queue<Integer> dodo = new LinkedList<>();
        Queue<Integer> suyeon = new LinkedList<>();

        // 그라운드에 올릴 카드 더미
        ArrayList<Integer> groundOfDodo = new ArrayList<>();
        ArrayList<Integer> groundOfSuyeon = new ArrayList<>();

        // 역순으로 주어지는 카드를 순서대로 넣기 위한 스택
        Stack<Integer> st1 = new Stack<>();
        Stack<Integer> st2 = new Stack<>();

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            st1.push(Integer.parseInt(st.nextToken()));
            st2.push(Integer.parseInt(st.nextToken()));
        }

        for (int i = 0; i < N; i++) {
            dodo.add(st1.pop());
            suyeon.add(st2.pop());
        }

        // 각 그라운드의 마지막 카드값
        int topOfDodo = 0;
        int topOfSuyeon = 0;

        for (int i = 0; i < M; i++) {
            // 순서대로 도도부터
            if (i % 2 == 0) {
                topOfDodo = dodo.poll();
                groundOfDodo.add(topOfDodo);
            } else {
                topOfSuyeon = suyeon.poll();
                groundOfSuyeon.add(topOfSuyeon);
            }

            // 덱이 비었으면 상대 승리
            if (dodo.isEmpty()) {
                System.out.println("su");
                return;
            }
            if (suyeon.isEmpty()) {
                System.out.println("do");
                return;
            }

            // 수연이 승리하는 경우, 도도의 그라운드부터 수연의 그라운드의 카드를 수연의 덱으로
            if (topOfDodo != 0 && topOfSuyeon != 0) {
                if (topOfDodo + topOfSuyeon == 5) {

                    groundOfDodo.forEach(suyeon::add);
                    groundOfDodo.clear();
                    groundOfSuyeon.forEach(suyeon::add);
                    groundOfSuyeon.clear();

                    topOfDodo = 0;
                    topOfSuyeon = 0;

                }
            }

            // 도도가 승리하는 경우, 수연의 그라운드부터 도도의 그라운드의 카드를 도도의 덱으로
            if (topOfDodo == 5 || topOfSuyeon == 5) {

                groundOfSuyeon.forEach(dodo::add);
                groundOfSuyeon.clear();
                groundOfDodo.forEach(dodo::add);
                groundOfDodo.clear();

                topOfDodo = 0;
                topOfSuyeon = 0;
            }

        }

        // 덱 크기를 비교하여 승리 확인
        if (dodo.size() > suyeon.size()) {
            System.out.println("do");
        } else if (dodo.size() < suyeon.size()) {
            System.out.println("su");
        } else {
            System.out.println("dosu");
        }

        br.close();
    }

}
