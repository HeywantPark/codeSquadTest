package org.example.codesquadtest.puzzle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class puzzle15V1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("간단한 숫자 퍼즐");

        int turn = 1;
        ArrayList<Integer> numbers = new ArrayList<>();

        System.out.println("Turn " + turn);

        for (int i = 1; i <= 8; i++) {
            numbers.add(i);
        }
        // 무작위로 섞기
        Collections.shuffle(numbers);
        System.out.println(numbers);

        while(true){
            System.out.println("교환할 두 숫자를 입력>");
            String input = sc.nextLine();

            // 쉼표와 공백(선택적으로 포함)을 기준으로 분리
            String[] parts = input.split(",\\s*");
            if(parts.length != 2){
                System.out.println("올바른 형식이 아닙니다. 예: 1,2");
                continue;
            }
            try {
                // 숫자 파싱
                int num1 = Integer.parseInt(parts[0]);
                int num2 = Integer.parseInt(parts[1]);

                // 변경할 숫자의 인덱스 찾기
                int index1 = -1, index2 = -1;
                for(int i = 0 ; i < numbers.size(); i++){
                    if (numbers.get(i) == num1) index1 = i;
                    if (numbers.get(i) == num2) index2 = i;
                }
                // 유효성 검사
                if(index1 == -1 || index2 == -1){
                    System.out.println("입력된 숫자가 리스트에 없습니다. 다시 시도하세요.");
                    continue;
                }
                // 숫자 교환
                int temp = numbers.get(index1);
                numbers.set(index1, numbers.get(index2));
                numbers.set(index2, temp);

                // 결과 출력
                turn++;
                System.out.println(numbers);
                System.out.println("Turn " + turn);

                if(isSorted(numbers)){
                    System.out.println("축하합니다! " + turn + "턴만에 퍼즐을 완성하셨습니다!");
                    break;
                }

            } catch (Exception e) {
                System.out.println("잘못 입력하셨습니다. 다시 입력하세요.");
            }
        }
    }
    public static boolean isSorted(ArrayList<Integer> numbers) {
        for (int i = 0; i < numbers.size() - 1; i++) {
            if (numbers.get(i) > numbers.get(i + 1)) return false;
        }
        return true;
    }
}
