package main.wonbin.day230914;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
    [시뮬레이션]_백준_상어 초등학교

    - 자리에 앉은 곳을 배열로 관리하고 좋아하는 친구와 위치를 각각 클래스로 관리하였다.
    - ArrayList에 앉을 수 있는 자리를 모두 넣고 조건에 따라 정렬하여 자리를 선택하였다.

 */
public class 상어_초등학교 {

    static class Favorite {
        int q, w, e, r;

        Favorite(int q, int w, int e, int r) {
            this.q = q;
            this.w = w;
            this.e = e;
            this.r = r;
        }
    }

    static class Point implements Comparable<Point> {
        int x, y, countEmptyPoint, favoriteStudentCount;

        Point(int x, int y, int countEmptyPoint, int favoriteStudentCount) {
            this.x = x;
            this.y = y;
            this.countEmptyPoint = countEmptyPoint;
            this.favoriteStudentCount = favoriteStudentCount;
        }

        @Override
        public int compareTo(Point o) {
            if (o.favoriteStudentCount == this.favoriteStudentCount) {
                if (this.countEmptyPoint == o.countEmptyPoint) {

                    if (this.x == o.x) {
                        return this.y - o.y;
                    }
                    return this.x - o.x;
                }
                return o.countEmptyPoint - this.countEmptyPoint;
            }
            return o.favoriteStudentCount - this.favoriteStudentCount;
        }

    }

    static int N;
    static Map<Integer, Favorite> favoriteMap = new HashMap();
    static ArrayList<Integer> sequentialStudentList = new ArrayList<>();
    static ArrayList<Point> sortPointList = new ArrayList<>();
    static boolean classMap[][];
    static int dx[] = {0, 0, -1, 1};
    static int dy[] = {-1, 1, 0, 0};
    static Point[] seatList;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        classMap = new boolean[N + 1][N + 1];
        seatList = new Point[N * N + 1];
        for (int i = 0; i < N * N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int student = Integer.parseInt(st.nextToken());

            sequentialStudentList.add(student);
            favoriteMap.put(student, new Favorite(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
        }

        for (int i = 0; i < sequentialStudentList.size(); i++) {
            int nowStudent = sequentialStudentList.get(i);

            Point now = nearFavoriteStudent(nowStudent);
            classMap[now.x][now.y] = true;
            seatList[nowStudent] = now;
        }

        System.out.println(searchingSatisfaction());
        br.close();

    }

    private static Point nearFavoriteStudent(int nowStudent) {
        Favorite favorite = favoriteMap.get(nowStudent);

        sortPointList.clear();

        Queue<Point> q = new LinkedList<>();
        if (seatList[favorite.q] != null) {
            q.add(seatList[favorite.q]);
        }
        if (seatList[favorite.w] != null) {
            q.add(seatList[favorite.w]);
        }
        if (seatList[favorite.e] != null) {
            q.add(seatList[favorite.e]);
        }
        if (seatList[favorite.r] != null) {
            q.add(seatList[favorite.r]);
        }

        if (q.isEmpty()) {
            findEmptySeats();
        } else {
            while (!q.isEmpty()) {
                findFavoriteSeats(q.poll());
            }
        }
        if (sortPointList.isEmpty()) {
            findEmptySeats();
        }
        Collections.sort(sortPointList);

        return sortPointList.get(0);
    }

    // 좋아하는 사람이 없거나 인접한 곳에 자리가 없을 때
    private static void findEmptySeats() {
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (!classMap[i][j]) {
                    int cnt = 0;
                    for (int h = 0; h < 4; h++) {
                        int nx = i + dx[h];
                        int ny = j + dy[h];

                        if (nx < 1 || ny < 1 || nx > N || ny > N)
                            continue;
                        if (classMap[nx][ny])
                            continue;
                        cnt++;
                    }
                    sortPointList.add(new Point(i, j, cnt, 0));
                }
            }
        }
    }

    private static void findFavoriteSeats(Point now) {

        // 좋아하는 사람의 인접한 빈 자리 탐색
        for (int i = 0; i < 4; i++) {
            int nx = now.x + dx[i];
            int ny = now.y + dy[i];

            if (nx < 1 || ny < 1 || nx > N || ny > N)
                continue;
            if (classMap[nx][ny])
                continue;

            // 좋아하는 사람이 근처에 있으면 favoriteStudentCount + 1
            boolean favoriteStudentsOn = false;
            for (int z = 0; z < sortPointList.size(); z++) {
                if (sortPointList.get(z).x == nx && sortPointList.get(z).y == ny) {
                    sortPointList.get(z).favoriteStudentCount++;
                    favoriteStudentsOn = true;
                    break;
                }
            }
            // 비어있는 자리탐색
            if (!favoriteStudentsOn) {
                int cnt = 0;

                for (int h = 0; h < 4; h++) {
                    int nnx = nx + dx[h];
                    int nny = ny + dy[h];

                    if (nnx < 1 || nny < 1 || nnx > N || nny > N) {
                        continue;
                    }
                    if (classMap[nnx][nny]) {
                        continue;
                    }
                    if (nnx == nx && nny == ny) {
                        continue;
                    }
                    cnt++;
                }
                sortPointList.add(new Point(nx, ny, cnt, 0));
            }

        }

    }

    // 만족도 조사
    private static int searchingSatisfaction() {
        int sum = 0;

        for (int i = 0; i < sequentialStudentList.size(); i++) {
            int nowStudent = sequentialStudentList.get(i);

            Favorite favorite = favoriteMap.get(nowStudent);

            Point nowSeat = seatList[nowStudent];

            int cnt = 0;
            if (Math.abs(nowSeat.x - seatList[favorite.q].x) + Math.abs(nowSeat.y - seatList[favorite.q].y) == 1)
                cnt++;
            if (Math.abs(nowSeat.x - seatList[favorite.w].x) + Math.abs(nowSeat.y - seatList[favorite.w].y) == 1)
                cnt++;
            if (Math.abs(nowSeat.x - seatList[favorite.e].x) + Math.abs(nowSeat.y - seatList[favorite.e].y) == 1)
                cnt++;
            if (Math.abs(nowSeat.x - seatList[favorite.r].x) + Math.abs(nowSeat.y - seatList[favorite.r].y) == 1)
                cnt++;

            switch (cnt) {
                case 1:
                    sum += 1;
                    break;
                case 2:
                    sum += 10;
                    break;
                case 3:
                    sum += 100;
                    break;
                case 4:
                    sum += 1000;
                    break;
                default:
                    break;
            }
        }

        return sum;
    }
}
