package com.mark.cafe.menus.drink;

import com.mark.cafe.menus.Drink;
import com.mark.cafe.recipes.DrinkSteps;
import com.mark.cafe.actions.ExtractShot;

import java.util.ArrayList;
import java.util.Arrays;

public class IceAmericano extends Drink implements ExtractShot {


    public IceAmericano(){
        super(DrinkSteps.ICE_AMERICANO.getStep(),
                4000,
                new ArrayList<>(Arrays.asList(
                        DrinkSteps.PREPARE_CUP,
                        DrinkSteps.ADD_ICE,
                        DrinkSteps.ADD_WATER,
                        DrinkSteps.EXTRACT_SHOT)));
    }

    @Override
    public void extractShot() {
        System.out.println("샷 추가 했습니다");
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
                addWater();
                break;
            case 3:
                extractShot();
                break;
        }
    }


}
