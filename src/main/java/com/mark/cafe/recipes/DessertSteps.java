package com.mark.cafe.recipes;

import com.mark.cafe.actions.Recipes;

public enum DessertSteps implements Recipes {
    BAKE("오븐에 굽기"),
    PREP("재료 준비하기"),
    MIX("재료 섞기"),
    CHOCO_DECORATOR("초코 데코레이터 하기"),
    STRAWBERRY_DECORATOR("딸기 데코레이터 하기"),
    ADD_CHOCO_CREAM("초코크림 바르기"),
    ADD_STRAWBERRY_CREAM("딸기크림 바르기");

    private final String step;

    DessertSteps(String step){
        this.step = step;
    }

    @Override
    public String getStep(){
        return step;
    }


}
