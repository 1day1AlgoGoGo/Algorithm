package main.wonbin.day230915;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

/*
    [시뮬레이션]_백준_17140_이차원 배열과 연산

    - 행과 열의 최대 길이를 계산하여 R, C 연산을 구분하였다.
    - 배열 값과 해당 값의 횟수를 class로 저장하여 ArrayList에 담아 정렬하였다.
    - 정렬된 값을 배열에 다시 집어넣었다.
    - 작업 단위를 메소드 단위로 나누어 구현하려고 노력했다.

 */

public class 이차원_배열과_연산 {

    static class NumCount implements Comparable<NumCount> {
        int num, count;

        NumCount(int num, int count) {
            this.num = num;
            this.count = count;
        }

        @Override
        public int compareTo(NumCount o) {
            if (this.count == o.count) {
                return this.num - o.num;
            }
            return this.count - o.count;
        }
    }

    static int arrays[][];

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        arrays = new int[101][101];


        for (int i = 1; i <= 3; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= 3; j++) {
                arrays[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int result = -1;
        int cnt = 0;

        // 최대 100번까지 정렬
        while (cnt <= 100) {
            if (arrays[r][c] == k) {
                result = cnt;
                break;
            }

            sortingArrays(checkingRAndCCalulation());

            cnt++;
        }
        System.out.println(result);

        br.close();
    }

    // 행을 정렬하는 메소드, calculationR이 true면 R 연산, false면 C 연산
    private static void sortingArrays(boolean calculationR) {

        // 배열값과 그 횟수를 키밸류로 저장하는 map
        Map<Integer, Integer> map;
        // 배열값과 그 횟수를 class로 저장해 정렬할 리스트
        ArrayList<NumCount> sortingList;
        // map에서 key값만 반환하는 keySet
        List<Integer> keySet;

        for (int i = 1; i <= 100; i++) {

            map = new HashMap<>();
            for (int j = 1; j <= 100; j++) {

                if (calculationR) {
                    if (arrays[i][j] == 0)
                        continue;
                    map.put(arrays[i][j], map.getOrDefault(arrays[i][j], 0) + 1);

                } else {
                    if (arrays[j][i] == 0)
                        continue;
                    map.put(arrays[j][i], map.getOrDefault(arrays[j][i], 0) + 1);

                }

            }

            // 키밸류 쌍이 저장되어 정렬됨.
            sortingList = new ArrayList<>();
            // map에서 key값만 반환하는 keySet
            keySet = map.keySet().stream().collect(Collectors.toList());
            for (int num : keySet) {
                if (sortingList.size() >= 50)
                    break;
                sortingList.add(new NumCount(num, map.get(num)));
            }
            // 리스트를 정렬 규칙에 맞게 정렬
            Collections.sort(sortingList);

            if (calculationR) {
                Arrays.fill(arrays[i], 0);
            } else {
                for (int j = 1; j <= 100; j++) {
                    arrays[j][i] = 0;
                }
            }
            // 배열의 인덱스
            int arrayIndex = 1;
            // 정렬된 리스트의 인덱스
            int listIndex = 0;

            while (arrayIndex <= 100 && listIndex < sortingList.size()) {
                if (calculationR) {
                    arrays[i][arrayIndex++] = sortingList.get(listIndex).num;
                    arrays[i][arrayIndex++] = sortingList.get(listIndex).count;
                } else {
                    arrays[arrayIndex++][i] = sortingList.get(listIndex).num;
                    arrays[arrayIndex++][i] = sortingList.get(listIndex).count;
                }

                listIndex++;
            }
        }
    }

    // 행과 열의 최댓값을 비교하여 정렬할 대상을 비교
    private static boolean checkingRAndCCalulation() {

        int colMax = 0;
        int rowMax = 0;
        for (int i = 1; i <= 100; i++) {
            int nowColLength = 0;
            for (int j = 1; j <= 100; j++) {
                if (arrays[i][j] != 0) {
                    nowColLength++;
                }
                if (nowColLength > colMax) {
                    colMax = Math.max(nowColLength, colMax);
                }
            }
            if (nowColLength == 0)
                break;
            else
                rowMax++;
        }

        if (rowMax >= colMax) {
            return true;
        } else {
            return false;
        }

    }


}
