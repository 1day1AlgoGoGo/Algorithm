package main.jaeseo.day230914;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
문제 자체가 그렇게 어렵지는 않았다.

좌석을 탐색하면서, 주어진 조건에 만족하는 최선의 좌석을 찾는 로직이 핵심이다.
이부분을 SeatInfo 클래스를 정의하고 최선의 좌석을 찾으려고 했다.

조건을 비교하는 부분에 if문이 너무 중첩되어서 가독성이 너무 떨어지는데 어떻게 해결해야될지 잘 모르겠다.. 아이디어 있으면 알려주세여
 */
public class 상어초등학교 {
    static BufferedReader br;
    static int N, map[][];
    static int dr[] = new int[]{0, 0, -1, 1};
    static int dc[] = new int[]{-1, 1, 0, 0};
    static List<Integer>[] likingStudentsNumberList;

    public static void main(String[] args) throws IOException {
        init();

        // 학생 정보 입력
        StringTokenizer st = null;
        for (int i = 0; i < N * N; i++) {
            st = new StringTokenizer(br.readLine());

            int studentNumber = Integer.parseInt(st.nextToken());

            int likingStudentNumber1 = Integer.parseInt(st.nextToken());
            int likingStudentNumber2 = Integer.parseInt(st.nextToken());
            int likingStudentNumber3 = Integer.parseInt(st.nextToken());
            int likingStudentNumber4 = Integer.parseInt(st.nextToken());

            likingStudentsNumberList[studentNumber].add(likingStudentNumber1);
            likingStudentsNumberList[studentNumber].add(likingStudentNumber2);
            likingStudentsNumberList[studentNumber].add(likingStudentNumber3);
            likingStudentsNumberList[studentNumber].add(likingStudentNumber4);

            // 학생 좌석 찾기
            setStudentSeat(studentNumber);
        }

        printSatisfyingValue();
    }

    private static void setStudentSeat(int studentNumber) {
        SeatInfo bestSeatInfo = null;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (map[i][j] == 0) {
                    // 비어있는 자리일때 주변의 좋아하는 학생 수 & 빈자리 찾기

                    int nextR = 0;
                    int nextC = 0;
                    int countLikingStudentAround = 0, countEmptySeat = 0;
                    // 주변 4자리 탐색
                    for (int k = 0; k < 4; k++) {
                        nextR = i + dr[k];
                        nextC = j + dc[k];

                        if (nextR < 0 || nextC < 0 || nextR >= N || nextC >= N) continue;

                        // 빈자리일때
                        if (map[nextR][nextC] == 0) countEmptySeat++;
                        // 빈자리가 아닐때
                        if (map[nextR][nextC] != 0) {
                            // 특정 학생이 좋아하는 학생 정보 리스트를 가져오기
                            List<Integer> likingStudentsNumber = likingStudentsNumberList[studentNumber];

                            for (int likingStudentNumber : likingStudentsNumber) {
                                if (likingStudentNumber == map[nextR][nextC]) countLikingStudentAround++;
                            }
                        }
                    }

                    // 최선의 좌석이 아직 없다면
                    if (bestSeatInfo == null) {
                        bestSeatInfo = new SeatInfo(i, j, countLikingStudentAround, countEmptySeat);
                    } else {
                        // 이미 최선의 자리가 있다면, 그 자리랑 비교하기

                        if (bestSeatInfo.countLikingStudentAround < countLikingStudentAround) {
                            // 첫번째 조건을 만족하면,
                            bestSeatInfo = new SeatInfo(i, j, countLikingStudentAround, countEmptySeat);
                        } else if (bestSeatInfo.countLikingStudentAround == countLikingStudentAround) {
                            //첫번째 조건을 둘다 만족하면,

                            if (bestSeatInfo.countEmptySeat < countEmptySeat) {
                                //두번째 조건을 만족하면,
                                bestSeatInfo = new SeatInfo(i, j, countLikingStudentAround, countEmptySeat);
                            } else if (bestSeatInfo.countEmptySeat == countEmptySeat) {
                                // 두번째 조건을 둘다 만족하면,

                                if (bestSeatInfo.r > i) {
                                    //행 조건을 만족하면,
                                    bestSeatInfo = new SeatInfo(i, j, countLikingStudentAround, countEmptySeat);
                                } else if (bestSeatInfo.r == i) {
                                    // 행 조건도 만족 못하면,

                                    if (bestSeatInfo.c > j) {
                                        // 열 조건 만족하면
                                        bestSeatInfo = new SeatInfo(i, j, countLikingStudentAround, countEmptySeat);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        map[bestSeatInfo.r][bestSeatInfo.c] = studentNumber;

    }

    private static void printSatisfyingValue() {
        int satisfyingValue = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int studentNumber = map[i][j];

                int nextR = 0;
                int nextC = 0;
                int countLikingStudentAround = 0;
                for (int k = 0; k < 4; k++) {
                    nextR = i + dr[k];
                    nextC = j + dc[k];

                    if (nextR < 0 || nextC < 0 || nextR >= N || nextC >= N) continue;

                    // 특정 학생이 좋아하는 학생 정보 리스트를 가져오기
                    List<Integer> likingStudentsNumber = likingStudentsNumberList[studentNumber];

                    for (int likingStudentNumber : likingStudentsNumber) {
                        if (likingStudentNumber == map[nextR][nextC]) countLikingStudentAround++;
                    }
                }

                if(countLikingStudentAround == 1){
                    satisfyingValue += 1;
                }else if(countLikingStudentAround == 2 ){
                    satisfyingValue += 10;
                }else if(countLikingStudentAround == 3){
                    satisfyingValue += 100;
                }else if(countLikingStudentAround == 4){
                    satisfyingValue += 1000;
                }
            }
        }

        System.out.println(satisfyingValue);
    }

    private static void init() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        map = new int[N][N];
        // 학생이 좋아하는 학생들을 관리하기 위해 사용하는 연결리스트
        // 학생이 1 ~ N*N 이기 때문에 +1 해줌
        likingStudentsNumberList = new ArrayList[N * N + 1];

        // 0번 학생은 없으니, 1부터 할당해주기
        for (int i = 1; i < N * N + 1; i++) {
            likingStudentsNumberList[i] = new ArrayList<>();
        }
    }


    static class SeatInfo {
        int r;
        int c;
        int countLikingStudentAround;
        int countEmptySeat;

        SeatInfo(int r, int c, int countLikingStudentAround, int countEmptySeat) {
            this.r = r;
            this.c = c;
            this.countLikingStudentAround = countLikingStudentAround;
            this.countEmptySeat = countEmptySeat;
        }
    }

}
