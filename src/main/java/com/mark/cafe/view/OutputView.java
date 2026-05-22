package com.mark.cafe.view;

import com.mark.cafe.actions.Messages;
import com.mark.cafe.actions.Recipes;

import java.util.List;

public class OutputView {


    public void displayMenu(String menuName){
        System.out.println("메뉴 이름은 " + menuName + "입니다.");
    }

    public void printMessage(Messages message){
        System.out.println(message.getMessage());
    }

    public void printExecuteStep(Recipes recipe){
        System.out.println(recipe.getStep());
    }

    public void checkResult(int money){
        System.out.println("현재 매출은 : " + money + " 입니다.");
    }

    public void showRecipe(List<String> recipes){
        for(String recipe: recipes){
            System.out.println(recipe);
        }
    }

}
