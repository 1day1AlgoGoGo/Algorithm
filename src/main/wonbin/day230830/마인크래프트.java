package main.wonbin.day230830;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
/*
    [시뮬레이션]백준_18111_마인크래프트

    최소 높이와 최대 높이를 계산해서 그 사이에 나올 수 있는 모든 값을
    계산하였다. 정렬을 해주어 높은 땅을 먼저 계산해주었다. 기준 높이보다
    높을 땐, 2만큼의 시간 * 높이 차를 해주었고, 낮은 경우 1 * 높이 차의 식으로
    시간 계산을 하였다. 시간복잡도 때문에 애먹은 문제

 */
public class 마인크래프트 {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int B = Integer.parseInt(st.nextToken());

        int list[] = new int[N * M];

        int cnt = 0;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                int temp = Integer.parseInt(st.nextToken());
                list[cnt++] = temp;
            }
        }

        int minTime = 1000000007;
        int heightOfLand = 0;

        Arrays.sort(list);
        int min = list[0];
        int max = list[list.length - 1];

        for (int i = min; i <= max; i++) {
            int land = B;
            int result = 0;
            boolean flag = true;

            for (int j = list.length - 1; j >= 0; j--) {
                if (land < 0) {
                    flag = false;
                    break;
                }
                int temp = list[j];

                if (temp > i) {
                    land += temp - i;
                    result += ((temp - i) << 1);
                } else if (temp < i) {
                    if (land >= i - temp) {
                        land -= (i - temp);
                        result += (i - temp);
                    } else {
                        flag = false;
                        break;
                    }
                }
            }

            if (flag) {
                if (minTime >= result) {
                    minTime = result;
                    if (heightOfLand < i) {
                        heightOfLand = i;
                    }
                }
            }
        }
        System.out.println(minTime + " " + heightOfLand);

        br.close();
    }
}