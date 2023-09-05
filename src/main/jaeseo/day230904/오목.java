package main.jaeseo.day230904;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

/* 오목을 체크하기 위해서는 [좌우|상하|북서남동|남서북동] 4가지 경우의 수가 있다고 생각했다
그래서, 바둑알 탐색할때마다 이 4가지 경우의 수를 체크하고, 오목인 경우엔 출력해야할 바둑알의 위치를 파악하기 위해 compartor를 활용하여 정렬했다.
(근데... 알고보니 원빈이 풀이가 더 간단하고 최적화가 더 잘되어있었다) */
public class 오목 {
    static int[][] map = new int[19][19];

    // 좌우|상하|북서남동|남서북동
    static int[] dr = new int[]{0, 0, -1, 1, -1, 1, 1, -1};
    static int[] dc = new int[]{-1, 1, 0, 0, -1, 1, -1, 1};

    static int[][] omokPositions = new int[5][2];
    static int winColor;

    public static void main(String[] args) throws IOException {
        // 입력 값 초기화를 위한 함수
        init();

        for (int r = 0; r < 19; r++) {
            for (int c = 0; c < 19; c++) {
                if (map[r][c] == 1 || map[r][c] == 2) {
                    //오목이 만들어졌는지를 boolean로 확인
                    boolean flag = checkOmok(map[r][c], r, c);
                    if (flag) {
                        //오목 완성 시, 둘째 줄에 오목알의 r,c 값을 출력해야되기때문에 해당 부분 함수화해서 반환 받기
                        int[] resultRC = checkResultRC();
                        System.out.println(winColor);
                        System.out.println(resultRC[0] + " " + resultRC[1]);
                        return;
                    }
                }
            }
        }
        System.out.println(0);
    }

    //오목 만들어졌는지 확인하는 함수
    private static boolean checkOmok(int color, int r, int c) {
        //오목이 만들어지는 형태가 3가지 (좌우|상하|북서남동|남서북동)이므로, 3가지를 하나하나 확인하기 위해 for문 구성
        for (int i = 0; i < 8; i += 2) {
            int count = 1;

            // 오목이 되었는지 확인하기 위해서는 두 번을 탐색해야함 (상하 -> 상으로 탐색 + 하로 탐색)
            for (int j = i; j < i + 2; j++) {
                int nextR = r;
                int nextC = c;
                // 현재 바둑돌을 해당 배열 첫번째 인덱스에 저장 (오목이 완성되었을때의 각 바둑돌의 위치를 저장하는 배열)
                omokPositions[0][0] = r;
                omokPositions[0][1] = c;

                //다음 위치의 바둑돌을 확인하기 위한 while문
                while (true) {
                    nextR += dr[j];
                    nextC += dc[j];

                    if (nextR < 0 || nextC < 0 || nextR >= 19 || nextC >= 19 || map[nextR][nextC] == 0) break;
                    if (map[nextR][nextC] != color) break;

                    // 육목이 될수도있기 때문에 5로 나머지 연산
                    omokPositions[count % 5][0] = nextR;
                    omokPositions[count % 5][1] = nextC;
                    count++;


                }
            }
            if (count == 5) {
                winColor = color;
                return true;
            }
        }
        return false;
    }


    private static int[] checkResultRC() {
        //상하 일직선인지 먼저 체크
        boolean flag = true;
        for (int i = 0; i < 5; i++) {
            // 상하 일직선이 아니면
            if (omokPositions[0][1] != omokPositions[i][1]) {
                flag = false;
            }
        }

        // 상하 일직선이면
        if (flag) {
            Arrays.sort(omokPositions, new Comparator<int[]>() {
                @Override
                public int compare(int[] o1, int[] o2) {
                    return o1[0] - o2[0];
                }
            });
        } else {
            //상하 일직선이 아니면
            Arrays.sort(omokPositions, new Comparator<int[]>() {
                @Override
                public int compare(int[] o1, int[] o2) {
                    return o1[1] - o2[1];
                }
            });
        }

        return new int[]{omokPositions[0][0] + 1, omokPositions[0][1] + 1};
    }

    private static void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = null;

        for (int i = 0; i < 19; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < 19; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }
}