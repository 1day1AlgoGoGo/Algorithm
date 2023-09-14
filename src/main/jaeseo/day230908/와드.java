package main.jaeseo.day230908;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
약간의 조건이 있는 bfs 문제여서 크게 어렵진않았다.
다만, 한별이가 있는 시점 기준으로 상하좌우가 시야에 포함되는지 헷갈려서 이를 확인하는데에 약간 시간이 걸렸다.
 */
public class 와드 {
    static int R, C, Hr, Hc;
    static char commands[], map[][];
    //상하좌우
    static int[] dr = new int[]{-1, 1, 0, 0};
    static int[] dc = new int[]{0, 0, -1, 1};
    static boolean[][] sightMap;

    public static void main(String[] args) throws IOException {
        //입력값 초기화
        init();

        for (int i = 0; i < commands.length; i++) {
            char command = commands[i];

            if (command == 'W') {
                //와드 놓는 작업 (= 시야 밝히는 작업)
                putWard();
            } else {
                // 한별이를 움직이게 하는 함수
                moveHanbyeol(command);
            }
        }
        // 마지막 한별이의 위치 기준으로 (상하좌우+한별이위치)에 시야밝히기
        checkHanbyeolView();

        //출력
        printSight();
    }

    private static void checkHanbyeolView() {
        sightMap[Hr][Hc] = true;

        for (int i = 0; i < 4; i++) {
            int nextR = Hr + dr[i];
            int nextC = Hc + dc[i];

            if (nextR < 0 || nextC < 0 || nextR >= R || nextC >= C || sightMap[nextR][nextC]) continue;

            sightMap[nextR][nextC] = true;
        }
    }

    private static void printSight() {
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (sightMap[i][j]) output.append('.');
                else output.append('#');
            }
            output.append('\n');
        }
        output.deleteCharAt(output.length() - 1);

        System.out.print(output);
    }

    private static void putWard() {
        char area = map[Hr][Hc];

        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{Hr, Hc});
        sightMap[Hr][Hc] = true;

        while (!q.isEmpty()) {
            int[] position = q.poll();

            for (int i = 0; i < 4; i++) {
                int nextR = position[0] + dr[i];
                int nextC = position[1] + dc[i];

                if (nextR < 0 || nextC < 0 || nextR >= R || nextC >= C || sightMap[nextR][nextC]) continue;
                // 와드 놓인 곳의 문자랑 다음 위치의 문자가 같은지 체크하는 if문
                if (map[nextR][nextC] != area) continue;

                q.add(new int[]{nextR, nextC});
                sightMap[nextR][nextC] = true;
            }
        }
    }

    private static void moveHanbyeol(char command) {
        int directionNumber = 0;

        // 명령 문자를 상하좌우로 움직일수있는 index로 변환
        if (command == 'U') directionNumber = 0;
        else if (command == 'D') directionNumber = 1;
        else if (command == 'L') directionNumber = 2;
        else if (command == 'R') directionNumber = 3;

        Hr += dr[directionNumber];
        Hc += dc[directionNumber];
    }

    private static void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        map = new char[R][C];
        sightMap = new boolean[R][C];

        for (int i = 0; i < R; i++) {
            char[] inputs = br.readLine().toCharArray();
            for (int j = 0; j < C; j++) {
                map[i][j] = inputs[j];
            }
        }

        st = new StringTokenizer(br.readLine());

        Hr = Integer.parseInt(st.nextToken()) -1;
        Hc = Integer.parseInt(st.nextToken())-1;

        commands = br.readLine().toCharArray();

    }
}
