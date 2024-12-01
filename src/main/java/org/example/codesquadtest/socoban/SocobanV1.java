package org.example.codesquadtest.socoban;

import java.util.ArrayList;
import java.util.List;

public class SocobanV1 {
    public static void main(String[] args) {
        // 입력 문자열
        String input = """
                Stage 1
                #####
                #OoP#
                #####
                =====
                Stage 2
                  #######
                ###  O  ###
                #    o    #
                # Oo P oO #
                ###  o  ###
                 #   O  #
                 ########
                """;

        // 맵 데이터 처리
        List<Stage> stages = parseStage(input);

        // 맵 데이터 출력
        for (Stage stage : stages) {
            stage.printStage();
            System.out.println();
        }
    }
    // (1) 문자열을 스테이지별로 처리하는 함수
    private static List<Stage> parseStage(String input) {
        List<Stage> stages = new ArrayList<>();
        String[] parts = input.split("====="); // 스테이지 구분

        for (String part : parts) {
            String[] line = part.split("\n");
            if (line.length < 2) continue;

            String name = line[0]; // 스테이지 이름 (첫줄)
            char[][] map = new char[line.length - 1][];
            int[][] mappedData = new int[line.length - 1][];


            // 맵 데이터 저장
            for (int i = 1; i < line.length; i++) {
                map[i - 1] = line[i].toCharArray();
                mappedData[i - 1] = convertData(line[i]);
            }
            stages.add(new Stage(name,map,mappedData));
        }
        return stages;
    }
    // (2) 한 줄의 데이터를 변환 (기호 -> 숫자)
    private static int[] convertData(String s) {
        int[] rowData = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            rowData[i] = switch (c) {
                case ' ' -> 0; // 빈공간
                case 'O' -> 1; // 구멍
                case 'o' -> 2; // 공
                case 'P' -> 3; // 플레이어
                case '#' -> 4; // 벽
                default -> 0;  // 기타 (예: 잘못된 값은 빈 공간으로 처리)
            };
        }
        return rowData;
    }

}
// (3) Stage 클래스: 스테이지 정보를 저장하는 객체
class Stage {
    private final String name; // 스테이지 이름
    private final char[][] map; // 맵 데이터
    private final int[][] mappedData;  // 변환된 숫자 데이터

    public Stage(String name, char[][] map, int[][] mappedData) {
        this.name = name;
        this.map = map;
        this.mappedData = mappedData;
    }
    // 출력하는 메서드
    public void printStage() {
        // 스테이지 이름 출력
        System.out.println(name);

        // 스테이지 원래 지도 출력
        for (char[] line : map) {
            System.out.println(new String(line));
        }
        // 데이터 기반 정보 계산
        int width = mappedData[0].length;
        int height = mappedData.length;
        int holeCount = 0, ballCount = 0, playerRow = -1, playerCol = -1;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (mappedData[i][j] == 1) holeCount++;
                if (mappedData[i][j] == 2) ballCount++;
                if (mappedData[i][j] == 3) {
                    playerRow = i;
                    playerCol = j;
                }
            }
        }
        System.out.println();
        System.out.println("가로크기: " + width);
        System.out.println("세로크기: " + height);
        System.out.println("구멍의 수: " + holeCount);
        System.out.println("공의 수: " + ballCount);
        if (playerRow != -1 && playerCol != -1) {
            System.out.println("플레이어 위치: " + playerRow + "행 " + playerCol + "열");
        } else {
            System.out.println("플레이어 위치: 없음");
        }
    }
}