package main.jaeseo.day230907;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class 기차가어둠을헤치고은하수를_v1 {
    static BufferedReader br;
    static StringTokenizer st;
    static int N, M, numberOfTrainToCrossingTheMilkyWay;
    static ArrayList<ArrayList<Boolean>> trains = new ArrayList<>();
    static ArrayList<ArrayList<Boolean>> crossedTrains = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        init();

        commandToTrain();

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
            System.out.println(command);
            int trainNumber = Integer.parseInt(st.nextToken());

            // 특정 열차를 가져오기
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

                train.set(20, false);
                train.add(0,false);
                train.remove(train.size() - 1);
            } else if (command == 4) {
                //한칸씩 앞으로 가기

                train.set(1, false);
                train.add(false);
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
