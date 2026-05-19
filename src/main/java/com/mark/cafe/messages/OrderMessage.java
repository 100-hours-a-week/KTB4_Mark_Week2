package com.mark.cafe.messages;

import com.mark.cafe.actions.Messages;

public enum OrderMessage implements Messages {
    DISPLAY_ORDER("주문이 들어왔습니다."),
    ASK_RECIPE("레시피를 확인하시겠습니까? Y / N");


    private String message;

    OrderMessage(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
