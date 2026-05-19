package com.mark.cafe.converter;

import com.mark.cafe.actions.Recipes;

import java.util.ArrayList;
import java.util.List;

public class RecipeConverter {


    public List<String> toSteps(List<Recipes> recipes){
        List<String> stringSteps = new ArrayList<>();
        for(Recipes recipe : recipes){
            stringSteps.add(recipe.getStep());
        }
        return stringSteps;
    }

    public List<String> toIndexSteps(List<Recipes> recipes){
        List<String> stringSteps = new ArrayList<>();
        for(int i = 0; i < recipes.size(); i++){
            stringSteps.add(i + " : " + recipes.get(i).getStep());
        }
        return stringSteps;
    }
}
