package main.jaeseo.day230907;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
문제에서 주어진 설명 그대로를 구현하려고 했다.
기차들과 기차안에 있는 좌석들을 ArrayList<ArrayList<Boolean>> 으로 구현했다.
승차/하차 두가지 상태만 존재하기에 boolean으로 하였다.
앞으로 한칸씩 미는거, 뒤로 한칸씩 미는거를 쉽게 구현하는 방법을 고민하였고, 가장 편할거같다고 떠오른 방법이 ArrayList에 22개를 추가해놓고 add, remove를 하는 방법이었다. (2개의 여유분을 두기)

그리고 기차가 은하수를 건넜는지 안건넜는지 판단하기위해, 이를 관리하는 ArrayList<ArrayList<Boolean>>를 하나 더 추가했다.
isCrossedTrain() 함수를 통해 검증하려고 했다.

처음에는 LinkedList로 구현했는데 시간초과가 나서 ArrayList로 바꾸었다.
근데 IndexOutOfBounds에러가 자꾸 난다... 이유를 모르겠다... 알려주세요...
*/
public class 기차가어둠을헤치고은하수를_v1 {
    static BufferedReader br;
    static StringTokenizer st;
    static int N, M, numberOfTrainToCrossingTheMilkyWay;
    static ArrayList<ArrayList<Boolean>> trains = new ArrayList<>();
    static ArrayList<ArrayList<Boolean>> crossedTrains = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        init();

        // 명령을 수행하는 함수
        commandToTrain();

        // 은하수를 건너는 함수
        crossTheMilkyWay();

        System.out.println(numberOfTrainToCrossingTheMilkyWay);
    }

    private static void init() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        for (int i = 0; i < N; i++) {
            // false가 22개 추가되어있는 ArrayList 추가.
            // 승객들 앞으로 이동하거나 뒤로 이동시킬때 편하게 하기 위해 22개 추가
            trains.add(new ArrayList<>(Arrays.asList(false, false, false, false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false, false, false, false,
                    false, false)));
        }
    }

    private static void commandToTrain() throws IOException {
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            int command = Integer.parseInt(st.nextToken());
            int trainNumber = Integer.parseInt(st.nextToken());

            // 해당 열차를 가져오기
            ArrayList<Boolean> train = trains.get(trainNumber);

            if (command == 1) {
                int seatNumber = Integer.parseInt(st.nextToken());

                // 기차 특정 좌석에 사람 태우기
                train.set(seatNumber, true);
            } else if (command == 2) {
                int seatNumber = Integer.parseInt(st.nextToken());

                // 기차 특정 좌석에 사람 내리기
                train.set(seatNumber, false);
            } else if (command == 3) {
                //한칸씩 뒤로가기

                // 맨뒤에 있는 사람을 먼저 내리게 만들기
                train.set(20, false);
                // 맨앞에 좌석 추가하기 (자동으로 한칸씩 뒤로감)
                train.add(0,false);
                // 좌서관리를 위해 마지막 좌석 제거
                train.remove(train.size() - 1);
            } else if (command == 4) {
                //한칸씩 앞으로 가기

                // 맨 앞 사람 내리기
                train.set(1, false);
                // 맨 뒤에 좌석 추가
                train.add(false);
                // 맨 앞 좌석 제거(자리 관리를 위해)
                train.remove(0);
            }
        }
    }

    private static void crossTheMilkyWay() {
        for (int i = 0; i < trains.size(); i++) {
            ArrayList<Boolean> train = trains.get(i);

            if (!isCrossedTrain(train)) {
                //이미 지나간 기차가 아니면?
                numberOfTrainToCrossingTheMilkyWay++;

                crossedTrains.add(train);
            }
        }
    }

    //이미 건넌 기차인지 확인하는 함수
    private static boolean isCrossedTrain(ArrayList<Boolean> train) {
        for (int i = 0; i < crossedTrains.size(); i++) {
            ArrayList<Boolean> crossedTrain = crossedTrains.get(i);

            // 이미 지나간 기차와 비교했을때 다른지 체크하는 플레그
            boolean isDifferent = false;
            for (int j = 0; j < train.size(); j++) {
                if (crossedTrain.get(j) != train.get(j)) {
                    isDifferent = true;
                }
            }

            if (!isDifferent) return true;
        }
        return false;
    }
}
