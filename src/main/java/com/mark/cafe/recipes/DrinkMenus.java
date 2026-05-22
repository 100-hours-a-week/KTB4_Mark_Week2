package com.mark.cafe.recipes;

import com.mark.cafe.actions.Menus;
import com.mark.cafe.actions.Recipes;

import java.util.List;

public enum DrinkMenus implements Menus {
    ICE_AMERICANO(
            "아이스 아메리카노",
            4000,
            List.of(
                    DrinkSteps.PREPARE_CUP,
                    DrinkSteps.ADD_ICE,
                    DrinkSteps.ADD_WATER,
                    DrinkSteps.EXTRACT_SHOT
                    )
    ),
    AMERICANO("아메리카노",
            3500,
            List.of(
                    DrinkSteps.PREPARE_CUP,
                    DrinkSteps.ADD_WATER,
                    DrinkSteps.EXTRACT_SHOT
            )
            ),
    ICE_GREEN_TEA_LATTE("아이스 그린티라떼",
            4000,
            List.of(
                    DrinkSteps.PREPARE_CUP,
                    DrinkSteps.ADD_ICE,
                    DrinkSteps.ADD_MILK,
                    DrinkSteps.ADD_GREEN_TEA_POWDER)
    ),
    GREEN_TEA_LATTE("그린티라떼",
            4000,
            List.of(
                DrinkSteps.PREPARE_CUP,
                DrinkSteps.ADD_MILK,
                DrinkSteps.ADD_GREEN_TEA_POWDER)
            );

    private final String menuName;
    private final int menuPrice;
    private final List<Recipes> menuSteps;

    DrinkMenus(String menuName, int menuPrice, List<Recipes> menuSteps){
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuSteps = menuSteps;
    }
    @Override
    public String getMenuName(){
        return menuName;
    }

    @Override
    public int getMenuPrice() {
        return menuPrice;
    }

    @Override
    public List<Recipes> getMenuSteps() {
        return List.copyOf(menuSteps);
    }


}
