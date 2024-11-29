package org.example.codesquadtest.puzzle;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class puzzle15V2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("재미있는 15 퍼즐!");

        int turn = 1;

        // 숫자 리스트 생성
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            list.add(String.format("%2d", i)); // 숫자를 두 자리로 포맷팅
        }
        list.add(" "); // 빈칸 추가

        // 숫자 섞기
        Collections.shuffle(list);

        // 2차원 배열에 값 채우기
        String[][] puzzle = new String[4][4];
        int index = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                puzzle[i][j] = "[" + list.get(index++) + "]";
            }
        }
        // 게임 루프
        while(true) {
            System.out.println("Turn " + turn);
            printPuzzle(puzzle); // 퍼즐 출력
            System.out.println("숫자 입력>");

            int num;

            try {
                num = sc.nextInt();
                if (num < 0 || num > 15) {
                    System.out.println("1~15 사이의 숫자를 입력하세요");
                    continue;
                }
            } catch (Exception e) {
                System.out.println("올바른 숫자를 입력하세요.");
                sc.nextLine(); // 잘못된 입력 버퍼 비우기
                continue;
            }
            // 숫자 위치와 빈칸 위치 찾기 및 교환
            boolean moved = false;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (puzzle[i][j].equals("[" + String.format("%2d", num) + "]")) {
                        moved = moveTile(i, j, puzzle); // 숫자 이동
                        break;
                    }
                }
                if (moved) break;
            }
            if (!moved) {
                System.out.println("선택한 숫자는 빈칸 옆에 있어야 합니다.");
                continue;
            }
            // 턴 증가 및 퍼즐 완성 확인
            turn++;
            if (isSorted(puzzle)){
                System.out.println("축하합니다! " + turn + "턴만에 퍼즐을 완성했습니다!");
                break;
            }
        }
    }
    private static boolean moveTile(int i, int j, String[][] puzzle) {
        int[] dr = {1,-1,0,0};
        int[] dc = {0,-0,1,-1};

        for (int k = 0; k < 4; k++) {
            int nr = i + dr[k];
            int nc = j + dc[k];
            if (nr >= 0 && nr < 4 && nc >= 0 && nc < 4) {
                if (puzzle[nr][nc].equals("[ ]")) {
                    puzzle[nr][nc] = puzzle[i][j];
                    puzzle[i][j] = "[ ]";
                    return true;
                }
            }
        }
        return false;
    }
    private static void printPuzzle(String[][] puzzle) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(puzzle[i][j] + " ");
            }
            System.out.println();
        }
    }
    private static boolean isSorted(String[][] puzzle) {
        int expected = 1;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == 3 && j == 3) return puzzle[i][j].equals("[ ]"); //마지막 칸인 경우 빈칸([  ])인지 확인
                if (!puzzle[i][j].equals("[" + String.format("%2d", expected++) + "]")) {
                    return false;
                }
            }
        }
        return true;
    }
}
