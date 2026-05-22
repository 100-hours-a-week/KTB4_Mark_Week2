package com.mark.cafe.recipes;

import com.mark.cafe.actions.Recipes;

public enum DrinkSteps implements Recipes {
    PREPARE_CUP("컵 준비하기"),
    ADD_ICE("얼음 넣기"),
    ADD_MILK("우유 넣기"),
    ADD_WATER("물 넣기"),
    EXTRACT_SHOT("에스프레소 추출 후 넣기"),
    ADD_GREEN_TEA_POWDER("그린티 파우더 넣기");

    private final String step;

    DrinkSteps(String step){
        this.step = step;
    }

    public String getStep(){
        return step;
    }

}
