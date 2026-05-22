package com.mark.cafe.messages;

import com.mark.cafe.actions.Messages;

public enum MakingMessage implements Messages {
    START_MAKING("제조를 시작합니다."),
    SELECT_RECIPE_ORDER("제조 방식을 올바른 레시피 순서대로 골라주세요"),
    CHOICE_STEP("선택지를 골라주세요"),
    WRONG_CHOICE_STEP("제조중 잘못된 행동을 했습니다 돈을 잃습니다"),
    LATE_MAKING("제조 시간이 지연되어 평점이 감소합니다"),
    FAILED_MAKING("제조 방법이 틀렸습니다 돈을 잃습니다."),
    SUCCESS_MAKING("메뉴 제조에 성공했습니다");


    private String message;

    MakingMessage(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

