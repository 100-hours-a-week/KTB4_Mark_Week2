package com.mark.cafe.menus.drink;

import com.mark.cafe.actions.AddGreenTeaPowder;
import com.mark.cafe.menus.Drink;
import com.mark.cafe.recipes.DrinkSteps;

import java.util.ArrayList;
import java.util.Arrays;

public class GreenTeaLatte extends Drink implements AddGreenTeaPowder {


    public GreenTeaLatte(){
        super( DrinkSteps.GREEN_TEA_LATTE.getStep(),
                4000,
                new ArrayList<>(Arrays.asList(
                        DrinkSteps.PREPARE_CUP,
                        DrinkSteps.ADD_MILK,
                        DrinkSteps.ADD_GREEN_TEA_POWDER
                )));
    }


    @Override
    public void executeStep(int idx) {
        switch(idx){
            case 0:
                prepareCup();
                break;
            case 1:
                addMilk();
                break;
            case 2:
                addGreenTeaPowder();
                break;
        }
    }

    @Override
    public void addGreenTeaPowder(){
        System.out.println("그린티 파우더를 넣었습니다.");
    }

}
