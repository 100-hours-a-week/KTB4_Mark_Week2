package com.mark.cafe.service;

import com.mark.cafe.actions.Messages;
import com.mark.cafe.actions.Recipes;
import com.mark.cafe.converters.RecipeConverter;
import com.mark.cafe.entity.Order;
import com.mark.cafe.entity.Player;
import com.mark.cafe.rule.GameRule;
import com.mark.cafe.view.InputView;
import com.mark.cafe.view.OutputView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static com.mark.cafe.messages.GameMessage.*;
import static com.mark.cafe.messages.MakingMessage.*;
import static com.mark.cafe.messages.OrderMessage.ASK_RECIPE;

public class PlayService{
    private final BlockingQueue<Order> orderQueue;
    private final OutputView outputView;
    private final InputView inputView;
    private final Player player;


    public PlayService(BlockingQueue<Order> orderQueue, OutputView outputView, InputView inputView, Player player){
        this.orderQueue = orderQueue;
        this.outputView = outputView;
        this.inputView = inputView;
        this.player = player;
    }
    
    public Messages play() {

        outputView.printMessage(START_GAME);
        outputView.checkResult(player.getMoney());
        while (true) {
            if(GameRule.isLowRating(player.getRating())){
                outputView.printMessage(LOW_RATING);
                return END_GAME;
            }
            boolean success = false;

            Order order = null;
            try {
                order = orderQueue.take();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

            outputView.displayMenu(order.getMenu().getName());
            outputView.printMessage(ASK_RECIPE);

            String checkRecipe = inputView.readLine();

            if (GameRule.AGREE_SHOW_RECIPE.equalsIgnoreCase(checkRecipe)) {
                outputView.showRecipe(RecipeConverter.toSteps(order.getMenu().getRecipeSteps()));
            }
            outputView.printMessage(SELECT_RECIPE_ORDER);
            for (int i = 0; i < order.getMenu().getRecipeSteps().size(); i++) {

                List<Recipes> shuffleRecipe = new ArrayList<Recipes>(order.getMenu().getRecipeSteps());
                Collections.shuffle(shuffleRecipe);

                outputView.showRecipe(RecipeConverter.toIndexSteps(shuffleRecipe));
                outputView.printMessage(CHOICE_STEP);

                String stepInput = inputView.readLine();

                int userChoice;
                try {
                    userChoice = Integer.parseInt(stepInput);
                } catch (NumberFormatException e) {
                    outputView.printMessage(WRONG_CHOICE_STEP);
                    player.loseMoney(order.getMenu().getPrice());
                    break;
                }

                if (userChoice >= order.getMenu().getRecipeSteps().size() || userChoice < 0) {
                    outputView.printMessage(WRONG_CHOICE_STEP);
                    player.loseMoney(order.getMenu().getPrice());
                    break;
                }

                if (!order.getMenu().checkSteps(shuffleRecipe.get(userChoice), i)) {
                    outputView.printMessage(FAILED_MAKING);
                    player.loseMoney(order.getMenu().getPrice());
                    break;
                } else {
                    outputView.printExecuteStep(order.getMenu().executeStep(i));

                }
                if (i == order.getMenu().getRecipeSteps().size() - 1) {
                    success = true;
                }
            }

            if (success) {
                outputView.printMessage(SUCCESS_MAKING);
                if(order.getOrderTime() < System.currentTimeMillis() - 1000 * 60 * 10){
                    outputView.printMessage(LATE_MAKING);
                    player.downRating();
                } else {
                    outputView.printMessage(UP_RATING);
                    player.upRating();
                }
                player.earnMoney(order.getMenu().getPrice());
            }

            outputView.checkResult(player.getMoney());
            if (GameRule.isFail(player.getMoney())) {
                outputView.printMessage(FAIL_GAME);
                return END_GAME;
            } else if (GameRule.isSuccess(player.getMoney())) {
                outputView.printMessage(SUCCESS_GAME);
                return END_GAME;
            }
        }
        return END_GAME;
    }

}
