package com.mark.cafe.menus.drink;

import com.mark.cafe.actions.Recipes;
import com.mark.cafe.menus.Drink;
import com.mark.cafe.recipes.DrinkSteps;
import com.mark.cafe.actions.ExtractShot;

import java.util.ArrayList;
import java.util.Arrays;

public class Americano extends Drink implements ExtractShot {


    public Americano(){
        super(DrinkSteps.AMERICANO.getStep(),
                3500,
                new ArrayList<>(Arrays.asList(
                        DrinkSteps.PREPARE_CUP,
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
                addWater();
                break;
            case 2:
                extractShot();
                break;
        }
    }

}
