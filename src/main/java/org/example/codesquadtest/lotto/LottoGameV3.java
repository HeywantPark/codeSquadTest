package org.example.codesquadtest.lotto;

import java.util.*;

public class LottoGameV3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Player player = new Player(10000);
        LottoGame game = new LottoGame(player,sc);
        game.start();
    }
}
// 플레이어 클래스
class Player {
    private int balance;

    public Player(int balance) {
        this.balance = balance;
    }
    public int getBalance() {
        return balance;
    }
    public void deduct(int amount){
        balance -= amount;
    }
    public void addPrize(int prize){
        balance += prize;
    }
    public boolean canPlay(int cost) {
        return balance >= cost;
    }
    // 플레이어 숫자 입력 메서드
    public Set<Integer> chooseNum (Scanner sc) {
        Set<Integer> chosenNumbers = new HashSet<>();
        System.out.println("1~45 중 로또 번호를 여섯개 입력하세요.");
        while (chosenNumbers.size() < 6) {
            int input = sc.nextInt();
            if (input < 1 || input > 45) {
                System.out.println("잘못된 입력입니다. 1~45 사이의 숫자를 입력하세요.");
            } else if (chosenNumbers.contains(input)) {
                System.out.println("중복된 번호입니다. 다른 번호를 입력하세요.");
            } else {
                chosenNumbers.add(input);
            }
        }
        return chosenNumbers;
    }
}
// 로또 게임 클래스
class LottoGame {
    private final Player player;
    private final Scanner sc;
    private int gameCount = 0;

    public LottoGame(Player player, Scanner sc) {
        this.player = player;
        this.sc = sc;
    }
    public void start() {
        while (player.canPlay(1000)) {
            gameCount++;
            System.out.println("\n플레이어의 재산 : " + player.getBalance() + "원");
            System.out.println(gameCount + "회차 게임(-1000)");
            player.deduct(1000);

            // 플레이어 숫자 선택
            Set<Integer> playerNum = player.chooseNum(sc);

            // 로또 결과 생성
            LottoResult lottoResult = new LottoResult();

            // 결과 판별 및 상금 계산
            String rank = lottoResult.determineRank(playerNum);
            int prize = lottoResult.getPrize(rank);
            player.addPrize(prize);

            // 결과 출력
            List<Integer> sortedPlayerNum = new ArrayList<>(playerNum);
            Collections.sort(sortedPlayerNum);
            List<Integer> sortedLottoNum = new ArrayList<>(lottoResult.getLottoNum());
            Collections.sort(sortedLottoNum);

            System.out.println("플레이어 번호: " + sortedPlayerNum);
            System.out.println(gameCount + "회차 당첨번호: " + sortedLottoNum + " + 보너스 숫자: " + lottoResult.getBonusNum());
            System.out.println(rank);
            System.out.println("플레이어의 재산: " + player.getBalance() + "원");
        }
        System.out.println("\n재산이 부족하여 게임을 종료합니다. 최종 재산: " + player.getBalance() + "원");
    }
}
// 로또 결과 클래스
class LottoResult {
    private final Set<Integer> lottoNum;
    private final int bonusNum;

    // 로또 번호 생성
    public LottoResult() {
        Random rand = new Random();
        lottoNum = new HashSet<>();
        while (lottoNum.size() < 6) {
            lottoNum.add(rand.nextInt(45) + 1);
        }
        int tempBonus;
        do {
            tempBonus = rand.nextInt(45) + 1;
        } while (lottoNum.contains(tempBonus));
        bonusNum = tempBonus;
    }
    public Set<Integer> getLottoNum() {
        return new HashSet<>(lottoNum);
    }
    public int getBonusNum() {
        return bonusNum;
    }
    // 당첨 등수 판별 메서드
    public String determineRank(Set<Integer> playerNum) {
        Set<Integer> intersection = new HashSet<>(playerNum);
        intersection.retainAll(lottoNum);
        int matchedCount = intersection.size();
        boolean hasBonus = playerNum.contains(bonusNum);

        if (matchedCount == 6) {
            return "1등입니다! (상금 1,000,000원)";
        } else if (matchedCount == 5 && hasBonus) {
            return "2등입니다! (상금 100,000원)";
        } else if (matchedCount == 5) {
            return "3등입니다! (상금 10,000원)";
        } else if (matchedCount == 4) {
            return "4등입니다! (상금 5,000원)";
        } else if (matchedCount == 3) {
            return "5등입니다! (상금 1,000원)";
        } else {
            return "낙첨되었습니다.";
        }
    }
    public int getPrize(String rank) {
        if (rank.contains("1등")) return 1000000;
        if (rank.contains("2등")) return 100000;
        if (rank.contains("3등")) return 10000;
        if (rank.contains("4등")) return 5000;
        if (rank.contains("5등")) return 1000;
        return 0;
    }
}











