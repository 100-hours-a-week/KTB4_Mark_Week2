package com.mark.cafe.messages;

import com.mark.cafe.actions.Messages;

public enum GameMessage implements Messages {
    START_GAME("""
                당신은 카페 아르바이트를 하게 됐습니다.
                들어오는 주문을 처리해주세요.
                일정 금액 이상 매출을 달성하면 퇴근입니다.
                일정 금액 이하로 매출이 떨어지면 쫓겨납니다."""),
    FAIL_GAME("매출을 너무 떨어트렸습니다. 쫓겨났습니다."),
    SUCCESS_GAME("매출을 성공적으로 올렸습니다. 퇴근입니다. 축하합니다."),
    DOWN_RATING("대기열이 가득차서 주문을 받지 못했습니다. 평점이 낮아졌습니다."),
    UP_RATING("평점이 올라갔습니다."),
    END_GAME("게임이 종료됐습니다"),
    LOW_RATING("평점이 너무 낮습니다 쫓겨났습니다.");


    private String message;

    GameMessage(String message){
        this.message = message;
    }

    @Override
    public String getMessage(){
        return message;
    }
}
