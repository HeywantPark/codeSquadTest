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

            // 맵 데이터 저장
            for (int i = 1; i < line.length; i++) {
                map[i - 1] = line[i].toCharArray();
            }
            stages.add(new Stage(name,map));
        }
        return stages;
    }

}
// (2) Stage 클래스: 스테이지 정보를 저장하는 객체
class Stage {
    private final String name; // 스테이지 이름
    private final char[][] map; // 맵 데이터

    public Stage(String name, char[][] map) {
        this.name = name;
        this.map = map;
    }
    public void printStage() {
        System.out.println(name);
        for (char[] line : map) {
            System.out.println(new String(line));
        }
    }
}