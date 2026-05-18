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

    public void showRecipe(){
        for(Recipes step : recipeSteps){
            System.out.println(step.getStep());
        }
    }
    public List<Recipes> getRecipeSteps(){
        return List.copyOf(recipeSteps);
    }

    public boolean checkSteps(Recipes choice, int idx){
        if(recipeSteps.get(idx) == choice) {
            return true;
        } else{
            return false;
        }
    }

    public abstract void executeStep(int idx);

    public String getName(){
        return name;
    }
    public int getPrice(){
        return price;
    }
}
