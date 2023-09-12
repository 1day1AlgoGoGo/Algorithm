package BOJ_Silver.S2_15787;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class 기차가어둠을헤치고은하수를 {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int[][] trains = new int[N][20];
        Set<String> set = new HashSet<>();

        int seatNo;
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(in.readLine());
            int order = Integer.parseInt(st.nextToken());
            int trainNo = Integer.parseInt(st.nextToken()) - 1;

            switch (order) {
                case 1:
                    seatNo = Integer.parseInt(st.nextToken()) - 1;
                    if (trains[trainNo][seatNo] == 0) {
                        trains[trainNo][seatNo] = 1;
                    }

                    break;
                case 2:
                    seatNo = Integer.parseInt(st.nextToken()) - 1;
                    if (trains[trainNo][seatNo] == 1) {
                        trains[trainNo][seatNo] = 0;
                    }

                    break;
                case 3:
                    for (int k = 19; k > 0; k--) {
                        trains[trainNo][k] = trains[trainNo][k - 1];
                    }

                    trains[trainNo][0] = 0;
                    break;
                case 4:
                    for (int k = 0; k < 19; k++) {
                        trains[trainNo][k] = trains[trainNo][k + 1];
                    }

                    trains[trainNo][19] = 0;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + order);
            }
        }

        for (int[] train : trains) {
            set.add(Arrays.toString(train));
        }

        System.out.println(set.size());
    }

    public static void main2(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int[] trains = new int[N + 1];
        Set<Integer> set = new HashSet<>();

        int seatNo;
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(in.readLine());
            int order = Integer.parseInt(st.nextToken());
            int trainNo = Integer.parseInt(st.nextToken());

            switch (order) {
                case 1:
                    seatNo = Integer.parseInt(st.nextToken()) - 1;
                    trains[trainNo] = trains[trainNo] | (1 << seatNo);
                    break;
                case 2:
                    seatNo = Integer.parseInt(st.nextToken()) - 1;
                    trains[trainNo] = trains[trainNo] & ~(1 << seatNo);
                    break;
                case 3:
                    trains[trainNo] = trains[trainNo] << 1;
                    trains[trainNo] = trains[trainNo] & (1 << 20) - 1;
                    break;
                case 4:
                    trains[trainNo] = trains[trainNo] >> 1;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + order);
            }
        }

        for (int i = 1; i < trains.length; i++) {
            set.add(trains[i]);
        }

        System.out.println(set.size());
    }
}
