package com.mark.cafe.menus.drink;

import com.mark.cafe.actions.AddGreenTeaPowder;
import com.mark.cafe.menus.Drink;
import com.mark.cafe.recipes.DrinkSteps;

import java.util.ArrayList;
import java.util.Arrays;

public class IceGreenTeaLatte extends Drink implements AddGreenTeaPowder {


    public IceGreenTeaLatte(){
        super(DrinkSteps.ICE_GREEN_TEA_LATTE.getStep(),
                4000,
                new ArrayList<>(Arrays.asList(
                        DrinkSteps.PREPARE_CUP,
                        DrinkSteps.ADD_ICE,
                        DrinkSteps.ADD_MILK,
                        DrinkSteps.ADD_GREEN_TEA_POWDER)));
    }


    @Override
    public void executeStep(int idx) {
        switch(idx){
            case 0:
                prepareCup();
                break;
            case 1:
                addIce();
                break;
            case 2:
                addMilk();
                break;
            case 3:
                addGreenTeaPowder();
                break;
        }
    }

    @Override
    public void addGreenTeaPowder(){
        System.out.println("그린티 파우더를 넣었습니다.");
    }

}
