package org.example.codesquadtest.lotto;

import java.util.*;

public class LottoGameV2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Set<Integer> playerNum = new HashSet<>();

        System.out.println("1~45 중 로또 번호를 여섯개 입력하세요.");

        // (1) 플레이어 숫자 입력받기
        while (playerNum.size() < 6) {
            int inputNum = sc.nextInt();

            if (!isValid(inputNum)){
                System.out.println("잘못된 입력입니다. 1~45 사이의 숫자를 입력하세요.");
                continue;
            }
            if (isDuplicated(inputNum, playerNum)){
                System.out.println("중복된 번호입니다. 다른 번호를 입력하세요.");
                continue;
            }
            playerNum.add(inputNum);
        }
        // (2) 랜덤 숫자 추출하기
        Set<Integer> randomNum = new HashSet<>();
        Random rand = new Random();

        while (randomNum.size() < 6) {
            int num = rand.nextInt(45) + 1; // 1~45 사이의 랜덤 숫자
            randomNum.add(num);
        }
        // (3) 보너스 번호 추출하기
        int bonusNum;
        do {
            bonusNum = rand.nextInt(45) + 1;
        } while (randomNum.contains(bonusNum));

        // (4) 오름 차순 정렬
        List<Integer> sortedPlayerNum = new ArrayList<>(playerNum);
        Collections.sort(sortedPlayerNum);

        List<Integer> sortedRandomNum = new ArrayList<>(randomNum);
        Collections.sort(sortedRandomNum);

        // (5) 로또 번호 출력
        System.out.println("로또 당첨 숫자 : " +sortedRandomNum + " + 보너스 숫자 " + bonusNum);
        System.out.println("플레이의 숫자 : " +sortedPlayerNum);

        String rank = determineRank(playerNum, randomNum, bonusNum);
        System.out.println("결과: " + rank);

    }
    // 당첨 등수 판별 메서드
    private static String determineRank(Set<Integer> playerNum, Set<Integer> randomNum, int bonusNum) {
        // 1. 교집합 계산: 당첨 번호와 일치하는 숫자 개수
        Set<Integer> intersection = new HashSet<>(playerNum);
        intersection.retainAll(randomNum);
        int matchedCount = intersection.size();

        // 2. 보너스 번호 포함 여부 확인
        boolean hasBouns = playerNum.contains(bonusNum);

        // 3. 등수 판별
        if (matchedCount == 6) {
            return "1등 (6개 숫자 모두 일치)";
        } else if (matchedCount == 5 && hasBouns) {
            return "2등 (5개 숫자 + 보너스 숫자 일치)";
        } else if (matchedCount == 5) {
            return "3등 (5개 숫자 일치)";
        } else if (matchedCount == 4) {
            return "4등 (4개 숫자 일치)";
        } else if (matchedCount == 3) {
            return "5등 (3개 숫자 일치)";
        } else {
            return "낙첨입니다.";
        }
    }

    // 숫자 유효성 검사 메서드
    public static boolean isValid(int inputNum) {
        return inputNum >= 1 && inputNum <= 45;
    }

    // 중복확인 메서드
    private static boolean isDuplicated(int inputNum, Set<Integer> playerNum) {
        return playerNum.contains(inputNum);
    }
}
