package com.mark.cafe.menus.dessert;

import com.mark.cafe.menus.Dessert;
import com.mark.cafe.recipes.DessertSteps;

import java.util.ArrayList;
import java.util.Arrays;

public class ChocoCake extends Dessert {

    public ChocoCake(){
        super(DessertSteps.CHOCO_CAKE.getStep(),
                5000,
                new ArrayList<>(Arrays.asList(
                        DessertSteps.PREP,
                        DessertSteps.MIX,
                        DessertSteps.BAKE,
                        DessertSteps.ADD_CHOCO_CREAM,
                        DessertSteps.CHOCO_DECORATOR)));
    }

    @Override
    public void addCream(){
        System.out.println(DessertSteps.ADD_CHOCO_CREAM.getStep());
    }

    @Override
    public void decorator(){
        System.out.println(DessertSteps.CHOCO_DECORATOR.getStep());
    }

    @Override
    public void executeStep(int idx) {
        switch(idx){
            case 0:
                prep();
                break;
            case 1:
                mix();
                break;
            case 2:
                bake();
                break;
            case 3:
                addCream();
                break;
            case 4:
                decorator();
                break;
        }
    }

}
