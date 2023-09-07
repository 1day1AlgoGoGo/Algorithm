package main.wonbin.day230907;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

/*
    [시뮬레이션]_백준_15787_기차가 어둠을 헤치고 은하수를
    
    기차를 리스트로 만들고 리스트 index로 각 좌석을 구현하였지만
    총 10만 개의 리스트가 만들어질 수 있어서 메모리를 과다하게 사용하였다.
    
 */
public class 기차가_어둠을_헤치고_은하수를_ver1 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        // 기차를 저장할 리스트
        ArrayList<ArrayList<Integer>> lists = new ArrayList<>();

        int temp[] = new int[20];

        for (int i = 0; i < N; i++) {
            
            // 각 기차를 빈 좌석 20개의 리스트로 채움
            ArrayList<Integer> tp = (ArrayList<Integer>) (Arrays.stream(temp).boxed().collect(Collectors.toList()));
            lists.add(tp);
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            // 명령어
            int order = Integer.parseInt(st.nextToken());
            
            // 기차 넘버
            int numberOfTrain = Integer.parseInt(st.nextToken()) - 1;
            
            // 각 명령어 이행
            if (order == 1) {
                // 기차 좌석
                int target = Integer.parseInt(st.nextToken()) - 1;
                lists.get(numberOfTrain).set(target, 1);
            } else if (order == 2) {
                int target = Integer.parseInt(st.nextToken()) - 1;
                lists.get(numberOfTrain).set(target, 0);
            } else if (order == 3) {
                lists.get(numberOfTrain).remove(lists.get(numberOfTrain).size() - 1);
                lists.get(numberOfTrain).add(0, 0);
            } else {
                lists.get(numberOfTrain).remove(0);
                lists.get(numberOfTrain).add(0);
            }
        }

        // 기차 각 좌석의 사람을 이진수로 계산하여 중복체크
        Set<Integer> set = new HashSet<>();
        int result = 0;

        for (int i = 0; i < N; i++) {
            int binary = 0;
            for (int j = 0; j < 20; j++) {
                if (lists.get(i).get(j) == 0)
                    continue;
                binary += (int) Math.pow(2, 19 - j);
            }
            if (!set.contains(binary)) {
                result++;
            }
            set.add(binary);
        }

        System.out.println(result);
        br.close();
    }

}
