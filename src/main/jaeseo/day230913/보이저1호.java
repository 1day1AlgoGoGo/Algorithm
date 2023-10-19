package main.jaeseo.day230913;

import java.io.*;
import java.util.*;

/*
방향 전환을 구현하는 아이디어는 어렵지 않게 떠올릴수있었으나 방향을 나타내는 directionIndex를 하나만 선언해서 문제였음..
originDirectionIndex를 따로 관리했어야함.

문제 풀이 방법
1. 일반적인 4방 dfs + while문을 활용한 깊이 탐색
2. '/' 혹은 '\'를 만날때 방향전환를 directionIndex 변수로 처리
3.
```
if(maximumSecond <= second){
    maximumSecond = second;

    if(originDirectionIndex == LEFT){
        resultDirection = 'L';
    }else if(originDirectionIndex == DOWN){
        resultDirection = 'D';
    }else if(originDirectionIndex == RIGHT){
        resultDirection = 'R';
    }else if(originDirectionIndex == UP){
        resultDirection = 'U';
    }
}
에서 originDirectionIndex으로 하지 않고, directionIndex으로 하게되면 원래의 방향이 아니라 전환되었을수도있는 방향으로 처리되기때문에 originDirectionIndex를 다루어야함!
```
 */

public class 보이저1호 {
    static int N, M, Pr, Pc, maximumSecond = Integer.MIN_VALUE;
    static char resultDirection;
    static char[][] map;
    //좌하우상
    static int[] dr = new int[]{0,1,0,-1};
    static int[] dc = new int[]{-1,0,1,0};

    static final int LEFT = 0;
    static final int DOWN = 1;
    static final int RIGHT = 2;
    static final int UP = 3;


    public static void main(String[] args) throws IOException {
        init();

        sendSignal();

        System.out.println(resultDirection);
        if(maximumSecond == N*M*2){
            System.out.println("Voyager");
        }else{
            System.out.println(maximumSecond);
        }


    }

    private static void sendSignal() {
        for (int i = 0; i < 4; i++) {
            int originDirectionIndex = i;
            int directionIndex = originDirectionIndex;

            int nextR = Pr;
            int nextC = Pc;

            int second = 0;

            while(true){
                nextR = nextR + dr[directionIndex];
                nextC = nextC + dc[directionIndex];

                second++;

                if(nextR < 0 || nextC < 0 || nextR >= N || nextC >= M)    break;
                if(map[nextR][nextC] == 'C')    break;
                if(second >= N * M * 2)  break;

                if(map[nextR][nextC] == '/' || map[nextR][nextC] == '\\'){
                    directionIndex = changeDirection(map[nextR][nextC], directionIndex);
                }

            }

            if(maximumSecond <= second){
                maximumSecond = second;

                if(originDirectionIndex == LEFT){
                    resultDirection = 'L';
                }else if(originDirectionIndex == DOWN){
                    resultDirection = 'D';
                }else if(originDirectionIndex == RIGHT){
                    resultDirection = 'R';
                }else if(originDirectionIndex == UP){
                    resultDirection = 'U';
                }
            }
        }

    }

    private static int changeDirection(char planet, int directionIndex) {

        if(planet == '/'){
            //좌하우상

            if(directionIndex == LEFT){
                // 좌
                directionIndex = DOWN;
            }else if(directionIndex == DOWN){
                // 하
                directionIndex = LEFT;
            }else if(directionIndex == RIGHT){
                // 우
                directionIndex = UP;
            }else if(directionIndex == UP){
                // 상
                directionIndex = RIGHT;
            }

        }else if(planet == '\\'){

            if(directionIndex == LEFT){
                // 좌
                directionIndex = UP;
            }else if(directionIndex == DOWN){
                // 하
                directionIndex = RIGHT;
            }else if(directionIndex == RIGHT){
                // 우
                directionIndex = DOWN;
            }else if(directionIndex == UP){
                // 상
                directionIndex = LEFT;
            }
        }

        return directionIndex;


    }

    private static void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new char[N][M];

        for (int i = 0; i < N; i++) {
            map[i] = br.readLine().toCharArray();
        }

        st = new StringTokenizer(br.readLine());
        Pr = Integer.parseInt(st.nextToken()) - 1;
        Pc = Integer.parseInt(st.nextToken()) - 1;

    }
}
