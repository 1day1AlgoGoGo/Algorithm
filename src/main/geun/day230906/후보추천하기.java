package BOJ_Silver.S1_1713;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class 후보추천하기 {
    private static class Student {
        private final int number;
        private int count;
        private final int time;

        public Student(int number, int count, int time) {
            this.number = number;
            this.count = count;
            this.time = time;
        }

        public int getNumber() {
            return number;
        }

        public int getCount() {
            return count;
        }

        public int getTime() {
            return time;
        }

        public void recommended() {
            this.count += 1;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(in.readLine());
        int T = Integer.parseInt(in.readLine());
        StringTokenizer st = new StringTokenizer(in.readLine());

        List<Student> list = new LinkedList<>();
        Map<Integer, Student> map = new HashMap<>();
        for (int t = 0; t < T; t++) {
            int number = Integer.parseInt(st.nextToken());

            boolean check = false;
            if (map.containsKey(number)) {
                for (Student student : list) {
                    if (student.getNumber() == number) {
                        student.recommended();
                        check = true;
                        break;
                    }
                }

                if (check) {
                    continue;
                }

            } else {
                map.put(number, new Student(number, 0, t));
            }


            if (list.size() == N) {
                list.sort(((o1, o2) -> {
                    if (o1.getCount() == o2.getCount()) {
                        return o1.getTime() - o2.getTime();
                    }

                    return o1.getCount() - o2.getCount();
                }));

                map.remove(list.get(0).getNumber());
                list.remove(0);
            }

            list.add(map.get(number));
        }

        StringBuilder builder = new StringBuilder();
        list.sort(Comparator.comparingInt(Student::getNumber));
        for (Student student : list) {
            builder.append(student.getNumber()).append(" ");
        }

        System.out.println(builder);
    }
}