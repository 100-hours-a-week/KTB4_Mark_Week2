package com.mark;


import com.mark.cafe.actions.Messages;
import com.mark.cafe.menus.MenuItem;
import com.mark.cafe.entity.Order;
import com.mark.cafe.entity.Player;
import com.mark.cafe.recipes.DessertMenus;
import com.mark.cafe.recipes.DrinkMenus;
import com.mark.cafe.service.OrderService;
import com.mark.cafe.producers.OrderProducer;
import com.mark.cafe.service.PlayService;
import com.mark.cafe.view.InputView;
import com.mark.cafe.view.OutputView;

import java.util.*;
import java.util.concurrent.*;

import static com.mark.cafe.messages.GameMessage.*;
import static com.mark.cafe.rule.GameRule.DEFAULT_RATING;
import static com.mark.cafe.rule.GameRule.MAX_ORDER_COUNT;

public class Main {
    public static void main(String[] args){

        Random random = new Random();
        Scanner sc = new Scanner(System.in);

        MenuItem[] menuItems = new MenuItem[]{
                new MenuItem(DrinkMenus.ICE_AMERICANO),
                new MenuItem(DrinkMenus.AMERICANO),
                new MenuItem(DrinkMenus.GREEN_TEA_LATTE),
                new MenuItem(DrinkMenus.ICE_GREEN_TEA_LATTE),
                new MenuItem(DessertMenus.CHOCO_CAKE),
                new MenuItem(DessertMenus.STRAWBERRY_CAKE)
        };

        OrderService orderService = new OrderService(menuItems);
        OutputView outputView = new OutputView();
        InputView inputView = new InputView(sc);

        BlockingQueue<Order> orderQueue = new ArrayBlockingQueue<>(MAX_ORDER_COUNT);

        int playerMoney = (random.nextInt(10) + 1) * 500;
        Player player = new Player(playerMoney, DEFAULT_RATING);

        Thread orderProducerThread = new Thread(new OrderProducer(orderService, orderQueue, outputView, player));
        orderProducerThread.start();
        Messages gameMessage = new PlayService(orderQueue, outputView, inputView, player).play();

        if(gameMessage == END_GAME){
            orderProducerThread.interrupt();
        }
        inputView.closeScanner();
    }
}