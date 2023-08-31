package main.jaeseo.day230831;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/*큰 사각형에서 작은 사각형을 빼주면서 넓이를 구하려고 방법을 고민하였으나, 각각 사각형들에 해당하는 변들을 구하는 방법을 찾지 못했음...
그러다가 결국 블로그를 참고해서 어떤식의 풀이인지만 확인하고, 코드는 직접 구현함..! 참고 (https://yoon990.tistory.com/30)

먼저 가장 핵심인 풀이 내용은, 큰 사각형에 해당하는 변 index 2개를 찾은 후, 해당 변들의 index에 +3을 각각 해주면 작은 사각형의 변들이 나옴!

그렇기때문에 가장 긴 변의 index를 구한 후, 해당 index 기준으로 직전, 직후의 변을 비교하여 더 큰 변이 큰 사각형의 나머지 변이라는 것을 알아낼 수 있었음.
그리고나서, 위에서 찾은 두개의 변 + 3을 해주어서 작은 사각형의 변을 찾아 계산하였음. */
public class 참외밭 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int K = Integer.parseInt(br.readLine());

        List<Integer> l = new LinkedList<>();

        StringTokenizer st = null;

        int max = -1;
        int maxIndex = 0;
        for (int i = 0; i < 6; i++) {
            st = new StringTokenizer(br.readLine());

            st.nextToken();

            l.add(Integer.parseInt(st.nextToken()));

            if (l.get(i) > max) {
                max = l.get(i);
                maxIndex = i;
            }
        }

        int secondMaxIndex = 0;
        if (l.get((maxIndex + 1) % 6) > l.get((maxIndex + 5) % 6)) {
            secondMaxIndex = (maxIndex + 1) % 6;
        } else {
            secondMaxIndex = (maxIndex + 5) % 6;
        }

        int bigRectangle = l.get(maxIndex) * l.get(secondMaxIndex);
        int smallRectangle = l.get((maxIndex + 3) % 6) * l.get((secondMaxIndex + 3) % 6);

        System.out.println((bigRectangle - smallRectangle) * K);
    }
}
