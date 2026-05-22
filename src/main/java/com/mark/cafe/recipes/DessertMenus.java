package com.mark.cafe.recipes;

import com.mark.cafe.actions.Menus;
import com.mark.cafe.actions.Recipes;
import java.util.List;


public enum DessertMenus implements Menus {
    STRAWBERRY_CAKE("딸기케이크", 5000,
                List.of(DessertSteps.PREP,
                    DessertSteps.MIX,
                    DessertSteps.BAKE,
                    DessertSteps.ADD_STRAWBERRY_CREAM,
                    DessertSteps.STRAWBERRY_DECORATOR)),

    CHOCO_CAKE("초코케이크", 5000, List.of(
            DessertSteps.PREP,
            DessertSteps.MIX,
            DessertSteps.BAKE,
            DessertSteps.ADD_CHOCO_CREAM,
            DessertSteps.CHOCO_DECORATOR));



    private final String menuName;
    private final int menuPrice;
    private final List<Recipes> menuSteps;

    DessertMenus(String menuName, int menuPrice, List<Recipes> menuSteps){
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuSteps = menuSteps;
    }

    @Override
    public String getMenuName() {
        return menuName;
    }

    @Override
    public int getMenuPrice() {
        return menuPrice;
    }

    @Override
    public List<Recipes> getMenuSteps() {
        return menuSteps;
    }
}
