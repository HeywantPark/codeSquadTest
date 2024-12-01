package org.example.codesquadtest.socoban;

import java.util.Scanner;

public class SocobanV2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // 입력 문자열
        String input = """
                Stage 2
                  #######
                ###  O  ###
                #    o    #
                # Oo P oO #
                ###  o  ###
                 #   O  #
                 ########
                """;

        // 지도 초기화 및 플레이어 위치 찾기
        GameStage stage = parseStage(input);
        int[] playerPos = stage.findPlayer();

        // 초기 상태 출력
        System.out.println(stage.getName());
        stage.printMap();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("SOKOBAN> ");
            String commands = scanner.nextLine();

            for (char c : commands.toCharArray()) {
                if (c == 'q') {
                    System.out.println("Bye~");
                    return;
                }
                // 플레이어의 이동 결과 출력
                boolean moved = stage.movePlayer(c,playerPos);
                if (!moved) {
                    System.out.printf("%c: (경고!) 해당 명령을 수행할 수 없습니다!\n", Character.toUpperCase(c));
                } else {
                    System.out.printf("%c: 이동 성공!\n", Character.toUpperCase(c));
                    stage.printMap();
                }
            }
        }
    }

    // (1) 문자열을 GameStage 객체로 변환
    private static GameStage parseStage(String input) {
        String[] lines = input.split("\n");
        String name = lines[0];
        char[][] map = new char[lines.length - 1][];

        for (int i = 1; i < lines.length; i++) {
            map[i - 1] = lines[i].toCharArray();
        }
        return new GameStage(name, map);
    }

}
// (2) Stage 클래스: 스테이지 정보 및 동작을 저장하는 객체
class GameStage {
    private final String name; // 스테이지 이름
    private final char[][] map; // 맵 데이터


    public GameStage(String name, char[][] map) {
        this.name = name;
        this.map = map;
    }
    public String getName() {
        return name;
    }
    // 현재 지도 출력
    public void printMap() {

        for (char[] line : map) {
            System.out.println(new String(line));
        }
        System.out.println();
    }
    // 플레이어 위치 찾기
    public int[] findPlayer() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 'P') {
                    return new int[]{i, j};
                }
            }
        }
        return null; // 플레이어가 없으면 null 반환
    }
    public boolean movePlayer(char c, int[] playerPos) {
        int[] direction = switch (c) {
            case 'w' -> new int[]{-1, 0}; // 위
            case 'a' -> new int[]{0, -1}; // 왼쪽
            case 's' -> new int[]{1, 0}; // 아래
            case 'd' -> new int[]{0, 1}; // 오른쪽
            default -> null;
        };
        if (direction == null) {
            System.out.printf("%c: (경고!) 지원하지 않는 명령입니다!\n",Character.toUpperCase(c));
        }

        int nr = direction[0] + playerPos[0];
        int nc = direction[1] + playerPos[1];

        if (nr < 0 || nr >= map.length || nc < 0 || nc >= map[0].length) {
            return false;
        }
        char targetTile = map[nr][nc];

        if (targetTile == ' ' || targetTile == '0') {
            map[playerPos[0]][playerPos[1]] = ' '; // 이전 위치를 빈 공간으로
            map[nr][nc] = 'P';            // 새 위치에 플레이어 배치
            playerPos[0] = nr;
            playerPos[1] = nc;
            return true;
        }
        return false;
    }
}
