package main.wonbin.day230919;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
    백준_시뮬레이션_1283_단축키 지정

    - 입력받은 단어를 띄어쓰기를 기준으로 배열로 저장했다.
    - 대표 알파벳이 지정되었는지 기록할 Boolean형 배열을 생성했다.
    - 각 단계별로 괄호 넣을 알파벳을 배열의 인덱스로 지정했다.
    - 지정된 인덱스에 해당하는 알파벳을 괄호로 출력했다.
 */

public class 단축키_지정 {

    static boolean alpabet[];
    static int wordIndex;
    static int letterIndex;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        alpabet = new boolean[27];

        int times = 0;
        while (times < N) {

            String word[] = br.readLine().split(" ");
            boolean done;

            wordIndex = -1;
            letterIndex = -1;

            done = firstLetterCheck(word);

            if (!done) {
                secondLetterCheck(word);
            }
            printWords(word);
            times++;
        }

        System.out.println(sb);

        br.close();
    }

    private static boolean firstLetterCheck(String word[]) {
        for (int i = 0; i < word.length; i++) {
            int firstLetterIndex = word[i].substring(0, 1).toUpperCase().charAt(0) - 65;
            if (!alpabet[firstLetterIndex]) {
                alpabet[firstLetterIndex] = true;
                wordIndex = i;
                letterIndex = 0;
                return true;
            }
        }
        return false;
    }

    private static void secondLetterCheck(String word[]) {
        for (int i = 0; i < word.length; i++) {
            for (int j = 1; j < word[i].length(); j++) {

                int secondLetterIndex = word[i].substring(j, j + 1).toUpperCase().charAt(0) - 65;
                if (!alpabet[secondLetterIndex]) {
                    alpabet[secondLetterIndex] = true;
                    wordIndex = i;
                    letterIndex = j;
                    return;
                }
            }
        }

        return;
    }

    private static void printWords(String word[]) {
        for (int i = 0; i < word.length; i++) {
            if (wordIndex == i) {
                for (int j = 0; j < word[i].length(); j++) {
                    if (j != letterIndex) {
                        sb.append(word[i].charAt(j));
                    } else {
                        sb.append("[").append(word[i].charAt(j)).append("]");
                    }
                }
                sb.append(" ");
            } else {
                sb.append(word[i]).append(" ");
            }
        }
        sb.append("\n");
    }
}
