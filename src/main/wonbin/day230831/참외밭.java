package main.wonbin.day230831;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
    [시뮬레이션] 백준_2477_참외밭

    큰 면적의 넓이를 구해서 작은 면적을 빼는 방식으로 최초 구현하였지만
    작은 면적을 구하는 식에서 문제가 있었다. 작은 면적의 세로나 가로 길이가
    최소 길이가 아니었던 것이다.

    이후 반시계 방향으로 방향전환할 때 시계방향으로 전환하면 해당 가로의 길이와 세로의 길이를 찾아
    작은 면적을 구하는 방식으로 접근했다. 이 방식이 맞았지만 시작점이 시계방향으로 향하는 곳부터
    시작할 경우 가로와 세로 길이를 찾지 못하는 예제가 있어서 처음 시작하는 시작점을 마지막으로
    한번 더 방문해주어 작은 면적의 세로와 가로 길이를 구하였다.

 */

public class 참외밭 {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());

        int max1 = 0;
        int max2 = 0;

        int result = 0;
        int dirBefore = 0;
        int valueBefore = 0;

        int list[][] = new int[7][2];

        for (int i = 0; i < 6; i++) {
            st = new StringTokenizer(br.readLine());

            list[i][0] = Integer.parseInt(st.nextToken());
            list[i][1] = Integer.parseInt(st.nextToken());
        }

        list[6][0] = list[0][0];
        list[6][1] = list[0][1];

        for (int i = 0; i < 7; i++) {

            int dir = list[i][0];
            int value = list[i][1];

            if (dir == 1 || dir == 2) {
                max1 = Math.max(max1, value);
            } else {
                max2 = Math.max(max2, value);
            }
            boolean flag = true;
            if (dirBefore == 1) {
                if (dir != 4) {
                    flag = false;
                }
            } else if (dirBefore == 2) {
                if (dir != 3) {
                    flag = false;
                }
            } else if (dirBefore == 3) {
                if (dir != 1) {
                    flag = false;
                }
            } else if (dirBefore == 4) {
                if (dir != 2) {
                    flag = false;
                }
            }
            dirBefore = dir;
            if (!flag) {
                result = valueBefore * value;
            }
            valueBefore = value;
        }

        System.out.println(((max1 * max2) - result) * N);

        br.close();
    }

}
