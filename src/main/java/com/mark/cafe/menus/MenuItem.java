package com.mark.cafe.menus;

import com.mark.cafe.actions.Recipes;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class MenuItem {
    private String name;
    private int price;
    private List<Recipes> recipeSteps;

    protected MenuItem(String name, int price, List<Recipes> recipeSteps){
        this.name = name;
        this.price = price;
        this.recipeSteps = recipeSteps;
    }

    public List<Recipes> getRecipeSteps(){
        return List.copyOf(recipeSteps);
    }

    public boolean checkSteps(Recipes choice, int idx){
        return recipeSteps.get(idx) == choice;
    }

    public abstract void executeStep(int idx);

    public String getName(){
        return name;
    }
    public int getPrice(){
        return price;
    }
}
