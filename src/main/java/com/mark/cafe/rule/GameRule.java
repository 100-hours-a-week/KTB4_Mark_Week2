package com.mark.cafe.rule;

public class GameRule {
    private static final int SUCCESS_AMOUNT = 10000;
    private static final int FAIL_AMOUNT = 0;
    public static final String AGREE_SHOW_RECIPE = "Y";

    public static boolean isSuccess(int money){
        return money >= SUCCESS_AMOUNT;
    }

    public static boolean isFail(int money){
        return money < FAIL_AMOUNT;
    }
}
