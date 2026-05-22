package com.mark.cafe.menus;

import com.mark.cafe.actions.Menus;
import com.mark.cafe.actions.Recipes;

import java.util.List;

public class MenuItem {
    private final String name;
    private final int price;
    private final List<Recipes> recipeSteps;

    public MenuItem(Menus menu){
        this.name = menu.getMenuName();
        this.price = menu.getMenuPrice();
        this.recipeSteps = menu.getMenuSteps();
    }

    public List<Recipes> getRecipeSteps(){
        return List.copyOf(recipeSteps);
    }

    public boolean checkSteps(Recipes choice, int idx){
        return recipeSteps.get(idx) == choice;
    }

    public Recipes executeStep(int idx){
        return recipeSteps.get(idx);
    };

    public String getName(){
        return name;
    }
    public int getPrice(){
        return price;
    }
}
