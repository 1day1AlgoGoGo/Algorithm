package main.jaeseo.day230901;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class 로봇 {
    static int[] dr = new int[]{-1,1,0,0};
    static int[] dc = new int[]{0,0,-1,1};

    static int R, C, k, robotR, robotC;
    static boolean[][] isVisited;
    static List<Integer> commands;

    public static void main(String[] args) throws IOException {
        // 입력 값 초기화하는 부분 함수화
        init();

        // LinkedList에 저장된 방향 값들을 탐색하기 위한 index
        int commandIndex = 0;
        while(true){
            // 상하좌우 = 1234
            int command = commands.get(commandIndex);
            // 로봇이 못움직일때까지 계속해서 반복되야하기때문에, 방향 값들 탐색을 위한 index 값을 0 ~ commands.size() 범위로 계속 초기화
            // moveR과 moveC가 if 조건에 안맞으면 continue 되버리기때문에, 해댱 if문보다 상단에 선언
            commandIndex = (commandIndex + 1) % commands.size();

            int moveR = robotR + dr[command-1];
            int moveC = robotC + dc[command-1];

            if(moveR < 0 || moveC < 0 || moveR >= R || moveC >= C) continue;
            if(isVisited[moveR][moveC]) continue;

            // 특정 방향으로 계속 반복해서 탐색하기 위해 while문 선언
            while(true){
                isVisited[moveR][moveC] = true;

                moveR = moveR + dr[command-1];
                moveC = moveC + dc[command-1];

                // 밖으로 벗어나거나, 이미 방문한 곳으로 가는경우, 그 직전의 r과 c로 돌아가야하기 때문에 robotR, robotC 초기화
                if(moveR < 0 || moveC < 0 || moveR >= R || moveC >= C || isVisited[moveR][moveC]){
                    robotR = moveR - dr[command-1];
                    robotC = moveC - dc[command-1];
                    break;
                }
            }

            // 현재상태에서 상하좌우로 움직일 수 있는지 판단하기 (즉, 로봇이 못 움직이는지 확인)
            int checkCount = 0;
            for (int i = 0; i < 4; i++) {
                int checkR = robotR + dr[i];
                int checkC = robotC + dc[i];

                if(checkR < 0 || checkC < 0 || checkR >= R || checkC >= C || isVisited[checkR][checkC]){
                    checkCount++;
                }
            }
            if(checkCount == 4) break;

        }

        System.out.println(robotR + " " + robotC);

    }

    private static void init() throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

         R = Integer.parseInt(st.nextToken());
         C = Integer.parseInt(st.nextToken());

        isVisited = new boolean[R][C];

         k = Integer.parseInt(br.readLine());


        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());

            int obstacleR = Integer.parseInt(st.nextToken());
            int obstacleC = Integer.parseInt(st.nextToken());

            isVisited[obstacleR][obstacleC] = true;
        }

        st = new StringTokenizer(br.readLine());

         robotR = Integer.parseInt(st.nextToken());
         robotC = Integer.parseInt(st.nextToken());

        isVisited[robotR][robotC] = true;

        st = new StringTokenizer(br.readLine());

        commands = new LinkedList<>();

        while(st.hasMoreTokens()){
            commands.add(Integer.parseInt(st.nextToken()));
        }

    }
}
