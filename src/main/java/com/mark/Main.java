package com.mark;


import com.mark.cafe.actions.Messages;
import com.mark.cafe.actions.Recipes;
import com.mark.cafe.converter.RecipeConverter;
import com.mark.cafe.menus.MenuItem;
import com.mark.cafe.menus.dessert.ChocoCake;
import com.mark.cafe.menus.dessert.StrawberryCake;
import com.mark.cafe.menus.drink.*;
import com.mark.cafe.player.Player;
import com.mark.cafe.rule.GameRule;
import com.mark.cafe.service.OrderService;
import com.mark.cafe.view.InputView;
import com.mark.cafe.view.OutputView;

import java.util.*;

import static com.mark.cafe.messages.GameMessage.*;
import static com.mark.cafe.messages.MakingMessage.*;
import static com.mark.cafe.messages.OrderMessage.ASK_RECIPE;
import static com.mark.cafe.messages.OrderMessage.DISPLAY_ORDER;

public class Main {
    public static void main(String[] args){

        Random random = new Random();
        Scanner sc = new Scanner(System.in);

        // 메뉴를 랜덤으로 뽑기 쉽게하기 위함, 다형성을 이용해서 상위 타입 참조변수로 하위 타입들 다루기 위함
        MenuItem[] menuItems = new MenuItem[]{
                new IceAmericano(),
                new Americano(),
                new GreenTeaLatte(),
                new IceGreenTeaLatte(),
                new ChocoCake(),
                new StrawberryCake()
        };

        OrderService orderService = new OrderService(menuItems);
        OutputView outputView = new OutputView();
        InputView inputView = new InputView(sc);
        RecipeConverter recipeConverter = new RecipeConverter();

        int playerMoney = (random.nextInt(10) + 1) * 500;

        outputView.printMessage(START_GAME);

        Player player = new Player(playerMoney);

        outputView.checkResult(player.getMoney());


        while(true){
            outputView.printMessage(DISPLAY_ORDER);
            boolean success = false;

            MenuItem menu = orderService.receiveOrder();
            outputView.displayMenu(menu.getName());
            outputView.printMessage(ASK_RECIPE);
            String checkRecipe = inputView.checkRecipe();

            if(GameRule.AGREE_SHOW_RECIPE.equalsIgnoreCase(checkRecipe)){
                outputView.showRecipe(recipeConverter.toSteps(menu.getRecipeSteps()));
            }
            outputView.printMessage(START_MAKING);
            outputView.printMessage(SELECT_RECIPE_ORDER);
            for(int i = 0; i < menu.getRecipeSteps().size(); i++){

                List<Recipes> shuffleRecipe = new ArrayList<Recipes>(menu.getRecipeSteps());
                Collections.shuffle(shuffleRecipe);

                outputView.askShuffleRecipe(recipeConverter.toIndexSteps(shuffleRecipe));
                outputView.printMessage(CHOICE_STEP);

                String stepInput = inputView.choiceStepUser();
                int userChoice;
                try{
                    userChoice = Integer.parseInt(stepInput);
                }catch (NumberFormatException e){
                    outputView.printMessage(WRONG_CHOICE_STEP);
                    player.loseMoney(menu.getPrice());
                    break;
                }

                if(userChoice >= menu.getRecipeSteps().size() || userChoice < 0){
                    outputView.printMessage(WRONG_CHOICE_STEP);
                    player.loseMoney(menu.getPrice());
                    break;
                }

                if(!menu.checkSteps(shuffleRecipe.get(userChoice), i)){
                    outputView.printMessage(FAILED_MAKING);
                    player.loseMoney(menu.getPrice());
                    break;
                } else {
                    menu.executeStep(i);
                }
                if(i == menu.getRecipeSteps().size() - 1){
                    success = true;
                }
            }
            if(success) {
                outputView.printMessage(SUCCESS_MAKING);
                player.earnMoney(menu.getPrice());
            }
            outputView.checkResult(player.getMoney());
            if(GameRule.isFail(player.getMoney())){
                outputView.printMessage(FAIL_GAME);
                break;
            } else if (GameRule.isSuccess(player.getMoney())){
                outputView.printMessage(SUCCESS_GAME);
                break;
            }
        }
        inputView.closeScanner();
    }
}