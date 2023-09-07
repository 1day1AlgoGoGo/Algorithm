package main.jaeseo.day230906;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.StringTokenizer;

/* 문제에 나온 그대로 구현하면 되었기때문에 크게 어려운 부분은 없었다.
사진틀을 LinkedList로 관리하고, Candidate라는 클래스를 따로 만들어 관리하였다.
추천받은 학생이 사진틀에 이미 걸려있으면 추천수를 그대로 올리고,
사진틀에 이미 걸려있지않으면, 사진틀 여유공간에 따라 바로 추가하거나, 제거후 추가하도록 구현하였다. */
public class 후보추천하기 {
    static int N;
    //사진 틀
    static LinkedList<Candidate> picture = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        StringTokenizer st = init();

        int order = 0;
        while (st.hasMoreTokens()){
            int studentNumber = Integer.parseInt(st.nextToken());

            // 추천받은 학생이 이미 사진틀에 걸려있는지 확인하기.
            boolean isAlreadyRecommend = false;
            int index = 0;
            for (int i = 0; i < picture.size(); i++) {
                Candidate candidate = picture.get(i);
                if(candidate.studentNumber == studentNumber){
                    isAlreadyRecommend = true;
                    index = i;
                }
            }

            // 추천받은 학생이 이미 사진틀에 걸려있는 경우
            if(isAlreadyRecommend){
                Candidate candidate = picture.get(index);

                // 이미 사진틀에 걸려있는 학생의 추천 수 증가
                candidate.recommendNumber++;
            }else{
                // 추천받은 학생이 사진틀에 걸려있지 않은 경우

                //사진틀에 여유가 있는 경우
                if(picture.size() < N){
                    picture.add(new Candidate(studentNumber, order++, 1));
                }else{
                    // 사진틀에 여유가 없는 경우

                    // 문제에 주어진 조건에 맞는 후보를 제거해야되기때문에 해당 조건에 맞게 정렬하기.
                    Collections.sort(picture);

                    picture.remove(0);
                    picture.add(new Candidate(studentNumber, order++, 1));
                }

            }
        }

        // 출력 조건에 맞추기위해 다시 한번 정렬
        Collections.sort(picture, new Comparator<Candidate>() {
            @Override
            public int compare(Candidate o1, Candidate o2) {
                return o1.studentNumber - o2.studentNumber;
            }
        });

        for (int i = 0; i < picture.size(); i++) {
            System.out.print(picture.get(i).studentNumber + " ");
        }

    }

    private static StringTokenizer init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        br.readLine();
        StringTokenizer st = new StringTokenizer(br.readLine());
        return st;
    }

    static class Candidate implements Comparable<Candidate>{
        int studentNumber;
        int order;
        int recommendNumber;

        Candidate(int studentNumber, int order, int recommendNumber){
            this.studentNumber = studentNumber;
            this.order = order;
            this.recommendNumber = recommendNumber;
        }

        // 추천수가 가장 낮은 후보가 있으면 그에 맞게 오름차순 정렬.
        // 추천수 가장 낮은 후보가 2명 이상이면, 사진틀에 등록된 순서 기준으로 오름차순 정렬
        @Override
        public int compareTo(Candidate o) {
            if(this.recommendNumber != o.recommendNumber)   return this.recommendNumber - o.recommendNumber;
            else    return this.order - o.order;
        }
    }
}
