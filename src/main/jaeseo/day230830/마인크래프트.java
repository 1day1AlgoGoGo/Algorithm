package main.jaeseo.day230830;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/*
문제를 처음 봤을때는 되게 막막했는데, 경우의수를 하나하나 적어가면서 직접 계산해보니 브루트포스로 가능하다고 알게 되어 막막함이 사라졌다.

내가 생각한 방법은,
주어진 높이들을 set에 저장하고, 해당 높이들을 하나씩 사용하며 그 값을 각각 높이와 빼주었다.
그럼 각 배열에 음수값과 양수값이 나올텐데, 음수값은 블록을 쌓아야하는경우, 양수값은 블록을 뺴줘야하는 경우다
그래서 이를 계산했다.

근데 결국 실수 몇개와 조건 하나를 채우지 못해 히든케이스에서 계속 틀려가지고 구글링해서 참고했다 (출처 - https://blackvill.tistory.com/220)

원래는, 주어진 높이를 set에 담아서 그 값들만 기준높이로 사용했는데, 알고보니 그 값이 아니라 그 사이값일때 최저시간이 나올때가 있었다. (참고 - https://www.acmicpc.net/board/view/123629)
그래서 maxHeight와 minHeight를 이용하여, 그 사이의 높이 모두 계산해야했다.
 */
public class 마인크래프트 {
    static int N, M, B, minSecond = Integer.MAX_VALUE, resultHeight, maxHeight = Integer.MIN_VALUE, minHeight = Integer.MAX_VALUE;
    static int[][] map;

    public static void main(String[] args) throws IOException {
        init();

        for (int i = minHeight; i <= maxHeight; i++) {
            makeFlattedLand(i);
        }

        System.out.println(minSecond + " " + resultHeight);

    }

    private static void makeFlattedLand(int standardHeight) {
        int[][] copyMap = new int[N][M];

        // 걸리는 시간을 저장하기위한 변수
        int second = Integer.MAX_VALUE;

        // 쌓아야할 블록 양
        int minusHeight = 0;
        // 빼야할 블록 양
        int plusHeight = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                copyMap[i][j] = map[i][j] - standardHeight;

                if (copyMap[i][j] < 0) minusHeight += copyMap[i][j];
                if (copyMap[i][j] > 0) plusHeight += copyMap[i][j];
            }
        }

        if (B + plusHeight + minusHeight >= 0) {

            // 절대값 minusHight * 1 (쌓는데 걸리는 시간)
            second = Math.abs(minusHeight);
            // plusHeight * 2 (블록 빼는데 걸리는 시간)
            second += plusHeight * 2;

            if (second < minSecond) {
                //최소시간
                minSecond = second;
                //최소시간일때의 높이
                resultHeight = standardHeight;
            } else if (second == minSecond && resultHeight < standardHeight) {
                resultHeight = standardHeight;
            }
        }


    }

    private static void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        B = Integer.parseInt(st.nextToken());

        map = new int[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                maxHeight = Math.max(maxHeight, map[i][j]);
                minHeight = Math.min(minHeight, map[i][j]);
            }
        }

    }
}
